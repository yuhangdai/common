package com.aotain.mytest.thread.communication;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/16
 */
public class ProducerThread implements Runnable{

    private ConProResource conProResource;

    public ProducerThread(){}

    public ProducerThread(ConProResource conProResource){
        this.conProResource = conProResource;
    }

    public void run() {
        while (true){
            conProResource.produce();
        }
    }
}
