package net.minecraft.world.gen.feature.structure;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.PaneBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.jigsaw.EmptyJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.FeatureJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.template.AlwaysTrueRuleTest;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.BlockStateMatchRuleTest;
import net.minecraft.world.gen.feature.template.RandomBlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleEntry;
import net.minecraft.world.gen.feature.template.RuleStructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

public class TaigaVillagePools {
   public static void init() {
   }

   static {
      ImmutableList<StructureProcessor> lvt_0_1_ = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.8F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.getDefaultState()), new RuleEntry(new TagMatchRuleTest(BlockTags.DOORS), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()), new RuleEntry(new BlockMatchRuleTest(Blocks.TORCH), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()), new RuleEntry(new BlockMatchRuleTest(Blocks.WALL_TORCH), AlwaysTrueRuleTest.INSTANCE, Blocks.AIR.getDefaultState()), new RuleEntry(new BlockMatchRuleTest(Blocks.CAMPFIRE), AlwaysTrueRuleTest.INSTANCE, (BlockState)Blocks.CAMPFIRE.getDefaultState().with(CampfireBlock.LIT, false)), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.08F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.SPRUCE_LOG, 0.08F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GLASS_PANE, 0.5F), AlwaysTrueRuleTest.INSTANCE, Blocks.COBWEB.getDefaultState()), new RuleEntry(new BlockStateMatchRuleTest((BlockState)((BlockState)Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true)), AlwaysTrueRuleTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.NORTH, true)).with(PaneBlock.SOUTH, true)), new RuleEntry(new BlockStateMatchRuleTest((BlockState)((BlockState)Blocks.GLASS_PANE.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true)), AlwaysTrueRuleTest.INSTANCE, (BlockState)((BlockState)Blocks.BROWN_STAINED_GLASS_PANE.getDefaultState().with(PaneBlock.EAST, true)).with(PaneBlock.WEST, true)), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.PUMPKIN_STEM.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.2F), AlwaysTrueRuleTest.INSTANCE, Blocks.POTATOES.getDefaultState()), new RuleEntry[0])));
      ImmutableList<StructureProcessor> lvt_1_1_ = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.COBBLESTONE, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.MOSSY_COBBLESTONE.getDefaultState()))));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/town_centers"), new ResourceLocation("empty"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/taiga/town_centers/taiga_meeting_point_1", lvt_1_1_), 49), new Pair(new SingleJigsawPiece("village/taiga/town_centers/taiga_meeting_point_2", lvt_1_1_), 49), new Pair(new SingleJigsawPiece("village/taiga/zombie/town_centers/taiga_meeting_point_1", lvt_0_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/zombie/town_centers/taiga_meeting_point_2", lvt_0_1_), 1)), JigsawPattern.PlacementBehaviour.RIGID));
      ImmutableList<StructureProcessor> lvt_2_1_ = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_PATH), new BlockMatchRuleTest(Blocks.WATER), Blocks.SPRUCE_PLANKS.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.GRASS_PATH, 0.2F), AlwaysTrueRuleTest.INSTANCE, Blocks.GRASS_BLOCK.getDefaultState()), new RuleEntry(new BlockMatchRuleTest(Blocks.GRASS_BLOCK), new BlockMatchRuleTest(Blocks.WATER), Blocks.WATER.getDefaultState()), new RuleEntry(new BlockMatchRuleTest(Blocks.DIRT), new BlockMatchRuleTest(Blocks.WATER), Blocks.WATER.getDefaultState()))));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/streets"), new ResourceLocation("village/taiga/terminators"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/taiga/streets/corner_01", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/streets/corner_02", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/streets/corner_03", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/streets/straight_01", lvt_2_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/streets/straight_02", lvt_2_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/streets/straight_03", lvt_2_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/streets/straight_04", lvt_2_1_), 7), new Pair(new SingleJigsawPiece("village/taiga/streets/straight_05", lvt_2_1_), 7), new Pair(new SingleJigsawPiece("village/taiga/streets/straight_06", lvt_2_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/streets/crossroad_01", lvt_2_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/streets/crossroad_02", lvt_2_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/streets/crossroad_03", lvt_2_1_), 2), new Pair[]{new Pair(new SingleJigsawPiece("village/taiga/streets/crossroad_04", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/streets/crossroad_05", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/streets/crossroad_06", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/streets/turn_01", lvt_2_1_), 3)}), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/zombie/streets"), new ResourceLocation("village/taiga/terminators"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/corner_01", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/corner_02", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/corner_03", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/straight_01", lvt_2_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/straight_02", lvt_2_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/straight_03", lvt_2_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/straight_04", lvt_2_1_), 7), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/straight_05", lvt_2_1_), 7), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/straight_06", lvt_2_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/crossroad_01", lvt_2_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/crossroad_02", lvt_2_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/crossroad_03", lvt_2_1_), 2), new Pair[]{new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/crossroad_04", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/crossroad_05", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/crossroad_06", lvt_2_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/streets/turn_01", lvt_2_1_), 3)}), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
      ImmutableList<StructureProcessor> lvt_3_1_ = ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.3F), AlwaysTrueRuleTest.INSTANCE, Blocks.PUMPKIN_STEM.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.WHEAT, 0.2F), AlwaysTrueRuleTest.INSTANCE, Blocks.POTATOES.getDefaultState()))));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/houses"), new ResourceLocation("village/taiga/terminators"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_small_house_1", lvt_1_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_small_house_2", lvt_1_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_small_house_3", lvt_1_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_small_house_4", lvt_1_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_small_house_5", lvt_1_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_medium_house_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_medium_house_2", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_medium_house_3", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_medium_house_4", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_butcher_shop_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_tool_smith_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_fletcher_house_1", lvt_1_1_), 2), new Pair[]{new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_shepherds_house_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_armorer_house_1", lvt_1_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_armorer_2", lvt_1_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_fisher_cottage_1", lvt_1_1_), 3), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_tannery_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_cartographer_house_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_library_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_masons_house_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_weaponsmith_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_weaponsmith_2", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_temple_1", lvt_1_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_large_farm_1", lvt_3_1_), 6), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_large_farm_2", lvt_3_1_), 6), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_small_farm_1", lvt_1_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_animal_pen_1", lvt_1_1_), 2), Pair.of(EmptyJigsawPiece.INSTANCE, 6)}), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/zombie/houses"), new ResourceLocation("village/taiga/terminators"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_small_house_1", lvt_0_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_small_house_2", lvt_0_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_small_house_3", lvt_0_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_small_house_4", lvt_0_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_small_house_5", lvt_0_1_), 4), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_medium_house_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_medium_house_2", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_medium_house_3", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_medium_house_4", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_butcher_shop_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_tool_smith_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_fletcher_house_1", lvt_0_1_), 2), new Pair[]{new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_shepherds_house_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_armorer_house_1", lvt_0_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_fisher_cottage_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_tannery_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_cartographer_house_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_library_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_masons_house_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_weaponsmith_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_weaponsmith_2", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_temple_1", lvt_0_1_), 2), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_large_farm_1", lvt_0_1_), 6), new Pair(new SingleJigsawPiece("village/taiga/zombie/houses/taiga_large_farm_2", lvt_0_1_), 6), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_small_farm_1", lvt_0_1_), 1), new Pair(new SingleJigsawPiece("village/taiga/houses/taiga_animal_pen_1", lvt_0_1_), 2), Pair.of(EmptyJigsawPiece.INSTANCE, 6)}), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/terminators"), new ResourceLocation("empty"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/plains/terminators/terminator_01", lvt_2_1_), 1), new Pair(new SingleJigsawPiece("village/plains/terminators/terminator_02", lvt_2_1_), 1), new Pair(new SingleJigsawPiece("village/plains/terminators/terminator_03", lvt_2_1_), 1), new Pair(new SingleJigsawPiece("village/plains/terminators/terminator_04", lvt_2_1_), 1)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/decor"), new ResourceLocation("empty"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/taiga/taiga_lamp_post_1"), 10), new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_1"), 4), new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_2"), 1), new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_3"), 1), new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_4"), 1), new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_5"), 2), new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_6"), 1), new Pair(new FeatureJigsawPiece(Feature.NORMAL_TREE.func_225566_b_(DefaultBiomeFeatures.field_226810_e_)), 4), new Pair(new FeatureJigsawPiece(Feature.NORMAL_TREE.func_225566_b_(DefaultBiomeFeatures.field_226809_d_)), 4), new Pair(new FeatureJigsawPiece(Feature.field_227244_A_.func_225566_b_(DefaultBiomeFeatures.field_226734_V_)), 2), new Pair(new FeatureJigsawPiece(Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226827_v_)), 4), new Pair(new FeatureJigsawPiece(Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226718_F_)), 1), new Pair[]{Pair.of(EmptyJigsawPiece.INSTANCE, 4)}), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/zombie/decor"), new ResourceLocation("empty"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_1"), 4), new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_2"), 1), new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_3"), 1), new Pair(new SingleJigsawPiece("village/taiga/taiga_decoration_4"), 1), new Pair(new FeatureJigsawPiece(Feature.NORMAL_TREE.func_225566_b_(DefaultBiomeFeatures.field_226810_e_)), 4), new Pair(new FeatureJigsawPiece(Feature.NORMAL_TREE.func_225566_b_(DefaultBiomeFeatures.field_226809_d_)), 4), new Pair(new FeatureJigsawPiece(Feature.field_227244_A_.func_225566_b_(DefaultBiomeFeatures.field_226734_V_)), 2), new Pair(new FeatureJigsawPiece(Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226827_v_)), 4), new Pair(new FeatureJigsawPiece(Feature.field_227248_z_.func_225566_b_(DefaultBiomeFeatures.field_226718_F_)), 1), Pair.of(EmptyJigsawPiece.INSTANCE, 4)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/villagers"), new ResourceLocation("empty"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/taiga/villagers/nitwit"), 1), new Pair(new SingleJigsawPiece("village/taiga/villagers/baby"), 1), new Pair(new SingleJigsawPiece("village/taiga/villagers/unemployed"), 10)), JigsawPattern.PlacementBehaviour.RIGID));
      JigsawManager.field_214891_a.register(new JigsawPattern(new ResourceLocation("village/taiga/zombie/villagers"), new ResourceLocation("empty"), ImmutableList.of(new Pair(new SingleJigsawPiece("village/taiga/zombie/villagers/nitwit"), 1), new Pair(new SingleJigsawPiece("village/taiga/zombie/villagers/unemployed"), 10)), JigsawPattern.PlacementBehaviour.RIGID));
   }
}
