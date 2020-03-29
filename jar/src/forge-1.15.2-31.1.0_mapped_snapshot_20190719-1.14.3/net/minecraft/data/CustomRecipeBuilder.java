package net.minecraft.data;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;

public class CustomRecipeBuilder {
   private final SpecialRecipeSerializer<?> serializer;

   public CustomRecipeBuilder(SpecialRecipeSerializer<?> p_i50786_1_) {
      this.serializer = p_i50786_1_;
   }

   public static CustomRecipeBuilder func_218656_a(SpecialRecipeSerializer<?> p_218656_0_) {
      return new CustomRecipeBuilder(p_218656_0_);
   }

   public void build(Consumer<IFinishedRecipe> p_200499_1_, final String p_200499_2_) {
      p_200499_1_.accept(new IFinishedRecipe() {
         public void serialize(JsonObject p_218610_1_) {
         }

         public IRecipeSerializer<?> getSerializer() {
            return CustomRecipeBuilder.this.serializer;
         }

         public ResourceLocation getID() {
            return new ResourceLocation(p_200499_2_);
         }

         @Nullable
         public JsonObject getAdvancementJson() {
            return null;
         }

         public ResourceLocation getAdvancementID() {
            return new ResourceLocation("");
         }
      });
   }
}
