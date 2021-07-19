package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.snakefangox.rapidregister.RapidRegister;
import net.snakefangox.rapidregister.annotations.BlockMeta;
import net.snakefangox.rapidregister.annotations.RegisterContents;
import net.snakefangox.rapidregister.storage.TemplateHandler;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Path;

public class BlockHandler<T extends Block> extends RegisterHandler<T> {

	private static final String BLOCKSTATES = "blockstates";
	private static final String ITEM = "item";
	private static final String ITEM_MODELS = "models" + File.separator + ITEM;
	private static final String LOOT_TABLES = "loot_tables" + File.separator + "blocks";

	public BlockHandler(Class<T> type) {
		super(type, "block");
	}

	@Override
	protected void register(T obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		BlockMeta meta = getOrDefault(field, classDefaults);
		Registry.register(Registry.BLOCK, identifier, obj);
		if (meta.registerItem()) {
			Item.Settings settings = new Item.Settings().maxCount(meta.maxCount()).rarity(Rarity.values()[meta.rarity()]);
			settings.group(getItemGroup(meta.blockItemGroup().replace(":", ".")));
			Registry.register(Registry.ITEM, identifier, new BlockItem(obj, settings));
		}
	}

	@Override
	protected void dataGen(T entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
		BlockMeta meta = getOrDefault(field, classDefaults);
		if (entry.getDefaultState().getOrEmpty(Properties.HORIZONTAL_FACING).isPresent()) {
			writeFile(assetPath.resolve(BLOCKSTATES), getJsonName(identifier), TemplateHandler.getProcessedTemplate(getTemplateName("rotate_state"), identifier));
		} else {
			writeFile(assetPath.resolve(BLOCKSTATES), getJsonName(identifier), TemplateHandler.getProcessedTemplate(getTemplateName("state"), identifier));
		}
		writeFile(assetPath.resolve(getModelPath()), getJsonName(identifier), TemplateHandler.getProcessedTemplate(getTemplateName(), identifier));
		writeFile(dataPath.resolve(LOOT_TABLES), getJsonName(identifier), TemplateHandler.getProcessedTemplate(getTemplateName("loot"), identifier));
		if (meta.registerItem())
			writeFile(assetPath.resolve(ITEM_MODELS), getJsonName(identifier), TemplateHandler.getProcessedTemplate(getTemplateName(ITEM), identifier));
		addLangKey(identifier);
		ensureDirExists(assetPath.resolve(getTexturePath()));
	}

	private BlockMeta getOrDefault(Field field, RegisterContents classDefaults) {
		BlockMeta meta = field.getAnnotation(BlockMeta.class);
		meta = meta == null ? classDefaults.defaultBlockMeta() : meta;
		return meta;
	}

	private ItemGroup getItemGroup(String blockItemGroup) {
		for (ItemGroup group : ItemGroup.GROUPS)
			if (group.getName().equals(blockItemGroup)) return group;
		RapidRegister.LOGGER.warn("Could not find " + blockItemGroup + " ItemGroup. Defaulting");
		return ItemGroup.BUILDING_BLOCKS;
	}
}
