package net.minecraft.client.particle;

import net.minecraft.particles.BasicParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BubbleColumnUpParticle extends SpriteTexturedParticle {
   private BubbleColumnUpParticle(World p_i48833_1_, double p_i48833_2_, double p_i48833_4_, double p_i48833_6_, double p_i48833_8_, double p_i48833_10_, double p_i48833_12_) {
      super(p_i48833_1_, p_i48833_2_, p_i48833_4_, p_i48833_6_);
      this.setSize(0.02F, 0.02F);
      this.particleScale *= this.rand.nextFloat() * 0.6F + 0.2F;
      this.motionX = p_i48833_8_ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
      this.motionY = p_i48833_10_ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
      this.motionZ = p_i48833_12_ * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
      this.maxAge = (int)(40.0D / (Math.random() * 0.8D + 0.2D));
   }

   public void tick() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      this.motionY += 0.005D;
      if (this.maxAge-- <= 0) {
         this.setExpired();
      } else {
         this.move(this.motionX, this.motionY, this.motionZ);
         this.motionX *= 0.8500000238418579D;
         this.motionY *= 0.8500000238418579D;
         this.motionZ *= 0.8500000238418579D;
         if (!this.world.getFluidState(new BlockPos(this.posX, this.posY, this.posZ)).isTagged(FluidTags.WATER)) {
            this.setExpired();
         }

      }
   }

   public IParticleRenderType getRenderType() {
      return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   // $FF: synthetic method
   BubbleColumnUpParticle(World p_i51051_1_, double p_i51051_2_, double p_i51051_4_, double p_i51051_6_, double p_i51051_8_, double p_i51051_10_, double p_i51051_12_, Object p_i51051_14_) {
      this(p_i51051_1_, p_i51051_2_, p_i51051_4_, p_i51051_6_, p_i51051_8_, p_i51051_10_, p_i51051_12_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Factory implements IParticleFactory<BasicParticleType> {
      private final IAnimatedSprite spriteSet;

      public Factory(IAnimatedSprite p_i50448_1_) {
         this.spriteSet = p_i50448_1_;
      }

      public Particle makeParticle(BasicParticleType p_199234_1_, World p_199234_2_, double p_199234_3_, double p_199234_5_, double p_199234_7_, double p_199234_9_, double p_199234_11_, double p_199234_13_) {
         BubbleColumnUpParticle lvt_15_1_ = new BubbleColumnUpParticle(p_199234_2_, p_199234_3_, p_199234_5_, p_199234_7_, p_199234_9_, p_199234_11_, p_199234_13_);
         lvt_15_1_.selectSpriteRandomly(this.spriteSet);
         return lvt_15_1_;
      }
   }
}
