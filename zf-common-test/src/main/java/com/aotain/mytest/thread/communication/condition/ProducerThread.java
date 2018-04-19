package com.aotain.mytest.thread.communication.condition;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/19
 */
public class ProducerThread implements Runnable{

    private ConditionResource conditionResource;
    private String name;

    public ProducerThread(ConditionResource conditionResource,String name){
        this.conditionResource = conditionResource;
        this.name = name;
    }

    @Override
    public void run() {
        while(true){
            conditionResource.produce(name);
        }
    }
}
