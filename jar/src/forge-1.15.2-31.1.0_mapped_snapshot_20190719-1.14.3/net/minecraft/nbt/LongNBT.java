package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class LongNBT extends NumberNBT {
   public static final INBTType<LongNBT> field_229697_a_ = new INBTType<LongNBT>() {
      public LongNBT func_225649_b_(DataInput p_225649_1_, int p_225649_2_, NBTSizeTracker p_225649_3_) throws IOException {
         p_225649_3_.read(128L);
         return LongNBT.func_229698_a_(p_225649_1_.readLong());
      }

      public String func_225648_a_() {
         return "LONG";
      }

      public String func_225650_b_() {
         return "TAG_Long";
      }

      public boolean func_225651_c_() {
         return true;
      }

      // $FF: synthetic method
      public INBT func_225649_b_(DataInput p_225649_1_, int p_225649_2_, NBTSizeTracker p_225649_3_) throws IOException {
         return this.func_225649_b_(p_225649_1_, p_225649_2_, p_225649_3_);
      }
   };
   private final long data;

   private LongNBT(long p_i45134_1_) {
      this.data = p_i45134_1_;
   }

   public static LongNBT func_229698_a_(long p_229698_0_) {
      return p_229698_0_ >= -128L && p_229698_0_ <= 1024L ? LongNBT.Cache.field_229699_a_[(int)p_229698_0_ + 128] : new LongNBT(p_229698_0_);
   }

   public void write(DataOutput p_74734_1_) throws IOException {
      p_74734_1_.writeLong(this.data);
   }

   public byte getId() {
      return 4;
   }

   public INBTType<LongNBT> func_225647_b_() {
      return field_229697_a_;
   }

   public String toString() {
      return this.data + "L";
   }

   public LongNBT copy() {
      return this;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         return p_equals_1_ instanceof LongNBT && this.data == ((LongNBT)p_equals_1_).data;
      }
   }

   public int hashCode() {
      return (int)(this.data ^ this.data >>> 32);
   }

   public ITextComponent toFormattedComponent(String p_199850_1_, int p_199850_2_) {
      ITextComponent lvt_3_1_ = (new StringTextComponent("L")).applyTextStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
      return (new StringTextComponent(String.valueOf(this.data))).appendSibling(lvt_3_1_).applyTextStyle(SYNTAX_HIGHLIGHTING_NUMBER);
   }

   public long getLong() {
      return this.data;
   }

   public int getInt() {
      return (int)(this.data & -1L);
   }

   public short getShort() {
      return (short)((int)(this.data & 65535L));
   }

   public byte getByte() {
      return (byte)((int)(this.data & 255L));
   }

   public double getDouble() {
      return (double)this.data;
   }

   public float getFloat() {
      return (float)this.data;
   }

   public Number getAsNumber() {
      return this.data;
   }

   // $FF: synthetic method
   public INBT copy() {
      return this.copy();
   }

   // $FF: synthetic method
   LongNBT(long p_i226080_1_, Object p_i226080_3_) {
      this(p_i226080_1_);
   }

   static class Cache {
      static final LongNBT[] field_229699_a_ = new LongNBT[1153];

      static {
         for(int lvt_0_1_ = 0; lvt_0_1_ < field_229699_a_.length; ++lvt_0_1_) {
            field_229699_a_[lvt_0_1_] = new LongNBT((long)(-128 + lvt_0_1_));
         }

      }
   }
}
