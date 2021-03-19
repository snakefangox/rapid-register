package net.snakefangox.rapidregister;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.snakefangox.rapidregister.annotations.BlockMeta;
import net.snakefangox.rapidregister.annotations.RegisterContents;

@RegisterContents
public class TestClass {

	public static final Item TEST = new Item(new Item.Settings());
	public static final ToolItem TEST_TOOL = new ToolItem(ToolMaterials.DIAMOND, new Item.Settings());

	public static final Block TEST_BLOCK = new Block(FabricBlockSettings.of(Material.AMETHYST));
	@BlockMeta(registerItem = false)
	public static final Block SPECIAL_TEST_BLOCK = new Block(FabricBlockSettings.of(Material.BARRIER));
}
