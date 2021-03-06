package net.minecraft.client.audio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.sound.PlaySoundSourceEvent;
import net.minecraftforge.client.event.sound.PlayStreamingSourceEvent;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.client.event.sound.SoundSetupEvent;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

@OnlyIn(Dist.CLIENT)
public class SoundEngine {
   private static final Marker LOG_MARKER = MarkerManager.getMarker("SOUNDS");
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Set<ResourceLocation> UNABLE_TO_PLAY = Sets.newHashSet();
   public final SoundHandler sndHandler;
   private final GameSettings options;
   private boolean loaded;
   private final SoundSystem sndSystem = new SoundSystem();
   private final Listener listener;
   private final AudioStreamManager audioStreamManager;
   private final SoundEngineExecutor executor;
   private final ChannelManager channelManager;
   private int ticks;
   private final Map<ISound, ChannelManager.Entry> playingSoundsChannel;
   private final Multimap<SoundCategory, ISound> field_217943_n;
   private final List<ITickableSound> tickableSounds;
   private final Map<ISound, Integer> delayedSounds;
   private final Map<ISound, Integer> playingSoundsStopTime;
   private final List<ISoundEventListener> listeners;
   private final List<ITickableSound> field_229361_s_;
   private final List<Sound> soundsToPreload;

   public SoundEngine(SoundHandler p_i50892_1_, GameSettings p_i50892_2_, IResourceManager p_i50892_3_) {
      this.listener = this.sndSystem.getListener();
      this.executor = new SoundEngineExecutor();
      this.channelManager = new ChannelManager(this.sndSystem, this.executor);
      this.playingSoundsChannel = Maps.newHashMap();
      this.field_217943_n = HashMultimap.create();
      this.tickableSounds = Lists.newArrayList();
      this.delayedSounds = Maps.newHashMap();
      this.playingSoundsStopTime = Maps.newHashMap();
      this.listeners = Lists.newArrayList();
      this.field_229361_s_ = Lists.newArrayList();
      this.soundsToPreload = Lists.newArrayList();
      this.sndHandler = p_i50892_1_;
      this.options = p_i50892_2_;
      this.audioStreamManager = new AudioStreamManager(p_i50892_3_);
      MinecraftForge.EVENT_BUS.post(new SoundSetupEvent(this));
   }

   public void reload() {
      UNABLE_TO_PLAY.clear();
      Iterator var1 = Registry.SOUND_EVENT.iterator();

      while(var1.hasNext()) {
         SoundEvent soundevent = (SoundEvent)var1.next();
         ResourceLocation resourcelocation = soundevent.getName();
         if (this.sndHandler.getAccessor(resourcelocation) == null) {
            LOGGER.warn("Missing sound for event: {}", Registry.SOUND_EVENT.getKey(soundevent));
            UNABLE_TO_PLAY.add(resourcelocation);
         }
      }

      this.unload();
      this.load();
      MinecraftForge.EVENT_BUS.post(new SoundLoadEvent(this));
   }

   private synchronized void load() {
      if (!this.loaded) {
         try {
            this.sndSystem.func_216404_a();
            this.listener.init();
            this.listener.setGain(this.options.getSoundLevel(SoundCategory.MASTER));
            CompletableFuture var10000 = this.audioStreamManager.func_217908_a(this.soundsToPreload);
            List var10001 = this.soundsToPreload;
            var10000.thenRun(var10001::clear);
            this.loaded = true;
            LOGGER.info(LOG_MARKER, "Sound engine started");
         } catch (RuntimeException var2) {
            LOGGER.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", var2);
         }
      }

   }

   private float getVolume(SoundCategory p_188769_1_) {
      return p_188769_1_ != null && p_188769_1_ != SoundCategory.MASTER ? this.options.getSoundLevel(p_188769_1_) : 1.0F;
   }

   public void setVolume(SoundCategory p_188771_1_, float p_188771_2_) {
      if (this.loaded) {
         if (p_188771_1_ == SoundCategory.MASTER) {
            this.listener.setGain(p_188771_2_);
         } else {
            this.playingSoundsChannel.forEach((p_lambda$setVolume$1_1_, p_lambda$setVolume$1_2_) -> {
               float f = this.getClampedVolume(p_lambda$setVolume$1_1_);
               p_lambda$setVolume$1_2_.runOnSoundExecutor((p_lambda$null$0_1_) -> {
                  if (f <= 0.0F) {
                     p_lambda$null$0_1_.func_216418_f();
                  } else {
                     p_lambda$null$0_1_.func_216430_b(f);
                  }

               });
            });
         }
      }

   }

