package com.ly.liugw.demo.test.chat.nettyApp;


import com.ly.liugw.demo.test.Constants;
import com.ly.liugw.demo.test.chat.decoder.MsgDecoder;
import com.ly.liugw.demo.test.chat.encode.MsgEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Client {

    public void start() {
        // 工作组
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        // 启动器
        Bootstrap bootstrap = new Bootstrap();

        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .group(nioEventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
//                        socketChannel.pipeline().addLast(new PacketDecoder());
                        socketChannel.pipeline().addLast(new MsgDecoder());
                        socketChannel.pipeline().addLast(MsgEncoder.INSTANCE);
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });

        // 建立连接
        bootstrap.connect(Constants.serverHost, Constants.serverPort).addListener(conn -> {
            if (conn.isSuccess()) {
                log.info("连接已建立！去找Server了.......");
            } else {
                log.error("无法建立连接！");
            }
        });
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }
}
