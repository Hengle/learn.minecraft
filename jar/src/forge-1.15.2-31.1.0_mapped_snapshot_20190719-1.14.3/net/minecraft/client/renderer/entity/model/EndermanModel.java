package net.minecraft.client.renderer.entity.model;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndermanModel<T extends LivingEntity> extends BipedModel<T> {
   public boolean isCarrying;
   public boolean isAttacking;

   public EndermanModel(float p_i46305_1_) {
      super(0.0F, -14.0F, 64, 32);
      float lvt_2_1_ = -14.0F;
      this.bipedHeadwear = new ModelRenderer(this, 0, 16);
      this.bipedHeadwear.func_228301_a_(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, p_i46305_1_ - 0.5F);
      this.bipedHeadwear.setRotationPoint(0.0F, -14.0F, 0.0F);
      this.bipedBody = new ModelRenderer(this, 32, 16);
      this.bipedBody.func_228301_a_(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, p_i46305_1_);
      this.bipedBody.setRotationPoint(0.0F, -14.0F, 0.0F);
      this.bipedRightArm = new ModelRenderer(this, 56, 0);
      this.bipedRightArm.func_228301_a_(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, p_i46305_1_);
      this.bipedRightArm.setRotationPoint(-3.0F, -12.0F, 0.0F);
      this.bipedLeftArm = new ModelRenderer(this, 56, 0);
      this.bipedLeftArm.mirror = true;
      this.bipedLeftArm.func_228301_a_(-1.0F, -2.0F, -1.0F, 2.0F, 30.0F, 2.0F, p_i46305_1_);
      this.bipedLeftArm.setRotationPoint(5.0F, -12.0F, 0.0F);
      this.bipedRightLeg = new ModelRenderer(this, 56, 0);
      this.bipedRightLeg.func_228301_a_(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, p_i46305_1_);
      this.bipedRightLeg.setRotationPoint(-2.0F, -2.0F, 0.0F);
      this.bipedLeftLeg = new ModelRenderer(this, 56, 0);
      this.bipedLeftLeg.mirror = true;
      this.bipedLeftLeg.func_228301_a_(-1.0F, 0.0F, -1.0F, 2.0F, 30.0F, 2.0F, p_i46305_1_);
      this.bipedLeftLeg.setRotationPoint(2.0F, -2.0F, 0.0F);
   }

   public void func_225597_a_(T p_225597_1_, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
      super.func_225597_a_(p_225597_1_, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
      this.bipedHead.showModel = true;
      float lvt_7_1_ = -14.0F;
      this.bipedBody.rotateAngleX = 0.0F;
      this.bipedBody.rotationPointY = -14.0F;
      this.bipedBody.rotationPointZ = -0.0F;
      ModelRenderer var10000 = this.bipedRightLeg;
      var10000.rotateAngleX -= 0.0F;
      var10000 = this.bipedLeftLeg;
      var10000.rotateAngleX -= 0.0F;
      var10000 = this.bipedRightArm;
      var10000.rotateAngleX = (float)((double)var10000.rotateAngleX * 0.5D);
      var10000 = this.bipedLeftArm;
      var10000.rotateAngleX = (float)((double)var10000.rotateAngleX * 0.5D);
      var10000 = this.bipedRightLeg;
      var10000.rotateAngleX = (float)((double)var10000.rotateAngleX * 0.5D);
      var10000 = this.bipedLeftLeg;
      var10000.rotateAngleX = (float)((double)var10000.rotateAngleX * 0.5D);
      float lvt_8_1_ = 0.4F;
      if (this.bipedRightArm.rotateAngleX > 0.4F) {
         this.bipedRightArm.rotateAngleX = 0.4F;
      }

      if (this.bipedLeftArm.rotateAngleX > 0.4F) {
         this.bipedLeftArm.rotateAngleX = 0.4F;
      }

      if (this.bipedRightArm.rotateAngleX < -0.4F) {
         this.bipedRightArm.rotateAngleX = -0.4F;
      }

      if (this.bipedLeftArm.rotateAngleX < -0.4F) {
         this.bipedLeftArm.rotateAngleX = -0.4F;
      }

      if (this.bipedRightLeg.rotateAngleX > 0.4F) {
         this.bipedRightLeg.rotateAngleX = 0.4F;
      }

      if (this.bipedLeftLeg.rotateAngleX > 0.4F) {
         this.bipedLeftLeg.rotateAngleX = 0.4F;
      }

      if (this.bipedRightLeg.rotateAngleX < -0.4F) {
         this.bipedRightLeg.rotateAngleX = -0.4F;
      }

      if (this.bipedLeftLeg.rotateAngleX < -0.4F) {
         this.bipedLeftLeg.rotateAngleX = -0.4F;
      }

      if (this.isCarrying) {
         this.bipedRightArm.rotateAngleX = -0.5F;
         this.bipedLeftArm.rotateAngleX = -0.5F;
         this.bipedRightArm.rotateAngleZ = 0.05F;
         this.bipedLeftArm.rotateAngleZ = -0.05F;
      }

      this.bipedRightArm.rotationPointZ = 0.0F;
      this.bipedLeftArm.rotationPointZ = 0.0F;
      this.bipedRightLeg.rotationPointZ = 0.0F;
      this.bipedLeftLeg.rotationPointZ = 0.0F;
      this.bipedRightLeg.rotationPointY = -5.0F;
      this.bipedLeftLeg.rotationPointY = -5.0F;
      this.bipedHead.rotationPointZ = -0.0F;
      this.bipedHead.rotationPointY = -13.0F;
      this.bipedHeadwear.rotationPointX = this.bipedHead.rotationPointX;
      this.bipedHeadwear.rotationPointY = this.bipedHead.rotationPointY;
      this.bipedHeadwear.rotationPointZ = this.bipedHead.rotationPointZ;
      this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
      this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
      this.bipedHeadwear.rotateAngleZ = this.bipedHead.rotateAngleZ;
      float lvt_9_2_;
      if (this.isAttacking) {
         lvt_9_2_ = 1.0F;
         var10000 = this.bipedHead;
         var10000.rotationPointY -= 5.0F;
      }

      lvt_9_2_ = -14.0F;
      this.bipedRightArm.setRotationPoint(-5.0F, -12.0F, 0.0F);
      this.bipedLeftArm.setRotationPoint(5.0F, -12.0F, 0.0F);
   }
}
