package net.minecraft.util;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArraySet<T> extends AbstractSet<T> {
   private final Comparator<T> field_226169_a_;
   private T[] field_226170_b_;
   private int field_226171_c_;

   private SortedArraySet(int p_i225697_1_, Comparator<T> p_i225697_2_) {
      this.field_226169_a_ = p_i225697_2_;
      if (p_i225697_1_ < 0) {
         throw new IllegalArgumentException("Initial capacity (" + p_i225697_1_ + ") is negative");
      } else {
         this.field_226170_b_ = func_226177_a_(new Object[p_i225697_1_]);
      }
   }

   public static <T extends Comparable<T>> SortedArraySet<T> func_226172_a_(int p_226172_0_) {
      return new SortedArraySet(p_226172_0_, Comparator.naturalOrder());
   }

   private static <T> T[] func_226177_a_(Object[] p_226177_0_) {
      return (Object[])p_226177_0_;
   }

   private int func_226182_c_(T p_226182_1_) {
      return Arrays.binarySearch(this.field_226170_b_, 0, this.field_226171_c_, p_226182_1_, this.field_226169_a_);
   }

   private static int func_226179_b_(int p_226179_0_) {
      return -p_226179_0_ - 1;
   }

   public boolean add(T p_add_1_) {
      int lvt_2_1_ = this.func_226182_c_(p_add_1_);
      if (lvt_2_1_ >= 0) {
         return false;
      } else {
         int lvt_3_1_ = func_226179_b_(lvt_2_1_);
         this.func_226176_a_(p_add_1_, lvt_3_1_);
         return true;
      }
   }

   private void func_226181_c_(int p_226181_1_) {
      if (p_226181_1_ > this.field_226170_b_.length) {
         if (this.field_226170_b_ != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            p_226181_1_ = (int)Math.max(Math.min((long)this.field_226170_b_.length + (long)(this.field_226170_b_.length >> 1), 2147483639L), (long)p_226181_1_);
         } else if (p_226181_1_ < 10) {
            p_226181_1_ = 10;
         }

         Object[] lvt_2_1_ = new Object[p_226181_1_];
         System.arraycopy(this.field_226170_b_, 0, lvt_2_1_, 0, this.field_226171_c_);
         this.field_226170_b_ = func_226177_a_(lvt_2_1_);
      }
   }

   private void func_226176_a_(T p_226176_1_, int p_226176_2_) {
      this.func_226181_c_(this.field_226171_c_ + 1);
      if (p_226176_2_ != this.field_226171_c_) {
         System.arraycopy(this.field_226170_b_, p_226176_2_, this.field_226170_b_, p_226176_2_ + 1, this.field_226171_c_ - p_226176_2_);
      }

      this.field_226170_b_[p_226176_2_] = p_226176_1_;
      ++this.field_226171_c_;
   }

   private void func_226183_d_(int p_226183_1_) {
      --this.field_226171_c_;
      if (p_226183_1_ != this.field_226171_c_) {
         System.arraycopy(this.field_226170_b_, p_226183_1_ + 1, this.field_226170_b_, p_226183_1_, this.field_226171_c_ - p_226183_1_);
      }

      this.field_226170_b_[this.field_226171_c_] = null;
   }

   private T func_226184_e_(int p_226184_1_) {
      return this.field_226170_b_[p_226184_1_];
   }

   public T func_226175_a_(T p_226175_1_) {
      int lvt_2_1_ = this.func_226182_c_(p_226175_1_);
      if (lvt_2_1_ >= 0) {
         return this.func_226184_e_(lvt_2_1_);
      } else {
         this.func_226176_a_(p_226175_1_, func_226179_b_(lvt_2_1_));
         return p_226175_1_;
      }
   }

   public boolean remove(Object p_remove_1_) {
      int lvt_2_1_ = this.func_226182_c_(p_remove_1_);
      if (lvt_2_1_ >= 0) {
         this.func_226183_d_(lvt_2_1_);
         return true;
      } else {
         return false;
      }
   }

   public T func_226178_b_() {
      return this.func_226184_e_(0);
   }

   public boolean contains(Object p_contains_1_) {
      int lvt_2_1_ = this.func_226182_c_(p_contains_1_);
      return lvt_2_1_ >= 0;
   }

   public Iterator<T> iterator() {
      return new SortedArraySet.Itr();
   }

   public int size() {
      return this.field_226171_c_;
   }

   public Object[] toArray() {
      return (Object[])this.field_226170_b_.clone();
   }

   public <U> U[] toArray(U[] p_toArray_1_) {
      if (p_toArray_1_.length < this.field_226171_c_) {
         return (Object[])Arrays.copyOf(this.field_226170_b_, this.field_226171_c_, p_toArray_1_.getClass());
      } else {
         System.arraycopy(this.field_226170_b_, 0, p_toArray_1_, 0, this.field_226171_c_);
         if (p_toArray_1_.length > this.field_226171_c_) {
            p_toArray_1_[this.field_226171_c_] = null;
         }

         return p_toArray_1_;
      }
   }

   public void clear() {
      Arrays.fill(this.field_226170_b_, 0, this.field_226171_c_, (Object)null);
      this.field_226171_c_ = 0;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         if (p_equals_1_ instanceof SortedArraySet) {
            SortedArraySet<?> lvt_2_1_ = (SortedArraySet)p_equals_1_;
            if (this.field_226169_a_.equals(lvt_2_1_.field_226169_a_)) {
               return this.field_226171_c_ == lvt_2_1_.field_226171_c_ && Arrays.equals(this.field_226170_b_, lvt_2_1_.field_226170_b_);
            }
         }

         return super.equals(p_equals_1_);
      }
   }

   class Itr implements Iterator<T> {
      private int field_226186_b_;
      private int field_226187_c_;

      private Itr() {
         this.field_226187_c_ = -1;
      }

      public boolean hasNext() {
         return this.field_226186_b_ < SortedArraySet.this.field_226171_c_;
      }

      public T next() {
         if (this.field_226186_b_ >= SortedArraySet.this.field_226171_c_) {
            throw new NoSuchElementException();
         } else {
            this.field_226187_c_ = this.field_226186_b_++;
            return SortedArraySet.this.field_226170_b_[this.field_226187_c_];
         }
      }

      public void remove() {
         if (this.field_226187_c_ == -1) {
            throw new IllegalStateException();
         } else {
            SortedArraySet.this.func_226183_d_(this.field_226187_c_);
            --this.field_226186_b_;
            this.field_226187_c_ = -1;
         }
      }

      // $FF: synthetic method
      Itr(Object p_i225699_2_) {
         this();
      }
   }
}
