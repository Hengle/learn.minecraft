package net.minecraft.advancements.criterion;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class RightClickBlockWithItemTrigger extends AbstractCriterionTrigger<RightClickBlockWithItemTrigger.Instance> {
   private final ResourceLocation field_226692_a_;

   public RightClickBlockWithItemTrigger(ResourceLocation p_i225742_1_) {
      this.field_226692_a_ = p_i225742_1_;
   }

   public ResourceLocation getId() {
      return this.field_226692_a_;
   }

   public RightClickBlockWithItemTrigger.Instance deserializeInstance(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      BlockPredicate lvt_3_1_ = BlockPredicate.func_226237_a_(p_192166_1_.get("block"));
      StatePropertiesPredicate lvt_4_1_ = StatePropertiesPredicate.func_227186_a_(p_192166_1_.get("state"));
      ItemPredicate lvt_5_1_ = ItemPredicate.deserialize(p_192166_1_.get("item"));
      return new RightClickBlockWithItemTrigger.Instance(this.field_226692_a_, lvt_3_1_, lvt_4_1_, lvt_5_1_);
   }

   public void func_226695_a_(ServerPlayerEntity p_226695_1_, BlockPos p_226695_2_, ItemStack p_226695_3_) {
      BlockState lvt_4_1_ = p_226695_1_.getServerWorld().getBlockState(p_226695_2_);
      this.func_227070_a_(p_226695_1_.getAdvancements(), (p_226694_4_) -> {
         return p_226694_4_.func_226700_a_(lvt_4_1_, p_226695_1_.getServerWorld(), p_226695_2_, p_226695_3_);
      });
   }

   // $FF: synthetic method
   public ICriterionInstance deserializeInstance(JsonObject p_192166_1_, JsonDeserializationContext p_192166_2_) {
      return this.deserializeInstance(p_192166_1_, p_192166_2_);
   }

   public static class Instance extends CriterionInstance {
      private final BlockPredicate field_226696_a_;
      private final StatePropertiesPredicate field_226697_b_;
      private final ItemPredicate field_226698_c_;

      public Instance(ResourceLocation p_i225743_1_, BlockPredicate p_i225743_2_, StatePropertiesPredicate p_i225743_3_, ItemPredicate p_i225743_4_) {
         super(p_i225743_1_);
         this.field_226696_a_ = p_i225743_2_;
         this.field_226697_b_ = p_i225743_3_;
         this.field_226698_c_ = p_i225743_4_;
      }

      public static RightClickBlockWithItemTrigger.Instance func_226699_a_(BlockPredicate.Builder p_226699_0_, ItemPredicate.Builder p_226699_1_) {
         return new RightClickBlockWithItemTrigger.Instance(CriteriaTriggers.field_229863_J_.field_226692_a_, p_226699_0_.func_226245_b_(), StatePropertiesPredicate.field_227178_a_, p_226699_1_.build());
      }

      public boolean func_226700_a_(BlockState p_226700_1_, ServerWorld p_226700_2_, BlockPos p_226700_3_, ItemStack p_226700_4_) {
         if (!this.field_226696_a_.func_226238_a_(p_226700_2_, p_226700_3_)) {
            return false;
         } else if (!this.field_226697_b_.func_227181_a_(p_226700_1_)) {
            return false;
         } else {
            return this.field_226698_c_.test(p_226700_4_);
         }
      }

      public JsonElement serialize() {
         JsonObject lvt_1_1_ = new JsonObject();
         lvt_1_1_.add("block", this.field_226696_a_.func_226236_a_());
         lvt_1_1_.add("state", this.field_226697_b_.func_227180_a_());
         lvt_1_1_.add("item", this.field_226698_c_.serialize());
         return lvt_1_1_;
      }
   }
}
