package net.minecraftforge.common.crafting.conditions;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class TagEmptyCondition implements ICondition {
   private static final ResourceLocation NAME = new ResourceLocation("forge", "tag_empty");
   private final ResourceLocation tag_name;

   public TagEmptyCondition(String location) {
      this(new ResourceLocation(location));
   }

   public TagEmptyCondition(String namespace, String path) {
      this(new ResourceLocation(namespace, path));
   }

   public TagEmptyCondition(ResourceLocation tag) {
      this.tag_name = tag;
   }

   public ResourceLocation getID() {
      return NAME;
   }

   public boolean test() {
      Tag<Item> tag = ItemTags.getCollection().get(this.tag_name);
      return tag == null || tag.getAllElements().isEmpty();
   }

   public String toString() {
      return "tag_empty(\"" + this.tag_name + "\")";
   }

   public static class Serializer implements IConditionSerializer<TagEmptyCondition> {
      public static final TagEmptyCondition.Serializer INSTANCE = new TagEmptyCondition.Serializer();

      public void write(JsonObject json, TagEmptyCondition value) {
         json.addProperty("tag", value.tag_name.toString());
      }

      public TagEmptyCondition read(JsonObject json) {
         return new TagEmptyCondition(new ResourceLocation(JSONUtils.getString(json, "tag")));
      }

      public ResourceLocation getID() {
         return TagEmptyCondition.NAME;
      }
   }
}
