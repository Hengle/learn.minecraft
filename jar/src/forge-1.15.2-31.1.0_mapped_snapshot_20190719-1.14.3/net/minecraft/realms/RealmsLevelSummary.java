package net.minecraft.realms;

import net.minecraft.world.storage.WorldSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsLevelSummary implements Comparable<RealmsLevelSummary> {
   private final WorldSummary levelSummary;

   public RealmsLevelSummary(WorldSummary p_i1109_1_) {
      this.levelSummary = p_i1109_1_;
   }

   public int getGameMode() {
      return this.levelSummary.getEnumGameType().getID();
   }

   public String getLevelId() {
      return this.levelSummary.getFileName();
   }

   public boolean hasCheats() {
      return this.levelSummary.getCheatsEnabled();
   }

   public boolean isHardcore() {
      return this.levelSummary.isHardcoreModeEnabled();
   }

   public boolean isRequiresConversion() {
      return this.levelSummary.requiresConversion();
   }

   public String getLevelName() {
      return this.levelSummary.getDisplayName();
   }

   public long getLastPlayed() {
      return this.levelSummary.getLastTimePlayed();
   }

   public int compareTo(WorldSummary p_compareTo_1_) {
      return this.levelSummary.compareTo(p_compareTo_1_);
   }

   public long getSizeOnDisk() {
      return this.levelSummary.getSizeOnDisk();
   }

   public int compareTo(RealmsLevelSummary p_compareTo_1_) {
      if (this.levelSummary.getLastTimePlayed() < p_compareTo_1_.getLastPlayed()) {
         return 1;
      } else {
         return this.levelSummary.getLastTimePlayed() > p_compareTo_1_.getLastPlayed() ? -1 : this.levelSummary.getFileName().compareTo(p_compareTo_1_.getLevelId());
      }
   }
}