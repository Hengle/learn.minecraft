package net.minecraft.client.audio;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UnderwaterAmbientSoundHandler implements IAmbientSoundHandler {
   private final ClientPlayerEntity player;
   private final SoundHandler soundHandler;
   private int delay = 0;

   public UnderwaterAmbientSoundHandler(ClientPlayerEntity p_i48885_1_, SoundHandler p_i48885_2_) {
      this.player = p_i48885_1_;
      this.soundHandler = p_i48885_2_;
   }

   public void tick() {
      --this.delay;
      if (this.delay <= 0 && this.player.canSwim()) {
         float lvt_1_1_ = this.player.world.rand.nextFloat();
         if (lvt_1_1_ < 1.0E-4F) {
            this.delay = 0;
            this.soundHandler.play(new UnderwaterAmbientSounds.SubSound(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE));
         } else if (lvt_1_1_ < 0.001F) {
            this.delay = 0;
            this.soundHandler.play(new UnderwaterAmbientSounds.SubSound(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS_RARE));
         } else if (lvt_1_1_ < 0.01F) {
            this.delay = 0;
            this.soundHandler.play(new UnderwaterAmbientSounds.SubSound(this.player, SoundEvents.AMBIENT_UNDERWATER_LOOP_ADDITIONS));
         }
      }

   }
}
