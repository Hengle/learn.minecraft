package net.minecraftforge.fml.network;

import io.netty.buffer.Unpooled;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.FuzzedBiomeMagnifier;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.LogicalSidedProvider;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.registries.ForgeRegistries;

public class FMLPlayMessages {
   public static class DimensionInfoMessage {
      private ResourceLocation dimName;
      private boolean skylight;
      private int id;
      private ResourceLocation modDimensionName;
      private PacketBuffer extraData;

      DimensionInfoMessage(DimensionType type) {
         this.id = type.getId() + 1;
         this.dimName = type.getRegistryName();
         this.modDimensionName = type.getModType().getRegistryName();
         this.skylight = type.func_218272_d();
         this.extraData = new PacketBuffer(Unpooled.buffer());
         type.getModType().write(this.extraData, true);
      }

      DimensionInfoMessage(int dimId, ResourceLocation dimname, ResourceLocation modDimensionName, boolean skylight, PacketBuffer extraData) {
         this.id = dimId;
         this.dimName = dimname;
         this.modDimensionName = modDimensionName;
         this.skylight = skylight;
         this.extraData = extraData;
      }

      public static FMLPlayMessages.DimensionInfoMessage decode(PacketBuffer buffer) {
         int dimId = buffer.readInt();
         ResourceLocation dimname = buffer.readResourceLocation();
         ResourceLocation moddimname = buffer.readResourceLocation();
         boolean skylight = buffer.readBoolean();
         PacketBuffer pb = new PacketBuffer(Unpooled.wrappedBuffer(buffer.readByteArray()));
         return new FMLPlayMessages.DimensionInfoMessage(dimId, dimname, moddimname, skylight, pb);
      }

      public static void encode(FMLPlayMessages.DimensionInfoMessage message, PacketBuffer buffer) {
         buffer.writeInt(message.id);
         buffer.writeResourceLocation(message.dimName);
         buffer.writeResourceLocation(message.modDimensionName);
         buffer.writeBoolean(message.skylight);
         buffer.writeByteArray(message.extraData.array());
      }

      private DimensionType makeDummyDimensionType() {
         ModDimension modDim = (ModDimension)ForgeRegistries.MOD_DIMENSIONS.getValue(this.modDimensionName);
         if (modDim == null) {
            return DimensionType.OVERWORLD;
         } else {
            modDim.read(this.extraData, true);
            return new DimensionType(this.id, "dummy", "dummy", modDim.getFactory(), this.skylight, FuzzedBiomeMagnifier.INSTANCE, modDim, this.extraData);
         }
      }

      public static boolean handle(FMLPlayMessages.DimensionInfoMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
         ((NetworkEvent.Context)contextSupplier.get()).enqueueWork(() -> {
            NetworkHooks.addCachedDimensionType(message.makeDummyDimensionType(), message.dimName);
         });
         return true;
      }
   }

   public static class OpenContainer {
      private final int id;
      private final int windowId;
      private final ITextComponent name;
      private final PacketBuffer additionalData;

      OpenContainer(ContainerType<?> id, int windowId, ITextComponent name, PacketBuffer additionalData) {
         this(Registry.MENU.getId(id), windowId, name, additionalData);
      }

      private OpenContainer(int id, int windowId, ITextComponent name, PacketBuffer additionalData) {
         this.id = id;
         this.windowId = windowId;
         this.name = name;
         this.additionalData = additionalData;
      }

      public static void encode(FMLPlayMessages.OpenContainer msg, PacketBuffer buf) {
         buf.writeVarInt(msg.id);
         buf.writeVarInt(msg.windowId);
         buf.writeTextComponent(msg.name);
         buf.writeByteArray(msg.additionalData.readByteArray());
      }

      public static FMLPlayMessages.OpenContainer decode(PacketBuffer buf) {
         return new FMLPlayMessages.OpenContainer(buf.readVarInt(), buf.readVarInt(), buf.readTextComponent(), new PacketBuffer(Unpooled.wrappedBuffer(buf.readByteArray(32600))));
      }

