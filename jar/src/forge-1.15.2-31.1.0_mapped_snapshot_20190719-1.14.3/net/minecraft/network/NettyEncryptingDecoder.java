package net.minecraft.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;

public class NettyEncryptingDecoder extends MessageToMessageDecoder<ByteBuf> {
   private final NettyEncryptionTranslator decryptionCodec;

   public NettyEncryptingDecoder(Cipher p_i45141_1_) {
      this.decryptionCodec = new NettyEncryptionTranslator(p_i45141_1_);
   }

   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws Exception {
      p_decode_3_.add(this.decryptionCodec.decipher(p_decode_1_, p_decode_2_));
   }

   // $FF: synthetic method
   protected void decode(ChannelHandlerContext p_decode_1_, Object p_decode_2_, List p_decode_3_) throws Exception {
      this.decode(p_decode_1_, (ByteBuf)p_decode_2_, p_decode_3_);
   }
}
