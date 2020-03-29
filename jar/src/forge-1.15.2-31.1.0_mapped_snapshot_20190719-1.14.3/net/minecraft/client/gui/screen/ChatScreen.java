package net.minecraft.client.gui.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.CommandSuggestionHelper;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChatScreen extends Screen {
   private String historyBuffer = "";
   private int sentHistoryCursor = -1;
   protected TextFieldWidget inputField;
   private String defaultInputFieldText = "";
   private CommandSuggestionHelper field_228174_e_;

   public ChatScreen(String p_i1024_1_) {
      super(NarratorChatListener.field_216868_a);
      this.defaultInputFieldText = p_i1024_1_;
   }

   protected void init() {
      this.minecraft.keyboardListener.enableRepeatEvents(true);
      this.sentHistoryCursor = this.minecraft.ingameGUI.getChatGUI().getSentMessages().size();
      this.inputField = new TextFieldWidget(this.font, 4, this.height - 12, this.width - 4, 12, I18n.format("chat.editBox")) {
         protected String getNarrationMessage() {
            return super.getNarrationMessage() + ChatScreen.this.field_228174_e_.func_228129_c_();
         }
      };
      this.inputField.setMaxStringLength(256);
      this.inputField.setEnableBackgroundDrawing(false);
      this.inputField.setText(this.defaultInputFieldText);
      this.inputField.func_212954_a(this::func_212997_a);
      this.children.add(this.inputField);
      this.field_228174_e_ = new CommandSuggestionHelper(this.minecraft, this, this.inputField, this.font, false, false, 1, 10, true, -805306368);
      this.field_228174_e_.func_228111_a_();
      this.func_212928_a(this.inputField);
   }

   public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
      String lvt_4_1_ = this.inputField.getText();
      this.init(p_resize_1_, p_resize_2_, p_resize_3_);
      this.setChatLine(lvt_4_1_);
      this.field_228174_e_.func_228111_a_();
   }

   public void removed() {
      this.minecraft.keyboardListener.enableRepeatEvents(false);
      this.minecraft.ingameGUI.getChatGUI().resetScroll();
   }

   public void tick() {
      this.inputField.tick();
   }

   private void func_212997_a(String p_212997_1_) {
      String lvt_2_1_ = this.inputField.getText();
      this.field_228174_e_.func_228124_a_(!lvt_2_1_.equals(this.defaultInputFieldText));
      this.field_228174_e_.func_228111_a_();
   }

   public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
      if (this.field_228174_e_.func_228115_a_(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
         return true;
      } else if (super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_)) {
         return true;
      } else if (p_keyPressed_1_ == 256) {
         this.minecraft.displayGuiScreen((Screen)null);
         return true;
      } else if (p_keyPressed_1_ != 257 && p_keyPressed_1_ != 335) {
         if (p_keyPressed_1_ == 265) {
            this.getSentHistory(-1);
            return true;
         } else if (p_keyPressed_1_ == 264) {
            this.getSentHistory(1);
            return true;
         } else if (p_keyPressed_1_ == 266) {
            this.minecraft.ingameGUI.getChatGUI().func_194813_a((double)(this.minecraft.ingameGUI.getChatGUI().getLineCount() - 1));
            return true;
         } else if (p_keyPressed_1_ == 267) {
            this.minecraft.ingameGUI.getChatGUI().func_194813_a((double)(-this.minecraft.ingameGUI.getChatGUI().getLineCount() + 1));
            return true;
         } else {
            return false;
         }
      } else {
         String lvt_4_1_ = this.inputField.getText().trim();
         if (!lvt_4_1_.isEmpty()) {
            this.sendMessage(lvt_4_1_);
         }

         this.minecraft.displayGuiScreen((Screen)null);
         return true;
      }
   }

   public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {
      if (p_mouseScrolled_5_ > 1.0D) {
         p_mouseScrolled_5_ = 1.0D;
      }

      if (p_mouseScrolled_5_ < -1.0D) {
         p_mouseScrolled_5_ = -1.0D;
      }

      if (this.field_228174_e_.func_228112_a_(p_mouseScrolled_5_)) {
         return true;
      } else {
         if (!hasShiftDown()) {
            p_mouseScrolled_5_ *= 7.0D;
         }

         this.minecraft.ingameGUI.getChatGUI().func_194813_a(p_mouseScrolled_5_);
         return true;
      }
   }

   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      if (this.field_228174_e_.func_228113_a_((double)((int)p_mouseClicked_1_), (double)((int)p_mouseClicked_3_), p_mouseClicked_5_)) {
         return true;
      } else {
         if (p_mouseClicked_5_ == 0) {
            ITextComponent lvt_6_1_ = this.minecraft.ingameGUI.getChatGUI().getTextComponent(p_mouseClicked_1_, p_mouseClicked_3_);
            if (lvt_6_1_ != null && this.handleComponentClicked(lvt_6_1_)) {
               return true;
            }
         }

         return this.inputField.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_) ? true : super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
      }
   }

   protected void insertText(String p_insertText_1_, boolean p_insertText_2_) {
      if (p_insertText_2_) {
         this.inputField.setText(p_insertText_1_);
      } else {
         this.inputField.writeText(p_insertText_1_);
      }

   }

   public void getSentHistory(int p_146402_1_) {
      int lvt_2_1_ = this.sentHistoryCursor + p_146402_1_;
      int lvt_3_1_ = this.minecraft.ingameGUI.getChatGUI().getSentMessages().size();
      lvt_2_1_ = MathHelper.clamp(lvt_2_1_, 0, lvt_3_1_);
      if (lvt_2_1_ != this.sentHistoryCursor) {
         if (lvt_2_1_ == lvt_3_1_) {
            this.sentHistoryCursor = lvt_3_1_;
            this.inputField.setText(this.historyBuffer);
         } else {
            if (this.sentHistoryCursor == lvt_3_1_) {
               this.historyBuffer = this.inputField.getText();
            }

            this.inputField.setText((String)this.minecraft.ingameGUI.getChatGUI().getSentMessages().get(lvt_2_1_));
            this.field_228174_e_.func_228124_a_(false);
            this.sentHistoryCursor = lvt_2_1_;
         }
      }
   }

   public void render(int p_render_1_, int p_render_2_, float p_render_3_) {
      this.setFocused(this.inputField);
      this.inputField.setFocused2(true);
      fill(2, this.height - 14, this.width - 2, this.height - 2, this.minecraft.gameSettings.func_216839_a(Integer.MIN_VALUE));
      this.inputField.render(p_render_1_, p_render_2_, p_render_3_);
      this.field_228174_e_.func_228114_a_(p_render_1_, p_render_2_);
      ITextComponent lvt_4_1_ = this.minecraft.ingameGUI.getChatGUI().getTextComponent((double)p_render_1_, (double)p_render_2_);
      if (lvt_4_1_ != null && lvt_4_1_.getStyle().getHoverEvent() != null) {
         this.renderComponentHoverEffect(lvt_4_1_, p_render_1_, p_render_2_);
      }

      super.render(p_render_1_, p_render_2_, p_render_3_);
   }

   public boolean isPauseScreen() {
      return false;
   }

   private void setChatLine(String p_208604_1_) {
      this.inputField.setText(p_208604_1_);
   }
}
