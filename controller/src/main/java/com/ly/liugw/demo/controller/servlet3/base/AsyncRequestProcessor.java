package com.ly.liugw.demo.controller.servlet3.base;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.io.PrintWriter;

public class AsyncRequestProcessor implements Runnable {
    private AsyncContext asyncContext;
    private int processTime;

    public AsyncRequestProcessor() {

    }

    public AsyncRequestProcessor(AsyncContext asyncContext, int processTime) {
        this.asyncContext = asyncContext;
        this.processTime = processTime;
    }

    @Override
    public void run() {
        System.out.println("是否异步：" + asyncContext.getRequest().isAsyncSupported());

        doWorkProcess(processTime);

        try (PrintWriter out = asyncContext.getResponse().getWriter()) {

            out.write(new String("process finish: ") + processTime + "ms.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 处理完成
        asyncContext.complete();
    }

    private void doWorkProcess(int processTime) {
        try {
            Thread.sleep(processTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
