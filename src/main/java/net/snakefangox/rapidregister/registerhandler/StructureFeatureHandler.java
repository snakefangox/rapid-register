package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.StructureFeature;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.lang.reflect.Field;
import java.nio.file.Path;

@SuppressWarnings("rawtypes")
public class StructureFeatureHandler extends RegisterHandler<StructureFeature> {

	public StructureFeatureHandler() {
		super(StructureFeature.class);
	}

	@Override
	protected void register(StructureFeature obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		Registry.register(Registry.STRUCTURE_FEATURE, identifier, obj);
	}

	@Override
	protected void dataGen(StructureFeature entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
	}
}
