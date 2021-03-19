package net.snakefangox.rapidregister;

import net.snakefangox.rapidregister.annotations.RegisterContents;

import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;

@RegisterContents
public class TestClass {
	public static final Item TEST = new Item(new Item.Settings());
	public static final ToolItem TEST_TOOL = new ToolItem(ToolMaterials.DIAMOND, new Item.Settings());
}
