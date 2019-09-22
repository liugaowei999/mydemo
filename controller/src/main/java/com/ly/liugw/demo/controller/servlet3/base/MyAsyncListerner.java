package com.ly.liugw.demo.controller.servlet3.base;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyAsyncListerner implements AsyncListener {
    @Override
    public void onComplete(AsyncEvent asyncEvent) throws IOException {
        System.out.println("MyAsyncListerner complete!");
    }

    /**
     * 当处理超时时，调用该方法
     *
     * @param asyncEvent
     * @throws IOException
     */
    @Override
    public void onTimeout(AsyncEvent asyncEvent) throws IOException {
        System.out.println("MyAsyncListerner onTimeout!");
        ServletResponse response = asyncEvent.getAsyncContext().getResponse();
        PrintWriter out = response.getWriter();
        out.write("TimeOut! 超时了");
    }

    @Override
    public void onError(AsyncEvent asyncEvent) throws IOException {
        System.out.println("MyAsyncListerner onError!");
    }

    @Override
    public void onStartAsync(AsyncEvent asyncEvent) throws IOException {
        System.out.println("MyAsyncListerner onStartAsync!");
    }
}
