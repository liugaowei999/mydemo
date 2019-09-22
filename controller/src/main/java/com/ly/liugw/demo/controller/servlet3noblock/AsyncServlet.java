package com.ly.liugw.demo.controller.servlet3noblock;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/async/task2", asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

//        req.setAttribute("org.apache.catalina.ASYNC_SPPORTED", true);

        // 通过request获取异步上下文对象
        AsyncContext asyncContext = req.startAsync();

        asyncContext.setTimeout(2000);

        ServletInputStream in = req.getInputStream();
        // 异步读取， 同时通过MyReadListener实现了非阻塞方式的获取
        in.setReadListener(new MyTestReadListener(in, asyncContext));

        // 获取输出流对象， 此时这里可直接输出响应内容并返回到页面（异步处理逻辑还没执行）
        PrintWriter out = resp.getWriter();
        out.println("直接返回，异步处理中....");
        out.flush();
    }
}
