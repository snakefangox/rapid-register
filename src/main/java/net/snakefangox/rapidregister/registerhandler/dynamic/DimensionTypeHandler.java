package net.snakefangox.rapidregister.registerhandler.dynamic;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.dimension.DimensionType;

public class DimensionTypeHandler extends DynamicRegisterHandler<DimensionType> {

	public DimensionTypeHandler() {
		super(DimensionType.class);
	}

	@Override
	protected void register(MinecraftServer server, DynamicRegistryManager manager, Storage storage) {
		addToDynRegistry(manager, RegistryKeys.DIMENSION_TYPE, storage);
	}
}
