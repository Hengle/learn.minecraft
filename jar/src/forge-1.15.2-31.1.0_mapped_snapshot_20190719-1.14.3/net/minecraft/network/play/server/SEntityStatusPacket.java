package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SEntityStatusPacket implements IPacket<IClientPlayNetHandler> {
   private int entityId;
   private byte logicOpcode;

   public SEntityStatusPacket() {
   }

   public SEntityStatusPacket(Entity p_i46946_1_, byte p_i46946_2_) {
      this.entityId = p_i46946_1_.getEntityId();
      this.logicOpcode = p_i46946_2_;
   }

   public void readPacketData(PacketBuffer p_148837_1_) throws IOException {
      this.entityId = p_148837_1_.readInt();
      this.logicOpcode = p_148837_1_.readByte();
   }

   public void writePacketData(PacketBuffer p_148840_1_) throws IOException {
      p_148840_1_.writeInt(this.entityId);
      p_148840_1_.writeByte(this.logicOpcode);
   }

   public void processPacket(IClientPlayNetHandler p_148833_1_) {
      p_148833_1_.handleEntityStatus(this);
   }

   @OnlyIn(Dist.CLIENT)
   public Entity getEntity(World p_149161_1_) {
      return p_149161_1_.getEntityByID(this.entityId);
   }

   @OnlyIn(Dist.CLIENT)
   public byte getOpCode() {
      return this.logicOpcode;
   }
}
