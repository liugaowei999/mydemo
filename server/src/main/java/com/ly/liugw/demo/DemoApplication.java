package com.ly.liugw.demo;

import com.ly.liugw.demo.test.chat.nettyApp.Client;
import com.ly.liugw.demo.test.chat.nettyApp.Server;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.Resource;

/**
 * @ServletComponentScan  : 该注解可以是spring 扫描加载 @WebServlet注解的servlet类
 *
 * https://github.com/qingzhou413/geektime-mq-rpc/blob/master/src/main/java/cn/qingzhou/geektimemqrpc/GeektimeMqRpcApplication.java
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages = {"com.ly.liugw.demo"})
@PropertySource(value = {"classpath:application.properties"})
public class DemoApplication implements ApplicationRunner {
    @Resource
    Client client;

    @Resource
    Server server;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("=======================================");
        System.out.println("======= Chat starting..... ======");

        server.start();
        client.start();
    }

}
