package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;

public class TemptGoal extends Goal {
   private static final EntityPredicate ENTITY_PREDICATE = (new EntityPredicate()).setDistance(10.0D).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks().setLineOfSiteRequired();
   protected final CreatureEntity creature;
   private final double speed;
   private double targetX;
   private double targetY;
   private double targetZ;
   private double pitch;
   private double yaw;
   protected PlayerEntity closestPlayer;
   private int delayTemptCounter;
   private boolean isRunning;
   private final Ingredient temptItem;
   private final boolean scaredByPlayerMovement;

   public TemptGoal(CreatureEntity p_i47822_1_, double p_i47822_2_, Ingredient p_i47822_4_, boolean p_i47822_5_) {
      this(p_i47822_1_, p_i47822_2_, p_i47822_5_, p_i47822_4_);
   }

   public TemptGoal(CreatureEntity p_i47823_1_, double p_i47823_2_, boolean p_i47823_4_, Ingredient p_i47823_5_) {
      this.creature = p_i47823_1_;
      this.speed = p_i47823_2_;
      this.temptItem = p_i47823_5_;
      this.scaredByPlayerMovement = p_i47823_4_;
      this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(p_i47823_1_.getNavigator() instanceof GroundPathNavigator) && !(p_i47823_1_.getNavigator() instanceof FlyingPathNavigator)) {
         throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
      }
   }

   public boolean shouldExecute() {
      if (this.delayTemptCounter > 0) {
         --this.delayTemptCounter;
         return false;
      } else {
         this.closestPlayer = this.creature.world.getClosestPlayer(ENTITY_PREDICATE, this.creature);
         if (this.closestPlayer == null) {
            return false;
         } else {
            return this.isTempting(this.closestPlayer.getHeldItemMainhand()) || this.isTempting(this.closestPlayer.getHeldItemOffhand());
         }
      }
   }

   protected boolean isTempting(ItemStack p_188508_1_) {
      return this.temptItem.test(p_188508_1_);
   }

   public boolean shouldContinueExecuting() {
      if (this.isScaredByPlayerMovement()) {
         if (this.creature.getDistanceSq(this.closestPlayer) < 36.0D) {
            if (this.closestPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D) {
               return false;
            }

            if (Math.abs((double)this.closestPlayer.rotationPitch - this.pitch) > 5.0D || Math.abs((double)this.closestPlayer.rotationYaw - this.yaw) > 5.0D) {
               return false;
            }
         } else {
            this.targetX = this.closestPlayer.func_226277_ct_();
            this.targetY = this.closestPlayer.func_226278_cu_();
            this.targetZ = this.closestPlayer.func_226281_cx_();
         }

         this.pitch = (double)this.closestPlayer.rotationPitch;
         this.yaw = (double)this.closestPlayer.rotationYaw;
      }

      return this.shouldExecute();
   }

   protected boolean isScaredByPlayerMovement() {
      return this.scaredByPlayerMovement;
   }

   public void startExecuting() {
      this.targetX = this.closestPlayer.func_226277_ct_();
      this.targetY = this.closestPlayer.func_226278_cu_();
      this.targetZ = this.closestPlayer.func_226281_cx_();
      this.isRunning = true;
   }

   public void resetTask() {
      this.closestPlayer = null;
      this.creature.getNavigator().clearPath();
      this.delayTemptCounter = 100;
      this.isRunning = false;
   }

   public void tick() {
      this.creature.getLookController().setLookPositionWithEntity(this.closestPlayer, (float)(this.creature.getHorizontalFaceSpeed() + 20), (float)this.creature.getVerticalFaceSpeed());
      if (this.creature.getDistanceSq(this.closestPlayer) < 6.25D) {
         this.creature.getNavigator().clearPath();
      } else {
         this.creature.getNavigator().tryMoveToEntityLiving(this.closestPlayer, this.speed);
      }

   }

   public boolean isRunning() {
      return this.isRunning;
   }
}