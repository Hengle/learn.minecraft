package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.model.BeeModel;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BeeRenderer extends MobRenderer<BeeEntity, BeeModel<BeeEntity>> {
   private static final ResourceLocation field_229040_a_ = new ResourceLocation("textures/entity/bee/bee_angry.png");
   private static final ResourceLocation field_229041_g_ = new ResourceLocation("textures/entity/bee/bee_angry_nectar.png");
   private static final ResourceLocation field_229042_h_ = new ResourceLocation("textures/entity/bee/bee.png");
   private static final ResourceLocation field_229043_i_ = new ResourceLocation("textures/entity/bee/bee_nectar.png");

   public BeeRenderer(EntityRendererManager p_i226033_1_) {
      super(p_i226033_1_, new BeeModel(), 0.4F);
   }

   public ResourceLocation getEntityTexture(BeeEntity p_110775_1_) {
      if (p_110775_1_.func_226427_ez_()) {
         return p_110775_1_.func_226411_eD_() ? field_229041_g_ : field_229040_a_;
      } else {
         return p_110775_1_.func_226411_eD_() ? field_229043_i_ : field_229042_h_;
      }
   }
}
