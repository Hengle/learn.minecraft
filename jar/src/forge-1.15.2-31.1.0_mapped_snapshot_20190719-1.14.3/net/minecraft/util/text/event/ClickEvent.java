package net.minecraft.util.text.event;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ClickEvent {
   private final ClickEvent.Action action;
   private final String value;

   public ClickEvent(ClickEvent.Action p_i45156_1_, String p_i45156_2_) {
      this.action = p_i45156_1_;
      this.value = p_i45156_2_;
   }

   public ClickEvent.Action getAction() {
      return this.action;
   }

   public String getValue() {
      return this.value;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass()) {
         ClickEvent lvt_2_1_ = (ClickEvent)p_equals_1_;
         if (this.action != lvt_2_1_.action) {
            return false;
         } else {
            if (this.value != null) {
               if (!this.value.equals(lvt_2_1_.value)) {
                  return false;
               }
            } else if (lvt_2_1_.value != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      return "ClickEvent{action=" + this.action + ", value='" + this.value + '\'' + '}';
   }

   public int hashCode() {
      int lvt_1_1_ = this.action.hashCode();
      lvt_1_1_ = 31 * lvt_1_1_ + (this.value != null ? this.value.hashCode() : 0);
      return lvt_1_1_;
   }

   public static enum Action {
      OPEN_URL("open_url", true),
      OPEN_FILE("open_file", false),
      RUN_COMMAND("run_command", true),
      SUGGEST_COMMAND("suggest_command", true),
      CHANGE_PAGE("change_page", true),
      COPY_TO_CLIPBOARD("copy_to_clipboard", true);

      private static final Map<String, ClickEvent.Action> NAME_MAPPING = (Map)Arrays.stream(values()).collect(Collectors.toMap(ClickEvent.Action::getCanonicalName, (p_199851_0_) -> {
         return p_199851_0_;
      }));
      private final boolean allowedInChat;
      private final String canonicalName;

      private Action(String p_i45155_3_, boolean p_i45155_4_) {
         this.canonicalName = p_i45155_3_;
         this.allowedInChat = p_i45155_4_;
      }

      public boolean shouldAllowInChat() {
         return this.allowedInChat;
      }

      public String getCanonicalName() {
         return this.canonicalName;
      }

      public static ClickEvent.Action getValueByCanonicalName(String p_150672_0_) {
         return (ClickEvent.Action)NAME_MAPPING.get(p_150672_0_);
      }
   }
}
