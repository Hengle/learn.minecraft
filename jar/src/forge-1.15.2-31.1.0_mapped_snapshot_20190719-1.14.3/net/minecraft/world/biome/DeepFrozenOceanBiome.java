package net.minecraft.world.biome;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.PerlinNoiseGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.MineshaftConfig;
import net.minecraft.world.gen.feature.structure.MineshaftStructure;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class DeepFrozenOceanBiome extends Biome {
   protected static final PerlinNoiseGenerator field_206856_bb = new PerlinNoiseGenerator(new SharedSeedRandom(3456L), 2, 0);

   public DeepFrozenOceanBiome() {
      super((new Biome.Builder()).surfaceBuilder(SurfaceBuilder.FROZEN_OCEAN, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG).precipitation(Biome.RainType.RAIN).category(Biome.Category.OCEAN).depth(-1.8F).scale(0.1F).temperature(0.5F).downfall(0.5F).waterColor(3750089).waterFogColor(329011).parent((String)null));
      this.func_226711_a_(Feature.OCEAN_RUIN.func_225566_b_(new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F)));
      this.func_226711_a_(Feature.OCEAN_MONUMENT.func_225566_b_(IFeatureConfig.NO_FEATURE_CONFIG));
      this.func_226711_a_(Feature.MINESHAFT.func_225566_b_(new MineshaftConfig(0.004D, MineshaftStructure.Type.NORMAL)));
      this.func_226711_a_(Feature.SHIPWRECK.func_225566_b_(new ShipwreckConfig(false)));
      DefaultBiomeFeatures.addOceanCarvers(this);
      DefaultBiomeFeatures.addStructures(this);
      DefaultBiomeFeatures.addLakes(this);
      DefaultBiomeFeatures.addIcebergs(this);
      DefaultBiomeFeatures.addMonsterRooms(this);
      DefaultBiomeFeatures.addBlueIce(this);
      DefaultBiomeFeatures.addStoneVariants(this);
      DefaultBiomeFeatures.addOres(this);
      DefaultBiomeFeatures.addSedimentDisks(this);
      DefaultBiomeFeatures.func_222296_u(this);
      DefaultBiomeFeatures.addDefaultFlowers(this);
      DefaultBiomeFeatures.func_222348_W(this);
      DefaultBiomeFeatures.addMushrooms(this);
      DefaultBiomeFeatures.addReedsAndPumpkins(this);
      DefaultBiomeFeatures.addSprings(this);
      DefaultBiomeFeatures.addFreezeTopLayer(this);
      this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 1, 1, 4));
      this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SALMON, 15, 1, 5));
      this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.POLAR_BEAR, 1, 1, 2));
      this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 1));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
      this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
   }

   public float getTemperature(BlockPos p_180626_1_) {
      float lvt_2_1_ = this.getDefaultTemperature();
      double lvt_3_1_ = field_206856_bb.func_215464_a((double)p_180626_1_.getX() * 0.05D, (double)p_180626_1_.getZ() * 0.05D, false) * 7.0D;
      double lvt_5_1_ = INFO_NOISE.func_215464_a((double)p_180626_1_.getX() * 0.2D, (double)p_180626_1_.getZ() * 0.2D, false);
      double lvt_7_1_ = lvt_3_1_ + lvt_5_1_;
      if (lvt_7_1_ < 0.3D) {
         double lvt_9_1_ = INFO_NOISE.func_215464_a((double)p_180626_1_.getX() * 0.09D, (double)p_180626_1_.getZ() * 0.09D, false);
         if (lvt_9_1_ < 0.8D) {
            lvt_2_1_ = 0.2F;
         }
      }

      if (p_180626_1_.getY() > 64) {
         float lvt_9_2_ = (float)(TEMPERATURE_NOISE.func_215464_a((double)((float)p_180626_1_.getX() / 8.0F), (double)((float)p_180626_1_.getZ() / 8.0F), false) * 4.0D);
         return lvt_2_1_ - (lvt_9_2_ + (float)p_180626_1_.getY() - 64.0F) * 0.05F / 30.0F;
      } else {
         return lvt_2_1_;
      }
   }
}
