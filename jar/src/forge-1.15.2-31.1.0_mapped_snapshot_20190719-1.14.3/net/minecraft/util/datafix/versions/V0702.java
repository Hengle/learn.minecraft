package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V0702 extends Schema {
   public V0702(int p_i49585_1_, Schema p_i49585_2_) {
      super(p_i49585_1_, p_i49585_2_);
   }

   protected static void registerEntity(Schema p_206636_0_, Map<String, Supplier<TypeTemplate>> p_206636_1_, String p_206636_2_) {
      p_206636_0_.register(p_206636_1_, p_206636_2_, () -> {
         return V0100.equipment(p_206636_0_);
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_registerEntities_1_) {
      Map<String, Supplier<TypeTemplate>> lvt_2_1_ = super.registerEntities(p_registerEntities_1_);
      registerEntity(p_registerEntities_1_, lvt_2_1_, "ZombieVillager");
      registerEntity(p_registerEntities_1_, lvt_2_1_, "Husk");
      return lvt_2_1_;
   }
}
