package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ShieldItem extends Item {
   public ShieldItem(Item.Properties p_i48470_1_) {
      super(p_i48470_1_);
      this.addPropertyOverride(new ResourceLocation("blocking"), (p_210314_0_, p_210314_1_, p_210314_2_) -> {
         return p_210314_2_ != null && p_210314_2_.isHandActive() && p_210314_2_.getActiveItemStack() == p_210314_0_ ? 1.0F : 0.0F;
      });
      DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
   }

   public String getTranslationKey(ItemStack p_77667_1_) {
      return p_77667_1_.getChildTag("BlockEntityTag") != null ? this.getTranslationKey() + '.' + getColor(p_77667_1_).getTranslationKey() : super.getTranslationKey(p_77667_1_);
   }

   @OnlyIn(Dist.CLIENT)
   public void addInformation(ItemStack p_77624_1_, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
      BannerItem.appendHoverTextFromTileEntityTag(p_77624_1_, p_77624_3_);
   }

   public UseAction getUseAction(ItemStack p_77661_1_) {
      return UseAction.BLOCK;
   }

   public int getUseDuration(ItemStack p_77626_1_) {
      return 72000;
   }

   public ActionResult<ItemStack> onItemRightClick(World p_77659_1_, PlayerEntity p_77659_2_, Hand p_77659_3_) {
      ItemStack lvt_4_1_ = p_77659_2_.getHeldItem(p_77659_3_);
      p_77659_2_.setActiveHand(p_77659_3_);
      return ActionResult.func_226249_b_(lvt_4_1_);
   }

   public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
      return ItemTags.PLANKS.contains(p_82789_2_.getItem()) || super.getIsRepairable(p_82789_1_, p_82789_2_);
   }

   public static DyeColor getColor(ItemStack p_195979_0_) {
      return DyeColor.byId(p_195979_0_.getOrCreateChildTag("BlockEntityTag").getInt("Base"));
   }
}
