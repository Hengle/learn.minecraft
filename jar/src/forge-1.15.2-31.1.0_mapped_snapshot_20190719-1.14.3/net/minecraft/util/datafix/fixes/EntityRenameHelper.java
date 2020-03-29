package net.minecraft.util.datafix.fixes;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;

public abstract class EntityRenameHelper extends EntityRename {
   public EntityRenameHelper(String p_i49714_1_, Schema p_i49714_2_, boolean p_i49714_3_) {
      super(p_i49714_1_, p_i49714_2_, p_i49714_3_);
   }

   protected Pair<String, Typed<?>> fix(String p_209149_1_, Typed<?> p_209149_2_) {
      Pair<String, Dynamic<?>> lvt_3_1_ = this.getNewNameAndTag(p_209149_1_, (Dynamic)p_209149_2_.getOrCreate(DSL.remainderFinder()));
      return Pair.of(lvt_3_1_.getFirst(), p_209149_2_.set(DSL.remainderFinder(), lvt_3_1_.getSecond()));
   }

   protected abstract Pair<String, Dynamic<?>> getNewNameAndTag(String var1, Dynamic<?> var2);
}
