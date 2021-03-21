package net.snakefangox.rapidregister.registerhandler.dynamic;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class BiomeHandler extends DynamicRegisterHandler<Biome> {

	public BiomeHandler() {
		super(Biome.class);
	}

	@Override
	protected void register(MinecraftServer server, DynamicRegistryManager manager, Storage storage) {
		addToDynRegistry(manager, Registry.BIOME_KEY, storage);
	}
}
