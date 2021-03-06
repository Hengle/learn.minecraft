package net.minecraft.client.gui.screen;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Matrix4f;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnchantmentNameParts;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantmentScreen extends ContainerScreen<EnchantmentContainer> {
   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");
   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("textures/entity/enchanting_table_book.png");
   private static final BookModel MODEL_BOOK = new BookModel();
   private final Random random = new Random();
   public int ticks;
   public float flip;
   public float oFlip;
   public float flipT;
   public float flipA;
   public float open;
   public float oOpen;
   private ItemStack last;

   public EnchantmentScreen(EnchantmentContainer p_i51090_1_, PlayerInventory p_i51090_2_, ITextComponent p_i51090_3_) {
      super(p_i51090_1_, p_i51090_2_, p_i51090_3_);
      this.last = ItemStack.EMPTY;
   }

   protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
      this.font.drawString(this.title.getFormattedText(), 12.0F, 5.0F, 4210752);
      this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0F, (float)(this.ySize - 96 + 2), 4210752);
   }

   public void tick() {
      super.tick();
      this.tickBook();
   }

   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;

      for(int k = 0; k < 3; ++k) {
         double d0 = p_mouseClicked_1_ - (double)(i + 60);
         double d1 = p_mouseClicked_3_ - (double)(j + 14 + 19 * k);
         if (d0 >= 0.0D && d1 >= 0.0D && d0 < 108.0D && d1 < 19.0D && ((EnchantmentContainer)this.container).enchantItem(this.minecraft.player, k)) {
            this.minecraft.playerController.sendEnchantPacket(((EnchantmentContainer)this.container).windowId, k);
            return true;
         }
      }

      return super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
   }

   protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
      RenderHelper.func_227783_c_();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.minecraft.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
      int i = (this.width - this.xSize) / 2;
      int j = (this.height - this.ySize) / 2;
      this.blit(i, j, 0, 0, this.xSize, this.ySize);
      RenderSystem.matrixMode(5889);
      RenderSystem.pushMatrix();
      RenderSystem.loadIdentity();
      int k = (int)this.minecraft.func_228018_at_().getGuiScaleFactor();
      RenderSystem.viewport((this.width - 320) / 2 * k, (this.height - 240) / 2 * k, 320 * k, 240 * k);
      RenderSystem.translatef(-0.34F, 0.23F, 0.0F);
      RenderSystem.multMatrix(Matrix4f.perspective(90.0D, 1.3333334F, 9.0F, 80.0F));
      RenderSystem.matrixMode(5888);
      MatrixStack matrixstack = new MatrixStack();
      matrixstack.func_227860_a_();
      MatrixStack.Entry matrixstack$entry = matrixstack.func_227866_c_();
      matrixstack$entry.func_227870_a_().func_226591_a_();
      matrixstack$entry.func_227872_b_().func_226119_c_();
      matrixstack.func_227861_a_(0.0D, 3.299999952316284D, 1984.0D);
      float f = 5.0F;
      matrixstack.func_227862_a_(5.0F, 5.0F, 5.0F);
      matrixstack.func_227863_a_(Vector3f.field_229183_f_.func_229187_a_(180.0F));
      matrixstack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(20.0F));
      float f1 = MathHelper.lerp(p_146976_1_, this.oOpen, this.open);
      matrixstack.func_227861_a_((double)((1.0F - f1) * 0.2F), (double)((1.0F - f1) * 0.1F), (double)((1.0F - f1) * 0.25F));
      float f2 = -(1.0F - f1) * 90.0F - 90.0F;
      matrixstack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(f2));
      matrixstack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180.0F));
      float f3 = MathHelper.lerp(p_146976_1_, this.oFlip, this.flip) + 0.25F;
      float f4 = MathHelper.lerp(p_146976_1_, this.oFlip, this.flip) + 0.75F;
      f3 = (f3 - (float)MathHelper.fastFloor((double)f3)) * 1.6F - 0.3F;
      f4 = (f4 - (float)MathHelper.fastFloor((double)f4)) * 1.6F - 0.3F;
      if (f3 < 0.0F) {
         f3 = 0.0F;
      }

      if (f4 < 0.0F) {
         f4 = 0.0F;
      }

      if (f3 > 1.0F) {
         f3 = 1.0F;
      }

      if (f4 > 1.0F) {
         f4 = 1.0F;
      }

      RenderSystem.enableRescaleNormal();
      MODEL_BOOK.func_228247_a_(0.0F, f3, f4, f1);
      IRenderTypeBuffer.Impl irendertypebuffer$impl = IRenderTypeBuffer.func_228455_a_(Tessellator.getInstance().getBuffer());
      IVertexBuilder ivertexbuilder = irendertypebuffer$impl.getBuffer(MODEL_BOOK.func_228282_a_(ENCHANTMENT_TABLE_BOOK_TEXTURE));
      MODEL_BOOK.func_225598_a_(matrixstack, ivertexbuilder, 15728880, OverlayTexture.field_229196_a_, 1.0F, 1.0F, 1.0F, 1.0F);
      irendertypebuffer$impl.func_228461_a_();
      matrixstack.func_227865_b_();
      RenderSystem.matrixMode(5889);
      RenderSystem.viewport(0, 0, this.minecraft.func_228018_at_().getFramebufferWidth(), this.minecraft.func_228018_at_().getFramebufferHeight());
      RenderSystem.popMatrix();
      RenderSystem.matrixMode(5888);
      RenderHelper.func_227784_d_();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      EnchantmentNameParts.getInstance().reseedRandomGenerator((long)((EnchantmentContainer)this.container).func_217005_f());
      int l = ((EnchantmentContainer)this.container).getLapisAmount();

      for(int i1 = 0; i1 < 3; ++i1) {
         int j1 = i + 60;
         int k1 = j1 + 20;
         this.setBlitOffset(0);
         this.minecraft.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
         int l1 = ((EnchantmentContainer)this.container).enchantLevels[i1];
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         if (l1 == 0) {
            this.blit(j1, j + 14 + 19 * i1, 0, 185, 108, 19);
         } else {
            String s = "" + l1;
            int i2 = 86 - this.font.getStringWidth(s);
            String s1 = EnchantmentNameParts.getInstance().generateNewRandomName(this.font, i2);
            FontRenderer fontrenderer = this.minecraft.getFontResourceManager().getFontRenderer(Minecraft.standardGalacticFontRenderer);
            int j2 = 6839882;
            if ((l >= i1 + 1 && this.minecraft.player.experienceLevel >= l1 || this.minecraft.player.abilities.isCreativeMode) && ((EnchantmentContainer)this.container).enchantClue[i1] != -1) {
               int k2 = p_146976_2_ - (i + 60);
               int l2 = p_146976_3_ - (j + 14 + 19 * i1);
               if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
                  this.blit(j1, j + 14 + 19 * i1, 0, 204, 108, 19);
                  j2 = 16777088;
               } else {
                  this.blit(j1, j + 14 + 19 * i1, 0, 166, 108, 19);
               }

               this.blit(j1 + 1, j + 15 + 19 * i1, 16 * i1, 223, 16, 16);
               fontrenderer.drawSplitString(s1, k1, j + 16 + 19 * i1, i2, j2);
               j2 = 8453920;
            } else {
               this.blit(j1, j + 14 + 19 * i1, 0, 185, 108, 19);
               this.blit(j1 + 1, j + 15 + 19 * i1, 16 * i1, 239, 16, 16);
               fontrenderer.drawSplitString(s1, k1, j + 16 + 19 * i1, i2, (j2 & 16711422) >> 1);
               j2 = 4226832;
            }

            fontrenderer = this.minecraft.fontRenderer;
            fontrenderer.drawStringWithShadow(s, (float)(k1 + 86 - fontrenderer.getStringWidth(s)), (float)(j + 16 + 19 * i1 + 7), j2);
         }
      }

   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      p_render_3_ = this.minecraft.getRenderPartialTicks();
      this.renderBackground();
      super.render(p_render_1_, p_render_2_, p_render_3_);
      this.renderHoveredToolTip(p_render_1_, p_render_2_);
      boolean flag = this.minecraft.player.abilities.isCreativeMode;
      int i = ((EnchantmentContainer)this.container).getLapisAmount();

      for(int j = 0; j < 3; ++j) {
         int k = ((EnchantmentContainer)this.container).enchantLevels[j];
         Enchantment enchantment = Enchantment.getEnchantmentByID(((EnchantmentContainer)this.container).enchantClue[j]);
         int l = ((EnchantmentContainer)this.container).worldClue[j];
         int i1 = j + 1;
         if (this.isPointInRegion(60, 14 + 19 * j, 108, 17, (double)p_render_1_, (double)p_render_2_) && k > 0) {
            List<String> list = Lists.newArrayList();
            list.add("" + TextFormatting.WHITE + TextFormatting.ITALIC + I18n.format("container.enchant.clue", enchantment == null ? "" : enchantment.getDisplayName(l).getFormattedText()));
            if (enchantment == null) {
               Collections.addAll(list, new String[]{"", TextFormatting.RED + I18n.format("forge.container.enchant.limitedEnchantability")});
            } else if (!flag) {
               list.add("");
               if (this.minecraft.player.experienceLevel < k) {
                  list.add(TextFormatting.RED + I18n.format("container.enchant.level.requirement", ((EnchantmentContainer)this.container).enchantLevels[j]));
               } else {
                  String s;
                  if (i1 == 1) {
                     s = I18n.format("container.enchant.lapis.one");
                  } else {
                     s = I18n.format("container.enchant.lapis.many", i1);
                  }

                  TextFormatting textformatting = i >= i1 ? TextFormatting.GRAY : TextFormatting.RED;
                  list.add(textformatting + "" + s);
                  if (i1 == 1) {
                     s = I18n.format("container.enchant.level.one");
                  } else {
                     s = I18n.format("container.enchant.level.many", i1);
                  }

                  list.add(TextFormatting.GRAY + "" + s);
               }
            }

            this.renderTooltip(list, p_render_1_, p_render_2_);
            break;
         }
      }

   }

   public void tickBook() {
      ItemStack itemstack = ((EnchantmentContainer)this.container).getSlot(0).getStack();
      if (!ItemStack.areItemStacksEqual(itemstack, this.last)) {
         this.last = itemstack;

         do {
            this.flipT += (float)(this.random.nextInt(4) - this.random.nextInt(4));
         } while(this.flip <= this.flipT + 1.0F && this.flip >= this.flipT - 1.0F);
      }

      ++this.ticks;
      this.oFlip = this.flip;
      this.oOpen = this.open;
      boolean flag = false;

      for(int i = 0; i < 3; ++i) {
         if (((EnchantmentContainer)this.container).enchantLevels[i] != 0) {
            flag = true;
         }
      }

      if (flag) {
         this.open += 0.2F;
      } else {
         this.open -= 0.2F;
      }

      this.open = MathHelper.clamp(this.open, 0.0F, 1.0F);
      float f1 = (this.flipT - this.flip) * 0.4F;
      float f = 0.2F;
      f1 = MathHelper.clamp(f1, -0.2F, 0.2F);
      this.flipA += (f1 - this.flipA) * 0.9F;
      this.flip += this.flipA;
   }
}
