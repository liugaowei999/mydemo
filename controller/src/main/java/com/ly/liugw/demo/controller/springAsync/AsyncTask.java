package com.ly.liugw.demo.controller.springAsync;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

public class AsyncTask implements Runnable {
    private DeferredResult deferredResult;


    public AsyncTask(DeferredResult<ResponseEntity<String>> deferredResult) {
        this.deferredResult = deferredResult;
    }

    @Override
    public void run() {
        Map<String, String> a = new HashMap<>();
        a.put("A","afsdafds");
        deferredResult.setResult(a);
    }
}
