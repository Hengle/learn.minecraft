package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.util.datafix.NamespacedSchema;
import net.minecraft.util.datafix.TypeReferences;

public class V1451_3 extends NamespacedSchema {
   public V1451_3(int p_i49600_1_, Schema p_i49600_2_) {
      super(p_i49600_1_, p_i49600_2_);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_registerEntities_1_) {
      Map<String, Supplier<TypeTemplate>> lvt_2_1_ = super.registerEntities(p_registerEntities_1_);
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:egg");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:ender_pearl");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:fireball");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:potion", (p_206498_1_) -> {
         return DSL.optionalFields("Potion", TypeReferences.ITEM_STACK.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:small_fireball");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:snowball");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:wither_skull");
      p_registerEntities_1_.registerSimple(lvt_2_1_, "minecraft:xp_bottle");
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:arrow", () -> {
         return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:enderman", () -> {
         return DSL.optionalFields("carriedBlockState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), V0100.equipment(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:falling_block", () -> {
         return DSL.optionalFields("BlockState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), "TileEntityData", TypeReferences.BLOCK_ENTITY.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:spectral_arrow", () -> {
         return DSL.optionalFields("inBlockState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:chest_minecart", () -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), "Items", DSL.list(TypeReferences.ITEM_STACK.in(p_registerEntities_1_)));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:commandblock_minecart", () -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:furnace_minecart", () -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:hopper_minecart", () -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), "Items", DSL.list(TypeReferences.ITEM_STACK.in(p_registerEntities_1_)));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:minecart", () -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:spawner_minecart", () -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_), TypeReferences.UNTAGGED_SPAWNER.in(p_registerEntities_1_));
      });
      p_registerEntities_1_.register(lvt_2_1_, "minecraft:tnt_minecart", () -> {
         return DSL.optionalFields("DisplayState", TypeReferences.BLOCK_STATE.in(p_registerEntities_1_));
      });
      return lvt_2_1_;
   }
}
