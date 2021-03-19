package net.snakefangox.rapidregister.registerhandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.RapidRegister;
import net.snakefangox.rapidregister.annotations.RegisterContents;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

public abstract class RegisterHandler<T> implements Comparable<RegisterHandler<?>> {

	protected static final String JSON = ".json";
	protected static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	protected static final TypeToken<Map<String, String>> LANG_MAP_TYPE = new TypeToken<Map<String, String>>() {
	};

	private final Class<T> type;
	private final String typeName;

	public RegisterHandler(Class<T> type) {
		this(type, type.getSimpleName());
	}

	public RegisterHandler(Class<T> type, String typeName) {
		this.type = type;
		this.typeName = typeName.toLowerCase(Locale.ROOT);
	}

	/**
	 * Tries to register the provided field, returns true if the field can be registered by this handler
	 */
	@SuppressWarnings("unchecked")
	public final boolean attemptRegister(RegisterContents classDefaults, Field field, String modid) {
		try {
			field.setAccessible(true);
			Object obj = field.get(null);
			if (type.isInstance(obj)) {
				T entry = (T) obj;
				Identifier identifier = new Identifier(modid, field.getName().toLowerCase(Locale.ROOT));
				register(entry, identifier, field, classDefaults);
				if (RapidRegister.runDataGen()) {
					dataGen(entry, identifier, field, RapidRegister.getAssetPath(modid), RapidRegister.getDataPath(modid), classDefaults);
				}
				return true;
			}
		} catch (IllegalAccessException e) {
			RapidRegister.LOGGER.warn("Could not register " + field.getName() + " due to " + e.getLocalizedMessage());
		} finally {
			field.setAccessible(false);
		}
		return false;
	}

	/**
	 * We want to try and register with more specific RegisterTypes first,
	 * I.E. we want to go ArmourItem then Item not the other way around.
	 */
	@Override
	public final int compareTo(@NotNull RegisterHandler<?> o) {
		boolean isSuper = type.isAssignableFrom(o.type);
		boolean isExtend = o.type.isAssignableFrom(type);
		if (isExtend) return -1;
		if (isSuper) return 1;
		return 0;
	}

	protected abstract void register(T obj, Identifier identifier, Field field, RegisterContents classDefaults);

	protected abstract void dataGen(T entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults);

	protected final boolean writeFile(Path dirPath, String fileName, String contents) {
		return writeFile(dirPath, fileName, contents, false);
	}

	/**
	 * Writes the given contents to the given file
	 *
	 * @param overwrite if the file will be overwritten if it already exists
	 * @return true if the file was written, false otherwise
	 */
	protected final boolean writeFile(Path dirPath, String fileName, String contents, boolean overwrite) {
		ensureDirExists(dirPath);
		File file = dirPath.resolve(fileName).toFile();
		try {
			boolean exists = file.exists();
			if (exists && !overwrite) return false;
			if (exists && !file.delete()) return false;
			if (!exists && !file.createNewFile()) return false;
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(contents);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			eatIOException(e);
			return false;
		}
		return true;
	}

	protected final boolean ensureDirExists(Path dirPath) {
		File dir = dirPath.toFile();
		boolean exists = dir.exists();
		if (!exists) exists = dir.mkdirs();
		if (!exists) {
			RapidRegister.LOGGER.warn("Path " + dirPath + " does not exist and could not be created");
			return false;
		}
		return true;
	}

	protected final File getOrCreateJsonFile(Path dir, String filename) {
		ensureDirExists(dir);
		File jsonFile = dir.resolve(filename).toFile();
		try {
			boolean exists = jsonFile.exists();
			FileWriter writer;
			if (!exists) {
				exists = jsonFile.createNewFile();
				if (exists) {
					writer = new FileWriter(jsonFile);
					writer.write("{\n}");
					writer.close();
				} else {
					RapidRegister.LOGGER.error("Could not get or create json file in: " + jsonFile.toString());
					return null;
				}
			}
		} catch (IOException e) {
			eatIOException(e);
			return null;
		}
		return jsonFile;
	}

	protected final String getModelPath() {
		return "models" + File.separator + typeName;
	}

	protected final String getTexturePath() {
		return "textures" + File.separator + typeName;
	}

	protected final String getJsonName(Identifier identifier) {
		return identifier.getPath() + JSON;
	}

	protected final void addLangKey(Identifier identifier) {
		addLangKey(identifier.getNamespace(), getTypeName(), identifier);
	}

	protected final void addLangKey(String modid, String type, Identifier identifier) {
		Path langDir = getLangPath(modid);
		File langFile = getOrCreateJsonFile(langDir, RapidRegister.getLang() + JSON);
		if (langFile == null) return;
		try {
			Map<String, String> langJson = GSON.fromJson(new FileReader(langFile), LANG_MAP_TYPE.getType());
			String langKey = getLangKey(type, identifier);
			if (!langJson.containsKey(langKey)) {
				langJson.put(langKey, getLangName(identifier));
				String newLang = GSON.toJson(langJson, LANG_MAP_TYPE.getType());
				FileWriter fileWriter = new FileWriter(langFile);
				fileWriter.write(newLang);
				fileWriter.close();
			}
		} catch (IOException e) {
			eatIOException(e);
		}
	}

	protected final Path getLangPath(String modid) {
		return Paths.get(RapidRegister.getAssetPath(modid).toString(), "lang");
	}

	protected final String getLangKey(String type, Identifier identifier) {
		return type + "." + identifier.getNamespace() + "." + identifier.getPath();
	}

	protected final String getLangName(Identifier identifier) {
		StringBuilder stringBuilder = new StringBuilder();
		String[] words = identifier.getPath().split("_");
		for (int i = 0; i < words.length; ++i) {
			String word = words[i];
			if (i > 0) stringBuilder.append(" ");
			stringBuilder.append(word.substring(0, 1).toUpperCase(Locale.ROOT));
			stringBuilder.append(word.substring(1));
		}
		return stringBuilder.toString();
	}

	protected final void eatIOException(IOException e) {
		RapidRegister.LOGGER.warn("Exception during dataGen: " + e.getLocalizedMessage());
	}

	protected final String getTemplateName() {
		return getTemplateName("");
	}

	protected final String getTemplateName(String prefix) {
		return (prefix.isEmpty() ? "" : prefix + "_") + typeName + ".json";
	}

	public Class<T> getType() {
		return type;
	}

	public String getTypeName() {
		return typeName;
	}
}
