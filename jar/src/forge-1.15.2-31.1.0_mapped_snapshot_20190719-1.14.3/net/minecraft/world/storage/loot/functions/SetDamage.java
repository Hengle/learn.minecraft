package net.minecraft.world.storage.loot.functions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetDamage extends LootFunction {
   private static final Logger LOGGER = LogManager.getLogger();
   private final RandomValueRange damageRange;

   private SetDamage(ILootCondition[] p_i46622_1_, RandomValueRange p_i46622_2_) {
      super(p_i46622_1_);
      this.damageRange = p_i46622_2_;
   }

   public ItemStack doApply(ItemStack p_215859_1_, LootContext p_215859_2_) {
      if (p_215859_1_.isDamageable()) {
         float lvt_3_1_ = 1.0F - this.damageRange.generateFloat(p_215859_2_.getRandom());
         p_215859_1_.setDamage(MathHelper.floor(lvt_3_1_ * (float)p_215859_1_.getMaxDamage()));
      } else {
         LOGGER.warn("Couldn't set damage of loot item {}", p_215859_1_);
      }

      return p_215859_1_;
   }

   public static LootFunction.Builder<?> func_215931_a(RandomValueRange p_215931_0_) {
      return builder((p_215930_1_) -> {
         return new SetDamage(p_215930_1_, p_215931_0_);
      });
   }

   // $FF: synthetic method
   SetDamage(ILootCondition[] p_i51221_1_, RandomValueRange p_i51221_2_, Object p_i51221_3_) {
      this(p_i51221_1_, p_i51221_2_);
   }

   public static class Serializer extends LootFunction.Serializer<SetDamage> {
      protected Serializer() {
         super(new ResourceLocation("set_damage"), SetDamage.class);
      }

      public void serialize(JsonObject p_186532_1_, SetDamage p_186532_2_, JsonSerializationContext p_186532_3_) {
         super.serialize(p_186532_1_, (LootFunction)p_186532_2_, p_186532_3_);
         p_186532_1_.add("damage", p_186532_3_.serialize(p_186532_2_.damageRange));
      }

      public SetDamage deserialize(JsonObject p_186530_1_, JsonDeserializationContext p_186530_2_, ILootCondition[] p_186530_3_) {
         return new SetDamage(p_186530_3_, (RandomValueRange)JSONUtils.deserializeClass(p_186530_1_, "damage", p_186530_2_, RandomValueRange.class));
      }

      // $FF: synthetic method
      public LootFunction deserialize(JsonObject p_186530_1_, JsonDeserializationContext p_186530_2_, ILootCondition[] p_186530_3_) {
         return this.deserialize(p_186530_1_, p_186530_2_, p_186530_3_);
      }
   }
}
