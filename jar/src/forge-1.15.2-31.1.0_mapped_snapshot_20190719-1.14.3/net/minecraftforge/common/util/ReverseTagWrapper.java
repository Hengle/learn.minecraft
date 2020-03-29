package net.minecraftforge.common.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;

public class ReverseTagWrapper<T> {
   private final T target;
   private final IntSupplier genSupplier;
   private final Supplier<TagCollection<T>> colSupplier;
   private int generation = -1;
   private Set<ResourceLocation> cache = null;

   public ReverseTagWrapper(T target, IntSupplier genSupplier, Supplier<TagCollection<T>> colSupplier) {
      this.target = target;
      this.genSupplier = genSupplier;
      this.colSupplier = colSupplier;
   }

   public Set<ResourceLocation> getTagNames() {
      if (this.cache == null || this.generation != this.genSupplier.getAsInt()) {
         this.cache = Collections.unmodifiableSet(new HashSet(((TagCollection)this.colSupplier.get()).getOwningTags(this.target)));
         this.generation = this.genSupplier.getAsInt();
      }

      return this.cache;
   }
}
