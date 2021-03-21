package net.snakefangox.rapidregister.mixin;

import com.mojang.serialization.Codec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import net.minecraft.world.gen.chunk.ChunkGenerator;

@Mixin(ChunkGenerator.class)
public interface MixinChunkGenerator {
	@Invoker
	Codec<? extends ChunkGenerator> invokeGetCodec();
}
