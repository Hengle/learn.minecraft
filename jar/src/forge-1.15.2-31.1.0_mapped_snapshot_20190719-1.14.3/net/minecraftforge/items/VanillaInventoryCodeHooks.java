package net.minecraftforge.items;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.DropperBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class VanillaInventoryCodeHooks {
   @Nullable
   public static Boolean extractHook(IHopper dest) {
      return (Boolean)getItemHandler(dest, Direction.UP).map((itemHandlerResult) -> {
         IItemHandler handler = (IItemHandler)itemHandlerResult.getKey();

         for(int i = 0; i < handler.getSlots(); ++i) {
            ItemStack extractItem = handler.extractItem(i, 1, true);
            if (!extractItem.isEmpty()) {
               for(int j = 0; j < dest.getSizeInventory(); ++j) {
                  ItemStack destStack = dest.getStackInSlot(j);
                  if (dest.isItemValidForSlot(j, extractItem) && (destStack.isEmpty() || destStack.getCount() < destStack.getMaxStackSize() && destStack.getCount() < dest.getInventoryStackLimit() && ItemHandlerHelper.canItemStacksStack(extractItem, destStack))) {
                     extractItem = handler.extractItem(i, 1, false);
                     if (destStack.isEmpty()) {
                        dest.setInventorySlotContents(j, extractItem);
                     } else {
                        destStack.grow(1);
                        dest.setInventorySlotContents(j, destStack);
                     }

                     dest.markDirty();
                     return true;
                  }
               }
            }
         }

         return false;
      }).orElse((Object)null);
   }

   public static boolean dropperInsertHook(World world, BlockPos pos, DispenserTileEntity dropper, int slot, @Nonnull ItemStack stack) {
      Direction enumfacing = (Direction)world.getBlockState(pos).get(DropperBlock.FACING);
      BlockPos blockpos = pos.offset(enumfacing);
      return (Boolean)getItemHandler(world, (double)blockpos.getX(), (double)blockpos.getY(), (double)blockpos.getZ(), enumfacing.getOpposite()).map((destinationResult) -> {
         IItemHandler itemHandler = (IItemHandler)destinationResult.getKey();
         Object destination = destinationResult.getValue();
         ItemStack dispensedStack = stack.copy().split(1);
         ItemStack remainder = putStackInInventoryAllSlots(dropper, destination, itemHandler, dispensedStack);
         if (remainder.isEmpty()) {
            remainder = stack.copy();
            remainder.shrink(1);
         } else {
            remainder = stack.copy();
         }

         dropper.setInventorySlotContents(slot, remainder);
         return false;
      }).orElse(true);
   }

   public static boolean insertHook(HopperTileEntity hopper) {
      Direction hopperFacing = (Direction)hopper.getBlockState().get(HopperBlock.FACING);
      return (Boolean)getItemHandler(hopper, hopperFacing).map((destinationResult) -> {
         IItemHandler itemHandler = (IItemHandler)destinationResult.getKey();
         Object destination = destinationResult.getValue();
         if (isFull(itemHandler)) {
            return false;
         } else {
            for(int i = 0; i < hopper.getSizeInventory(); ++i) {
               if (!hopper.getStackInSlot(i).isEmpty()) {
                  ItemStack originalSlotContents = hopper.getStackInSlot(i).copy();
                  ItemStack insertStack = hopper.decrStackSize(i, 1);
                  ItemStack remainder = putStackInInventoryAllSlots(hopper, destination, itemHandler, insertStack);
                  if (remainder.isEmpty()) {
                     return true;
                  }

                  hopper.setInventorySlotContents(i, originalSlotContents);
               }
            }

            return false;
         }
      }).orElse(false);
   }

   private static ItemStack putStackInInventoryAllSlots(TileEntity source, Object destination, IItemHandler destInventory, ItemStack stack) {
      for(int slot = 0; slot < destInventory.getSlots() && !stack.isEmpty(); ++slot) {
         stack = insertStack(source, destination, destInventory, stack, slot);
      }

      return stack;
   }

   private static ItemStack insertStack(TileEntity source, Object destination, IItemHandler destInventory, ItemStack stack, int slot) {
      ItemStack itemstack = destInventory.getStackInSlot(slot);
      if (destInventory.insertItem(slot, stack, true).isEmpty()) {
         boolean insertedItem = false;
         boolean inventoryWasEmpty = isEmpty(destInventory);
         if (itemstack.isEmpty()) {
            destInventory.insertItem(slot, stack, false);
            stack = ItemStack.EMPTY;
            insertedItem = true;
         } else if (ItemHandlerHelper.canItemStacksStack(itemstack, stack)) {
            int originalSize = stack.getCount();
            stack = destInventory.insertItem(slot, stack, false);
            insertedItem = originalSize < stack.getCount();
         }

         if (insertedItem && inventoryWasEmpty && destination instanceof HopperTileEntity) {
            HopperTileEntity destinationHopper = (HopperTileEntity)destination;
            if (!destinationHopper.mayTransfer()) {
               int k = 0;
               destinationHopper.setTransferCooldown(8 - k);
            }
         }
      }

      return stack;
   }

   private static LazyOptional<Pair<IItemHandler, Object>> getItemHandler(IHopper hopper, Direction hopperFacing) {
      double x = hopper.getXPos() + (double)hopperFacing.getXOffset();
      double y = hopper.getYPos() + (double)hopperFacing.getYOffset();
      double z = hopper.getZPos() + (double)hopperFacing.getZOffset();
      return getItemHandler(hopper.getWorld(), x, y, z, hopperFacing.getOpposite());
   }

   private static boolean isFull(IItemHandler itemHandler) {
      for(int slot = 0; slot < itemHandler.getSlots(); ++slot) {
         ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
         if (stackInSlot.isEmpty() || stackInSlot.getCount() != stackInSlot.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   private static boolean isEmpty(IItemHandler itemHandler) {
      for(int slot = 0; slot < itemHandler.getSlots(); ++slot) {
         ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
         if (stackInSlot.getCount() > 0) {
            return false;
         }
      }

      return true;
   }

   public static LazyOptional<Pair<IItemHandler, Object>> getItemHandler(World worldIn, double x, double y, double z, Direction side) {
      int i = MathHelper.floor(x);
      int j = MathHelper.floor(y);
      int k = MathHelper.floor(z);
      BlockPos blockpos = new BlockPos(i, j, k);
      BlockState state = worldIn.getBlockState(blockpos);
      if (state.hasTileEntity()) {
         TileEntity tileentity = worldIn.getTileEntity(blockpos);
         if (tileentity != null) {
            return tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side).map((capability) -> {
               return ImmutablePair.of(capability, tileentity);
            });
         }
      }

      return LazyOptional.empty();
   }
}