package net.minecraft.world.gen;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerationStage {
   public static enum Carving {
      AIR("air"),
      LIQUID("liquid");

      private static final Map<String, GenerationStage.Carving> BY_NAME = (Map)Arrays.stream(values()).collect(Collectors.toMap(GenerationStage.Carving::getName, (p_222672_0_) -> {
         return p_222672_0_;
      }));
      private final String name;

      private Carving(String p_i50879_3_) {
         this.name = p_i50879_3_;
      }

      public String getName() {
         return this.name;
      }
   }

   public static enum Decoration {
      RAW_GENERATION("raw_generation"),
      LOCAL_MODIFICATIONS("local_modifications"),
      UNDERGROUND_STRUCTURES("underground_structures"),
      SURFACE_STRUCTURES("surface_structures"),
      UNDERGROUND_ORES("underground_ores"),
      UNDERGROUND_DECORATION("underground_decoration"),
      VEGETAL_DECORATION("vegetal_decoration"),
      TOP_LAYER_MODIFICATION("top_layer_modification");

      private static final Map<String, GenerationStage.Decoration> BY_NAME = (Map)Arrays.stream(values()).collect(Collectors.toMap(GenerationStage.Decoration::getName, (p_222675_0_) -> {
         return p_222675_0_;
      }));
      private final String name;

      private Decoration(String p_i50878_3_) {
         this.name = p_i50878_3_;
      }

      public String getName() {
         return this.name;
      }
   }
}
