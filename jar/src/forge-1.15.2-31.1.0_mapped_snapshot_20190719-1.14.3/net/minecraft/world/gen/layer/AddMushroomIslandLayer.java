package net.minecraft.world.gen.layer;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IBishopTransformer;

public enum AddMushroomIslandLayer implements IBishopTransformer {
   INSTANCE;

   private static final int MUSHROOM_FIELDS = Registry.BIOME.getId(Biomes.MUSHROOM_FIELDS);

   public int apply(INoiseRandom p_202792_1_, int p_202792_2_, int p_202792_3_, int p_202792_4_, int p_202792_5_, int p_202792_6_) {
      return LayerUtil.isShallowOcean(p_202792_6_) && LayerUtil.isShallowOcean(p_202792_5_) && LayerUtil.isShallowOcean(p_202792_2_) && LayerUtil.isShallowOcean(p_202792_4_) && LayerUtil.isShallowOcean(p_202792_3_) && p_202792_1_.random(100) == 0 ? MUSHROOM_FIELDS : p_202792_6_;
   }
}
