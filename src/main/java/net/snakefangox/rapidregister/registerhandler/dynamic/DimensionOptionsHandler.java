package net.snakefangox.rapidregister.registerhandler.dynamic;

import com.mojang.serialization.Lifecycle;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionOptions;

public class DimensionOptionsHandler extends DynamicRegisterHandler<DimensionOptions> {

	public DimensionOptionsHandler() {
		super(DimensionOptions.class);
	}

	@Override
	protected void register(MinecraftServer server, DynamicRegistryManager manager, Storage storage) {
		server.getSaveProperties().getGeneratorOptions().getDimensions()
				.add(storage.getRegistryKey(Registry.DIMENSION_OPTIONS), storage.obj, Lifecycle.stable());
	}
}
