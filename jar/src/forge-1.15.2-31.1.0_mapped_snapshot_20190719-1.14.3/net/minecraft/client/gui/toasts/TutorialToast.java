package net.minecraft.client.gui.toasts;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TutorialToast implements IToast {
   private final TutorialToast.Icons icon;
   private final String title;
   private final String subtitle;
   private IToast.Visibility visibility;
   private long lastDelta;
   private float displayedProgress;
   private float currentProgress;
   private final boolean hasProgressBar;

   public TutorialToast(TutorialToast.Icons p_i47487_1_, ITextComponent p_i47487_2_, @Nullable ITextComponent p_i47487_3_, boolean p_i47487_4_) {
      this.visibility = IToast.Visibility.SHOW;
      this.icon = p_i47487_1_;
      this.title = p_i47487_2_.getFormattedText();
      this.subtitle = p_i47487_3_ == null ? null : p_i47487_3_.getFormattedText();
      this.hasProgressBar = p_i47487_4_;
   }

   public IToast.Visibility draw(ToastGui p_193653_1_, long p_193653_2_) {
      p_193653_1_.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
      RenderSystem.color3f(1.0F, 1.0F, 1.0F);
      p_193653_1_.blit(0, 0, 0, 96, 160, 32);
      this.icon.draw(p_193653_1_, 6, 6);
      if (this.subtitle == null) {
         p_193653_1_.getMinecraft().fontRenderer.drawString(this.title, 30.0F, 12.0F, -11534256);
      } else {
         p_193653_1_.getMinecraft().fontRenderer.drawString(this.title, 30.0F, 7.0F, -11534256);
         p_193653_1_.getMinecraft().fontRenderer.drawString(this.subtitle, 30.0F, 18.0F, -16777216);
      }

      if (this.hasProgressBar) {
         AbstractGui.fill(3, 28, 157, 29, -1);
         float lvt_4_1_ = (float)MathHelper.clampedLerp((double)this.displayedProgress, (double)this.currentProgress, (double)((float)(p_193653_2_ - this.lastDelta) / 100.0F));
         int lvt_5_2_;
         if (this.currentProgress >= this.displayedProgress) {
            lvt_5_2_ = -16755456;
         } else {
            lvt_5_2_ = -11206656;
         }

         AbstractGui.fill(3, 28, (int)(3.0F + 154.0F * lvt_4_1_), 29, lvt_5_2_);
         this.displayedProgress = lvt_4_1_;
         this.lastDelta = p_193653_2_;
      }

      return this.visibility;
   }

   public void hide() {
      this.visibility = IToast.Visibility.HIDE;
   }

   public void setProgress(float p_193669_1_) {
      this.currentProgress = p_193669_1_;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Icons {
      MOVEMENT_KEYS(0, 0),
      MOUSE(1, 0),
      TREE(2, 0),
      RECIPE_BOOK(0, 1),
      WOODEN_PLANKS(1, 1);

      private final int column;
      private final int row;

      private Icons(int p_i47576_3_, int p_i47576_4_) {
         this.column = p_i47576_3_;
         this.row = p_i47576_4_;
      }

      public void draw(AbstractGui p_193697_1_, int p_193697_2_, int p_193697_3_) {
         RenderSystem.enableBlend();
         p_193697_1_.blit(p_193697_2_, p_193697_3_, 176 + this.column * 20, this.row * 20, 20, 20);
         RenderSystem.enableBlend();
      }
   }
}
