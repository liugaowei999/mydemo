package com.ly.liugw.demo.test.chat.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private  static FixLengthDecoder fixLengthDecoder = new FixLengthDecoder();

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int readableBytes = byteBuf.readableBytes();
        if (readableBytes < FixLengthDecoder.LENGTH_FIELD_LENGTH) {
            return;
        }
        Object content = fixLengthDecoder.decode(channelHandlerContext, byteBuf);
        if (content != null) {
            list.add(content);
        }

    }
}
