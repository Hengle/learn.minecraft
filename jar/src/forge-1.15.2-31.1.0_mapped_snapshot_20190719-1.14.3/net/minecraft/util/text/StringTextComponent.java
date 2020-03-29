package net.minecraft.util.text;

public class StringTextComponent extends TextComponent {
   private final String text;

   public StringTextComponent(String p_i45159_1_) {
      this.text = p_i45159_1_;
   }

   public String getText() {
      return this.text;
   }

   public String getUnformattedComponentText() {
      return this.text;
   }

   public StringTextComponent shallowCopy() {
      return new StringTextComponent(this.text);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (!(p_equals_1_ instanceof StringTextComponent)) {
         return false;
      } else {
         StringTextComponent lvt_2_1_ = (StringTextComponent)p_equals_1_;
         return this.text.equals(lvt_2_1_.getText()) && super.equals(p_equals_1_);
      }
   }

   public String toString() {
      return "TextComponent{text='" + this.text + '\'' + ", siblings=" + this.siblings + ", style=" + this.getStyle() + '}';
   }

   // $FF: synthetic method
   public ITextComponent shallowCopy() {
      return this.shallowCopy();
   }
}
