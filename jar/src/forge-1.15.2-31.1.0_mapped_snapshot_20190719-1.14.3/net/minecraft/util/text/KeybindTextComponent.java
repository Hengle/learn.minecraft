package net.minecraft.util.text;

import java.util.function.Function;
import java.util.function.Supplier;

public class KeybindTextComponent extends TextComponent {
   public static Function<String, Supplier<String>> displaySupplierFunction = (p_193635_0_) -> {
      return () -> {
         return p_193635_0_;
      };
   };
   private final String keybind;
   private Supplier<String> displaySupplier;

   public KeybindTextComponent(String p_i47521_1_) {
      this.keybind = p_i47521_1_;
   }

   public String getUnformattedComponentText() {
      if (this.displaySupplier == null) {
         this.displaySupplier = (Supplier)displaySupplierFunction.apply(this.keybind);
      }

      return (String)this.displaySupplier.get();
   }

   public KeybindTextComponent shallowCopy() {
      return new KeybindTextComponent(this.keybind);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof KeybindTextComponent)) {
         return false;
      } else {
         KeybindTextComponent lvt_2_1_ = (KeybindTextComponent)p_equals_1_;
         return this.keybind.equals(lvt_2_1_.keybind) && super.equals(p_equals_1_);
      }
   }

   public String toString() {
      return "KeybindComponent{keybind='" + this.keybind + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
   }

   public String getKeybind() {
      return this.keybind;
   }

   // $FF: synthetic method
   public ITextComponent shallowCopy() {
      return this.shallowCopy();
   }
}