   public void unload() {
      if (this.loaded) {
         this.stopAllSounds();
         this.audioStreamManager.func_217912_a();
         this.sndSystem.func_216409_b();
         this.loaded = false;
      }

   }

   public void stop(ISound p_148602_1_) {
      if (this.loaded) {
         ChannelManager.Entry channelmanager$entry = (ChannelManager.Entry)this.playingSoundsChannel.get(p_148602_1_);
         if (channelmanager$entry != null) {
            channelmanager$entry.runOnSoundExecutor(SoundSource::func_216418_f);
         }
      }

   }

   public void stopAllSounds() {
      if (this.loaded) {
         this.executor.restart();
         this.playingSoundsChannel.values().forEach((p_lambda$stopAllSounds$2_0_) -> {
            p_lambda$stopAllSounds$2_0_.runOnSoundExecutor(SoundSource::func_216418_f);
         });
         this.playingSoundsChannel.clear();
         this.channelManager.releaseAll();
         this.delayedSounds.clear();
         this.tickableSounds.clear();
         this.field_217943_n.clear();
         this.playingSoundsStopTime.clear();
         this.field_229361_s_.clear();
      }

   }

   public void addListener(ISoundEventListener p_188774_1_) {
      this.listeners.add(p_188774_1_);
   }

   public void removeListener(ISoundEventListener p_188773_1_) {
      this.listeners.remove(p_188773_1_);
   }

   public void tick(boolean p_217921_1_) {
      if (!p_217921_1_) {
         this.func_217927_h();
      }

      this.channelManager.tick();
   }

   private void func_217927_h() {
      ++this.ticks;
      this.field_229361_s_.forEach(this::play);
      this.field_229361_s_.clear();
      Iterator iterator = this.tickableSounds.iterator();

      while(iterator.hasNext()) {
         ITickableSound itickablesound = (ITickableSound)iterator.next();
         itickablesound.tick();
         if (itickablesound.isDonePlaying()) {
            this.stop(itickablesound);
         } else {
            float f = this.getClampedVolume(itickablesound);
            float f1 = this.getClampedPitch(itickablesound);
            Vec3d vec3d = new Vec3d((double)itickablesound.getX(), (double)itickablesound.getY(), (double)itickablesound.getZ());
            ChannelManager.Entry channelmanager$entry = (ChannelManager.Entry)this.playingSoundsChannel.get(itickablesound);
            if (channelmanager$entry != null) {
               channelmanager$entry.runOnSoundExecutor((p_lambda$func_217927_h$3_3_) -> {
                  p_lambda$func_217927_h$3_3_.func_216430_b(f);
                  p_lambda$func_217927_h$3_3_.func_216422_a(f1);
                  p_lambda$func_217927_h$3_3_.func_216420_a(vec3d);
               });
            }
         }
      }

      iterator = this.playingSoundsChannel.entrySet().iterator();

      ISound isound;
      while(iterator.hasNext()) {
         Entry<ISound, ChannelManager.Entry> entry = (Entry)iterator.next();
         ChannelManager.Entry channelmanager$entry1 = (ChannelManager.Entry)entry.getValue();
         isound = (ISound)entry.getKey();
         float f2 = this.options.getSoundLevel(isound.getCategory());
         if (f2 <= 0.0F) {
            channelmanager$entry1.runOnSoundExecutor(SoundSource::func_216418_f);
            iterator.remove();
         } else if (channelmanager$entry1.isReleased()) {
            int j = (Integer)this.playingSoundsStopTime.get(isound);
            if (j <= this.ticks) {
               int i = isound.getRepeatDelay();
               if (isound.canRepeat() && i > 0) {
                  this.delayedSounds.put(isound, this.ticks + i);
               }

               iterator.remove();
               LOGGER.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", channelmanager$entry1);
               this.playingSoundsStopTime.remove(isound);

               try {
                  this.field_217943_n.remove(isound.getCategory(), isound);
               } catch (RuntimeException var9) {
               }

               if (isound instanceof ITickableSound) {
                  this.tickableSounds.remove(isound);
               }
            }
         }
      }

      Iterator iterator1 = this.delayedSounds.entrySet().iterator();

      while(iterator1.hasNext()) {
         Entry<ISound, Integer> entry1 = (Entry)iterator1.next();
         if (this.ticks >= (Integer)entry1.getValue()) {
            isound = (ISound)entry1.getKey();
            if (isound instanceof ITickableSound) {
               ((ITickableSound)isound).tick();
            }

            this.play(isound);
            iterator1.remove();
         }
      }

   }

