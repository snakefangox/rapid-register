package net.snakefangox.rapidregister.registerhandler.dynamic;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionOptions;

public class DimensionOptionsHandler extends DynamicRegisterHandler<DimensionOptions> {

	public DimensionOptionsHandler() {
		super(DimensionOptions.class);
	}

	@Override
	protected void register(MinecraftServer server, DynamicRegistryManager manager, Storage storage) {
		addToDynRegistry(manager, RegistryKeys.DIMENSION, storage);
	}
}
