package com.aotain.mytest.thread.communication.condition;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/19
 */
public class FixedThreadPoolTest {
    public static void main(String[] args) {
        ConditionResource conditionResource = new ConditionResource();
        ThreadPoolExecutor produceThreadPoolExecutor =
                new ThreadPoolExecutor(2,2,0, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        ThreadPoolExecutor consumerThreadPoolExecutor =
                new ThreadPoolExecutor(3,3,0, TimeUnit.SECONDS,new LinkedBlockingQueue<>());
        for (int i=0;i<2;i++){
            ConsumerThread consumerThread = new ConsumerThread(conditionResource,i+"");
            produceThreadPoolExecutor.submit(consumerThread);
        }
        for (int i=0;i<3;i++){
            ProducerThread producerThread = new ProducerThread(conditionResource,i+"");
            consumerThreadPoolExecutor.submit(producerThread);
        }
        produceThreadPoolExecutor.shutdown();
        consumerThreadPoolExecutor.shutdown();
    }
}
