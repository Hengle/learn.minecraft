package net.minecraft.pathfinding;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlaggedPathPoint extends PathPoint {
   private float field_224765_m = Float.MAX_VALUE;
   private PathPoint field_224766_n;
   private boolean field_224767_o;

   public FlaggedPathPoint(PathPoint p_i51802_1_) {
      super(p_i51802_1_.x, p_i51802_1_.y, p_i51802_1_.z);
   }

   @OnlyIn(Dist.CLIENT)
   public FlaggedPathPoint(int p_i51803_1_, int p_i51803_2_, int p_i51803_3_) {
      super(p_i51803_1_, p_i51803_2_, p_i51803_3_);
   }

   public void func_224761_a(float p_224761_1_, PathPoint p_224761_2_) {
      if (p_224761_1_ < this.field_224765_m) {
         this.field_224765_m = p_224761_1_;
         this.field_224766_n = p_224761_2_;
      }

   }

   public PathPoint func_224763_d() {
      return this.field_224766_n;
   }

   public void func_224764_e() {
      this.field_224767_o = true;
   }

   public boolean func_224762_f() {
      return this.field_224767_o;
   }

   @OnlyIn(Dist.CLIENT)
   public static FlaggedPathPoint func_224760_c(PacketBuffer p_224760_0_) {
      FlaggedPathPoint lvt_1_1_ = new FlaggedPathPoint(p_224760_0_.readInt(), p_224760_0_.readInt(), p_224760_0_.readInt());
      lvt_1_1_.field_222861_j = p_224760_0_.readFloat();
      lvt_1_1_.costMalus = p_224760_0_.readFloat();
      lvt_1_1_.visited = p_224760_0_.readBoolean();
      lvt_1_1_.nodeType = PathNodeType.values()[p_224760_0_.readInt()];
      lvt_1_1_.distanceToTarget = p_224760_0_.readFloat();
      return lvt_1_1_;
   }
}