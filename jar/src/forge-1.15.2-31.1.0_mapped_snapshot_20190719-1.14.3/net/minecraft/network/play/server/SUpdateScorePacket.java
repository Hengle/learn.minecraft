package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SUpdateScorePacket implements IPacket<IClientPlayNetHandler> {
   private String name = "";
   @Nullable
   private String objective;
   private int value;
   private ServerScoreboard.Action action;

   public SUpdateScorePacket() {
   }

   public SUpdateScorePacket(ServerScoreboard.Action p_i47930_1_, @Nullable String p_i47930_2_, String p_i47930_3_, int p_i47930_4_) {
      if (p_i47930_1_ != ServerScoreboard.Action.REMOVE && p_i47930_2_ == null) {
         throw new IllegalArgumentException("Need an objective name");
      } else {
         this.name = p_i47930_3_;
         this.objective = p_i47930_2_;
         this.value = p_i47930_4_;
         this.action = p_i47930_1_;
      }
   }

   public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
      this.name = p_148837_1_.readString(40);
      this.action = (ServerScoreboard.Action)p_148837_1_.readEnumValue(ServerScoreboard.Action.class);
      String lvt_2_1_ = p_148837_1_.readString(16);
      this.objective = Objects.equals(lvt_2_1_, "") ? null : lvt_2_1_;
      if (this.action != ServerScoreboard.Action.REMOVE) {
         this.value = p_148837_1_.readVarInt();
      }

   }

   public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeString(this.name);
      p_148840_1_.writeEnumValue(this.action);
      p_148840_1_.writeString(this.objective == null ? "" : this.objective);
      if (this.action != ServerScoreboard.Action.REMOVE) {
         p_148840_1_.writeVarInt(this.value);
      }

   }

   public void processPacket(IClientPlayNetHandler p_148833_1_) {
      p_148833_1_.handleUpdateScore(this);
   }

   @OnlyIn(Dist.CLIENT)
   public String getPlayerName() {
      return this.name;
   }

   @Nullable
   @OnlyIn(Dist.CLIENT)
   public String getObjectiveName() {
      return this.objective;
   }

   @OnlyIn(Dist.CLIENT)
   public int getScoreValue() {
      return this.value;
   }

   @OnlyIn(Dist.CLIENT)
   public ServerScoreboard.Action getAction() {
      return this.action;
   }
}
