package com.ly.liugw.demo.controller.servlet3noblock;

import javax.servlet.AsyncContext;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class MyTestReadListener implements ReadListener {
    private ServletInputStream in;
    private AsyncContext asyncContext;


    public MyTestReadListener(ServletInputStream in, AsyncContext asyncContext) {
        this.in = in;
        this.asyncContext = asyncContext;
    }

    @Override
    public void onDataAvailable() throws IOException {
        System.out.println("MyTestReadListener onDataAvailable -- 数据可用了！");
    }

    @Override
    public void onAllDataRead() throws IOException {
        System.out.println("MyTestReadListener onAllDataRead -- 数据全部读取了！开始业务逻辑处理");
        try {
            // 设置暂停1秒， 模拟耗时的业务处理
            Thread.sleep(1000);

            PrintWriter out = asyncContext.getResponse().getWriter();
            out.write("业务逻辑处理完成！");
            out.flush();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("MyTestReadListener onError -- 出错了！");
        throwable.printStackTrace();
    }
}
