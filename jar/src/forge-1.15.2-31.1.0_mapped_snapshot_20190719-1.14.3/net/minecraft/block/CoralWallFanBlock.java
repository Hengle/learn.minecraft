package net.minecraft.block;

import java.util.Random;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class CoralWallFanBlock extends DeadCoralWallFanBlock {
   private final Block deadBlock;

   protected CoralWallFanBlock(Block p_i49774_1_, Block.Properties p_i49774_2_) {
      super(p_i49774_2_);
      this.deadBlock = p_i49774_1_;
   }

   public void onBlockAdded(BlockState p_220082_1_, World p_220082_2_, BlockPos p_220082_3_, BlockState p_220082_4_, boolean p_220082_5_) {
      this.updateIfDry(p_220082_1_, p_220082_2_, p_220082_3_);
   }

   public void func_225534_a_(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
      if (!isInWater(p_225534_1_, p_225534_2_, p_225534_3_)) {
         p_225534_2_.setBlockState(p_225534_3_, (BlockState)((BlockState)this.deadBlock.getDefaultState().with(WATERLOGGED, false)).with(FACING, p_225534_1_.get(FACING)), 2);
      }

   }

   public BlockState updatePostPlacement(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
      if (p_196271_2_.getOpposite() == p_196271_1_.get(FACING) && !p_196271_1_.isValidPosition(p_196271_4_, p_196271_5_)) {
         return Blocks.AIR.getDefaultState();
      } else {
         if ((Boolean)p_196271_1_.get(WATERLOGGED)) {
            p_196271_4_.getPendingFluidTicks().scheduleTick(p_196271_5_, Fluids.WATER, Fluids.WATER.getTickRate(p_196271_4_));
         }

         this.updateIfDry(p_196271_1_, p_196271_4_, p_196271_5_);
         return super.updatePostPlacement(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
      }
   }
}
