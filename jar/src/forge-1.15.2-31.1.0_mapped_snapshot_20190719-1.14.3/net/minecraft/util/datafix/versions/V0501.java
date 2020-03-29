package net.minecraft.util.datafix.versions;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class V0501 extends Schema {
   public V0501(int p_i49588_1_, Schema p_i49588_2_) {
      super(p_i49588_1_, p_i49588_2_);
   }

   protected static void registerEntity(Schema p_207502_0_, Map<String, Supplier<TypeTemplate>> p_207502_1_, String p_207502_2_) {
      p_207502_0_.register(p_207502_1_, p_207502_2_, () -> {
         return V0100.equipment(p_207502_0_);
      });
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema p_registerEntities_1_) {
      Map<String, Supplier<TypeTemplate>> lvt_2_1_ = super.registerEntities(p_registerEntities_1_);
      registerEntity(p_registerEntities_1_, lvt_2_1_, "PolarBear");
      return lvt_2_1_;
   }
}
