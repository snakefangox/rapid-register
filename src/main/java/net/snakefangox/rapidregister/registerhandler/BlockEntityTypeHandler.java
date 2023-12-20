package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.lang.reflect.Field;
import java.nio.file.Path;

@SuppressWarnings("rawtypes")
public class BlockEntityTypeHandler extends RegisterHandler<BlockEntityType> {

	public BlockEntityTypeHandler() {
		super(BlockEntityType.class);
	}

	@Override
	protected void register(BlockEntityType obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		Registry.register(Registries.BLOCK_ENTITY_TYPE, identifier, obj);
	}

	@Override
	protected void dataGen(BlockEntityType entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
	}
}
