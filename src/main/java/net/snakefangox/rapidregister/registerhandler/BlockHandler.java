package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.snakefangox.rapidregister.annotations.ItemMeta;
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
        ItemMeta meta = getOrDefault(field, classDefaults);
        Registry.register(Registries.BLOCK, identifier, obj);
        if (meta.registerItem()) {
            Item.Settings settings = new Item.Settings().maxCount(meta.maxCount()).rarity(Rarity.values()[meta.rarity()]);
            Item item = new BlockItem(obj, settings);
            addItemToGroup(item, RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(meta.itemGroup())));
            Registry.register(Registries.ITEM, identifier, item);
        }
    }

    @Override
    protected void dataGen(T entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
        ItemMeta meta = getOrDefault(field, classDefaults);
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
}
