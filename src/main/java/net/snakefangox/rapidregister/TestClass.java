package net.snakefangox.rapidregister;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.math.BlockPos;
import net.snakefangox.rapidregister.annotations.BlockMeta;
import net.snakefangox.rapidregister.annotations.RegisterContents;
import org.jetbrains.annotations.Nullable;

@RegisterContents
public class TestClass {

	public static final Item TEST = new Item(new Item.Settings());
	public static final ToolItem TEST_TOOL = new ToolItem(ToolMaterials.DIAMOND, new Item.Settings());

	public static final Block TEST_BLOCK = new Block(FabricBlockSettings.of(Material.AMETHYST));
	@BlockMeta(registerItem = false)
	public static final Block SPECIAL_TEST_BLOCK = new Block(FabricBlockSettings.of(Material.BARRIER));

	public static final BlockEntityType<BadBE> SPECIAL_TEST_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(BadBE::new, SPECIAL_TEST_BLOCK).build();

	public static final ExtendedScreenHandlerType<BadSH> SPECIAL_SCREEN_HANDLER = new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new BadSH(syncId));


	public static class BadBE extends BlockEntity {
		public BadBE(BlockPos pos, BlockState state) {
			super(TestClass.SPECIAL_TEST_BLOCK_ENTITY, pos, state);
		}
	}

	public static class BadSH extends ScreenHandler {

		protected BadSH(int syncId) {
			super(SPECIAL_SCREEN_HANDLER, syncId);
		}

		@Override
		public boolean canUse(PlayerEntity player) {
			return false;
		}
	}
}
