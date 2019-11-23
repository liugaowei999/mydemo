package com.ly.liugw.demo.test.chat.nettyApp;

import com.ly.liugw.demo.test.Constants;
import com.ly.liugw.demo.test.chat.Message;
import com.ly.liugw.demo.test.common.TimeHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<Message> {
    private final String z0 = "吃了没，您吶?";
    private final String l1 = "刚吃。";
    private final String l2 = "您这，嘛去？";
    private final String z3 = "嗨！吃饱了溜溜弯儿。";
    private final String l4 = "有空家里坐坐啊。";
    private final String z5 = "回头去给老太太请安！";

    private int cnt1 = 0;
    private int cnt2 = 0;
    private int cnt3 = 0;

    private final int CLIENT = 0;

    private Object lock = new Object();



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        log.info("Client 收到消息：{}", message);
        // TimeHelper.start();
        if (z0.equals(message.getMsg())) {
            cnt1++;
//            log.info("{} Client 发送消息：{}",cnt1, l1);
//            log.info("Client 发送消息：{}", l2);
            //synchronized (lock) {
                ctx.write(new Message(message.getIndex(),CLIENT, l1));
                ctx.write(new Message(message.getIndex(), CLIENT,l2));
            //}

            if (cnt1 % Constants.BATCH_COUNT == 0) {
                ctx.flush();
            }
        } else if (z3.equals(message.getMsg())) {
            cnt2++;
//            log.info("Client 发送消息：{}", l4);
//            synchronized (lock) {
                ctx.write(new Message(message.getIndex(),CLIENT, l4));
//            }
            if (cnt2 % Constants.BATCH_COUNT == 0) {
                ctx.flush();
            }
        } else if (z5.equals(message.getMsg())) {
            cnt3++;
            if (cnt3 == Constants.SEND_COUNT) {
                TimeHelper.end();
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("Client channel Active, 碰到老朋友了{}，开始聊天.......", ctx.channel().remoteAddress());
        new Thread(() -> {
            //连接建立完成
            for (int i = 1; i <= Constants.SEND_COUNT; i++) {
                Message message = new Message(i, CLIENT, z0);
//                log.info("{} Server Active 发送消息：{} =========",i, message.getMsg());
//                synchronized (lock) {
                    ctx.write(message);
//                }
                if ((i + 1) % Constants.BATCH_COUNT == 0) {
                    ctx.flush();
                }
            }
        }).start();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error("LiDaYeMsgHandler exceptionCaught:", cause);
        ctx.close();
    }
}
