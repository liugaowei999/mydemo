package com.ly.liugw.demo.test.chat.nettyApp;

import com.ly.liugw.demo.test.Constants;
import com.ly.liugw.demo.test.chat.Message;
import com.ly.liugw.demo.test.common.TimeHelper;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * ctx.write(new Message(message.getIndex(), SERVER, z3));
 * netty 已经实现了线程安全， 应用不需要处理并发场景。
 * 一个channel在netty中只会被一个线程处理， 不存在并发的问题。业务端另起一个Thread执行ctx.write，最终netty也会交给本channel的线程执行写入操作，不会有并发问题
 * io.netty.channel.AbstractChannelHandlerContext#write(java.lang.Object, boolean, io.netty.channel.ChannelPromise)
 * if (!safeExecute(executor, task, promise, m)) {
 *                 // We failed to submit the AbstractWriteTask. We need to cancel it so we decrement the pending bytes
 *                 // and put it back in the Recycler for re-use later.
 *                 //
 *                 // See https://github.com/netty/netty/issues/8343.
 *                 task.cancel();
 *             }
 */
@Slf4j
//@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<Message> {
    public static final ServerHandler INSTANCE = new ServerHandler();

    private final String z0 = "吃了没，您吶?";
    private final String l1 = "刚吃。";
    private final String l2 = "您这，嘛去？";
    private final String z3 = "嗨！吃饱了溜溜弯儿。";
    private final String l4 = "有空家里坐坐啊。";
    private final String z5 = "回头去给老太太请安！";

    private final int SERVER = 1;
    private int cnt1 = 0;
    private int cnt2 = 0;

    private ServerHandler() {
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) throws Exception {
        //log.info("Server 收到消息：{}", message);
        if (l2.equals(message.getMsg())) {
            cnt1++;
//            log.info("Server 发送消息：{}", z3);
//            synchronized (ctx) {
                ctx.write(new Message(message.getIndex(), SERVER, z3));
//            }
            if (cnt1 % Constants.BATCH_COUNT == 0) {
                ctx.flush();
            }
        } else if (l4.equals(message.getMsg())) {
            cnt2++;
//            log.info("Server 发送消息：{}", z5);
//            synchronized (ctx) {
                ctx.write(new Message(message.getIndex(), SERVER, z5));
//            }
            if (cnt2 % Constants.BATCH_COUNT == 0) {
                ctx.flush();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        log.error("服务端发生错误！{}", cause);
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info("Server channel Active, 碰到老朋友{}了，开始聊天.......", ctx.channel().remoteAddress());
        TimeHelper.start();

        new Thread(() -> {
            //连接建立完成
            for (int i = 1; i <= Constants.SEND_COUNT; i++) {
                Message message = new Message(i, SERVER, z0);
//                log.info("{} Server Active 发送消息：{} =========",i, message.getMsg());
//                synchronized (ctx) {
                    ctx.write(message);
//                }
                if ((i + 1) % Constants.BATCH_COUNT == 0) {
                    ctx.flush();
                }
            }
        }).start();
        log.info("Server channel Actived.$$$$$$$$$");
    }
}
