package net.minecraft.world.storage.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.functions.ILootFunction;

public class ItemLootEntry extends StandaloneLootEntry {
   private final Item item;

   private ItemLootEntry(Item p_i51255_1_, int p_i51255_2_, int p_i51255_3_, ILootCondition[] p_i51255_4_, ILootFunction[] p_i51255_5_) {
      super(p_i51255_2_, p_i51255_3_, p_i51255_4_, p_i51255_5_);
      this.item = p_i51255_1_;
   }

   public void func_216154_a(Consumer<ItemStack> p_216154_1_, LootContext p_216154_2_) {
      p_216154_1_.accept(new ItemStack(this.item));
   }

   public static StandaloneLootEntry.Builder<?> builder(IItemProvider p_216168_0_) {
      return builder((p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_) -> {
         return new ItemLootEntry(p_216168_0_.asItem(), p_216169_1_, p_216169_2_, p_216169_3_, p_216169_4_);
      });
   }

   // $FF: synthetic method
   ItemLootEntry(Item p_i51256_1_, int p_i51256_2_, int p_i51256_3_, ILootCondition[] p_i51256_4_, ILootFunction[] p_i51256_5_, Object p_i51256_6_) {
      this(p_i51256_1_, p_i51256_2_, p_i51256_3_, p_i51256_4_, p_i51256_5_);
   }

   public static class Serializer extends StandaloneLootEntry.Serializer<ItemLootEntry> {
      public Serializer() {
         super(new ResourceLocation("item"), ItemLootEntry.class);
      }

      public void serialize(JsonObject p_212830_1_, ItemLootEntry p_212830_2_, JsonSerializationContext p_212830_3_) {
         super.serialize(p_212830_1_, (StandaloneLootEntry)p_212830_2_, p_212830_3_);
         ResourceLocation lvt_4_1_ = Registry.ITEM.getKey(p_212830_2_.item);
         if (lvt_4_1_ == null) {
            throw new IllegalArgumentException("Can't serialize unknown item " + p_212830_2_.item);
         } else {
            p_212830_1_.addProperty("name", lvt_4_1_.toString());
         }
      }

      protected ItemLootEntry func_212829_b_(JsonObject p_212829_1_, JsonDeserializationContext p_212829_2_, int p_212829_3_, int p_212829_4_, ILootCondition[] p_212829_5_, ILootFunction[] p_212829_6_) {
         Item lvt_7_1_ = JSONUtils.getItem(p_212829_1_, "name");
         return new ItemLootEntry(lvt_7_1_, p_212829_3_, p_212829_4_, p_212829_5_, p_212829_6_);
      }

      // $FF: synthetic method
      protected StandaloneLootEntry func_212829_b_(JsonObject p_212829_1_, JsonDeserializationContext p_212829_2_, int p_212829_3_, int p_212829_4_, ILootCondition[] p_212829_5_, ILootFunction[] p_212829_6_) {
         return this.func_212829_b_(p_212829_1_, p_212829_2_, p_212829_3_, p_212829_4_, p_212829_5_, p_212829_6_);
      }
   }
}
