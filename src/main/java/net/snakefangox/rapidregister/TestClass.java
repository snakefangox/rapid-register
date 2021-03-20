package net.snakefangox.rapidregister;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.impl.screenhandler.ExtendedScreenHandlerType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterials;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.source.HorizontalVoronoiBiomeAccessType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.EndSpikeFeature;
import net.minecraft.world.gen.feature.EndSpikeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.snakefangox.rapidregister.annotations.BlockMeta;
import net.snakefangox.rapidregister.annotations.RegisterContents;

import java.util.OptionalLong;

@RegisterContents
public class TestClass {

	public static final Item TEST = new Item(new Item.Settings());
	public static final ToolItem TEST_TOOL = new ToolItem(ToolMaterials.DIAMOND, new Item.Settings());

	public static final Block TEST_BLOCK = new Block(FabricBlockSettings.of(Material.AMETHYST));
	@BlockMeta(registerItem = false)
	public static final Block SPECIAL_TEST_BLOCK = new Block(FabricBlockSettings.of(Material.BARRIER));

	public static final BlockEntityType<BadBE> SPECIAL_TEST_BLOCK_ENTITY = FabricBlockEntityTypeBuilder.create(BadBE::new, SPECIAL_TEST_BLOCK).build();

	public static final ExtendedScreenHandlerType<BadSH> SPECIAL_SCREEN_HANDLER = new ExtendedScreenHandlerType<>((syncId, inventory, buf) -> new BadSH(syncId));

	public static SoundEvent LOUD_BANG;

	public static final StatusEffect LO_JUMP = new BadSE();

	public static final Biome LOW_LANDS = new Biome.Builder().precipitation(Biome.Precipitation.RAIN).category(Biome.Category.FOREST).depth(2)
			.effects(new BiomeEffects.Builder().fogColor(0xffffff).waterColor(0xffffff).skyColor(0xffffff).fogColor(0xffffff).waterFogColor(0xffffff).build()).scale(1)
			.temperature(0.5F).downfall(0.3F).spawnSettings(new SpawnSettings.Builder().build()).generationSettings(GenerationSettings.INSTANCE).build();

	public static final Feature<?> LIVING_DUNES = new EndSpikeFeature(EndSpikeFeatureConfig.CODEC);

	public static final DimensionType ROILING_PITS = DimensionType.create(OptionalLong.empty(), true, false, true, true, 1,
			false, true, true, true, false, 0, 64, 64, HorizontalVoronoiBiomeAccessType.INSTANCE,
			BlockTags.INFINIBURN_OVERWORLD.getId(), new Identifier("the_nether"), 0.0F);

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

	public static class BadSE extends StatusEffect {
		protected BadSE() {
			super(StatusEffectType.HARMFUL, 0xffee00);
		}
	}
}
