package net.minecraft.entity.monster;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class HuskEntity extends ZombieEntity {
   public HuskEntity(EntityType<? extends HuskEntity> p_i50204_1_, World p_i50204_2_) {
      super(p_i50204_1_, p_i50204_2_);
   }

   public static boolean func_223334_b(EntityType<HuskEntity> p_223334_0_, IWorld p_223334_1_, SpawnReason p_223334_2_, BlockPos p_223334_3_, Random p_223334_4_) {
      return func_223325_c(p_223334_0_, p_223334_1_, p_223334_2_, p_223334_3_, p_223334_4_) && (p_223334_2_ == SpawnReason.SPAWNER || p_223334_1_.func_226660_f_(p_223334_3_));
   }

   protected boolean shouldBurnInDay() {
      return false;
   }

   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_HUSK_AMBIENT;
   }

   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
      return SoundEvents.ENTITY_HUSK_HURT;
   }

   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_HUSK_DEATH;
   }

   protected SoundEvent getStepSound() {
      return SoundEvents.ENTITY_HUSK_STEP;
   }

   public boolean attackEntityAsMob(Entity p_70652_1_) {
      boolean lvt_2_1_ = super.attackEntityAsMob(p_70652_1_);
      if (lvt_2_1_ && this.getHeldItemMainhand().isEmpty() && p_70652_1_ instanceof LivingEntity) {
         float lvt_3_1_ = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
         ((LivingEntity)p_70652_1_).addPotionEffect(new EffectInstance(Effects.HUNGER, 140 * (int)lvt_3_1_));
      }

      return lvt_2_1_;
   }

   protected boolean shouldDrown() {
      return true;
   }

   protected void onDrowned() {
      this.func_213698_b(EntityType.ZOMBIE);
      this.world.playEvent((PlayerEntity)null, 1041, new BlockPos(this), 0);
   }

   protected ItemStack getSkullDrop() {
      return ItemStack.EMPTY;
   }
}