      public static void handle(FMLPlayMessages.OpenContainer msg, Supplier<NetworkEvent.Context> ctx) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            ScreenManager.getScreenFactory(msg.getType(), Minecraft.getInstance(), msg.getWindowId(), msg.getName()).ifPresent((f) -> {
               Container c = msg.getType().create(msg.getWindowId(), Minecraft.getInstance().player.inventory, msg.getAdditionalData());
               Screen s = f.create(c, Minecraft.getInstance().player.inventory, msg.getName());
               Minecraft.getInstance().player.openContainer = ((IHasContainer)s).getContainer();
               Minecraft.getInstance().displayGuiScreen(s);
            });
         });
         ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
      }

      public final ContainerType<?> getType() {
         return (ContainerType)Registry.MENU.getByValue(this.id);
      }

      public int getWindowId() {
         return this.windowId;
      }

      public ITextComponent getName() {
         return this.name;
      }

      public PacketBuffer getAdditionalData() {
         return this.additionalData;
      }
   }

   public static class SpawnEntity {
      private final Entity entity;
      private final int typeId;
      private final int entityId;
      private final UUID uuid;
      private final double posX;
      private final double posY;
      private final double posZ;
      private final byte pitch;
      private final byte yaw;
      private final byte headYaw;
      private final int velX;
      private final int velY;
      private final int velZ;
      private final PacketBuffer buf;

      SpawnEntity(Entity e) {
         this.entity = e;
         this.typeId = Registry.ENTITY_TYPE.getId(e.getType());
         this.entityId = e.getEntityId();
         this.uuid = e.getUniqueID();
         this.posX = e.func_226277_ct_();
         this.posY = e.func_226278_cu_();
         this.posZ = e.func_226281_cx_();
         this.pitch = (byte)MathHelper.floor(e.rotationPitch * 256.0F / 360.0F);
         this.yaw = (byte)MathHelper.floor(e.rotationYaw * 256.0F / 360.0F);
         this.headYaw = (byte)((int)(e.getRotationYawHead() * 256.0F / 360.0F));
         Vec3d vec3d = e.getMotion();
         double d1 = MathHelper.clamp(vec3d.x, -3.9D, 3.9D);
         double d2 = MathHelper.clamp(vec3d.y, -3.9D, 3.9D);
         double d3 = MathHelper.clamp(vec3d.z, -3.9D, 3.9D);
         this.velX = (int)(d1 * 8000.0D);
         this.velY = (int)(d2 * 8000.0D);
         this.velZ = (int)(d3 * 8000.0D);
         this.buf = null;
      }

      private SpawnEntity(int typeId, int entityId, UUID uuid, double posX, double posY, double posZ, byte pitch, byte yaw, byte headYaw, int velX, int velY, int velZ, PacketBuffer buf) {
         this.entity = null;
         this.typeId = typeId;
         this.entityId = entityId;
         this.uuid = uuid;
         this.posX = posX;
         this.posY = posY;
         this.posZ = posZ;
         this.pitch = pitch;
         this.yaw = yaw;
         this.headYaw = headYaw;
         this.velX = velX;
         this.velY = velY;
         this.velZ = velZ;
         this.buf = buf;
      }

      public static void encode(FMLPlayMessages.SpawnEntity msg, PacketBuffer buf) {
         buf.writeVarInt(msg.typeId);
         buf.writeInt(msg.entityId);
         buf.writeLong(msg.uuid.getMostSignificantBits());
         buf.writeLong(msg.uuid.getLeastSignificantBits());
         buf.writeDouble(msg.posX);
         buf.writeDouble(msg.posY);
         buf.writeDouble(msg.posZ);
         buf.writeByte(msg.pitch);
         buf.writeByte(msg.yaw);
         buf.writeByte(msg.headYaw);
         buf.writeShort(msg.velX);
         buf.writeShort(msg.velY);
         buf.writeShort(msg.velZ);
         if (msg.entity instanceof IEntityAdditionalSpawnData) {
            ((IEntityAdditionalSpawnData)msg.entity).writeSpawnData(buf);
         }

      }

      public static FMLPlayMessages.SpawnEntity decode(PacketBuffer buf) {
         return new FMLPlayMessages.SpawnEntity(buf.readVarInt(), buf.readInt(), new UUID(buf.readLong(), buf.readLong()), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readByte(), buf.readByte(), buf.readByte(), buf.readShort(), buf.readShort(), buf.readShort(), buf);
      }

      public static void handle(FMLPlayMessages.SpawnEntity msg, Supplier<NetworkEvent.Context> ctx) {
         ((NetworkEvent.Context)ctx.get()).enqueueWork(() -> {
            EntityType<?> type = (EntityType)Registry.ENTITY_TYPE.getByValue(msg.typeId);
            if (type == null) {
               throw new RuntimeException(String.format("Could not spawn entity (id %d) with unknown type at (%f, %f, %f)", msg.entityId, msg.posX, msg.posY, msg.posZ));
            } else {
               Optional<World> world = (Optional)LogicalSidedProvider.CLIENTWORLD.get(((NetworkEvent.Context)ctx.get()).getDirection().getReceptionSide());
               Entity e = (Entity)world.map((w) -> {
                  return type.customClientSpawn(msg, w);
               }).orElse((Object)null);
               if (e != null) {
                  e.func_213312_b(msg.posX, msg.posY, msg.posZ);
                  e.setPositionAndRotation(msg.posX, msg.posY, msg.posZ, (float)(msg.yaw * 360) / 256.0F, (float)(msg.pitch * 360) / 256.0F);
                  e.setRotationYawHead((float)(msg.headYaw * 360) / 256.0F);
                  e.setRenderYawOffset((float)(msg.headYaw * 360) / 256.0F);
                  e.setEntityId(msg.entityId);
                  e.setUniqueId(msg.uuid);
                  ClientWorld.class.getClass();
                  world.filter(ClientWorld.class::isInstance).ifPresent((w) -> {
                     ((ClientWorld)w).addEntity(msg.entityId, e);
                  });
                  e.setVelocity((double)msg.velX / 8000.0D, (double)msg.velY / 8000.0D, (double)msg.velZ / 8000.0D);
                  if (e instanceof IEntityAdditionalSpawnData) {
                     ((IEntityAdditionalSpawnData)e).readSpawnData(msg.buf);
                  }

               }
            }
         });
         ((NetworkEvent.Context)ctx.get()).setPacketHandled(true);
      }

      public Entity getEntity() {
         return this.entity;
      }

      public int getTypeId() {
         return this.typeId;
      }

      public int getEntityId() {
         return this.entityId;
      }

      public UUID getUuid() {
         return this.uuid;
      }

      public double getPosX() {
         return this.posX;
      }

      public double getPosY() {
         return this.posY;
      }

      public double getPosZ() {
         return this.posZ;
      }

      public byte getPitch() {
         return this.pitch;
      }

      public byte getYaw() {
         return this.yaw;
      }

      public byte getHeadYaw() {
         return this.headYaw;
      }

      public int getVelX() {
         return this.velX;
      }

      public int getVelY() {
         return this.velY;
      }

      public int getVelZ() {
         return this.velZ;
      }

      public PacketBuffer getAdditionalData() {
         return this.buf;
      }
   }
}
