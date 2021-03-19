package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;

public class SoundHandler extends RegisterHandler<SoundEvent> {

	private static final String SOUNDS = "sounds" + JSON;

	public SoundHandler() {
		super(SoundEvent.class);
	}

	@Override
	protected void register(SoundEvent obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		Registry.register(Registry.SOUND_EVENT, identifier, obj);
	}

	@Override
	protected void dataGen(SoundEvent entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
		File soundsFile = getOrCreateJsonFile(assetPath, SOUNDS);

	}
}
