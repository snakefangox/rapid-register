package net.snakefangox.rapidregister.registerhandler;

import com.google.gson.reflect.TypeToken;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.RapidRegister;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;

public class SoundHandler extends RegisterHandler<SoundEvent> {

	private static final String SOUNDS = "sounds" + JSON;
	private static final String SUBTITLES = "subtitles";
	protected static final TypeToken<Map<String, SoundData>> SOUNDS_MAP_TYPE = new TypeToken<Map<String, SoundData>>() {
	};

	public SoundHandler() {
		super(SoundEvent.class);
	}

	@Override
	protected void register(SoundEvent obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		if (obj == null) {
			obj = SoundEvent.of(identifier);
			try {
				field.set(null, obj);
			} catch (IllegalAccessException e) {
				RapidRegister.LOGGER.error("Could not fill " + field.getName() + ". Check it isn't final or just pre-assign the sound event");
			}
		}
		Registry.register(Registries.SOUND_EVENT, identifier, obj);
	}

	@Override
	protected void dataGen(SoundEvent entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
		File soundsFile = getOrCreateJsonFile(assetPath, SOUNDS);
		if (soundsFile == null) return;
		try (Reader reader = new FileReader(soundsFile)) {
			Map<String, SoundData> sounds = GSON.fromJson(reader, SOUNDS_MAP_TYPE.getType());
			if (!sounds.containsKey(identifier.getPath())) {
				sounds.put(identifier.getPath(), new SoundData(getLangKey(SUBTITLES, identifier), identifier.toString(), false));
				String newSounds = GSON.toJson(sounds, SOUNDS_MAP_TYPE.getType());
				FileWriter fileWriter = new FileWriter(soundsFile);
				fileWriter.write(newSounds);
				fileWriter.close();
			}
		} catch (IOException e) {
			eatIOException(e);
		}
		addLangKey(identifier.getNamespace(), SUBTITLES, identifier);
		ensureDirExists(assetPath.resolve("sounds"));
	}

	private static class SoundData {
		final String subtitle;
		final String[] sounds;
		final boolean streams;

		public SoundData(String subtitle, String sound, boolean streams) {
			this.subtitle = subtitle;
			this.sounds = new String[] {sound};
			this.streams = streams;
		}

		public SoundData(String subtitle, String[] sounds, boolean streams) {
			this.subtitle = subtitle;
			this.sounds = sounds;
			this.streams = streams;
		}

		@Override
		public String toString() {
			return "SoundData{" +
					"subtitle='" + subtitle + '\'' +
					", sounds=" + Arrays.toString(sounds) +
					", streams=" + streams +
					'}';
		}
	}
}
