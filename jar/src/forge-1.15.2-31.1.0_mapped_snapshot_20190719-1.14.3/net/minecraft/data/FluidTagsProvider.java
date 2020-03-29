package net.minecraft.data;

import java.nio.file.Path;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class FluidTagsProvider extends TagsProvider<Fluid> {
   public FluidTagsProvider(DataGenerator p_i49156_1_) {
      super(p_i49156_1_, Registry.FLUID);
   }

   protected void registerTags() {
      this.getBuilder(FluidTags.WATER).add((Object[])(Fluids.WATER, Fluids.FLOWING_WATER));
      this.getBuilder(FluidTags.LAVA).add((Object[])(Fluids.LAVA, Fluids.FLOWING_LAVA));
   }

   protected Path makePath(ResourceLocation p_200431_1_) {
      return this.generator.getOutputFolder().resolve("data/" + p_200431_1_.getNamespace() + "/tags/fluids/" + p_200431_1_.getPath() + ".json");
   }

   public String getName() {
      return "Fluid Tags";
   }

   protected void setCollection(TagCollection<Fluid> p_200429_1_) {
      FluidTags.setCollection(p_200429_1_);
   }
}
