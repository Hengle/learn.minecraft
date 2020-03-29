package net.minecraft.client.gui.screen.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChestScreen extends ContainerScreen<ChestContainer> implements IHasContainer<ChestContainer> {
   private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
   private final int inventoryRows;

   public ChestScreen(ChestContainer p_i51095_1_, PlayerInventory p_i51095_2_, ITextComponent p_i51095_3_) {
      super(p_i51095_1_, p_i51095_2_, p_i51095_3_);
      this.passEvents = false;
      int lvt_4_1_ = true;
      int lvt_5_1_ = true;
      this.inventoryRows = p_i51095_1_.getNumRows();
      this.ySize = 114 + this.inventoryRows * 18;
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      super.render(p_render_1_, p_render_2_, p_render_3_);
      this.renderHoveredToolTip(p_render_1_, p_render_2_);
   }

   protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
      this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
      this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
      int lvt_4_1_ = (this.width - this.xSize) / 2;
      int lvt_5_1_ = (this.height - this.ySize) / 2;
      this.blit(lvt_4_1_, lvt_5_1_, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
      this.blit(lvt_4_1_, lvt_5_1_ + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
   }
}
