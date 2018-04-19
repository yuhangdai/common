package com.aotain.mytest.thread.communication.condition;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件资源 不同线程需持有同一个Condition
 *
 * @author HP
 * @date 2018/04/19
 */
public class ConditionResource {

    private AtomicInteger automic = new AtomicInteger(1);

    private  Lock lock = new ReentrantLock();
    private  Condition pro_condition = lock.newCondition();
    private  Condition con_condition = lock.newCondition();

    public void produce(String name){
        try{
            lock.lock();
            System.out.println(" producer "+name+"is working..."+automic.getAndAdd(1));
            con_condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public void consumer(String name){
        try{
            lock.lock();
            System.out.println(" consumer "+name+"is working..."+automic.getAndAdd(1));
            pro_condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}
