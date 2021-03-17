package net.snakefangox.rapidregister;

import net.fabricmc.api.ModInitializer;
import net.snakefangox.rapidregister.annotations.RegisterContents;
import net.snakefangox.rapidregister.registerhandler.RegisterHandler;
import net.snakefangox.rapidregister.registerhandler.TypeRegisterSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RapidRegister implements ModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();

	private static final TypeRegisterSet typeRegister = new TypeRegisterSet();
	private static final Map<String, String> dataGen = new HashMap<>();

	@Override
	public void onInitialize() {

	}

	/**
	 * Registers all content declared in classes that are in subpackages from the given initializer.
	 * Useful if your {@link ModInitializer} is in your root source package for your mod.
	 */
	public static void register(String modid, ModInitializer modInitializer) {
		register(modid, modInitializer.getClass().getPackage().getName());
	}

	/**
	 * Registers all content declared in classes that are in subpackages from the given root package.
	 * Useful if your {@link ModInitializer} is in a subpackage or you want to be more specific about
	 * the location of your content classes.
	 */
	public static void register(String modid, String rootContentPackage) {
		Reflections reflections = new Reflections(rootContentPackage, new TypeAnnotationsScanner());
		Set<Class<?>> classes = reflections.getTypesAnnotatedWith(RegisterContents.class);
		classes.forEach(c -> registerClass(c, modid));
	}

	/**
	 * Registers all content declared in the given classes. Useful if you don't
	 * like the overhead that comes with package traversal.
	 */
	public static void register(String modid, Class<?>... classes) {
		Arrays.stream(classes).forEach(c -> registerClass(c, modid));
	}

	private static void registerClass(Class<?> clazz, String modid) {
		Arrays.stream(clazz.getFields()).filter(f -> Modifier.isStatic(f.getModifiers()))
				.forEach(f -> typeRegister.attemptRegister(clazz, f, modid));
	}

	public static void addRegisterHandler(RegisterHandler<?> registerHandler) {
		typeRegister.addHandler(registerHandler);
	}

	/**
	 * Requests DataGen for the provided modid
	 *
	 * @param resourcePath should point to the root of your resource dir
	 */
	public static void requestDataGen(String modid, String resourcePath) {
		if (dataGen.containsKey(modid)) {
			LOGGER.warn("Modid " + modid + " somehow already registered for DataGen");
			return;
		}
		if (!resourcePath.endsWith(File.separator)) resourcePath += File.separator;
		dataGen.put(modid, resourcePath);
	}

	public static boolean shouldRunDataGen(String modid) {
		return dataGen.containsKey(modid);
	}

	public static String getResourcePath(String modid) {
		return dataGen.get(modid);
	}

	public static String getAssetPath(String modid) {
		return getResourcePath(modid) + "assets" + File.separator;
	}

	public static String getDataPath(String modid) {
		return getResourcePath(modid) + "data" + File.separator;
	}

	static {

	}
}
