package com.ly.liugw.demo.test.chat.encode;

import com.ly.liugw.demo.test.chat.Message;
import com.ly.liugw.demo.test.common.CharsetHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@ChannelHandler.Sharable
public class MsgEncoder extends MessageToByteEncoder<Message> {
    public static final MsgEncoder INSTANCE = new MsgEncoder();

    private MsgEncoder() {}

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        if (message == null) {
            throw new RuntimeException("要编码的消息内容为空！");
        }
//        byte[] indexByte = new byte[4];
////        message.getIndex();
        byte[] bytes = message.getMsg().getBytes(CharsetHelper.UTF8);
        int length = bytes.length;
        byteBuf.writeInt(message.getIndex());
        byteBuf.writeInt(message.getType());
        byteBuf.writeInt(length);
        byteBuf.writeBytes(bytes);
    }
}
