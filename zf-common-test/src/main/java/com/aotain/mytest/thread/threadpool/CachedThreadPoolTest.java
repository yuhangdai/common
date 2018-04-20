package com.aotain.mytest.thread.threadpool;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/19
 */
public class CachedThreadPoolTest {
    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(0,Integer.MAX_VALUE,60,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
        for (int i=0;i<5;i++){
            threadPoolExecutor.submit(new LiftOff());
        }
        threadPoolExecutor.shutdown();
    }
}
