package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.FileDownload;
import com.mojang.realmsclient.dto.WorldDownload;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsDefaultVertexFormat;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.Tezzelator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsDownloadLatestWorldScreen extends RealmsScreen {
   private static final Logger field_224175_a = LogManager.getLogger();
   private final RealmsScreen field_224176_b;
   private final WorldDownload field_224177_c;
   private final String field_224178_d;
   private final RateLimiter field_224179_e;
   private RealmsButton field_224180_f;
   private final String field_224181_g;
   private final RealmsDownloadLatestWorldScreen.DownloadStatus field_224182_h;
   private volatile String field_224183_i;
   private volatile String field_224184_j;
   private volatile String field_224185_k;
   private volatile boolean field_224186_l;
   private volatile boolean field_224187_m = true;
   private volatile boolean field_224188_n;
   private volatile boolean field_224189_o;
   private Long field_224190_p;
   private Long field_224191_q;
   private long field_224192_r;
   private int field_224193_s;
   private static final String[] field_224194_t = new String[]{"", ".", ". .", ". . ."};
   private int field_224195_u;
   private final int field_224196_v = 100;
   private int field_224197_w = -1;
   private boolean field_224198_x;
   private static final ReentrantLock field_224199_y = new ReentrantLock();

   public RealmsDownloadLatestWorldScreen(RealmsScreen p_i51770_1_, WorldDownload p_i51770_2_, String p_i51770_3_) {
      this.field_224176_b = p_i51770_1_;
      this.field_224181_g = p_i51770_3_;
      this.field_224177_c = p_i51770_2_;
      this.field_224182_h = new RealmsDownloadLatestWorldScreen.DownloadStatus();
      this.field_224178_d = getLocalizedString("mco.download.title");
      this.field_224179_e = RateLimiter.create(0.10000000149011612D);
   }

   public void func_224167_a(int p_224167_1_) {
      this.field_224197_w = p_224167_1_;
   }

   public void init() {
      this.setKeyboardHandlerSendRepeatsToGui(true);
      this.buttonsAdd(this.field_224180_f = new RealmsButton(0, this.width() / 2 - 100, this.height() - 42, 200, 20, getLocalizedString("gui.cancel")) {
         public void onPress() {
            RealmsDownloadLatestWorldScreen.this.field_224186_l = true;
            RealmsDownloadLatestWorldScreen.this.func_224174_d();
         }
      });
      this.func_224162_c();
   }

   private void func_224162_c() {
      if (!this.field_224188_n) {
         if (!this.field_224198_x && this.func_224152_a(this.field_224177_c.downloadLink) >= 5368709120L) {
            String lvt_1_1_ = getLocalizedString("mco.download.confirmation.line1", new Object[]{func_224150_b(5368709120L)});
            String lvt_2_1_ = getLocalizedString("mco.download.confirmation.line2");
            Realms.setScreen(new RealmsLongConfirmationScreen(this, RealmsLongConfirmationScreen.Type.Warning, lvt_1_1_, lvt_2_1_, false, 100));
         } else {
            this.func_224165_h();
         }

      }
   }

   public void confirmResult(boolean p_confirmResult_1_, int p_confirmResult_2_) {
      this.field_224198_x = true;
      Realms.setScreen(this);
      this.func_224165_h();
   }

   private long func_224152_a(String p_224152_1_) {
      FileDownload lvt_2_1_ = new FileDownload();
      return lvt_2_1_.func_224827_a(p_224152_1_);
   }

   public void tick() {
      super.tick();
      ++this.field_224193_s;
      if (this.field_224184_j != null && this.field_224179_e.tryAcquire(1)) {
         List<String> lvt_1_1_ = Lists.newArrayList();
         lvt_1_1_.add(this.field_224178_d);
         lvt_1_1_.add(this.field_224184_j);
         if (this.field_224185_k != null) {
            lvt_1_1_.add(this.field_224185_k + "%");
            lvt_1_1_.add(func_224153_a(this.field_224192_r));
         }

         if (this.field_224183_i != null) {
            lvt_1_1_.add(this.field_224183_i);
         }

         String lvt_2_1_ = String.join(System.lineSeparator(), lvt_1_1_);
         Realms.narrateNow(lvt_2_1_);
      }

   }

   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (p_keyPressed_1_ == 256) {
         this.field_224186_l = true;
         this.func_224174_d();
         return true;
      } else {
         return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
      }
   }

   private void func_224174_d() {
      if (this.field_224188_n && this.field_224197_w != -1 && this.field_224183_i == null) {
         this.field_224176_b.confirmResult(true, this.field_224197_w);
      }

      Realms.setScreen(this.field_224176_b);
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.renderBackground();
      if (this.field_224189_o && !this.field_224188_n) {
         this.field_224184_j = getLocalizedString("mco.download.extracting");
      }

      this.drawCenteredString(this.field_224178_d, this.width() / 2, 20, 16777215);
      this.drawCenteredString(this.field_224184_j, this.width() / 2, 50, 16777215);
      if (this.field_224187_m) {
         this.func_224161_e();
      }

      if (this.field_224182_h.field_225139_a != 0L && !this.field_224186_l) {
         this.func_224164_f();
         this.func_224149_g();
      }

      if (this.field_224183_i != null) {
         this.drawCenteredString(this.field_224183_i, this.width() / 2, 110, 16711680);
      }

      super.render(p_render_1_, p_render_2_, p_render_3_);
   }

   private void func_224161_e() {
      int lvt_1_1_ = this.fontWidth(this.field_224184_j);
      if (this.field_224193_s % 10 == 0) {
         ++this.field_224195_u;
      }

      this.drawString(field_224194_t[this.field_224195_u % field_224194_t.length], this.width() / 2 + lvt_1_1_ / 2 + 5, 50, 16777215);
   }

   private void func_224164_f() {
      double lvt_1_1_ = this.field_224182_h.field_225139_a.doubleValue() / this.field_224182_h.field_225140_b.doubleValue() * 100.0D;
      this.field_224185_k = String.format(Locale.ROOT, "%.1f", lvt_1_1_);
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.disableTexture();
      Tezzelator lvt_3_1_ = Tezzelator.instance;
      lvt_3_1_.begin(7, RealmsDefaultVertexFormat.POSITION_COLOR);
      double lvt_4_1_ = (double)(this.width() / 2 - 100);
      double lvt_6_1_ = 0.5D;
      lvt_3_1_.vertex(lvt_4_1_ - 0.5D, 95.5D, 0.0D).color(217, 210, 210, 255).endVertex();
      lvt_3_1_.vertex(lvt_4_1_ + 200.0D * lvt_1_1_ / 100.0D + 0.5D, 95.5D, 0.0D).color(217, 210, 210, 255).endVertex();
      lvt_3_1_.vertex(lvt_4_1_ + 200.0D * lvt_1_1_ / 100.0D + 0.5D, 79.5D, 0.0D).color(217, 210, 210, 255).endVertex();
      lvt_3_1_.vertex(lvt_4_1_ - 0.5D, 79.5D, 0.0D).color(217, 210, 210, 255).endVertex();
      lvt_3_1_.vertex(lvt_4_1_, 95.0D, 0.0D).color(128, 128, 128, 255).endVertex();
      lvt_3_1_.vertex(lvt_4_1_ + 200.0D * lvt_1_1_ / 100.0D, 95.0D, 0.0D).color(128, 128, 128, 255).endVertex();
      lvt_3_1_.vertex(lvt_4_1_ + 200.0D * lvt_1_1_ / 100.0D, 80.0D, 0.0D).color(128, 128, 128, 255).endVertex();
      lvt_3_1_.vertex(lvt_4_1_, 80.0D, 0.0D).color(128, 128, 128, 255).endVertex();
      lvt_3_1_.end();
      RenderSystem.enableTexture();
      this.drawCenteredString(this.field_224185_k + " %", this.width() / 2, 84, 16777215);
   }

   private void func_224149_g() {
      if (this.field_224193_s % 20 == 0) {
         if (this.field_224190_p != null) {
            long lvt_1_1_ = System.currentTimeMillis() - this.field_224191_q;
            if (lvt_1_1_ == 0L) {
               lvt_1_1_ = 1L;
            }

            this.field_224192_r = 1000L * (this.field_224182_h.field_225139_a - this.field_224190_p) / lvt_1_1_;
            this.func_224156_c(this.field_224192_r);
         }

         this.field_224190_p = this.field_224182_h.field_225139_a;
         this.field_224191_q = System.currentTimeMillis();
      } else {
         this.func_224156_c(this.field_224192_r);
      }

   }

   private void func_224156_c(long p_224156_1_) {
      if (p_224156_1_ > 0L) {
         int lvt_3_1_ = this.fontWidth(this.field_224185_k);
         String lvt_4_1_ = "(" + func_224153_a(p_224156_1_) + ")";
         this.drawString(lvt_4_1_, this.width() / 2 + lvt_3_1_ / 2 + 15, 84, 16777215);
      }

   }

   public static String func_224153_a(long p_224153_0_) {
      int lvt_2_1_ = true;
      if (p_224153_0_ < 1024L) {
         return p_224153_0_ + " B/s";
      } else {
         int lvt_3_1_ = (int)(Math.log((double)p_224153_0_) / Math.log(1024.0D));
         String lvt_4_1_ = "KMGTPE".charAt(lvt_3_1_ - 1) + "";
         return String.format(Locale.ROOT, "%.1f %sB/s", (double)p_224153_0_ / Math.pow(1024.0D, (double)lvt_3_1_), lvt_4_1_);
      }
   }

   public static String func_224150_b(long p_224150_0_) {
      int lvt_2_1_ = true;
      if (p_224150_0_ < 1024L) {
         return p_224150_0_ + " B";
      } else {
         int lvt_3_1_ = (int)(Math.log((double)p_224150_0_) / Math.log(1024.0D));
         String lvt_4_1_ = "KMGTPE".charAt(lvt_3_1_ - 1) + "";
         return String.format(Locale.ROOT, "%.0f %sB", (double)p_224150_0_ / Math.pow(1024.0D, (double)lvt_3_1_), lvt_4_1_);
      }
   }

   private void func_224165_h() {
      (new Thread(() -> {
         try {
            if (!field_224199_y.tryLock(1L, TimeUnit.SECONDS)) {
               return;
            }

            this.field_224184_j = getLocalizedString("mco.download.preparing");
            if (!this.field_224186_l) {
               this.field_224184_j = getLocalizedString("mco.download.downloading", new Object[]{this.field_224181_g});
               FileDownload lvt_1_1_ = new FileDownload();
               lvt_1_1_.func_224827_a(this.field_224177_c.downloadLink);
               lvt_1_1_.func_224830_a(this.field_224177_c, this.field_224181_g, this.field_224182_h, this.getLevelStorageSource());

               while(!lvt_1_1_.func_224835_b()) {
                  if (lvt_1_1_.func_224836_c()) {
                     lvt_1_1_.func_224834_a();
                     this.field_224183_i = getLocalizedString("mco.download.failed");
                     this.field_224180_f.setMessage(getLocalizedString("gui.done"));
                     return;
                  }

                  if (lvt_1_1_.func_224837_d()) {
                     this.field_224189_o = true;
                  }

                  if (this.field_224186_l) {
                     lvt_1_1_.func_224834_a();
                     this.func_224159_i();
                     return;
                  }

                  try {
                     Thread.sleep(500L);
                  } catch (InterruptedException var8) {
                     field_224175_a.error("Failed to check Realms backup download status");
                  }
               }

               this.field_224188_n = true;
               this.field_224184_j = getLocalizedString("mco.download.done");
               this.field_224180_f.setMessage(getLocalizedString("gui.done"));
               return;
            }

            this.func_224159_i();
         } catch (InterruptedException var9) {
            field_224175_a.error("Could not acquire upload lock");
            return;
         } catch (Exception var10) {
            this.field_224183_i = getLocalizedString("mco.download.failed");
            var10.printStackTrace();
            return;
         } finally {
            if (!field_224199_y.isHeldByCurrentThread()) {
               return;
            }

            field_224199_y.unlock();
            this.field_224187_m = false;
            this.field_224188_n = true;
         }

      })).start();
   }

   private void func_224159_i() {
      this.field_224184_j = getLocalizedString("mco.download.cancelled");
   }

   @OnlyIn(Dist.CLIENT)
   public class DownloadStatus {
      public volatile Long field_225139_a = 0L;
      public volatile Long field_225140_b = 0L;
   }
}
