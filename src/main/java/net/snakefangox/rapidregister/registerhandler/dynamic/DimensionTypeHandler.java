package net.snakefangox.rapidregister.registerhandler.dynamic;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

public class DimensionTypeHandler extends DynamicRegisterHandler<DimensionType> {

	public DimensionTypeHandler() {
		super(DimensionType.class);
	}

	@Override
	protected void register(MinecraftServer server, DynamicRegistryManager manager, Storage storage) {
		addToDynRegistry(manager, Registry.DIMENSION_TYPE_KEY, storage);
	}
}
