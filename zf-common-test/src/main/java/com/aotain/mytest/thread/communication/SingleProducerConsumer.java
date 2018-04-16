package com.aotain.mytest.thread.communication;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/16
 */
public class SingleProducerConsumer {
    public static void main(String[] args) {
        ConProResource conProResource = new ConProResource("烤鸭");

        ConsumerThread consumerThread = new ConsumerThread(conProResource);
        ProducerThread producerThread = new ProducerThread(conProResource);
        Thread t1 = new Thread(consumerThread);
        Thread t2 = new Thread(producerThread);
        t1.start();
        t2.start();
    }
}
