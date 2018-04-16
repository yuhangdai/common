package com.aotain.mytest.thread.communication;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/16
 */
public class ConsumerThread implements Runnable{

    private LockResource conProResource;

    public ConsumerThread(){

    }

    public ConsumerThread(LockResource conProResource){
        this.conProResource = conProResource;
    }

    public void run() {
        while (true){
            conProResource.consumer();
        }
    }
}
