package net.minecraftforge.fml.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.IPacket;
import net.minecraft.network.NettyVarint21FrameDecoder;
import net.minecraft.network.NettyVarint21FrameEncoder;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PacketLoggingHandler {
   private static final Logger LOGGER = LogManager.getLogger();

   public static void register(NetworkManager manager) {
      ChannelPipeline pipeline = manager.channel().pipeline();
      final PacketDirection direction = manager.getDirection();
      if (manager.isLocalChannel()) {
         pipeline.addBefore("packet_handler", "splitter", new SimpleChannelInboundHandler<IPacket<?>>() {
            String prefix;

            {
               this.prefix = direction == PacketDirection.SERVERBOUND ? "SERVER: C->S" : "CLIENT: S->C";
            }

            protected void channelRead0(ChannelHandlerContext ctx, IPacket<?> msg) throws Exception {
               PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
               msg.writePacketData(buf);
               PacketLoggingHandler.LOGGER.debug("{} {}:\n{}", this.prefix, msg.getClass().getSimpleName(), ByteBufUtils.getContentDump(buf));
               ctx.fireChannelRead(msg);
            }
         });
         pipeline.addBefore("splitter", "prepender", new ChannelOutboundHandlerAdapter() {
            String prefix;

            {
               this.prefix = direction == PacketDirection.SERVERBOUND ? "SERVER: S->C" : "CLIENT: C->S";
            }

            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
               if (msg instanceof IPacket) {
                  PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
                  ((IPacket)msg).writePacketData(buf);
                  PacketLoggingHandler.LOGGER.debug("{} {}:\n{}", this.prefix, msg.getClass().getSimpleName(), ByteBufUtils.getContentDump(buf));
               }

               ctx.write(msg, promise);
            }
         });
      } else {
         pipeline.replace("splitter", "splitter", new NettyVarint21FrameDecoder() {
            String prefix;

            {
               this.prefix = direction == PacketDirection.SERVERBOUND ? "SERVER: C->S" : "CLIENT: S->C";
            }

            protected void decode(ChannelHandlerContext context, ByteBuf input, List<Object> output) throws Exception {
               super.decode(context, input, output);
               Iterator itr = output.iterator();

               while(itr.hasNext()) {
                  ByteBuf pkt = (ByteBuf)itr.next();
                  pkt.markReaderIndex();
                  PacketLoggingHandler.LOGGER.debug("{}:\n{}", this.prefix, ByteBufUtils.getContentDump(pkt));
                  pkt.resetReaderIndex();
               }

            }
         });
         pipeline.replace("prepender", "prepender", new NettyVarint21FrameEncoder() {
            String prefix;

            {
               this.prefix = direction == PacketDirection.SERVERBOUND ? "SERVER: S->C" : "CLIENT: C->S";
            }

            protected void encode(ChannelHandlerContext context, ByteBuf input, ByteBuf output) throws Exception {
               input.markReaderIndex();
               PacketLoggingHandler.LOGGER.debug("{}:\n{}", this.prefix, ByteBufUtils.getContentDump(input));
               input.resetReaderIndex();
               super.encode(context, input, output);
            }
         });
      }

   }
}