   public boolean isPlaying(ISound p_217933_1_) {
      if (!this.loaded) {
         return false;
      } else {
         return this.playingSoundsStopTime.containsKey(p_217933_1_) && (Integer)this.playingSoundsStopTime.get(p_217933_1_) <= this.ticks ? true : this.playingSoundsChannel.containsKey(p_217933_1_);
      }
   }

   public void play(ISound p_148611_1_) {
      if (this.loaded) {
         p_148611_1_ = ForgeHooksClient.playSound(this, p_148611_1_);
         if (p_148611_1_ == null) {
            return;
         }

         SoundEventAccessor soundeventaccessor = p_148611_1_.createAccessor(this.sndHandler);
         ResourceLocation resourcelocation = p_148611_1_.getSoundLocation();
         if (soundeventaccessor == null) {
            if (UNABLE_TO_PLAY.add(resourcelocation)) {
               LOGGER.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", resourcelocation);
            }
         } else {
            if (!this.listeners.isEmpty()) {
               Iterator var4 = this.listeners.iterator();

               while(var4.hasNext()) {
                  ISoundEventListener isoundeventlistener = (ISoundEventListener)var4.next();
                  isoundeventlistener.onPlaySound(p_148611_1_, soundeventaccessor);
               }
            }

            if (this.listener.getGain() <= 0.0F) {
               LOGGER.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", resourcelocation);
            } else {
               Sound sound = p_148611_1_.getSound();
               if (sound == SoundHandler.MISSING_SOUND) {
                  if (UNABLE_TO_PLAY.add(resourcelocation)) {
                     LOGGER.warn(LOG_MARKER, "Unable to play empty soundEvent: {}", resourcelocation);
                  }
               } else {
                  float f3 = p_148611_1_.getVolume();
                  float f = Math.max(f3, 1.0F) * (float)sound.getAttenuationDistance();
                  SoundCategory soundcategory = p_148611_1_.getCategory();
                  float f1 = this.getClampedVolume(p_148611_1_);
                  float f2 = this.getClampedPitch(p_148611_1_);
                  ISound.AttenuationType isound$attenuationtype = p_148611_1_.getAttenuationType();
                  boolean flag = p_148611_1_.isGlobal();
                  if (f1 == 0.0F && !p_148611_1_.canBeSilent()) {
                     LOGGER.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.", sound.getSoundLocation());
                  } else {
                     boolean flag1 = p_148611_1_.canRepeat() && p_148611_1_.getRepeatDelay() == 0;
                     Vec3d vec3d = new Vec3d((double)p_148611_1_.getX(), (double)p_148611_1_.getY(), (double)p_148611_1_.getZ());
                     ChannelManager.Entry channelmanager$entry = this.channelManager.createChannel(sound.isStreaming() ? SoundSystem.Mode.STREAMING : SoundSystem.Mode.STATIC);
                     LOGGER.debug(LOG_MARKER, "Playing sound {} for event {}", sound.getSoundLocation(), resourcelocation);
                     this.playingSoundsStopTime.put(p_148611_1_, this.ticks + 20);
                     this.playingSoundsChannel.put(p_148611_1_, channelmanager$entry);
                     this.field_217943_n.put(soundcategory, p_148611_1_);
                     channelmanager$entry.runOnSoundExecutor((p_lambda$play$4_7_) -> {
                        p_lambda$play$4_7_.func_216422_a(f2);
                        p_lambda$play$4_7_.func_216430_b(f1);
                        if (isound$attenuationtype == ISound.AttenuationType.LINEAR) {
                           p_lambda$play$4_7_.func_216423_c(f);
                        } else {
                           p_lambda$play$4_7_.func_216419_h();
                        }

                        p_lambda$play$4_7_.func_216425_a(flag1);
                        p_lambda$play$4_7_.func_216420_a(vec3d);
                        p_lambda$play$4_7_.func_216432_b(flag);
                     });
                     if (!sound.isStreaming()) {
                        this.audioStreamManager.func_217909_a(sound.getSoundAsOggLocation()).thenAccept((p_lambda$play$6_3_) -> {
                           channelmanager$entry.runOnSoundExecutor((p_lambda$null$5_3_) -> {
                              p_lambda$null$5_3_.func_216429_a(p_lambda$play$6_3_);
                              p_lambda$null$5_3_.func_216438_c();
                              MinecraftForge.EVENT_BUS.post(new PlaySoundSourceEvent(this, p_148611_1_, p_lambda$null$5_3_));
                           });
                        });
                     } else {
                        this.audioStreamManager.func_217917_b(sound.getSoundAsOggLocation()).thenAccept((p_lambda$play$8_3_) -> {
                           channelmanager$entry.runOnSoundExecutor((p_lambda$null$7_3_) -> {
                              p_lambda$null$7_3_.func_216433_a(p_lambda$play$8_3_);
                              p_lambda$null$7_3_.func_216438_c();
                              MinecraftForge.EVENT_BUS.post(new PlayStreamingSourceEvent(this, p_148611_1_, p_lambda$null$7_3_));
                           });
                        });
                     }

                     if (p_148611_1_ instanceof ITickableSound) {
                        this.tickableSounds.add((ITickableSound)p_148611_1_);
                     }
                  }
               }
            }
         }
      }

   }

