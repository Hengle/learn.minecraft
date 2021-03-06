package net.minecraft.client.gui.screen;

import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.LecternContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LecternScreen extends ReadBookScreen implements IHasContainer<LecternContainer> {
   private final LecternContainer field_214182_c;
   private final IContainerListener field_214183_d = new IContainerListener() {
      public void sendAllContents(Container p_71110_1_, NonNullList<ItemStack> p_71110_2_) {
         LecternScreen.this.func_214175_g();
      }

      public void sendSlotContents(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_) {
         LecternScreen.this.func_214175_g();
      }

      public void sendWindowProperty(Container p_71112_1_, int p_71112_2_, int p_71112_3_) {
         if (p_71112_2_ == 0) {
            LecternScreen.this.func_214176_h();
         }

      }
   };

   public LecternScreen(LecternContainer p_i51082_1_, PlayerInventory p_i51082_2_, ITextComponent p_i51082_3_) {
      this.field_214182_c = p_i51082_1_;
   }

   public LecternContainer getContainer() {
      return this.field_214182_c;
   }

   protected void init() {
      super.init();
      this.field_214182_c.addListener(this.field_214183_d);
   }

   public void onClose() {
      this.minecraft.player.closeScreen();
      super.onClose();
   }

   public void removed() {
      super.removed();
      this.field_214182_c.removeListener(this.field_214183_d);
   }

   protected void func_214162_b() {
      if (this.minecraft.player.isAllowEdit()) {
         this.addButton(new Button(this.width / 2 - 100, 196, 98, 20, I18n.format("gui.done"), (p_214181_1_) -> {
            this.minecraft.displayGuiScreen((Screen)null);
         }));
         this.addButton(new Button(this.width / 2 + 2, 196, 98, 20, I18n.format("lectern.take_book"), (p_214178_1_) -> {
            this.func_214179_c(3);
         }));
      } else {
         super.func_214162_b();
      }

   }

   protected void func_214165_d() {
      this.func_214179_c(1);
   }

   protected void func_214163_e() {
      this.func_214179_c(2);
   }

   protected boolean func_214153_b(int p_214153_1_) {
      if (p_214153_1_ != this.field_214182_c.getPage()) {
         this.func_214179_c(100 + p_214153_1_);
         return true;
      } else {
         return false;
      }
   }

   private void func_214179_c(int p_214179_1_) {
      this.minecraft.playerController.sendEnchantPacket(this.field_214182_c.windowId, p_214179_1_);
   }

   public boolean isPauseScreen() {
      return false;
   }

   private void func_214175_g() {
      ItemStack lvt_1_1_ = this.field_214182_c.getBook();
      this.func_214155_a(ReadBookScreen.IBookInfo.func_216917_a(lvt_1_1_));
   }

   private void func_214176_h() {
      this.func_214160_a(this.field_214182_c.getPage());
   }

   // $FF: synthetic method
   public Container getContainer() {
      return this.getContainer();
   }
}
