package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.world.gen.carver.ICarverConfig;

public class ProbabilityConfig implements ICarverConfig, IFeatureConfig {
   public final float probability;

   public ProbabilityConfig(float p_i48847_1_) {
      this.probability = p_i48847_1_;
   }

   public <T> Dynamic<T> serialize(DynamicOps<T> p_214634_1_) {
      return new Dynamic(p_214634_1_, p_214634_1_.createMap(ImmutableMap.of(p_214634_1_.createString("probability"), p_214634_1_.createFloat(this.probability))));
   }

   public static <T> ProbabilityConfig deserialize(Dynamic<T> p_214645_0_) {
      float lvt_1_1_ = p_214645_0_.get("probability").asFloat(0.0F);
      return new ProbabilityConfig(lvt_1_1_);
   }
}
