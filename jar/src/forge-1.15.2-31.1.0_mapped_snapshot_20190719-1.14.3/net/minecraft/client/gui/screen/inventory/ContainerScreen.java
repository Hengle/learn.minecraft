package net.minecraft.client.gui.screen.inventory;

import com.google.common.collect.Sets;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiContainerEvent;
import net.minecraftforge.common.MinecraftForge;

@OnlyIn(Dist.CLIENT)
public abstract class ContainerScreen<T extends Container> extends Screen implements IHasContainer<T> {
   public static final ResourceLocation INVENTORY_BACKGROUND = new ResourceLocation("textures/gui/container/inventory.png");
   protected int xSize = 176;
   protected int ySize = 166;
   protected final T container;
   protected final PlayerInventory playerInventory;
   protected int guiLeft;
   protected int guiTop;
   protected Slot hoveredSlot;
   private Slot clickedSlot;
   private boolean isRightMouseClick;
   private ItemStack draggedStack;
   private int touchUpX;
   private int touchUpY;
   private Slot returningStackDestSlot;
   private long returningStackTime;
   private ItemStack returningStack;
   private Slot currentDragTargetSlot;
   private long dragItemDropDelay;
   protected final Set<Slot> dragSplittingSlots;
   protected boolean dragSplitting;
   private int dragSplittingLimit;
   private int dragSplittingButton;
   private boolean ignoreMouseUp;
   private int dragSplittingRemnant;
   private long lastClickTime;
   private Slot lastClickSlot;
   private int lastClickButton;
   private boolean doubleClick;
   private ItemStack shiftClickedSlot;
   protected int slotColor;

   public ContainerScreen(T p_i51105_1_, PlayerInventory p_i51105_2_, ITextComponent p_i51105_3_) {
      super(p_i51105_3_);
      this.draggedStack = ItemStack.EMPTY;
      this.returningStack = ItemStack.EMPTY;
      this.dragSplittingSlots = Sets.newHashSet();
      this.shiftClickedSlot = ItemStack.EMPTY;
      this.slotColor = -2130706433;
      this.container = p_i51105_1_;
      this.playerInventory = p_i51105_2_;
      this.ignoreMouseUp = true;
   }

   protected void init() {
      super.init();
      this.guiLeft = (this.width - this.xSize) / 2;
      this.guiTop = (this.height - this.ySize) / 2;
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      int i = this.guiLeft;
      int j = this.guiTop;
      this.drawGuiContainerBackgroundLayer(p_render_3_, p_render_1_, p_render_2_);
      MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.DrawBackground(this, p_render_1_, p_render_2_));
      RenderSystem.disableRescaleNormal();
      RenderSystem.disableDepthTest();
      super.render(p_render_1_, p_render_2_, p_render_3_);
      RenderSystem.pushMatrix();
      RenderSystem.translatef((float)i, (float)j, 0.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableRescaleNormal();
      this.hoveredSlot = null;
      int k = true;
      int l = true;
      RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

      int l2;
      int i3;
      for(int i1 = 0; i1 < this.container.inventorySlots.size(); ++i1) {
         Slot slot = (Slot)this.container.inventorySlots.get(i1);
         if (slot.isEnabled()) {
            this.drawSlot(slot);
         }

         if (this.isSlotSelected(slot, (double)p_render_1_, (double)p_render_2_) && slot.isEnabled()) {
            this.hoveredSlot = slot;
            RenderSystem.disableDepthTest();
            int j1 = slot.xPos;
            l2 = slot.yPos;
            RenderSystem.colorMask(true, true, true, false);
            i3 = this.getSlotColor(i1);
            this.fillGradient(j1, l2, j1 + 16, l2 + 16, i3, i3);
            RenderSystem.colorMask(true, true, true, true);
            RenderSystem.enableDepthTest();
         }
      }

      this.drawGuiContainerForegroundLayer(p_render_1_, p_render_2_);
      MinecraftForge.EVENT_BUS.post(new GuiContainerEvent.DrawForeground(this, p_render_1_, p_render_2_));
      PlayerInventory playerinventory = this.minecraft.player.inventory;
      ItemStack itemstack = this.draggedStack.isEmpty() ? playerinventory.getItemStack() : this.draggedStack;
      if (!itemstack.isEmpty()) {
         int j2 = true;
         l2 = this.draggedStack.isEmpty() ? 8 : 16;
         String s = null;
         if (!this.draggedStack.isEmpty() && this.isRightMouseClick) {
            itemstack = itemstack.copy();
            itemstack.setCount(MathHelper.ceil((float)itemstack.getCount() / 2.0F));
         } else if (this.dragSplitting && this.dragSplittingSlots.size() > 1) {
            itemstack = itemstack.copy();
            itemstack.setCount(this.dragSplittingRemnant);
            if (itemstack.isEmpty()) {
               s = "" + TextFormatting.YELLOW + "0";
            }
         }

         this.drawItemStack(itemstack, p_render_1_ - i - 8, p_render_2_ - j - l2, s);
      }

      if (!this.returningStack.isEmpty()) {
         float f = (float)(Util.milliTime() - this.returningStackTime) / 100.0F;
         if (f >= 1.0F) {
            f = 1.0F;
            this.returningStack = ItemStack.EMPTY;
         }

         l2 = this.returningStackDestSlot.xPos - this.touchUpX;
         i3 = this.returningStackDestSlot.yPos - this.touchUpY;
         int l1 = this.touchUpX + (int)((float)l2 * f);
         int i2 = this.touchUpY + (int)((float)i3 * f);
         this.drawItemStack(this.returningStack, l1, i2, (String)null);
      }

      RenderSystem.popMatrix();
      RenderSystem.enableDepthTest();
   }

