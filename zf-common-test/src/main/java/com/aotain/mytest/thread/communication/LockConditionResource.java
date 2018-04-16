package com.aotain.mytest.thread.communication;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/16
 */
public class LockConditionResource implements LockResource{

    private String name;

    private int count = 1;

    private volatile boolean exist = false;

    private Lock lock = new ReentrantLock();
    private Condition producerCondition = lock.newCondition();
    private Condition consumerCondition = lock.newCondition();


    public LockConditionResource(){

    }

    public LockConditionResource(String name){
        this.name = name;
    }

    public void produce(){
        lock.lock();
        try{
            while ( exist ) {
                producerCondition.await();
            }

            count++;
            System.out.println(Thread.currentThread().getName()+"=====生产==="+name+count);
            exist = true;
            consumerCondition.signal();
        } catch (InterruptedException e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void consumer(){
        lock.lock();
        try{
            while (!exist){
                consumerCondition.await();
            }
            System.out.println(Thread.currentThread().getName()+"=====消费==="+name+count);
            exist = false;
            producerCondition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