   public void func_229363_a_(ITickableSound p_229363_1_) {
      this.field_229361_s_.add(p_229363_1_);
   }

   public void enqueuePreload(Sound p_204259_1_) {
      this.soundsToPreload.add(p_204259_1_);
   }

   private float getClampedPitch(ISound p_188772_1_) {
      return MathHelper.clamp(p_188772_1_.getPitch(), 0.5F, 2.0F);
   }

   private float getClampedVolume(ISound p_188770_1_) {
      return MathHelper.clamp(p_188770_1_.getVolume() * this.getVolume(p_188770_1_.getCategory()), 0.0F, 1.0F);
   }

   public void pause() {
      if (this.loaded) {
         this.channelManager.func_217897_a((p_lambda$pause$9_0_) -> {
            p_lambda$pause$9_0_.forEach(SoundSource::func_216439_d);
         });
      }

   }

   public void resume() {
      if (this.loaded) {
         this.channelManager.func_217897_a((p_lambda$resume$10_0_) -> {
            p_lambda$resume$10_0_.forEach(SoundSource::func_216437_e);
         });
      }

   }

   public void playDelayed(ISound p_148599_1_, int p_148599_2_) {
      this.delayedSounds.put(p_148599_1_, this.ticks + p_148599_2_);
   }

   public void updateListener(ActiveRenderInfo p_217920_1_) {
      if (this.loaded && p_217920_1_.isValid()) {
         Vec3d vec3d = p_217920_1_.getProjectedView();
         Vector3f vector3f = p_217920_1_.func_227996_l_();
         Vector3f vector3f1 = p_217920_1_.func_227997_m_();
         this.executor.execute(() -> {
            this.listener.setPosition(vec3d);
            this.listener.func_227580_a_(vector3f, vector3f1);
         });
      }

   }

   public void stop(@Nullable ResourceLocation p_195855_1_, @Nullable SoundCategory p_195855_2_) {
      Iterator var3;
      ISound isound1;
      if (p_195855_2_ != null) {
         var3 = this.field_217943_n.get(p_195855_2_).iterator();

         while(true) {
            do {
               if (!var3.hasNext()) {
                  return;
               }

               isound1 = (ISound)var3.next();
            } while(p_195855_1_ != null && !isound1.getSoundLocation().equals(p_195855_1_));

            this.stop(isound1);
         }
      } else if (p_195855_1_ == null) {
         this.stopAllSounds();
      } else {
         var3 = this.playingSoundsChannel.keySet().iterator();

         while(var3.hasNext()) {
            isound1 = (ISound)var3.next();
            if (isound1.getSoundLocation().equals(p_195855_1_)) {
               this.stop(isound1);
            }
         }
      }

   }

   public String getDebugString() {
      return this.sndSystem.getDebugString();
   }
}
