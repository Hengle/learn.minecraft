package net.minecraft.client.util;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.gui.recipebook.RecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.VanillaResourceType;

@OnlyIn(Dist.CLIENT)
public class SearchTreeManager implements IResourceManagerReloadListener {
   public static final SearchTreeManager.Key<ItemStack> field_215359_a = new SearchTreeManager.Key();
   public static final SearchTreeManager.Key<ItemStack> field_215360_b = new SearchTreeManager.Key();
   public static final SearchTreeManager.Key<RecipeList> RECIPES = new SearchTreeManager.Key();
   private final Map<SearchTreeManager.Key<?>, IMutableSearchTree<?>> trees = Maps.newHashMap();

   public void onResourceManagerReload(IResourceManager p_195410_1_) {
      Iterator var2 = this.trees.values().iterator();

      while(var2.hasNext()) {
         IMutableSearchTree<?> imutablesearchtree = (IMutableSearchTree)var2.next();
         imutablesearchtree.recalculate();
      }

   }

   public <T> void add(SearchTreeManager.Key<T> p_215357_1_, IMutableSearchTree<T> p_215357_2_) {
      this.trees.put(p_215357_1_, p_215357_2_);
   }

   public <T> IMutableSearchTree<T> get(SearchTreeManager.Key<T> p_215358_1_) {
      return (IMutableSearchTree)this.trees.get(p_215358_1_);
   }

   public IResourceType getResourceType() {
      return VanillaResourceType.LANGUAGES;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Key<T> {
   }
}
