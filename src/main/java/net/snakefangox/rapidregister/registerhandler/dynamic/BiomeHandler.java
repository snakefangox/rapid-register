package net.snakefangox.rapidregister.registerhandler.dynamic;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.biome.Biome;

public class BiomeHandler extends DynamicRegisterHandler<Biome> {

	public BiomeHandler() {
		super(Biome.class);
	}

	@Override
	protected void register(MinecraftServer server, DynamicRegistryManager manager, Storage storage) {
		addToDynRegistry(manager, RegistryKeys.BIOME, storage);
	}
}
