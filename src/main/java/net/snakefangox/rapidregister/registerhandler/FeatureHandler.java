package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.lang.reflect.Field;
import java.nio.file.Path;

@SuppressWarnings("rawtypes")
public class FeatureHandler extends RegisterHandler<Feature> {

	public FeatureHandler() {
		super(Feature.class);
	}

	@Override
	protected void register(Feature obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		Registry.register(Registry.FEATURE, identifier, obj);
	}

	@Override
	protected void dataGen(Feature entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
	}
}
