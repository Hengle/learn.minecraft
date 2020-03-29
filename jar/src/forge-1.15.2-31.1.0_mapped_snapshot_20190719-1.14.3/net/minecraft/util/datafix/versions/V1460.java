package net.minecraft.util.datafix.versions;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1460 extends NamespacedSchema {
   public V1460(int p_i49595_1_, Schema p_i49595_2_) {
      super(p_i49595_1_, p_i49595_2_);
   }

   protected static void registerEntity(Schema p_206557_0_, Map<String, Supplier<TypeTemplate>> p_206557_1_, String p_206557_2_) {
      p_206557_0_.register(p_206557_1_, p_206557_2_, () -> {
         return V0100.equipment(p_206557_0_);
      });
   }

   protected static void registerInventory(Schema p_206531_0_, Map<String, Supplier<TypeTemplate>> p_206531_1_, String p_206531_2_) {
      p_206531_0_.register(p_206531_1_, p_206531_2_, () -> {
         return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(p_206531_0_)));
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_registerEntities_1_) {
      Map<String, Supplier<TypeTemplate>> lvt_2_1_ = Maps.newHashMap();
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:area_effect_cloud");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:armor_stand");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:arrow", (p_206552_1_) -> {
         return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:bat");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:blaze");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:boat");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:cave_spider");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:chest_minecart", (p_206546_1_) -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), "Items", DSL.list(TypeReferences.ITEM_STACK.in(p_registerEntities_1_)));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:chicken");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:commandblock_minecart", (p_206529_1_) -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:cow");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:creeper");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:donkey", (p_206533_1_) -> {
         return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(p_registerEntities_1_)), "SaddleItem", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), V0100.equipment(p_registerEntities_1_));
      });
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:dragon_fireball");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:egg");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:elder_guardian");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:ender_crystal");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:ender_dragon");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:enderman", (p_206523_1_) -> {
         return DSL.optionalFields("carriedBlockState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), V0100.equipment(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:endermite");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:ender_pearl");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:evocation_fangs");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:evocation_illager");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:eye_of_ender_signal");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:falling_block", (p_206524_1_) -> {
         return DSL.optionalFields("BlockState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:fireball");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:fireworks_rocket", (p_206554_1_) -> {
         return DSL.optionalFields("FireworksItem", TypeReferences.ITEM_STACK.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:furnace_minecart", (p_206515_1_) -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:ghast");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:giant");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:guardian");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:hopper_minecart", (p_206541_1_) -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), "Items", DSL.list(TypeReferences.ITEM_STACK.in(p_registerEntities_1_)));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:horse", (p_206545_1_) -> {
         return DSL.optionalFields("ArmorItem", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), "SaddleItem", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), V0100.equipment(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:husk");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:illusion_illager");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:item", (p_206520_1_) -> {
         return DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:item_frame", (p_206535_1_) -> {
         return DSL.optionalFields("Item", TypeReferences.ITEM_STACK.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:leash_knot");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:llama", (p_209327_1_) -> {
         return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(p_registerEntities_1_)), "SaddleItem", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), "DecorItem", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), V0100.equipment(p_registerEntities_1_));
      });
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:llama_spit");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:magma_cube");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:minecart", (p_206555_1_) -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:mooshroom");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:mule", (p_206526_1_) -> {
         return DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(p_registerEntities_1_)), "SaddleItem", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), V0100.equipment(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:ocelot");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:painting");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:parrot");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:pig");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:polar_bear");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:potion", (p_206542_1_) -> {
         return DSL.optionalFields("Potion", TypeReferences.ITEM_STACK.in(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:rabbit");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:sheep");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:shulker");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:shulker_bullet");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:silverfish");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:skeleton");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:skeleton_horse", (p_206516_1_) -> {
         return DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), V0100.equipment(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:slime");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:small_fireball");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:snowball");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:snowman");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:spawner_minecart", (p_206527_1_) -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), TypeReferences.UNTAGGED_SPAWNER.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:spectral_arrow", (p_206522_1_) -> {
         return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:spider");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:squid");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:stray");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:tnt");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:tnt_minecart", (p_206551_1_) -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:vex");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:villager", (p_206534_1_) -> {
         return DSL.optionalFields("Inventory", DSL.list(TypeReferences.ITEM_STACK.in(p_registerEntities_1_)), "Offers", DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), "buyB", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), "sell", TypeReferences.ITEM_STACK.in(p_registerEntities_1_)))), V0100.equipment(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:villager_golem");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:vindication_illager");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:witch");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:wither");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:wither_skeleton");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:wither_skull");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:wolf");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:xp_bottle");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:xp_orb");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:zombie");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:zombie_horse", (p_206521_1_) -> {
         return DSL.optionalFields("SaddleItem", TypeReferences.ITEM_STACK.in(p_registerEntities_1_), V0100.equipment(p_registerEntities_1_));
      });
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:zombie_pigman");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "minecraft:zombie_villager");
      return lvt_2_1_;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema p_registerBlockEntities_1_) {
      Map<String, Supplier<TypeTemplate>> lvt_2_1_ = Maps.newHashMap();
      registerInventory(p_registerBlockEntities_1_, lvt_2_1_, "minecraft:furnace");
      registerInventory(p_registerBlockEntities_1_, lvt_2_1_, "minecraft:chest");
      registerInventory(p_registerBlockEntities_1_, lvt_2_1_, "minecraft:trapped_chest");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:ender_chest");
      p_registerBlockEntities_1_.register(lvt_2_1_, "minecraft:jukebox", (p_206549_1_) -> {
         return DSL.optionalFields("RecordItem", TypeReferences.ITEM_STACK.in(p_registerBlockEntities_1_));
      });
      registerInventory(p_registerBlockEntities_1_, lvt_2_1_, "minecraft:dispenser");
      registerInventory(p_registerBlockEntities_1_, lvt_2_1_, "minecraft:dropper");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:sign");
      p_registerBlockEntities_1_.register(lvt_2_1_, "minecraft:mob_spawner", (p_206530_1_) -> {
         return TypeReferences.UNTAGGED_SPAWNER.in(p_registerBlockEntities_1_);
      });
      p_registerBlockEntities_1_.register(lvt_2_1_, "minecraft:piston", (p_206518_1_) -> {
         return DSL.optionalFields("blockState", TypeReferences.BLOCK_STATE.in(p_registerBlockEntities_1_));
      });
      registerInventory(p_registerBlockEntities_1_, lvt_2_1_, "minecraft:brewing_stand");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:enchanting_table");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:end_portal");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:beacon");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:skull");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:daylight_detector");
      registerInventory(p_registerBlockEntities_1_, lvt_2_1_, "minecraft:hopper");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:comparator");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:banner");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:structure_block");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:end_gateway");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:command_block");
      registerInventory(p_registerBlockEntities_1_, lvt_2_1_, "minecraft:shulker_box");
      p_registerBlockEntities_1_.registerSimple(lvt_2_1_, "minecraft:bed");
      return lvt_2_1_;
   }

   public void registerTypes(Schema p_registerTypes_1_, Map<String, Supplier<TypeTemplate>> p_registerTypes_2_, Map<String, Supplier<TypeTemplate>> p_registerTypes_3_) {
      p_registerTypes_1_.registerType(false, TypeReferences.LEVEL, DSL::remainder);
      p_registerTypes_1_.registerType(false, TypeReferences.RECIPE, () -> {
         return DSL.constType(DSL.namespacedString());
      });
      p_registerTypes_1_.registerType(false, TypeReferences.PLAYER, () -> {
         return DSL.optionalFields("RootVehicle", DSL.optionalFields("Entity", TypeReferences.ENTITY_TYPE.in(p_registerTypes_1_)), "Inventory", DSL.list(TypeReferences.ITEM_STACK.in(p_registerTypes_1_)), "EnderItems", DSL.list(TypeReferences.ITEM_STACK.in(p_registerTypes_1_)), DSL.optionalFields("ShoulderEntityLeft", TypeReferences.ENTITY_TYPE.in(p_registerTypes_1_), "ShoulderEntityRight", TypeReferences.ENTITY_TYPE.in(p_registerTypes_1_), "recipeBook", DSL.optionalFields("recipes", DSL.list(TypeReferences.RECIPE.in(p_registerTypes_1_)), "toBeDisplayed", DSL.list(TypeReferences.RECIPE.in(p_registerTypes_1_)))));
      });
      p_registerTypes_1_.registerType(false, TypeReferences.CHUNK, () -> {
         return DSL.fields("Level", DSL.optionalFields("Entities", DSL.list(TypeReferences.ENTITY_TYPE.in(p_registerTypes_1_)), "TileEntities", DSL.list(TypeReferences.BLOCK_ENTITY.in(p_registerTypes_1_)), "TileTicks", DSL.list(DSL.fields("i", TypeReferences.BLOCK_NAME.in(p_registerTypes_1_))), "Sections", DSL.list(DSL.optionalFields("Palette", DSL.list(TypeReferences.BLOCK_STATE.in(p_registerTypes_1_))))));
      });
      p_registerTypes_1_.registerType(true, TypeReferences.BLOCK_ENTITY, () -> {
         return DSL.taggedChoiceLazy("id", DSL.namespacedString(), p_registerTypes_3_);
      });
      p_registerTypes_1_.registerType(true, TypeReferences.ENTITY_TYPE, () -> {
         return DSL.optionalFields("Passengers", DSL.list(TypeReferences.ENTITY_TYPE.in(p_registerTypes_1_)), TypeReferences.ENTITY.in(p_registerTypes_1_));
      });
      p_registerTypes_1_.registerType(true, TypeReferences.ENTITY, () -> {
         return DSL.taggedChoiceLazy("id", DSL.namespacedString(), p_registerTypes_2_);
      });
      p_registerTypes_1_.registerType(true, TypeReferences.ITEM_STACK, () -> {
         return DSL.hook(DSL.optionalFields("id", TypeReferences.ITEM_NAME.in(p_registerTypes_1_), "tag", DSL.optionalFields("EntityTag", TypeReferences.ENTITY_TYPE.in(p_registerTypes_1_), "BlockEntityTag", TypeReferences.BLOCK_ENTITY.in(p_registerTypes_1_), "CanDestroy", DSL.list(TypeReferences.BLOCK_NAME.in(p_registerTypes_1_)), "CanPlaceOn", DSL.list(TypeReferences.BLOCK_NAME.in(p_registerTypes_1_)))), V0705.field_206597_b, HookFunction.IDENTITY);
      });
      p_registerTypes_1_.registerType(false, TypeReferences.HOTBAR, () -> {
         return DSL.compoundList(DSL.list(TypeReferences.ITEM_STACK.in(p_registerTypes_1_)));
      });
      p_registerTypes_1_.registerType(false, TypeReferences.OPTIONS, DSL::remainder);
      p_registerTypes_1_.registerType(false, TypeReferences.STRUCTURE, () -> {
         return DSL.optionalFields("entities", DSL.list(DSL.optionalFields("nbt", TypeReferences.ENTITY_TYPE.in(p_registerTypes_1_))), "blocks", DSL.list(DSL.optionalFields("nbt", TypeReferences.BLOCK_ENTITY.in(p_registerTypes_1_))), "palette", DSL.list(TypeReferences.BLOCK_STATE.in(p_registerTypes_1_)));
      });
      p_registerTypes_1_.registerType(false, TypeReferences.BLOCK_NAME, () -> {
         return DSL.constType(DSL.namespacedString());
      });
      p_registerTypes_1_.registerType(false, TypeReferences.ITEM_NAME, () -> {
         return DSL.constType(DSL.namespacedString());
      });
      p_registerTypes_1_.registerType(false, TypeReferences.BLOCK_STATE, DSL::remainder);
      Supplier<TypeTemplate> lvt_4_1_ = () -> {
         return DSL.compoundList(TypeReferences.ITEM_NAME.in(p_registerTypes_1_), DSL.constType(DSL.intType()));
      };
      p_registerTypes_1_.registerType(false, TypeReferences.STATS, () -> {
         return DSL.optionalFields("stats", DSL.optionalFields("minecraft:mined", DSL.compoundList(TypeReferences.BLOCK_NAME.in(p_registerTypes_1_), DSL.constType(DSL.intType())), "minecraft:crafted", (TypeTemplate)lvt_4_1_.get(), "minecraft:used", (TypeTemplate)lvt_4_1_.get(), "minecraft:broken", (TypeTemplate)lvt_4_1_.get(), "minecraft:picked_up", (TypeTemplate)lvt_4_1_.get(), DSL.optionalFields("minecraft:dropped", (TypeTemplate)lvt_4_1_.get(), "minecraft:killed", DSL.compoundList(TypeReferences.ENTITY_NAME.in(p_registerTypes_1_), DSL.constType(DSL.intType())), "minecraft:killed_by", DSL.compoundList(TypeReferences.ENTITY_NAME.in(p_registerTypes_1_), DSL.constType(DSL.intType())), "minecraft:custom", DSL.compoundList(DSL.constType(DSL.namespacedString()), DSL.constType(DSL.intType())))));
      });
      p_registerTypes_1_.registerType(false, TypeReferences.SAVED_DATA, () -> {
         return DSL.optionalFields("data", DSL.optionalFields("Features", DSL.compoundList(TypeReferences.STRUCTURE_FEATURE.in(p_registerTypes_1_)), "Objectives", DSL.list(TypeReferences.OBJECTIVE.in(p_registerTypes_1_)), "Teams", DSL.list(TypeReferences.TEAM.in(p_registerTypes_1_))));
      });
      p_registerTypes_1_.registerType(false, TypeReferences.STRUCTURE_FEATURE, () -> {
         return DSL.optionalFields("Children", DSL.list(DSL.optionalFields("CA", TypeReferences.BLOCK_STATE.in(p_registerTypes_1_), "CB", TypeReferences.BLOCK_STATE.in(p_registerTypes_1_), "CC", TypeReferences.BLOCK_STATE.in(p_registerTypes_1_), "CD", TypeReferences.BLOCK_STATE.in(p_registerTypes_1_))));
      });
      p_registerTypes_1_.registerType(false, TypeReferences.OBJECTIVE, DSL::remainder);
      p_registerTypes_1_.registerType(false, TypeReferences.TEAM, DSL::remainder);
      p_registerTypes_1_.registerType(true, TypeReferences.UNTAGGED_SPAWNER, () -> {
         return DSL.optionalFields("SpawnPotentials", DSL.list(DSL.fields("Entity", TypeReferences.ENTITY_TYPE.in(p_registerTypes_1_))), "SpawnData", TypeReferences.ENTITY_TYPE.in(p_registerTypes_1_));
      });
      p_registerTypes_1_.registerType(false, TypeReferences.ADVANCEMENTS, () -> {
         return DSL.optionalFields("minecraft:adventure/adventuring_time", DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.BIOME.in(p_registerTypes_1_), DSL.constType(DSL.string()))), "minecraft:adventure/kill_a_mob", DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(p_registerTypes_1_), DSL.constType(DSL.string()))), "minecraft:adventure/kill_all_mobs", DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(p_registerTypes_1_), DSL.constType(DSL.string()))), "minecraft:husbandry/bred_all_animals", DSL.optionalFields("criteria", DSL.compoundList(TypeReferences.ENTITY_NAME.in(p_registerTypes_1_), DSL.constType(DSL.string()))));
      });
      p_registerTypes_1_.registerType(false, TypeReferences.BIOME, () -> {
         return DSL.constType(DSL.namespacedString());
      });
      p_registerTypes_1_.registerType(false, TypeReferences.ENTITY_NAME, () -> {
         return DSL.constType(DSL.namespacedString());
      });
      p_registerTypes_1_.registerType(false, TypeReferences.POI_CHUNK, DSL::remainder);
   }
}
