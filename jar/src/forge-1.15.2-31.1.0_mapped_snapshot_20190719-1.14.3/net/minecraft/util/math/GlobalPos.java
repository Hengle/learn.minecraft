package net.minecraft.util.math;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import java.util.Objects;
import net.minecraft.util.IDynamicSerializable;
import net.minecraft.world.dimension.DimensionType;

public final class GlobalPos implements IDynamicSerializable {
   private final DimensionType dimension;
   private final BlockPos pos;

   private GlobalPos(DimensionType p_i50796_1_, BlockPos p_i50796_2_) {
      this.dimension = p_i50796_1_;
      this.pos = p_i50796_2_;
   }

   public static GlobalPos of(DimensionType p_218179_0_, BlockPos p_218179_1_) {
      return new GlobalPos(p_218179_0_, p_218179_1_);
   }

   public static GlobalPos deserialize(Dynamic<?> p_218176_0_) {
      return (GlobalPos)p_218176_0_.get("dimension").map(DimensionType::func_218271_a).flatMap((p_218181_1_) -> {
         return p_218176_0_.get("pos").map(BlockPos::deserialize).map((p_218182_1_) -> {
            return new GlobalPos(p_218181_1_, p_218182_1_);
         });
      }).orElseThrow(() -> {
         return new IllegalArgumentException("Could not parse GlobalPos");
      });
   }

   public DimensionType getDimension() {
      return this.dimension;
   }

   public BlockPos getPos() {
      return this.pos;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         GlobalPos lvt_2_1_ = (GlobalPos)p_equals_1_;
         return Objects.equals(this.dimension, lvt_2_1_.dimension) && Objects.equals(this.pos, lvt_2_1_.pos);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.dimension, this.pos});
   }

   public <T> T serialize(DynamicOps<T> p_218175_1_) {
      return p_218175_1_.createMap(ImmutableMap.of(p_218175_1_.createString("dimension"), this.dimension.serialize(p_218175_1_), p_218175_1_.createString("pos"), this.pos.serialize(p_218175_1_)));
   }

   public String toString() {
      return this.dimension.toString() + " " + this.pos;
   }
}
