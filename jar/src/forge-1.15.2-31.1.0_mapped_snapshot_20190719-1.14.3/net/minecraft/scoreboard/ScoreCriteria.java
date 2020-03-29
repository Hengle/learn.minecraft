package net.minecraft.scoreboard;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Optional;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;

public class ScoreCriteria {
   public static final Map<String, ScoreCriteria> INSTANCES = Maps.newHashMap();
   public static final ScoreCriteria DUMMY = new ScoreCriteria("dummy");
   public static final ScoreCriteria TRIGGER = new ScoreCriteria("trigger");
   public static final ScoreCriteria DEATH_COUNT = new ScoreCriteria("deathCount");
   public static final ScoreCriteria PLAYER_KILL_COUNT = new ScoreCriteria("playerKillCount");
   public static final ScoreCriteria TOTAL_KILL_COUNT = new ScoreCriteria("totalKillCount");
   public static final ScoreCriteria HEALTH;
   public static final ScoreCriteria FOOD;
   public static final ScoreCriteria AIR;
   public static final ScoreCriteria ARMOR;
   public static final ScoreCriteria XP;
   public static final ScoreCriteria LEVEL;
   public static final ScoreCriteria[] TEAM_KILL;
   public static final ScoreCriteria[] KILLED_BY_TEAM;
   private final String name;
   private final boolean readOnly;
   private final ScoreCriteria.RenderType renderType;

   public ScoreCriteria(String p_i47676_1_) {
      this(p_i47676_1_, false, ScoreCriteria.RenderType.INTEGER);
   }

   protected ScoreCriteria(String p_i47677_1_, boolean p_i47677_2_, ScoreCriteria.RenderType p_i47677_3_) {
      this.name = p_i47677_1_;
      this.readOnly = p_i47677_2_;
      this.renderType = p_i47677_3_;
      INSTANCES.put(p_i47677_1_, this);
   }

   public static Optional<ScoreCriteria> func_216390_a(String p_216390_0_) {
      if (INSTANCES.containsKey(p_216390_0_)) {
         return Optional.of(INSTANCES.get(p_216390_0_));
      } else {
         int lvt_1_1_ = p_216390_0_.indexOf(58);
         return lvt_1_1_ < 0 ? Optional.empty() : Registry.STATS.getValue(ResourceLocation.create(p_216390_0_.substring(0, lvt_1_1_), '.')).flatMap((p_216392_2_) -> {
            return func_216391_a(p_216392_2_, ResourceLocation.create(p_216390_0_.substring(lvt_1_1_ + 1), '.'));
         });
      }
   }

   private static <T> Optional<ScoreCriteria> func_216391_a(StatType<T> p_216391_0_, ResourceLocation p_216391_1_) {
      Optional var10000 = p_216391_0_.getRegistry().getValue(p_216391_1_);
      p_216391_0_.getClass();
      return var10000.map(p_216391_0_::get);
   }

   public String getName() {
      return this.name;
   }

   public boolean isReadOnly() {
      return this.readOnly;
   }

   public ScoreCriteria.RenderType getRenderType() {
      return this.renderType;
   }

   static {
      HEALTH = new ScoreCriteria("health", true, ScoreCriteria.RenderType.HEARTS);
      FOOD = new ScoreCriteria("food", true, ScoreCriteria.RenderType.INTEGER);
      AIR = new ScoreCriteria("air", true, ScoreCriteria.RenderType.INTEGER);
      ARMOR = new ScoreCriteria("armor", true, ScoreCriteria.RenderType.INTEGER);
      XP = new ScoreCriteria("xp", true, ScoreCriteria.RenderType.INTEGER);
      LEVEL = new ScoreCriteria("level", true, ScoreCriteria.RenderType.INTEGER);
      TEAM_KILL = new ScoreCriteria[]{new ScoreCriteria("teamkill." + TextFormatting.BLACK.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_BLUE.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_GREEN.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_AQUA.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_RED.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_PURPLE.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.GOLD.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.GRAY.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.DARK_GRAY.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.BLUE.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.GREEN.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.AQUA.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.RED.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.LIGHT_PURPLE.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.YELLOW.getFriendlyName()), new ScoreCriteria("teamkill." + TextFormatting.WHITE.getFriendlyName())};
      KILLED_BY_TEAM = new ScoreCriteria[]{new ScoreCriteria("killedByTeam." + TextFormatting.BLACK.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_BLUE.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_GREEN.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_AQUA.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_RED.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_PURPLE.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.GOLD.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.GRAY.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.DARK_GRAY.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.BLUE.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.GREEN.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.AQUA.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.RED.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.LIGHT_PURPLE.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.YELLOW.getFriendlyName()), new ScoreCriteria("killedByTeam." + TextFormatting.WHITE.getFriendlyName())};
   }

   public static enum RenderType {
      INTEGER("integer"),
      HEARTS("hearts");

      private final String field_211840_c;
      private static final Map<String, ScoreCriteria.RenderType> field_211841_d;

      private RenderType(String p_i49784_3_) {
         this.field_211840_c = p_i49784_3_;
      }

      public String getId() {
         return this.field_211840_c;
      }

      public static ScoreCriteria.RenderType byId(String p_211839_0_) {
         return (ScoreCriteria.RenderType)field_211841_d.getOrDefault(p_211839_0_, INTEGER);
      }

      static {
         Builder<String, ScoreCriteria.RenderType> lvt_0_1_ = ImmutableMap.builder();
         ScoreCriteria.RenderType[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            ScoreCriteria.RenderType lvt_4_1_ = var1[var3];
            lvt_0_1_.put(lvt_4_1_.field_211840_c, lvt_4_1_);
         }

         field_211841_d = lvt_0_1_.build();
      }
   }
}
