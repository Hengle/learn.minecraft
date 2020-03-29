package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public enum OceanLayer implements IAreaTransformer0 {
   INSTANCE;

   public int apply(INoiseRandom p_215735_1_, int p_215735_2_, int p_215735_3_) {
      ImprovedNoiseGenerator lvt_4_1_ = p_215735_1_.getNoiseGenerator();
      double lvt_5_1_ = lvt_4_1_.func_215456_a((double)p_215735_2_ / 8.0D, (double)p_215735_3_ / 8.0D, 0.0D, 0.0D, 0.0D);
      if (lvt_5_1_ > 0.4D) {
         return LayerUtil.WARM_OCEAN;
      } else if (lvt_5_1_ > 0.2D) {
         return LayerUtil.LUKEWARM_OCEAN;
      } else if (lvt_5_1_ < -0.4D) {
         return LayerUtil.FROZEN_OCEAN;
      } else {
         return lvt_5_1_ < -0.2D ? LayerUtil.COLD_OCEAN : LayerUtil.OCEAN;
      }
   }
}
