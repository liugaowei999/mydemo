package com.ly.liugw.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * @ServletComponentScan  : 该注解可以是spring 扫描加载 @WebServlet注解的servlet类
 */
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages = {"com.ly.liugw.demo"})
@PropertySource(value = {"classpath:application.properties"})
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}
