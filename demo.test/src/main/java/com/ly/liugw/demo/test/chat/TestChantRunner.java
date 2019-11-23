package com.ly.liugw.demo.test.chat;

import com.ly.liugw.demo.test.chat.nettyApp.Client;
import com.ly.liugw.demo.test.chat.nettyApp.Server;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import javax.annotation.Resource;

//@Component
class TestChantRunner implements ApplicationRunner {
    @Resource
    Client client;

    @Resource
    Server server;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("======= Chat starting..... ======");

        server.start();
        client.start();
    }
}