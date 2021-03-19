package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.lang.reflect.Field;
import java.nio.file.Path;

@SuppressWarnings("rawtypes")
public class BlockEntityHandler extends RegisterHandler<BlockEntityType> {

	public BlockEntityHandler() {
		super(BlockEntityType.class);
	}

	@Override
	protected void register(BlockEntityType obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, identifier, obj);
	}

	@Override
	protected void dataGen(BlockEntityType entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
	}
}
