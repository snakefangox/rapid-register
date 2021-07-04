package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.lang.reflect.Field;
import java.nio.file.Path;

@SuppressWarnings("rawtypes")
public class ConfiguredStructureFeatureHandler extends RegisterHandler<ConfiguredStructureFeature> {

	public ConfiguredStructureFeatureHandler() {
		super(ConfiguredStructureFeature.class);
	}

	@Override
	protected void register(ConfiguredStructureFeature obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, identifier, obj);
	}

	@Override
	protected void dataGen(ConfiguredStructureFeature entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
	}
}
