package net.minecraft.item;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

public enum DyeColor implements IStringSerializable {
   WHITE(0, "white", 16383998, MaterialColor.SNOW, 15790320, 16777215),
   ORANGE(1, "orange", 16351261, MaterialColor.ADOBE, 15435844, 16738335),
   MAGENTA(2, "magenta", 13061821, MaterialColor.MAGENTA, 12801229, 16711935),
   LIGHT_BLUE(3, "light_blue", 3847130, MaterialColor.LIGHT_BLUE, 6719955, 10141901),
   YELLOW(4, "yellow", 16701501, MaterialColor.YELLOW, 14602026, 16776960),
   LIME(5, "lime", 8439583, MaterialColor.LIME, 4312372, 12582656),
   PINK(6, "pink", 15961002, MaterialColor.PINK, 14188952, 16738740),
   GRAY(7, "gray", 4673362, MaterialColor.GRAY, 4408131, 8421504),
   LIGHT_GRAY(8, "light_gray", 10329495, MaterialColor.LIGHT_GRAY, 11250603, 13882323),
   CYAN(9, "cyan", 1481884, MaterialColor.CYAN, 2651799, 65535),
   PURPLE(10, "purple", 8991416, MaterialColor.PURPLE, 8073150, 10494192),
   BLUE(11, "blue", 3949738, MaterialColor.BLUE, 2437522, 255),
   BROWN(12, "brown", 8606770, MaterialColor.BROWN, 5320730, 9127187),
   GREEN(13, "green", 6192150, MaterialColor.GREEN, 3887386, 65280),
   RED(14, "red", 11546150, MaterialColor.RED, 11743532, 16711680),
   BLACK(15, "black", 1908001, MaterialColor.BLACK, 1973019, 0);

   private static final DyeColor[] VALUES = (DyeColor[])Arrays.stream(values()).sorted(Comparator.comparingInt(DyeColor::getId)).toArray((p_lambda$static$0_0_) -> {
      return new DyeColor[p_lambda$static$0_0_];
   });
   private static final Int2ObjectOpenHashMap<DyeColor> BY_FIREWORK_COLOR = new Int2ObjectOpenHashMap((Map)Arrays.stream(values()).collect(Collectors.toMap((p_lambda$static$1_0_) -> {
      return p_lambda$static$1_0_.fireworkColor;
   }, (p_lambda$static$2_0_) -> {
      return p_lambda$static$2_0_;
   })));
   private final int id;
   private final String translationKey;
   private final MaterialColor mapColor;
   private final int colorValue;
   private final int swappedColorValue;
   private final float[] colorComponentValues;
   private final int fireworkColor;
   private final Tag<Item> tag;
   private final int field_218390_z;

   private DyeColor(int p_i50049_3_, String p_i50049_4_, int p_i50049_5_, MaterialColor p_i50049_6_, int p_i50049_7_, int p_i50049_8_) {
      this.id = p_i50049_3_;
      this.translationKey = p_i50049_4_;
      this.colorValue = p_i50049_5_;
      this.mapColor = p_i50049_6_;
      this.field_218390_z = p_i50049_8_;
      int i = (p_i50049_5_ & 16711680) >> 16;
      int j = (p_i50049_5_ & '\uff00') >> 8;
      int k = (p_i50049_5_ & 255) >> 0;
      this.swappedColorValue = k << 16 | j << 8 | i << 0;
      this.tag = new ItemTags.Wrapper(new ResourceLocation("forge", "dyes/" + p_i50049_4_));
      this.colorComponentValues = new float[]{(float)i / 255.0F, (float)j / 255.0F, (float)k / 255.0F};
      this.fireworkColor = p_i50049_7_;
   }

   public int getId() {
      return this.id;
   }

   public String getTranslationKey() {
      return this.translationKey;
   }

   public float[] getColorComponentValues() {
      return this.colorComponentValues;
   }

   public MaterialColor getMapColor() {
      return this.mapColor;
   }

   public int getFireworkColor() {
      return this.fireworkColor;
   }

   public int func_218388_g() {
      return this.field_218390_z;
   }

   public static DyeColor byId(int p_196056_0_) {
      if (p_196056_0_ < 0 || p_196056_0_ >= VALUES.length) {
         p_196056_0_ = 0;
      }

      return VALUES[p_196056_0_];
   }

   public static DyeColor byTranslationKey(String p_204271_0_, DyeColor p_204271_1_) {
      DyeColor[] var2 = values();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         DyeColor dyecolor = var2[var4];
         if (dyecolor.translationKey.equals(p_204271_0_)) {
            return dyecolor;
         }
      }

      return p_204271_1_;
   }

   @Nullable
   public static DyeColor byFireworkColor(int p_196058_0_) {
      return (DyeColor)BY_FIREWORK_COLOR.get(p_196058_0_);
   }

   public String toString() {
      return this.translationKey;
   }

   public String getName() {
      return this.translationKey;
   }

   public int getColorValue() {
      return this.colorValue;
   }

   public Tag<Item> getTag() {
      return this.tag;
   }

   @Nullable
   public static DyeColor getColor(ItemStack p_getColor_0_) {
      if (p_getColor_0_.getItem() instanceof DyeItem) {
         return ((DyeItem)p_getColor_0_.getItem()).getDyeColor();
      } else {
         DyeColor[] var1 = VALUES;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            DyeColor color = var1[var3];
            if (p_getColor_0_.getItem().isIn(color.getTag())) {
               return color;
            }
         }

         return null;
      }
   }
}
