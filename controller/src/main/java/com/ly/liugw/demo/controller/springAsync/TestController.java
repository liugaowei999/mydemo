package com.ly.liugw.demo.controller.springAsync;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

@Controller
@RequestMapping("/async/task3")
public class TestController {

    @ResponseBody
    @RequestMapping("/{str}")
    public DeferredResult<ResponseEntity<String>> testProcess(@PathVariable String str) {
        final DeferredResult<ResponseEntity<String>> deferredResult = new DeferredResult<>();

        // 业务逻辑异步处理， 将处理结果set 到 DeferredResult
        new Thread(new AsyncTask(deferredResult)).start();
        return deferredResult;
    }
}