   protected void renderHoveredToolTip(int p_191948_1_, int p_191948_2_) {
      if (this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
         this.renderTooltip(this.hoveredSlot.getStack(), p_191948_1_, p_191948_2_);
      }

   }

   private void drawItemStack(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_) {
      RenderSystem.translatef(0.0F, 0.0F, 32.0F);
      this.setBlitOffset(200);
      this.itemRenderer.zLevel = 200.0F;
      FontRenderer font = p_146982_1_.getItem().getFontRenderer(p_146982_1_);
      if (font == null) {
         font = this.font;
      }

      this.itemRenderer.renderItemAndEffectIntoGUI(p_146982_1_, p_146982_2_, p_146982_3_);
      this.itemRenderer.renderItemOverlayIntoGUI(font, p_146982_1_, p_146982_2_, p_146982_3_ - (this.draggedStack.isEmpty() ? 0 : 8), p_146982_4_);
      this.setBlitOffset(0);
      this.itemRenderer.zLevel = 0.0F;
   }

   protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
   }

   protected abstract void drawGuiContainerBackgroundLayer(float var1, int var2, int var3);

   private void drawSlot(Slot p_146977_1_) {
      int i = p_146977_1_.xPos;
      int j = p_146977_1_.yPos;
      ItemStack itemstack = p_146977_1_.getStack();
      boolean flag = false;
      boolean flag1 = p_146977_1_ == this.clickedSlot && !this.draggedStack.isEmpty() && !this.isRightMouseClick;
      ItemStack itemstack1 = this.minecraft.player.inventory.getItemStack();
      String s = null;
      if (p_146977_1_ == this.clickedSlot && !this.draggedStack.isEmpty() && this.isRightMouseClick && !itemstack.isEmpty()) {
         itemstack = itemstack.copy();
         itemstack.setCount(itemstack.getCount() / 2);
      } else if (this.dragSplitting && this.dragSplittingSlots.contains(p_146977_1_) && !itemstack1.isEmpty()) {
         if (this.dragSplittingSlots.size() == 1) {
            return;
         }

         if (Container.canAddItemToSlot(p_146977_1_, itemstack1, true) && this.container.canDragIntoSlot(p_146977_1_)) {
            itemstack = itemstack1.copy();
            flag = true;
            Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack, p_146977_1_.getStack().isEmpty() ? 0 : p_146977_1_.getStack().getCount());
            int k = Math.min(itemstack.getMaxStackSize(), p_146977_1_.getItemStackLimit(itemstack));
            if (itemstack.getCount() > k) {
               s = TextFormatting.YELLOW.toString() + k;
               itemstack.setCount(k);
            }
         } else {
            this.dragSplittingSlots.remove(p_146977_1_);
            this.updateDragSplitting();
         }
      }

      this.setBlitOffset(100);
      this.itemRenderer.zLevel = 100.0F;
      if (itemstack.isEmpty() && p_146977_1_.isEnabled()) {
         Pair<ResourceLocation, ResourceLocation> pair = p_146977_1_.func_225517_c_();
         if (pair != null) {
            TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)this.minecraft.func_228015_a_((ResourceLocation)pair.getFirst()).apply(pair.getSecond());
            this.minecraft.getTextureManager().bindTexture(textureatlassprite.func_229241_m_().func_229223_g_());
            blit(i, j, this.getBlitOffset(), 16, 16, textureatlassprite);
            flag1 = true;
         }
      }

      if (!flag1) {
         if (flag) {
            fill(i, j, i + 16, j + 16, -2130706433);
         }

         RenderSystem.enableDepthTest();
         this.itemRenderer.renderItemAndEffectIntoGUI(this.minecraft.player, itemstack, i, j);
         this.itemRenderer.renderItemOverlayIntoGUI(this.font, itemstack, i, j, s);
      }

      this.itemRenderer.zLevel = 0.0F;
      this.setBlitOffset(0);
   }

   private void updateDragSplitting() {
      ItemStack itemstack = this.minecraft.player.inventory.getItemStack();
      if (!itemstack.isEmpty() && this.dragSplitting) {
         if (this.dragSplittingLimit == 2) {
            this.dragSplittingRemnant = itemstack.getMaxStackSize();
         } else {
            this.dragSplittingRemnant = itemstack.getCount();

            ItemStack itemstack1;
            int i;
            for(Iterator var2 = this.dragSplittingSlots.iterator(); var2.hasNext(); this.dragSplittingRemnant -= itemstack1.getCount() - i) {
               Slot slot = (Slot)var2.next();
               itemstack1 = itemstack.copy();
               ItemStack itemstack2 = slot.getStack();
               i = itemstack2.isEmpty() ? 0 : itemstack2.getCount();
               Container.computeStackSize(this.dragSplittingSlots, this.dragSplittingLimit, itemstack1, i);
               int j = Math.min(itemstack1.getMaxStackSize(), slot.getItemStackLimit(itemstack1));
               if (itemstack1.getCount() > j) {
                  itemstack1.setCount(j);
               }
            }
         }
      }

   }

   private Slot getSelectedSlot(double p_195360_1_, double p_195360_3_) {
      for(int i = 0; i < this.container.inventorySlots.size(); ++i) {
         Slot slot = (Slot)this.container.inventorySlots.get(i);
         if (this.isSlotSelected(slot, p_195360_1_, p_195360_3_) && slot.isEnabled()) {
            return slot;
         }
      }

      return null;
   }

   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      if (super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
         return true;
      } else {
         InputMappings.Input mouseKey = InputMappings.Type.MOUSE.getOrMakeInput(p_mouseClicked_5_);
         boolean flag = this.minecraft.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey);
         Slot slot = this.getSelectedSlot(p_mouseClicked_1_, p_mouseClicked_3_);
         long i = Util.milliTime();
         this.doubleClick = this.lastClickSlot == slot && i - this.lastClickTime < 250L && this.lastClickButton == p_mouseClicked_5_;
         this.ignoreMouseUp = false;
         if (p_mouseClicked_5_ == 0 || p_mouseClicked_5_ == 1 || flag) {
            int j = this.guiLeft;
            int k = this.guiTop;
            boolean flag1 = this.hasClickedOutside(p_mouseClicked_1_, p_mouseClicked_3_, j, k, p_mouseClicked_5_);
            if (slot != null) {
               flag1 = false;
            }

            int l = -1;
            if (slot != null) {
               l = slot.slotNumber;
            }

            if (flag1) {
               l = -999;
            }

            if (this.minecraft.gameSettings.touchscreen && flag1 && this.minecraft.player.inventory.getItemStack().isEmpty()) {
               this.minecraft.displayGuiScreen((Screen)null);
               return true;
            }

            if (l != -1) {
               if (this.minecraft.gameSettings.touchscreen) {
                  if (slot != null && slot.getHasStack()) {
                     this.clickedSlot = slot;
                     this.draggedStack = ItemStack.EMPTY;
                     this.isRightMouseClick = p_mouseClicked_5_ == 1;
                  } else {
                     this.clickedSlot = null;
                  }
               } else if (!this.dragSplitting) {
                  if (this.minecraft.player.inventory.getItemStack().isEmpty()) {
                     if (this.minecraft.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) {
                        this.handleMouseClick(slot, l, p_mouseClicked_5_, ClickType.CLONE);
                     } else {
                        boolean flag2 = l != -999 && (InputMappings.isKeyDown(Minecraft.getInstance().func_228018_at_().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().func_228018_at_().getHandle(), 344));
                        ClickType clicktype = ClickType.PICKUP;
                        if (flag2) {
                           this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.EMPTY;
                           clicktype = ClickType.QUICK_MOVE;
                        } else if (l == -999) {
                           clicktype = ClickType.THROW;
                        }

                        this.handleMouseClick(slot, l, p_mouseClicked_5_, clicktype);
                     }

                     this.ignoreMouseUp = true;
                  } else {
                     this.dragSplitting = true;
                     this.dragSplittingButton = p_mouseClicked_5_;
                     this.dragSplittingSlots.clear();
                     if (p_mouseClicked_5_ == 0) {
                        this.dragSplittingLimit = 0;
                     } else if (p_mouseClicked_5_ == 1) {
                        this.dragSplittingLimit = 1;
                     } else if (this.minecraft.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) {
                        this.dragSplittingLimit = 2;
                     }
                  }
               }
            }
         }

         this.lastClickSlot = slot;
         this.lastClickTime = i;
         this.lastClickButton = p_mouseClicked_5_;
         return true;
      }
   }

   protected boolean hasClickedOutside(double p_195361_1_, double p_195361_3_, int p_195361_5_, int p_195361_6_, int p_195361_7_) {
      return p_195361_1_ < (double)p_195361_5_ || p_195361_3_ < (double)p_195361_6_ || p_195361_1_ >= (double)(p_195361_5_ + this.xSize) || p_195361_3_ >= (double)(p_195361_6_ + this.ySize);
   }

   public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
      Slot slot = this.getSelectedSlot(p_mouseDragged_1_, p_mouseDragged_3_);
      ItemStack itemstack = this.minecraft.player.inventory.getItemStack();
      if (this.clickedSlot != null && this.minecraft.gameSettings.touchscreen) {
         if (p_mouseDragged_5_ == 0 || p_mouseDragged_5_ == 1) {
            if (this.draggedStack.isEmpty()) {
               if (slot != this.clickedSlot && !this.clickedSlot.getStack().isEmpty()) {
                  this.draggedStack = this.clickedSlot.getStack().copy();
               }
            } else if (this.draggedStack.getCount() > 1 && slot != null && Container.canAddItemToSlot(slot, this.draggedStack, false)) {
               long i = Util.milliTime();
               if (this.currentDragTargetSlot == slot) {
                  if (i - this.dragItemDropDelay > 500L) {
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
                     this.handleMouseClick(slot, slot.slotNumber, 1, ClickType.PICKUP);
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, 0, ClickType.PICKUP);
                     this.dragItemDropDelay = i + 750L;
                     this.draggedStack.shrink(1);
                  }
               } else {
                  this.currentDragTargetSlot = slot;
                  this.dragItemDropDelay = i;
               }
            }
         }
      } else if (this.dragSplitting && slot != null && !itemstack.isEmpty() && (itemstack.getCount() > this.dragSplittingSlots.size() || this.dragSplittingLimit == 2) && Container.canAddItemToSlot(slot, itemstack, true) && slot.isItemValid(itemstack) && this.container.canDragIntoSlot(slot)) {
         this.dragSplittingSlots.add(slot);
         this.updateDragSplitting();
      }

      return true;
   }

   public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
      Slot slot = this.getSelectedSlot(p_mouseReleased_1_, p_mouseReleased_3_);
      int i = this.guiLeft;
      int j = this.guiTop;
      boolean flag = this.hasClickedOutside(p_mouseReleased_1_, p_mouseReleased_3_, i, j, p_mouseReleased_5_);
      if (slot != null) {
         flag = false;
      }

      InputMappings.Input mouseKey = InputMappings.Type.MOUSE.getOrMakeInput(p_mouseReleased_5_);
      int k = -1;
      if (slot != null) {
         k = slot.slotNumber;
      }

      if (flag) {
         k = -999;
      }

      Slot slot1;
      Iterator var14;
      if (this.doubleClick && slot != null && p_mouseReleased_5_ == 0 && this.container.canMergeSlot(ItemStack.EMPTY, slot)) {
         if (hasShiftDown()) {
            if (!this.shiftClickedSlot.isEmpty()) {
               var14 = this.container.inventorySlots.iterator();

               while(var14.hasNext()) {
                  slot1 = (Slot)var14.next();
                  if (slot1 != null && slot1.canTakeStack(this.minecraft.player) && slot1.getHasStack() && slot1.isSameInventory(slot) && Container.canAddItemToSlot(slot1, this.shiftClickedSlot, true)) {
                     this.handleMouseClick(slot1, slot1.slotNumber, p_mouseReleased_5_, ClickType.QUICK_MOVE);
                  }
               }
            }
         } else {
            this.handleMouseClick(slot, k, p_mouseReleased_5_, ClickType.PICKUP_ALL);
         }

         this.doubleClick = false;
         this.lastClickTime = 0L;
      } else {
         if (this.dragSplitting && this.dragSplittingButton != p_mouseReleased_5_) {
            this.dragSplitting = false;
            this.dragSplittingSlots.clear();
            this.ignoreMouseUp = true;
            return true;
         }

         if (this.ignoreMouseUp) {
            this.ignoreMouseUp = false;
            return true;
         }

         boolean flag1;
         if (this.clickedSlot != null && this.minecraft.gameSettings.touchscreen) {
            if (p_mouseReleased_5_ == 0 || p_mouseReleased_5_ == 1) {
               if (this.draggedStack.isEmpty() && slot != this.clickedSlot) {
                  this.draggedStack = this.clickedSlot.getStack();
               }

               flag1 = Container.canAddItemToSlot(slot, this.draggedStack, false);
               if (k != -1 && !this.draggedStack.isEmpty() && flag1) {
                  this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, p_mouseReleased_5_, ClickType.PICKUP);
                  this.handleMouseClick(slot, k, 0, ClickType.PICKUP);
                  if (this.minecraft.player.inventory.getItemStack().isEmpty()) {
                     this.returningStack = ItemStack.EMPTY;
                  } else {
                     this.handleMouseClick(this.clickedSlot, this.clickedSlot.slotNumber, p_mouseReleased_5_, ClickType.PICKUP);
                     this.touchUpX = MathHelper.floor(p_mouseReleased_1_ - (double)i);
                     this.touchUpY = MathHelper.floor(p_mouseReleased_3_ - (double)j);
                     this.returningStackDestSlot = this.clickedSlot;
                     this.returningStack = this.draggedStack;
                     this.returningStackTime = Util.milliTime();
                  }
               } else if (!this.draggedStack.isEmpty()) {
                  this.touchUpX = MathHelper.floor(p_mouseReleased_1_ - (double)i);
                  this.touchUpY = MathHelper.floor(p_mouseReleased_3_ - (double)j);
                  this.returningStackDestSlot = this.clickedSlot;
                  this.returningStack = this.draggedStack;
                  this.returningStackTime = Util.milliTime();
               }

               this.draggedStack = ItemStack.EMPTY;
               this.clickedSlot = null;
            }
         } else if (this.dragSplitting && !this.dragSplittingSlots.isEmpty()) {
            this.handleMouseClick((Slot)null, -999, Container.getQuickcraftMask(0, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
            var14 = this.dragSplittingSlots.iterator();

            while(var14.hasNext()) {
               slot1 = (Slot)var14.next();
               this.handleMouseClick(slot1, slot1.slotNumber, Container.getQuickcraftMask(1, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
            }

            this.handleMouseClick((Slot)null, -999, Container.getQuickcraftMask(2, this.dragSplittingLimit), ClickType.QUICK_CRAFT);
         } else if (!this.minecraft.player.inventory.getItemStack().isEmpty()) {
            if (this.minecraft.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) {
               this.handleMouseClick(slot, k, p_mouseReleased_5_, ClickType.CLONE);
            } else {
               flag1 = k != -999 && (InputMappings.isKeyDown(Minecraft.getInstance().func_228018_at_().getHandle(), 340) || InputMappings.isKeyDown(Minecraft.getInstance().func_228018_at_().getHandle(), 344));
               if (flag1) {
                  this.shiftClickedSlot = slot != null && slot.getHasStack() ? slot.getStack().copy() : ItemStack.EMPTY;
               }

               this.handleMouseClick(slot, k, p_mouseReleased_5_, flag1 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
            }
         }
      }

      if (this.minecraft.player.inventory.getItemStack().isEmpty()) {
         this.lastClickTime = 0L;
      }

      this.dragSplitting = false;
      return true;
   }

   private boolean isSlotSelected(Slot p_195362_1_, double p_195362_2_, double p_195362_4_) {
      return this.isPointInRegion(p_195362_1_.xPos, p_195362_1_.yPos, 16, 16, p_195362_2_, p_195362_4_);
   }

   protected boolean isPointInRegion(int p_195359_1_, int p_195359_2_, int p_195359_3_, int p_195359_4_, double p_195359_5_, double p_195359_7_) {
      int i = this.guiLeft;
      int j = this.guiTop;
      p_195359_5_ -= (double)i;
      p_195359_7_ -= (double)j;
      return p_195359_5_ >= (double)(p_195359_1_ - 1) && p_195359_5_ < (double)(p_195359_1_ + p_195359_3_ + 1) && p_195359_7_ >= (double)(p_195359_2_ - 1) && p_195359_7_ < (double)(p_195359_2_ + p_195359_4_ + 1);
   }

   protected void handleMouseClick(Slot p_184098_1_, int p_184098_2_, int p_184098_3_, ClickType p_184098_4_) {
      if (p_184098_1_ != null) {
         p_184098_2_ = p_184098_1_.slotNumber;
      }

      this.minecraft.playerController.windowClick(this.container.windowId, p_184098_2_, p_184098_3_, p_184098_4_, this.minecraft.player);
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
         return true;
      } else {
         InputMappings.Input mouseKey = InputMappings.getInputByCode(p_keyPressed_1_, p_keyPressed_2_);
         if (p_keyPressed_1_ != 256 && !this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey)) {
            if (this.func_195363_d(p_keyPressed_1_, p_keyPressed_2_)) {
               return true;
            } else {
               if (this.hoveredSlot != null && this.hoveredSlot.getHasStack()) {
                  if (this.minecraft.gameSettings.keyBindPickBlock.isActiveAndMatches(mouseKey)) {
                     this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, 0, ClickType.CLONE);
                     return true;
                  }

                  if (this.minecraft.gameSettings.keyBindDrop.isActiveAndMatches(mouseKey)) {
                     this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, hasControlDown() ? 1 : 0, ClickType.THROW);
                     return true;
                  }
               } else if (this.minecraft.gameSettings.keyBindDrop.isActiveAndMatches(mouseKey)) {
                  return true;
               }

               return false;
            }
         } else {
            this.minecraft.player.closeScreen();
            return true;
         }
      }
   }

   protected boolean func_195363_d(int p_195363_1_, int p_195363_2_) {
      if (this.minecraft.player.inventory.getItemStack().isEmpty() && this.hoveredSlot != null) {
         for(int i = 0; i < 9; ++i) {
            if (this.minecraft.gameSettings.keyBindsHotbar[i].isActiveAndMatches(InputMappings.getInputByCode(p_195363_1_, p_195363_2_))) {
               this.handleMouseClick(this.hoveredSlot, this.hoveredSlot.slotNumber, i, ClickType.SWAP);
               return true;
            }
         }
      }

      return false;
   }

   public void removed() {
      if (this.minecraft.player != null) {
         this.container.onContainerClosed(this.minecraft.player);
      }

   }

   public boolean isPauseScreen() {
      return false;
   }

   public void tick() {
      super.tick();
      if (!this.minecraft.player.isAlive() || this.minecraft.player.removed) {
         this.minecraft.player.closeScreen();
      }

   }

   public T getContainer() {
      return this.container;
   }

   @Nullable
   public Slot getSlotUnderMouse() {
      return this.hoveredSlot;
   }

   public int getGuiLeft() {
      return this.guiLeft;
   }

   public int getGuiTop() {
      return this.guiTop;
   }

   public int getXSize() {
      return this.xSize;
   }

   public int getYSize() {
      return this.ySize;
   }

   public int getSlotColor(int p_getSlotColor_1_) {
      return this.slotColor;
   }
}
