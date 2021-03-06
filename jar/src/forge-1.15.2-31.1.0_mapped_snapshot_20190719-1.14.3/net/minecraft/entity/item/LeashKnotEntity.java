package net.minecraft.entity.item;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.server.SSpawnObjectPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class LeashKnotEntity extends HangingEntity {
   public LeashKnotEntity(EntityType<? extends LeashKnotEntity> p_i50223_1_, World p_i50223_2_) {
      super(p_i50223_1_, p_i50223_2_);
   }

   public LeashKnotEntity(World p_i45851_1_, BlockPos p_i45851_2_) {
      super(EntityType.LEASH_KNOT, p_i45851_1_, p_i45851_2_);
      this.setPosition((double)p_i45851_2_.getX() + 0.5D, (double)p_i45851_2_.getY() + 0.5D, (double)p_i45851_2_.getZ() + 0.5D);
      float f = 0.125F;
      float f1 = 0.1875F;
      float f2 = 0.25F;
      this.setBoundingBox(new AxisAlignedBB(this.func_226277_ct_() - 0.1875D, this.func_226278_cu_() - 0.25D + 0.125D, this.func_226281_cx_() - 0.1875D, this.func_226277_ct_() + 0.1875D, this.func_226278_cu_() + 0.25D + 0.125D, this.func_226281_cx_() + 0.1875D));
      this.forceSpawn = true;
   }

   public void setPosition(double p_70107_1_, double p_70107_3_, double p_70107_5_) {
      super.setPosition((double)MathHelper.floor(p_70107_1_) + 0.5D, (double)MathHelper.floor(p_70107_3_) + 0.5D, (double)MathHelper.floor(p_70107_5_) + 0.5D);
   }

   protected void updateBoundingBox() {
      this.func_226288_n_((double)this.hangingPosition.getX() + 0.5D, (double)this.hangingPosition.getY() + 0.5D, (double)this.hangingPosition.getZ() + 0.5D);
      if (this.isAddedToWorld() && this.world instanceof ServerWorld) {
         ((ServerWorld)this.world).chunkCheck(this);
      }

   }

   public void updateFacingWithBoundingBox(Direction p_174859_1_) {
   }

   public int getWidthPixels() {
      return 9;
   }

   public int getHeightPixels() {
      return 9;
   }

   protected float getEyeHeight(Pose p_213316_1_, EntitySize p_213316_2_) {
      return -0.0625F;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isInRangeToRenderDist(double p_70112_1_) {
      return p_70112_1_ < 1024.0D;
   }

   public void onBroken(@Nullable Entity p_110128_1_) {
      this.playSound(SoundEvents.ENTITY_LEASH_KNOT_BREAK, 1.0F, 1.0F);
   }

   public void writeAdditional(CompoundNBT p_213281_1_) {
   }

   public void readAdditional(CompoundNBT p_70037_1_) {
   }

   public boolean processInitialInteract(PlayerEntity p_184230_1_, Hand p_184230_2_) {
      if (this.world.isRemote) {
         return true;
      } else {
         boolean flag = false;
         double d0 = 7.0D;
         List<MobEntity> list = this.world.getEntitiesWithinAABB(MobEntity.class, new AxisAlignedBB(this.func_226277_ct_() - 7.0D, this.func_226278_cu_() - 7.0D, this.func_226281_cx_() - 7.0D, this.func_226277_ct_() + 7.0D, this.func_226278_cu_() + 7.0D, this.func_226281_cx_() + 7.0D));
         Iterator var7 = list.iterator();

         MobEntity mobentity1;
         while(var7.hasNext()) {
            mobentity1 = (MobEntity)var7.next();
            if (mobentity1.getLeashHolder() == p_184230_1_) {
               mobentity1.setLeashHolder(this, true);
               flag = true;
            }
         }

         if (!flag) {
            this.remove();
            if (p_184230_1_.abilities.isCreativeMode) {
               var7 = list.iterator();

               while(var7.hasNext()) {
                  mobentity1 = (MobEntity)var7.next();
                  if (mobentity1.getLeashed() && mobentity1.getLeashHolder() == this) {
                     mobentity1.clearLeashed(true, false);
                  }
               }
            }
         }

         return true;
      }
   }

   public boolean onValidSurface() {
      return this.world.getBlockState(this.hangingPosition).getBlock().isIn(BlockTags.FENCES);
   }

   public static LeashKnotEntity create(World p_213855_0_, BlockPos p_213855_1_) {
      int i = p_213855_1_.getX();
      int j = p_213855_1_.getY();
      int k = p_213855_1_.getZ();
      Iterator var5 = p_213855_0_.getEntitiesWithinAABB(LeashKnotEntity.class, new AxisAlignedBB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D)).iterator();

      LeashKnotEntity leashknotentity;
      do {
         if (!var5.hasNext()) {
            LeashKnotEntity leashknotentity1 = new LeashKnotEntity(p_213855_0_, p_213855_1_);
            p_213855_0_.addEntity(leashknotentity1);
            leashknotentity1.playPlaceSound();
            return leashknotentity1;
         }

         leashknotentity = (LeashKnotEntity)var5.next();
      } while(!leashknotentity.getHangingPosition().equals(p_213855_1_));

      return leashknotentity;
   }

   public void playPlaceSound() {
      this.playSound(SoundEvents.ENTITY_LEASH_KNOT_PLACE, 1.0F, 1.0F);
   }

   public IPacket<?> createSpawnPacket() {
      return new SSpawnObjectPacket(this, this.getType(), 0, this.getHangingPosition());
   }
}
