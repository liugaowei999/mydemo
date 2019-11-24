package com.ly.liugw.demo.test.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeHelper {
    private static long start;
    private static long end;

    public static void start() {
        start = System.currentTimeMillis();
        System.out.println("====== start at " + start);
    }

    public static void end() {
        end = System.currentTimeMillis();
        System.out.println("====== start at " + start);
        System.out.println("====== end   at " + end);
        log.info("总计耗时 {}s", (end - start) / 1000.0);
    }
}
