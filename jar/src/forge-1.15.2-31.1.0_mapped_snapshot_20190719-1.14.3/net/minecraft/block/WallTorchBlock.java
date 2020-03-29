package net.minecraft.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class WallTorchBlock extends TorchBlock {
   public static final DirectionProperty HORIZONTAL_FACING;
   private static final Map<Direction, VoxelShape> SHAPES;

   protected WallTorchBlock(Block.Properties p_i48298_1_) {
      super(p_i48298_1_);
      this.setDefaultState((BlockState)((BlockState)this.stateContainer.getBaseState()).with(HORIZONTAL_FACING, Direction.NORTH));
   }

   public String getTranslationKey() {
      return this.asItem().getTranslationKey();
   }

   public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
      return func_220289_j(p_220053_1_);
   }

   public static VoxelShape func_220289_j(BlockState p_220289_0_) {
      return (VoxelShape)SHAPES.get(p_220289_0_.get(HORIZONTAL_FACING));
   }

   public boolean isValidPosition(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
      Direction lvt_4_1_ = (Direction)p_196260_1_.get(HORIZONTAL_FACING);
      BlockPos lvt_5_1_ = p_196260_3_.offset(lvt_4_1_.getOpposite());
      BlockState lvt_6_1_ = p_196260_2_.getBlockState(lvt_5_1_);
      return lvt_6_1_.func_224755_d(p_196260_2_, lvt_5_1_, lvt_4_1_);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
      BlockState lvt_2_1_ = this.getDefaultState();
      IWorldReader lvt_3_1_ = p_196258_1_.getWorld();
      BlockPos lvt_4_1_ = p_196258_1_.getPos();
      Direction[] lvt_5_1_ = p_196258_1_.getNearestLookingDirections();
      Direction[] var6 = lvt_5_1_;
      int var7 = lvt_5_1_.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Direction lvt_9_1_ = var6[var8];
         if (lvt_9_1_.getAxis().isHorizontal()) {
            Direction lvt_10_1_ = lvt_9_1_.getOpposite();
            lvt_2_1_ = (BlockState)lvt_2_1_.with(HORIZONTAL_FACING, lvt_10_1_);
            if (lvt_2_1_.isValidPosition(lvt_3_1_, lvt_4_1_)) {
               return lvt_2_1_;
            }
         }
      }

      return null;
   }

   public BlockState updatePostPlacement(BlockState p_196271_1_, Direction p_196271_2_, BlockState p_196271_3_, IWorld p_196271_4_, BlockPos p_196271_5_, BlockPos p_196271_6_) {
      return p_196271_2_.getOpposite() == p_196271_1_.get(HORIZONTAL_FACING) && !p_196271_1_.isValidPosition(p_196271_4_, p_196271_5_) ? Blocks.AIR.getDefaultState() : p_196271_1_;
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState p_180655_1_, World p_180655_2_, BlockPos p_180655_3_, Random p_180655_4_) {
      Direction lvt_5_1_ = (Direction)p_180655_1_.get(HORIZONTAL_FACING);
      double lvt_6_1_ = (double)p_180655_3_.getX() + 0.5D;
      double lvt_8_1_ = (double)p_180655_3_.getY() + 0.7D;
      double lvt_10_1_ = (double)p_180655_3_.getZ() + 0.5D;
      double lvt_12_1_ = 0.22D;
      double lvt_14_1_ = 0.27D;
      Direction lvt_16_1_ = lvt_5_1_.getOpposite();
      p_180655_2_.addParticle(ParticleTypes.SMOKE, lvt_6_1_ + 0.27D * (double)lvt_16_1_.getXOffset(), lvt_8_1_ + 0.22D, lvt_10_1_ + 0.27D * (double)lvt_16_1_.getZOffset(), 0.0D, 0.0D, 0.0D);
      p_180655_2_.addParticle(ParticleTypes.FLAME, lvt_6_1_ + 0.27D * (double)lvt_16_1_.getXOffset(), lvt_8_1_ + 0.22D, lvt_10_1_ + 0.27D * (double)lvt_16_1_.getZOffset(), 0.0D, 0.0D, 0.0D);
   }

   public BlockState rotate(BlockState p_185499_1_, Rotation p_185499_2_) {
      return (BlockState)p_185499_1_.with(HORIZONTAL_FACING, p_185499_2_.rotate((Direction)p_185499_1_.get(HORIZONTAL_FACING)));
   }

   public BlockState mirror(BlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.rotate(p_185471_2_.toRotation((Direction)p_185471_1_.get(HORIZONTAL_FACING)));
   }

   protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
      p_206840_1_.add(HORIZONTAL_FACING);
   }

   static {
      HORIZONTAL_FACING = HorizontalBlock.HORIZONTAL_FACING;
      SHAPES = Maps.newEnumMap(ImmutableMap.of(Direction.NORTH, Block.makeCuboidShape(5.5D, 3.0D, 11.0D, 10.5D, 13.0D, 16.0D), Direction.SOUTH, Block.makeCuboidShape(5.5D, 3.0D, 0.0D, 10.5D, 13.0D, 5.0D), Direction.WEST, Block.makeCuboidShape(11.0D, 3.0D, 5.5D, 16.0D, 13.0D, 10.5D), Direction.EAST, Block.makeCuboidShape(0.0D, 3.0D, 5.5D, 5.0D, 13.0D, 10.5D)));
   }
}
