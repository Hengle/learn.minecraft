package net.minecraft.world.gen.layer;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum SmoothLayer implements ICastleTransformer {
   INSTANCE;

   public int apply(INoiseRandom p_202748_1_, int p_202748_2_, int p_202748_3_, int p_202748_4_, int p_202748_5_, int p_202748_6_) {
      boolean lvt_7_1_ = p_202748_3_ == p_202748_5_;
      boolean lvt_8_1_ = p_202748_2_ == p_202748_4_;
      if (lvt_7_1_ == lvt_8_1_) {
         if (lvt_7_1_) {
            return p_202748_1_.random(2) == 0 ? p_202748_5_ : p_202748_2_;
         } else {
            return p_202748_6_;
         }
      } else {
         return lvt_7_1_ ? p_202748_5_ : p_202748_2_;
      }
   }
}
