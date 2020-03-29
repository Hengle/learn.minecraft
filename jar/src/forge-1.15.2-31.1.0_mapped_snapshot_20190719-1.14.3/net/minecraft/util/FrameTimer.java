package net.minecraft.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FrameTimer {
   private final long[] frames = new long[240];
   private int lastIndex;
   private int counter;
   private int index;

   public void addFrame(long p_181747_1_) {
      this.frames[this.index] = p_181747_1_;
      ++this.index;
      if (this.index == 240) {
         this.index = 0;
      }

      if (this.counter < 240) {
         this.lastIndex = 0;
         ++this.counter;
      } else {
         this.lastIndex = this.parseIndex(this.index + 1);
      }

   }

   @OnlyIn(Dist.CLIENT)
   public int func_219792_a(long p_219792_1_, int p_219792_3_, int p_219792_4_) {
      double lvt_5_1_ = (double)p_219792_1_ / (double)(1000000000L / (long)p_219792_4_);
      return (int)(lvt_5_1_ * (double)p_219792_3_);
   }

   @OnlyIn(Dist.CLIENT)
   public int getLastIndex() {
      return this.lastIndex;
   }

   @OnlyIn(Dist.CLIENT)
   public int getIndex() {
      return this.index;
   }

   public int parseIndex(int p_181751_1_) {
      return p_181751_1_ % 240;
   }

   @OnlyIn(Dist.CLIENT)
   public long[] getFrames() {
      return this.frames;
   }
}