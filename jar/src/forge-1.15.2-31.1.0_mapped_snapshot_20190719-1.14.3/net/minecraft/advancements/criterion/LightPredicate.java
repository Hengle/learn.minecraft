package net.minecraft.advancements.criterion;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class LightPredicate {
   public static final LightPredicate field_226854_a_;
   private final MinMaxBounds.IntBound field_226855_b_;

   private LightPredicate(MinMaxBounds.IntBound p_i225753_1_) {
      this.field_226855_b_ = p_i225753_1_;
   }

   public boolean func_226858_a_(ServerWorld p_226858_1_, BlockPos p_226858_2_) {
      if (this == field_226854_a_) {
         return true;
      } else if (!p_226858_1_.isBlockPresent(p_226858_2_)) {
         return false;
      } else {
         return this.field_226855_b_.test(p_226858_1_.getLight(p_226858_2_));
      }
   }

   public JsonElement func_226856_a_() {
      if (this == field_226854_a_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject lvt_1_1_ = new JsonObject();
         lvt_1_1_.add("light", this.field_226855_b_.serialize());
         return lvt_1_1_;
      }
   }

   public static LightPredicate func_226857_a_(@Nullable JsonElement p_226857_0_) {
      if (p_226857_0_ != null && !p_226857_0_.isJsonNull()) {
         JsonObject lvt_1_1_ = JSONUtils.getJsonObject(p_226857_0_, "light");
         MinMaxBounds.IntBound lvt_2_1_ = MinMaxBounds.IntBound.fromJson(lvt_1_1_.get("light"));
         return new LightPredicate(lvt_2_1_);
      } else {
         return field_226854_a_;
      }
   }

   static {
      field_226854_a_ = new LightPredicate(MinMaxBounds.IntBound.UNBOUNDED);
   }
}
