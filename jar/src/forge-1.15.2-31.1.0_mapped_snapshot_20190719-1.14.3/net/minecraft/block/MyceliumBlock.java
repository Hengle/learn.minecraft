package net.minecraft.block;

import java.util.Random;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MyceliumBlock extends SpreadableSnowyDirtBlock {
   public MyceliumBlock(Block.Properties p_i48362_1_) {
      super(p_i48362_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      super.animateTick(p_180655_1_, p_180655_2_, p_180655_3_, p_180655_4_);
      if (p_180655_4_.nextInt(10) == 0) {
         p_180655_2_.addParticle(ParticleTypes.MYCELIUM, (double)p_180655_3_.getX() + (double)p_180655_4_.nextFloat(), (double)p_180655_3_.getY() + 1.1D, (double)p_180655_3_.getZ() + (double)p_180655_4_.nextFloat(), 0.0D, 0.0D, 0.0D);
      }

   }
}
