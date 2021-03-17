package net.snakefangox.rapidregister.registerhandler;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.lang.annotation.Annotation;

public class ItemHandler extends RegisterHandler<Item> {

	public ItemHandler() {
		super(Item.class);
	}

	@Override
	protected void register(Item obj, Identifier identifier, Annotation[] annotations) {
		Registry.register(Registry.ITEM, identifier, obj);
	}

	@Override
	protected void dataGen(Item entry, Identifier identifier, Annotation[] annotations, String assetPath, String dataPath) {

	}
}
