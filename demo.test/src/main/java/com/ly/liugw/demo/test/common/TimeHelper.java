package com.ly.liugw.demo.test.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeHelper {
    private static long start;
    private static long end;

    public static void start() {
        start = System.currentTimeMillis();
    }

    public static void end() {
        end = System.currentTimeMillis();
        log.info("总计耗时 {}s", (end - start) / 1000.0);
    }
}
