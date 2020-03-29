package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class ChanceRange extends SimplePlacement<ChanceRangeConfig> {
   public ChanceRange(Function<Dynamic<?>, ? extends ChanceRangeConfig> p_i51358_1_) {
      super(p_i51358_1_);
   }

   public Stream<BlockPos> getPositions(Random p_212852_1_, ChanceRangeConfig p_212852_2_, BlockPos p_212852_3_) {
      if (p_212852_1_.nextFloat() < p_212852_2_.chance) {
         int lvt_4_1_ = p_212852_1_.nextInt(16) + p_212852_3_.getX();
         int lvt_5_1_ = p_212852_1_.nextInt(16) + p_212852_3_.getZ();
         int lvt_6_1_ = p_212852_1_.nextInt(p_212852_2_.top - p_212852_2_.topOffset) + p_212852_2_.bottomOffset;
         return Stream.of(new BlockPos(lvt_4_1_, lvt_6_1_, lvt_5_1_));
      } else {
         return Stream.empty();
      }
   }
}