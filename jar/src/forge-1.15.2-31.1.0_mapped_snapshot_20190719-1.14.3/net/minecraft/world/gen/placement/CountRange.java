package net.minecraft.world.gen.placement;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class CountRange extends SimplePlacement<CountRangeConfig> {
   public CountRange(Function<Dynamic<?>, ? extends CountRangeConfig> p_i51357_1_) {
      super(p_i51357_1_);
   }

   public Stream<BlockPos> getPositions(Random p_212852_1_, CountRangeConfig p_212852_2_, BlockPos p_212852_3_) {
      return IntStream.range(0, p_212852_2_.count).mapToObj((p_227453_3_) -> {
         int lvt_4_1_ = p_212852_1_.nextInt(16) + p_212852_3_.getX();
         int lvt_5_1_ = p_212852_1_.nextInt(16) + p_212852_3_.getZ();
         int lvt_6_1_ = p_212852_1_.nextInt(p_212852_2_.maximum - p_212852_2_.topOffset) + p_212852_2_.bottomOffset;
         return new BlockPos(lvt_4_1_, lvt_6_1_, lvt_5_1_);
      });
   }
}
