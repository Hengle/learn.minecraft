package net.minecraft.item;

import javax.annotation.Nullable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemUseContext {
   protected final PlayerEntity player;
   protected final Hand hand;
   protected final BlockRayTraceResult rayTraceResult;
   protected final World world;
   protected final ItemStack item;

   public ItemUseContext(PlayerEntity p_i50033_1_, Hand p_i50033_2_, BlockRayTraceResult p_i50033_3_) {
      this(p_i50033_1_.world, p_i50033_1_, p_i50033_2_, p_i50033_1_.getHeldItem(p_i50033_2_), p_i50033_3_);
   }

   protected ItemUseContext(World p_i50034_1_, @Nullable PlayerEntity p_i50034_2_, Hand p_i50034_3_, ItemStack p_i50034_4_, BlockRayTraceResult p_i50034_5_) {
      this.player = p_i50034_2_;
      this.hand = p_i50034_3_;
      this.rayTraceResult = p_i50034_5_;
      this.item = p_i50034_4_;
      this.world = p_i50034_1_;
   }

   public BlockPos getPos() {
      return this.rayTraceResult.getPos();
   }

   public Direction getFace() {
      return this.rayTraceResult.getFace();
   }

   public Vec3d getHitVec() {
      return this.rayTraceResult.getHitVec();
   }

   public boolean func_221533_k() {
      return this.rayTraceResult.isInside();
   }

   public ItemStack getItem() {
      return this.item;
   }

   @Nullable
   public PlayerEntity getPlayer() {
      return this.player;
   }

   public Hand getHand() {
      return this.hand;
   }

   public World getWorld() {
      return this.world;
   }

   public Direction getPlacementHorizontalFacing() {
      return this.player == null ? Direction.NORTH : this.player.getHorizontalFacing();
   }

   public boolean func_225518_g_() {
      return this.player != null && this.player.func_226563_dT_();
   }

   public float getPlacementYaw() {
      return this.player == null ? 0.0F : this.player.rotationYaw;
   }
}
