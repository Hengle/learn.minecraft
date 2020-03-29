package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.PandaModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PandaHeldItemLayer extends LayerRenderer<PandaEntity, PandaModel<PandaEntity>> {
   public PandaHeldItemLayer(IEntityRenderer<PandaEntity, PandaModel<PandaEntity>> p_i50930_1_) {
      super(p_i50930_1_);
   }

   public void func_225628_a_(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, PandaEntity p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
      ItemStack lvt_11_1_ = p_225628_4_.getItemStackFromSlot(EquipmentSlotType.MAINHAND);
      if (p_225628_4_.func_213556_dX() && !p_225628_4_.func_213566_eo()) {
         float lvt_12_1_ = -0.6F;
         float lvt_13_1_ = 1.4F;
         if (p_225628_4_.func_213578_dZ()) {
            lvt_12_1_ -= 0.2F * MathHelper.sin(p_225628_8_ * 0.6F) + 0.2F;
            lvt_13_1_ -= 0.09F * MathHelper.sin(p_225628_8_ * 0.6F);
         }

         p_225628_1_.func_227860_a_();
         p_225628_1_.func_227861_a_(0.10000000149011612D, (double)lvt_13_1_, (double)lvt_12_1_);
         Minecraft.getInstance().getFirstPersonRenderer().func_228397_a_(p_225628_4_, lvt_11_1_, ItemCameraTransforms.TransformType.GROUND, false, p_225628_1_, p_225628_2_, p_225628_3_);
         p_225628_1_.func_227865_b_();
      }
   }
}
