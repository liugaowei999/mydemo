package com.ly.liugw.demo.controller.servlet3;

import com.ly.liugw.demo.controller.servlet3.base.AsyncRequestProcessor;
import com.ly.liugw.demo.controller.servlet3.base.MyAsyncListerner;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadPoolExecutor;

@WebServlet(urlPatterns = "/async/task", asyncSupported = true)
public class AsyncServletTest extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.currentTimeMillis();
        System.out.println("Async Servlet start! ThreadName=" + Thread.currentThread().getName() + ", ThreadId=" + Thread.currentThread().getId());

        resp.setContentType("test/html;charset=UTF-8");

        req.setAttribute("org.apache.catalina.ASYNC_SPPORTED", true);

        // 动态设置模拟后续业务逻辑处理的时间， 便于测试对比异步上下文时间超时与不超时的代码反应
        String time = req.getParameter("time");
        int processTime = Integer.valueOf(time);

        if (processTime > 5000) {
            processTime = 5000;
        }

        AsyncContext asyncContext = req.startAsync();

        // 添加监听
        asyncContext.addListener(new MyAsyncListerner());
        asyncContext.setTimeout(4000); // 异步servlet处理有对应的超时时间， 如果在指定的时间内没有处理完业务逻辑，则会抛出异常

        // 获取业务工作线程池，这里的executor就是在下文中AppContextListener类里面设置的
        ThreadPoolExecutor executor = (ThreadPoolExecutor) req.getServletContext().getAttribute("executor");

        // 异步执行具体业务逻辑，同时把processTime模拟业务逻辑处理的时间穿进去
        executor.execute(new AsyncRequestProcessor(asyncContext, processTime));

        long endTime = System.currentTimeMillis();

        System.out.println("====== Async Servlet end! ThreadName=" + Thread.currentThread().getName() + ", ThreadId=" + Thread.currentThread().getId() + ", Time cost=" + (endTime-startTime) + "ms.");
        resp.setBufferSize(10);
        PrintWriter out = resp.getWriter();

        out.write("====== Async Servlet end! ThreadName=" + Thread.currentThread().getName() + ", ThreadId=" + Thread.currentThread().getId() + ", Time cost=" + (endTime-startTime) + "ms." + "\n");
        out.flush();


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        out.write("xxxxxxxxxxxxxxxxxxxxxxxxxx\n");
        out.flush();

    }
}
