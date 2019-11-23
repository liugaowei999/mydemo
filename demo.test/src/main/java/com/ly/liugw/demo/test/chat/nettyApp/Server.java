package com.ly.liugw.demo.test.chat.nettyApp;

import com.ly.liugw.demo.test.Constants;
import com.ly.liugw.demo.test.chat.decoder.MsgDecoder;
import com.ly.liugw.demo.test.chat.encode.MsgEncoder;
import com.ly.liugw.demo.test.common.NamedThreadFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Server {

    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1, new NamedThreadFactory("blg-boss"));
        EventLoopGroup workerGroup = new NioEventLoopGroup(1, new NamedThreadFactory("blg-worker"));

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(65535))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new MsgDecoder());
                        ch.pipeline().addLast(MsgEncoder.INSTANCE);
                        ch.pipeline().addLast(ServerHandler.INSTANCE);
                    }
                });
        b.bind(Constants.serverPort).addListener(future -> {
            if (future.isSuccess()) {
                log.info("server startup success, 等待朋友的到来........");
            } else {
                log.error("server startup failed", future.cause());
            }
        });


    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }
}
