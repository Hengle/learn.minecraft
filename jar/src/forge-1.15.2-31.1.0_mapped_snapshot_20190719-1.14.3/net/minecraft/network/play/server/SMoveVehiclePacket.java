package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SMoveVehiclePacket implements IPacket<IClientPlayNetHandler> {
   private double x;
   private double y;
   private double z;
   private float yaw;
   private float pitch;

   public SMoveVehiclePacket() {
   }

   public SMoveVehiclePacket(Entity p_i46935_1_) {
      this.x = p_i46935_1_.func_226277_ct_();
      this.y = p_i46935_1_.func_226278_cu_();
      this.z = p_i46935_1_.func_226281_cx_();
      this.yaw = p_i46935_1_.rotationYaw;
      this.pitch = p_i46935_1_.rotationPitch;
   }

   public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
      this.x = p_148837_1_.readDouble();
      this.y = p_148837_1_.readDouble();
      this.z = p_148837_1_.readDouble();
      this.yaw = p_148837_1_.readFloat();
      this.pitch = p_148837_1_.readFloat();
   }

   public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeDouble(this.x);
      p_148840_1_.writeDouble(this.y);
      p_148840_1_.writeDouble(this.z);
      p_148840_1_.writeFloat(this.yaw);
      p_148840_1_.writeFloat(this.pitch);
   }

   public void processPacket(IClientPlayNetHandler p_148833_1_) {
      p_148833_1_.handleMoveVehicle(this);
   }

   @OnlyIn(Dist.CLIENT)
   public double getX() {
      return this.x;
   }

   @OnlyIn(Dist.CLIENT)
   public double getY() {
      return this.y;
   }

   @OnlyIn(Dist.CLIENT)
   public double getZ() {
      return this.z;
   }

   @OnlyIn(Dist.CLIENT)
   public float getYaw() {
      return this.yaw;
   }

   @OnlyIn(Dist.CLIENT)
   public float getPitch() {
      return this.pitch;
   }
}
