package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.entity.model.RabbitModel;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RabbitRenderer extends MobRenderer<RabbitEntity, RabbitModel<RabbitEntity>> {
   private static final ResourceLocation BROWN = new ResourceLocation("textures/entity/rabbit/brown.png");
   private static final ResourceLocation WHITE = new ResourceLocation("textures/entity/rabbit/white.png");
   private static final ResourceLocation BLACK = new ResourceLocation("textures/entity/rabbit/black.png");
   private static final ResourceLocation GOLD = new ResourceLocation("textures/entity/rabbit/gold.png");
   private static final ResourceLocation SALT = new ResourceLocation("textures/entity/rabbit/salt.png");
   private static final ResourceLocation WHITE_SPLOTCHED = new ResourceLocation("textures/entity/rabbit/white_splotched.png");
   private static final ResourceLocation TOAST = new ResourceLocation("textures/entity/rabbit/toast.png");
   private static final ResourceLocation CAERBANNOG = new ResourceLocation("textures/entity/rabbit/caerbannog.png");

   public RabbitRenderer(EntityRendererManager p_i47196_1_) {
      super(p_i47196_1_, new RabbitModel(), 0.3F);
   }

   public ResourceLocation getEntityTexture(RabbitEntity p_110775_1_) {
      String lvt_2_1_ = TextFormatting.getTextWithoutFormattingCodes(p_110775_1_.getName().getString());
      if (lvt_2_1_ != null && "Toast".equals(lvt_2_1_)) {
         return TOAST;
      } else {
         switch(p_110775_1_.getRabbitType()) {
         case 0:
         default:
            return BROWN;
         case 1:
            return WHITE;
         case 2:
            return BLACK;
         case 3:
            return WHITE_SPLOTCHED;
         case 4:
            return GOLD;
         case 5:
            return SALT;
         case 99:
            return CAERBANNOG;
         }
      }
   }
}
