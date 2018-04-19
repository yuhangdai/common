package com.aotain.mytest.thread.communication.condition;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/19
 */
public class ConsumerThread implements Runnable{

    private ConditionResource conditionResource;
    private String name;

    public ConsumerThread(ConditionResource conditionResource,String name){
        this.conditionResource = conditionResource;
        this.name = name;
    }

    @Override
    public void run() {
        while(true){
            conditionResource.consumer(name);
        }
    }
}
