package net.minecraft.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.resources.IAsyncReloader;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.ClientModLoader;

@OnlyIn(Dist.CLIENT)
public class ResourceLoadProgressGui extends LoadingGui {
   private static final ResourceLocation MOJANG_LOGO_TEXTURE = new ResourceLocation("textures/gui/title/mojang.png");
   private final Minecraft mc;
   private final IAsyncReloader asyncReloader;
   private final Consumer<Optional<Throwable>> completedCallback;
   private final boolean reloading;
   private float field_212978_f;
   private long field_212979_g = -1L;
   private long field_212980_h = -1L;

   public ResourceLoadProgressGui(Minecraft p_i225928_1_, IAsyncReloader p_i225928_2_, Consumer<Optional<Throwable>> p_i225928_3_, boolean p_i225928_4_) {
      this.mc = p_i225928_1_;
      this.asyncReloader = p_i225928_2_;
      this.completedCallback = p_i225928_3_;
      this.reloading = p_i225928_4_;
   }

   public static void loadLogoTexture(Minecraft p_212970_0_) {
      p_212970_0_.getTextureManager().func_229263_a_(MOJANG_LOGO_TEXTURE, new ResourceLoadProgressGui.MojangLogoTexture());
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      int i = this.mc.func_228018_at_().getScaledWidth();
      int j = this.mc.func_228018_at_().getScaledHeight();
      long k = Util.milliTime();
      if (this.reloading && (this.asyncReloader.asyncPartDone() || this.mc.currentScreen != null) && this.field_212980_h == -1L) {
         this.field_212980_h = k;
      }

      float f = this.field_212979_g > -1L ? (float)(k - this.field_212979_g) / 1000.0F : -1.0F;
      float f1 = this.field_212980_h > -1L ? (float)(k - this.field_212980_h) / 500.0F : -1.0F;
      float f2;
      int j1;
      if (f >= 1.0F) {
         if (this.mc.currentScreen != null) {
            this.mc.currentScreen.render(0, 0, p_render_3_);
         }

         j1 = MathHelper.ceil((1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
         fill(0, 0, i, j, 16777215 | j1 << 24);
         f2 = 1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F);
      } else if (this.reloading) {
         if (this.mc.currentScreen != null && f1 < 1.0F) {
            this.mc.currentScreen.render(p_render_1_, p_render_2_, p_render_3_);
         }

         j1 = MathHelper.ceil(MathHelper.clamp((double)f1, 0.15D, 1.0D) * 255.0D);
         fill(0, 0, i, j, 16777215 | j1 << 24);
         f2 = MathHelper.clamp(f1, 0.0F, 1.0F);
      } else {
         fill(0, 0, i, j, -1);
         f2 = 1.0F;
      }

      j1 = (this.mc.func_228018_at_().getScaledWidth() - 256) / 2;
      int i1 = (this.mc.func_228018_at_().getScaledHeight() - 256) / 2;
      this.mc.getTextureManager().bindTexture(MOJANG_LOGO_TEXTURE);
      RenderSystem.enableBlend();
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, f2);
      this.blit(j1, i1, 0, 0, 256, 256);
      float f3 = this.asyncReloader.estimateExecutionSpeed();
      this.field_212978_f = MathHelper.clamp(this.field_212978_f * 0.95F + f3 * 0.050000012F, 0.0F, 1.0F);
      ClientModLoader.renderProgressText();
      if (f < 1.0F) {
         this.func_228181_a_(i / 2 - 150, j / 4 * 3, i / 2 + 150, j / 4 * 3 + 10, 1.0F - MathHelper.clamp(f, 0.0F, 1.0F));
      }

      if (f >= 2.0F) {
         this.mc.setLoadingGui((LoadingGui)null);
      }

      if (this.field_212979_g == -1L && this.asyncReloader.fullyDone() && (!this.reloading || f1 >= 2.0F)) {
         try {
            this.asyncReloader.join();
            this.completedCallback.accept(Optional.empty());
         } catch (Throwable var15) {
            this.completedCallback.accept(Optional.of(var15));
         }

         this.field_212979_g = Util.milliTime();
         if (this.mc.currentScreen != null) {
            this.mc.currentScreen.init(this.mc, this.mc.func_228018_at_().getScaledWidth(), this.mc.func_228018_at_().getScaledHeight());
         }
      }

   }

   private void func_228181_a_(int p_228181_1_, int p_228181_2_, int p_228181_3_, int p_228181_4_, float p_228181_5_) {
      int i = MathHelper.ceil((float)(p_228181_3_ - p_228181_1_ - 1) * this.field_212978_f);
      fill(p_228181_1_ - 1, p_228181_2_ - 1, p_228181_3_ + 1, p_228181_4_ + 1, -16777216 | Math.round((1.0F - p_228181_5_) * 255.0F) << 16 | Math.round((1.0F - p_228181_5_) * 255.0F) << 8 | Math.round((1.0F - p_228181_5_) * 255.0F));
      fill(p_228181_1_, p_228181_2_, p_228181_3_, p_228181_4_, -1);
      fill(p_228181_1_ + 1, p_228181_2_ + 1, p_228181_1_ + i, p_228181_4_ - 1, -16777216 | (int)MathHelper.lerp(1.0F - p_228181_5_, 226.0F, 255.0F) << 16 | (int)MathHelper.lerp(1.0F - p_228181_5_, 40.0F, 255.0F) << 8 | (int)MathHelper.lerp(1.0F - p_228181_5_, 55.0F, 255.0F));
   }

   public boolean isPauseScreen() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   static class MojangLogoTexture extends SimpleTexture {
      public MojangLogoTexture() {
         super(ResourceLoadProgressGui.MOJANG_LOGO_TEXTURE);
      }

      protected SimpleTexture.TextureData func_215246_b(IResourceManager p_215246_1_) {
         Minecraft minecraft = Minecraft.getInstance();
         VanillaPack vanillapack = minecraft.getPackFinder().getVanillaPack();

         try {
            InputStream inputstream = vanillapack.getResourceStream(ResourcePackType.CLIENT_RESOURCES, ResourceLoadProgressGui.MOJANG_LOGO_TEXTURE);
            Throwable var5 = null;

            SimpleTexture.TextureData var7;
            try {
               SimpleTexture.TextureData simpletexture$texturedata = new SimpleTexture.TextureData((TextureMetadataSection)null, NativeImage.read(inputstream));
               var7 = simpletexture$texturedata;
            } catch (Throwable var17) {
               var5 = var17;
               throw var17;
            } finally {
               if (inputstream != null) {
                  if (var5 != null) {
                     try {
                        inputstream.close();
                     } catch (Throwable var16) {
                        var5.addSuppressed(var16);
                     }
                  } else {
                     inputstream.close();
                  }
               }

            }

            return var7;
         } catch (IOException var19) {
            return new SimpleTexture.TextureData(var19);
         }
      }
   }
}
