package com.ly.liugw.demo.test.lru.lru.test;

import com.ly.liugw.demo.test.common.TimeHelper;
import com.ly.liugw.demo.test.lru.LruCache;
import com.ly.liugw.demo.test.lru.lru.LruCacheImp;

public class LruTest {


    public static void main(String[] args) {
        LruCache<String, String> lruCacheImp = new LruCacheImp<String, String>(5, null);
        lruCacheImp.set("1", "a");
        lruCacheImp.print();
        lruCacheImp.set("2", "a");
        lruCacheImp.print();
        lruCacheImp.set("3", "a");
//        lruCacheImp.print();
//        System.out.println("查询2=" + lruCacheImp.get("2"));
//        lruCacheImp.print();
        lruCacheImp.set("3", "b");
//        lruCacheImp.print();
        lruCacheImp.set("4", "a");
        lruCacheImp.set("5", "a");
//        lruCacheImp.print();
        lruCacheImp.set("6", "a");
        lruCacheImp.print();
        System.out.println("查询1=" + lruCacheImp.get("1"));
        lruCacheImp.print();
        System.out.println("查询2=" + lruCacheImp.get("2"));
        lruCacheImp.print();
        lruCacheImp.set("7", "a");
        lruCacheImp.print();

        System.out.println("查询6=" + lruCacheImp.get("6"));
        lruCacheImp.print();

        TimeHelper.start();
        for (int i=1; i<1000000; i++) {
            lruCacheImp.set("" + i, "a");
//            if (i % 100000 == 0) {
//                try {
//                    lruCacheImp.print();
//                    //System.gc();
//                    //Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
        lruCacheImp.print();
        TimeHelper.end();

        while (true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
