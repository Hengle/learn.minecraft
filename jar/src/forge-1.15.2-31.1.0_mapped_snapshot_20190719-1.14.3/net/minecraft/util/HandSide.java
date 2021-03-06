package net.minecraft.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum HandSide {
   LEFT(new TranslationTextComponent("options.mainHand.left", new Object[0])),
   RIGHT(new TranslationTextComponent("options.mainHand.right", new Object[0]));

   private final ITextComponent handName;

   private HandSide(ITextComponent p_i46806_3_) {
      this.handName = p_i46806_3_;
   }

   @OnlyIn(Dist.CLIENT)
   public HandSide opposite() {
      return this == LEFT ? RIGHT : LEFT;
   }

   public String toString() {
      return this.handName.getString();
   }
}
