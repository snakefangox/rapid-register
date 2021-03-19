package net.snakefangox.rapidregister.registerhandler;

import java.lang.reflect.Field;
import java.nio.file.Path;

import net.snakefangox.rapidregister.storage.TemplateHandler;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ItemHandler<T extends Item> extends RegisterHandler<T> {

	public ItemHandler(Class<T> type) {
		super(type, "item");
	}

	@Override
	protected void register(T obj, Identifier identifier, Field field) {
		Registry.register(Registry.ITEM, identifier, obj);
	}

	@Override
	protected void dataGen(T entry, Identifier identifier, Field field, Path assetPath, Path dataPath) {
		writeFile(assetPath.resolve(getModelPath()), getJsonName(identifier), TemplateHandler.getProcessedTemplate(getTemplateName(), identifier));
		ensureDirExists(assetPath.resolve(getTexturePath()));
	}
}
