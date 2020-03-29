package net.minecraft.world;

import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.math.BlockPos;

public class WorldGenTickList<T> implements ITickList<T> {
   private final Function<BlockPos, ITickList<T>> tickListProvider;

   public WorldGenTickList(Function<BlockPos, ITickList<T>> p_i48981_1_) {
      this.tickListProvider = p_i48981_1_;
   }

   public boolean isTickScheduled(BlockPos p_205359_1_, T p_205359_2_) {
      return ((ITickList)this.tickListProvider.apply(p_205359_1_)).isTickScheduled(p_205359_1_, p_205359_2_);
   }

   public void scheduleTick(BlockPos p_205362_1_, T p_205362_2_, int p_205362_3_, TickPriority p_205362_4_) {
      ((ITickList)this.tickListProvider.apply(p_205362_1_)).scheduleTick(p_205362_1_, p_205362_2_, p_205362_3_, p_205362_4_);
   }

   public boolean isTickPending(BlockPos p_205361_1_, T p_205361_2_) {
      return false;
   }

   public void func_219497_a(Stream<NextTickListEntry<T>> p_219497_1_) {
      p_219497_1_.forEach((p_219507_1_) -> {
         ((ITickList)this.tickListProvider.apply(p_219507_1_.position)).func_219497_a(Stream.of(p_219507_1_));
      });
   }
}
