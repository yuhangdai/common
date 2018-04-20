package com.aotain.mytest.thread.threadpool;

import java.util.concurrent.*;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/19
 */
public class FixedThreadPoolTest {
    public static void main(String[] args) {

        ThreadPoolExecutor threadPoolExecutor =
                new ThreadPoolExecutor(3,3,0, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());
        for (int i=0;i<5;i++){
            threadPoolExecutor.submit(new LiftOff());
        }
        threadPoolExecutor.shutdown();
    }
}
