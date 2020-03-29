package net.minecraft.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EvokerFangsModel<T extends Entity> extends SegmentedModel<T> {
   private final ModelRenderer base = new ModelRenderer(this, 0, 0);
   private final ModelRenderer upperJaw;
   private final ModelRenderer lowerJaw;

   public EvokerFangsModel() {
      this.base.setRotationPoint(-5.0F, 22.0F, -5.0F);
      this.base.func_228300_a_(0.0F, 0.0F, 0.0F, 10.0F, 12.0F, 10.0F);
      this.upperJaw = new ModelRenderer(this, 40, 0);
      this.upperJaw.setRotationPoint(1.5F, 22.0F, -4.0F);
      this.upperJaw.func_228300_a_(0.0F, 0.0F, 0.0F, 4.0F, 14.0F, 8.0F);
      this.lowerJaw = new ModelRenderer(this, 40, 0);
      this.lowerJaw.setRotationPoint(-1.5F, 22.0F, 4.0F);
      this.lowerJaw.func_228300_a_(0.0F, 0.0F, 0.0F, 4.0F, 14.0F, 8.0F);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      float lvt_7_1_ = p_225597_2_ * 2.0F;
      if (lvt_7_1_ > 1.0F) {
         lvt_7_1_ = 1.0F;
      }

      lvt_7_1_ = 1.0F - lvt_7_1_ * lvt_7_1_ * lvt_7_1_;
      this.upperJaw.rotateAngleZ = 3.1415927F - lvt_7_1_ * 0.35F * 3.1415927F;
      this.lowerJaw.rotateAngleZ = 3.1415927F + lvt_7_1_ * 0.35F * 3.1415927F;
      this.lowerJaw.rotateAngleY = 3.1415927F;
      float lvt_8_1_ = (p_225597_2_ + MathHelper.sin(p_225597_2_ * 2.7F)) * 0.6F * 12.0F;
      this.upperJaw.rotationPointY = 24.0F - lvt_8_1_;
      this.lowerJaw.rotationPointY = this.upperJaw.rotationPointY;
      this.base.rotationPointY = this.upperJaw.rotationPointY;
   }

   public Iterable<ModelRenderer> func_225601_a_() {
      return ImmutableList.of(this.base, this.upperJaw, this.lowerJaw);
   }
}
