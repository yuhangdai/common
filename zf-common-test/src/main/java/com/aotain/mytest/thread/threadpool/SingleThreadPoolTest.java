package com.aotain.mytest.thread.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/19
 */
public class SingleThreadPoolTest {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(1,1,0, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>());
        for (int i=0;i<5;i++){
            threadPoolExecutor.submit(new LiftOff());
        }
        threadPoolExecutor.shutdown();
    }
}
