package net.minecraft.realms;

import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsServerAddress {
   private final String host;
   private final int port;

   protected RealmsServerAddress(String p_i1121_1_, int p_i1121_2_) {
      this.host = p_i1121_1_;
      this.port = p_i1121_2_;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public static RealmsServerAddress parseString(String p_parseString_0_) {
      ServerAddress lvt_1_1_ = ServerAddress.fromString(p_parseString_0_);
      return new RealmsServerAddress(lvt_1_1_.getIP(), lvt_1_1_.getPort());
   }
}
