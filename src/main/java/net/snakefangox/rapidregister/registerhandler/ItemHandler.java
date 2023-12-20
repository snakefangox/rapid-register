package net.snakefangox.rapidregister.registerhandler;

import java.lang.reflect.Field;
import java.nio.file.Path;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.snakefangox.rapidregister.annotations.ItemMeta;
import net.snakefangox.rapidregister.annotations.RegisterContents;
import net.snakefangox.rapidregister.storage.TemplateHandler;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ItemHandler<T extends Item> extends RegisterHandler<T> {

	public ItemHandler(Class<T> type) {
		super(type, "item");
	}

	@Override
	protected void register(T obj, Identifier identifier, Field field, RegisterContents classDefaults) {
		ItemMeta meta = getOrDefault(field, classDefaults);
		Registry.register(Registries.ITEM, identifier, obj);
		addItemToGroup(obj, RegistryKey.of(RegistryKeys.ITEM_GROUP, new Identifier(meta.itemGroup())));
	}

	@Override
	protected void dataGen(T entry, Identifier identifier, Field field, Path assetPath, Path dataPath, RegisterContents classDefaults) {
		writeFile(assetPath.resolve(getModelPath()), getJsonName(identifier), TemplateHandler.getProcessedTemplate(getTemplateName(), identifier));
		addLangKey(identifier);
		ensureDirExists(assetPath.resolve(getTexturePath()));
	}
}
