package com.ly.liugw.demo.test.chat.decoder;

import com.ly.liugw.demo.test.chat.Message;
import com.ly.liugw.demo.test.common.CharsetHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class MsgDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int index = byteBuf.readInt();
        int type = byteBuf.readInt();
        int len = byteBuf.readInt();

        byte[] data = new byte[len];
        byteBuf.readBytes(data);

        Message message = new Message(index, type, new String(data, CharsetHelper.UTF8));
        list.add(message);
    }
}
