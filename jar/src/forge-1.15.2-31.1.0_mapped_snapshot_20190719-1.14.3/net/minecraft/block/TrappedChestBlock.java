package net.minecraft.block;

import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TrappedChestTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;

public class TrappedChestBlock extends ChestBlock {
   public TrappedChestBlock(Block.Properties p_i48306_1_) {
      super(p_i48306_1_, () -> {
         return TileEntityType.TRAPPED_CHEST;
      });
   }

   public TileEntity createNewTileEntity(IBlockReader p_196283_1_) {
      return new TrappedChestTileEntity();
   }

   protected Stat<ResourceLocation> getOpenStat() {
      return Stats.CUSTOM.get(Stats.TRIGGER_TRAPPED_CHEST);
   }

   public boolean canProvidePower(BlockState p_149744_1_) {
      return true;
   }

   public int getWeakPower(BlockState p_180656_1_, IBlockReader p_180656_2_, BlockPos p_180656_3_, Direction p_180656_4_) {
      return MathHelper.clamp(ChestTileEntity.getPlayersUsing(p_180656_2_, p_180656_3_), 0, 15);
   }

   public int getStrongPower(BlockState p_176211_1_, IBlockReader p_176211_2_, BlockPos p_176211_3_, Direction p_176211_4_) {
      return p_176211_4_ == Direction.UP ? p_176211_1_.getWeakPower(p_176211_2_, p_176211_3_, p_176211_4_) : 0;
   }
}
