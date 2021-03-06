package net.minecraft.nbt;

public class NBTSizeTracker {
   public static final NBTSizeTracker INFINITE = new NBTSizeTracker(0L) {
      public void read(long p_152450_1_) {
      }
   };
   private final long max;
   private long read;

   public NBTSizeTracker(long p_i46342_1_) {
      this.max = p_i46342_1_;
   }

   public void read(long p_152450_1_) {
      this.read += p_152450_1_ / 8L;
      if (this.read > this.max) {
         throw new RuntimeException("Tried to read NBT tag that was too big; tried to allocate: " + this.read + "bytes where max allowed: " + this.max);
      }
   }

   public String readUTF(String p_readUTF_1_) {
      this.read(16L);
      if (p_readUTF_1_ == null) {
         return p_readUTF_1_;
      } else {
         int len = p_readUTF_1_.length();
         int utflen = 0;

         for(int i = 0; i < len; ++i) {
            int c = p_readUTF_1_.charAt(i);
            if (c >= 1 && c <= 127) {
               ++utflen;
            } else if (c > 2047) {
               utflen += 3;
            } else {
               utflen += 2;
            }
         }

         this.read((long)(8 * utflen));
         return p_readUTF_1_;
      }
   }
}
