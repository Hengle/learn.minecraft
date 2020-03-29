package net.minecraft.client.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IMutableSearchTree<T> extends ISearchTree<T> {
   void func_217872_a(T var1);

   void func_217871_a();

   void recalculate();
}
