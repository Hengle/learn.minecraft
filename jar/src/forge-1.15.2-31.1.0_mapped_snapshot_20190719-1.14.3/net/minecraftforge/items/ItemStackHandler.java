package net.minecraftforge.items;

import javax.annotation.Nonnull;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;

public class ItemStackHandler implements IItemHandler, IItemHandlerModifiable, INBTSerializable<CompoundNBT> {
   protected NonNullList<ItemStack> stacks;

   public ItemStackHandler() {
      this(1);
   }

   public ItemStackHandler(int size) {
      this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
   }

   public ItemStackHandler(NonNullList<ItemStack> stacks) {
      this.stacks = stacks;
   }

   public void setSize(int size) {
      this.stacks = NonNullList.withSize(size, ItemStack.EMPTY);
   }

   public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
      this.validateSlotIndex(slot);
      this.stacks.set(slot, stack);
      this.onContentsChanged(slot);
   }

   public int getSlots() {
      return this.stacks.size();
   }

   @Nonnull
   public ItemStack getStackInSlot(int slot) {
      this.validateSlotIndex(slot);
      return (ItemStack)this.stacks.get(slot);
   }

   @Nonnull
   public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
      if (stack.isEmpty()) {
         return ItemStack.EMPTY;
      } else if (!this.isItemValid(slot, stack)) {
         return stack;
      } else {
         this.validateSlotIndex(slot);
         ItemStack existing = (ItemStack)this.stacks.get(slot);
         int limit = this.getStackLimit(slot, stack);
         if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) {
               return stack;
            }

            limit -= existing.getCount();
         }

         if (limit <= 0) {
            return stack;
         } else {
            boolean reachedLimit = stack.getCount() > limit;
            if (!simulate) {
               if (existing.isEmpty()) {
                  this.stacks.set(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
               } else {
                  existing.grow(reachedLimit ? limit : stack.getCount());
               }

               this.onContentsChanged(slot);
            }

            return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
         }
      }
   }

   @Nonnull
   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if (amount == 0) {
         return ItemStack.EMPTY;
      } else {
         this.validateSlotIndex(slot);
         ItemStack existing = (ItemStack)this.stacks.get(slot);
         if (existing.isEmpty()) {
            return ItemStack.EMPTY;
         } else {
            int toExtract = Math.min(amount, existing.getMaxStackSize());
            if (existing.getCount() <= toExtract) {
               if (!simulate) {
                  this.stacks.set(slot, ItemStack.EMPTY);
                  this.onContentsChanged(slot);
               }

               return existing;
            } else {
               if (!simulate) {
                  this.stacks.set(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                  this.onContentsChanged(slot);
               }

               return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
            }
         }
      }
   }

   public int getSlotLimit(int slot) {
      return 64;
   }

   protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
      return Math.min(this.getSlotLimit(slot), stack.getMaxStackSize());
   }

   public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
      return true;
   }

   public CompoundNBT serializeNBT() {
      ListNBT nbtTagList = new ListNBT();

      for(int i = 0; i < this.stacks.size(); ++i) {
         if (!((ItemStack)this.stacks.get(i)).isEmpty()) {
            CompoundNBT itemTag = new CompoundNBT();
            itemTag.putInt("Slot", i);
            ((ItemStack)this.stacks.get(i)).write(itemTag);
            nbtTagList.add(itemTag);
         }
      }

      CompoundNBT nbt = new CompoundNBT();
      nbt.put("Items", nbtTagList);
      nbt.putInt("Size", this.stacks.size());
      return nbt;
   }

   public void deserializeNBT(CompoundNBT nbt) {
      this.setSize(nbt.contains("Size", 3) ? nbt.getInt("Size") : this.stacks.size());
      ListNBT tagList = nbt.getList("Items", 10);

      for(int i = 0; i < tagList.size(); ++i) {
         CompoundNBT itemTags = tagList.getCompound(i);
         int slot = itemTags.getInt("Slot");
         if (slot >= 0 && slot < this.stacks.size()) {
            this.stacks.set(slot, ItemStack.read(itemTags));
         }
      }

      this.onLoad();
   }

   protected void validateSlotIndex(int slot) {
      if (slot < 0 || slot >= this.stacks.size()) {
         throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.stacks.size() + ")");
      }
   }

   protected void onLoad() {
   }

   protected void onContentsChanged(int slot) {
   }
}
