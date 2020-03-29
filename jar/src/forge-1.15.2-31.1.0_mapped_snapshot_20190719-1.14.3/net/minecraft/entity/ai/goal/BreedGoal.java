package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;

public class BreedGoal extends Goal {
   private static final EntityPredicate field_220689_d = (new EntityPredicate()).setDistance(8.0D).allowInvulnerable().allowFriendlyFire().setLineOfSiteRequired();
   protected final AnimalEntity animal;
   private final Class<? extends AnimalEntity> mateClass;
   protected final World world;
   protected AnimalEntity field_75391_e;
   private int spawnBabyDelay;
   private final double moveSpeed;

   public BreedGoal(AnimalEntity p_i1619_1_, double p_i1619_2_) {
      this(p_i1619_1_, p_i1619_2_, p_i1619_1_.getClass());
   }

   public BreedGoal(AnimalEntity p_i47306_1_, double p_i47306_2_, Class<? extends AnimalEntity> p_i47306_4_) {
      this.animal = p_i47306_1_;
      this.world = p_i47306_1_.world;
      this.mateClass = p_i47306_4_;
      this.moveSpeed = p_i47306_2_;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean shouldExecute() {
      if (!this.animal.isInLove()) {
         return false;
      } else {
         this.field_75391_e = this.getNearbyMate();
         return this.field_75391_e != null;
      }
   }

   public boolean shouldContinueExecuting() {
      return this.field_75391_e.isAlive() && this.field_75391_e.isInLove() && this.spawnBabyDelay < 60;
   }

   public void resetTask() {
      this.field_75391_e = null;
      this.spawnBabyDelay = 0;
   }

   public void tick() {
      this.animal.getLookController().setLookPositionWithEntity(this.field_75391_e, 10.0F, (float)this.animal.getVerticalFaceSpeed());
      this.animal.getNavigator().tryMoveToEntityLiving(this.field_75391_e, this.moveSpeed);
      ++this.spawnBabyDelay;
      if (this.spawnBabyDelay >= 60 && this.animal.getDistanceSq(this.field_75391_e) < 9.0D) {
         this.spawnBaby();
      }

   }

   @Nullable
   private AnimalEntity getNearbyMate() {
      List<AnimalEntity> list = this.world.getTargettableEntitiesWithinAABB(this.mateClass, field_220689_d, this.animal, this.animal.getBoundingBox().grow(8.0D));
      double d0 = Double.MAX_VALUE;
      AnimalEntity animalentity = null;
      Iterator var5 = list.iterator();

      while(var5.hasNext()) {
         AnimalEntity animalentity1 = (AnimalEntity)var5.next();
         if (this.animal.canMateWith(animalentity1) && this.animal.getDistanceSq(animalentity1) < d0) {
            animalentity = animalentity1;
            d0 = this.animal.getDistanceSq(animalentity1);
         }
      }

      return animalentity;
   }

   protected void spawnBaby() {
      AgeableEntity ageableentity = this.animal.createChild(this.field_75391_e);
      BabyEntitySpawnEvent event = new BabyEntitySpawnEvent(this.animal, this.field_75391_e, ageableentity);
      boolean cancelled = MinecraftForge.EVENT_BUS.post(event);
      ageableentity = event.getChild();
      if (cancelled) {
         this.animal.setGrowingAge(6000);
         this.field_75391_e.setGrowingAge(6000);
         this.animal.resetInLove();
         this.field_75391_e.resetInLove();
      } else {
         if (ageableentity != null) {
            ServerPlayerEntity serverplayerentity = this.animal.getLoveCause();
            if (serverplayerentity == null && this.field_75391_e.getLoveCause() != null) {
               serverplayerentity = this.field_75391_e.getLoveCause();
            }

            if (serverplayerentity != null) {
               serverplayerentity.addStat(Stats.ANIMALS_BRED);
               CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this.animal, this.field_75391_e, ageableentity);
            }

            this.animal.setGrowingAge(6000);
            this.field_75391_e.setGrowingAge(6000);
            this.animal.resetInLove();
            this.field_75391_e.resetInLove();
            ageableentity.setGrowingAge(-24000);
            ageableentity.setLocationAndAngles(this.animal.func_226277_ct_(), this.animal.func_226278_cu_(), this.animal.func_226281_cx_(), 0.0F, 0.0F);
            this.world.addEntity(ageableentity);
            this.world.setEntityState(this.animal, (byte)18);
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
               this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.func_226277_ct_(), this.animal.func_226278_cu_(), this.animal.func_226281_cx_(), this.animal.getRNG().nextInt(7) + 1));
            }
         }

      }
   }
}
