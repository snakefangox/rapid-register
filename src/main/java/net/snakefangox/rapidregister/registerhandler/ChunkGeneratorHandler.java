package net.snakefangox.rapidregister.registerhandler;

import java.lang.reflect.Field;
import java.nio.file.Path;

import net.snakefangox.rapidregister.annotations.RegisterContents;
import net.snakefangox.rapidregister.mixin.MixinChunkGenerator;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class ChunkGeneratorHandler extends RegisterHandler<ChunkGenerator> {

	public ChunkGeneratorHandler() {
		super(ChunkGenerator.class);
	}

	@Override
	protected void register(ChunkGenerator obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		Registry.register(Registry.CHUNK_GENERATOR, identifier, ((MixinChunkGenerator) obj).invokeGetCodec());
	}

	@Override
	protected void dataGen(ChunkGenerator entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
	}
}
