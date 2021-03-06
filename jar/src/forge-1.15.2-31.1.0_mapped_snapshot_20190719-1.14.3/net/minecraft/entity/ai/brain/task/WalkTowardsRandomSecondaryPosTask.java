package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.entity.ai.brain.memory.MemoryModuleStatus;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.memory.WalkTarget;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.server.ServerWorld;

public class WalkTowardsRandomSecondaryPosTask extends Task<VillagerEntity> {
   private final MemoryModuleType<List<GlobalPos>> field_220573_a;
   private final MemoryModuleType<GlobalPos> field_220574_b;
   private final float field_220575_c;
   private final int field_220576_d;
   private final int field_220577_e;
   private long field_220578_f;
   @Nullable
   private GlobalPos field_220579_g;

   public WalkTowardsRandomSecondaryPosTask(MemoryModuleType<List<GlobalPos>> p_i50340_1_, float p_i50340_2_, int p_i50340_3_, int p_i50340_4_, MemoryModuleType<GlobalPos> p_i50340_5_) {
      super(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleStatus.REGISTERED, p_i50340_1_, MemoryModuleStatus.VALUE_PRESENT, p_i50340_5_, MemoryModuleStatus.VALUE_PRESENT));
      this.field_220573_a = p_i50340_1_;
      this.field_220575_c = p_i50340_2_;
      this.field_220576_d = p_i50340_3_;
      this.field_220577_e = p_i50340_4_;
      this.field_220574_b = p_i50340_5_;
   }

   protected boolean shouldExecute(ServerWorld p_212832_1_, VillagerEntity p_212832_2_) {
      Optional<List<GlobalPos>> lvt_3_1_ = p_212832_2_.getBrain().getMemory(this.field_220573_a);
      Optional<GlobalPos> lvt_4_1_ = p_212832_2_.getBrain().getMemory(this.field_220574_b);
      if (lvt_3_1_.isPresent() && lvt_4_1_.isPresent()) {
         List<GlobalPos> lvt_5_1_ = (List)lvt_3_1_.get();
         if (!lvt_5_1_.isEmpty()) {
            this.field_220579_g = (GlobalPos)lvt_5_1_.get(p_212832_1_.getRandom().nextInt(lvt_5_1_.size()));
            return this.field_220579_g != null && Objects.equals(p_212832_1_.getDimension().getType(), this.field_220579_g.getDimension()) && ((GlobalPos)lvt_4_1_.get()).getPos().withinDistance(p_212832_2_.getPositionVec(), (double)this.field_220577_e);
         }
      }

      return false;
   }

   protected void startExecuting(ServerWorld p_212831_1_, VillagerEntity p_212831_2_, long p_212831_3_) {
      if (p_212831_3_ > this.field_220578_f && this.field_220579_g != null) {
         p_212831_2_.getBrain().setMemory(MemoryModuleType.WALK_TARGET, (Object)(new WalkTarget(this.field_220579_g.getPos(), this.field_220575_c, this.field_220576_d)));
         this.field_220578_f = p_212831_3_ + 100L;
      }

   }
}
