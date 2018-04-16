package com.aotain.mytest.thread.communication;

/**
 * 生产者消费者资源类
 *
 * @author HP
 * @date 2018/04/16
 */
public class ConProResource implements LockResource{

    private String name;

    private int count = 1;

    private volatile boolean exist = false;

    public ConProResource(){

    }

    public ConProResource(String name){
        this.name = name;
    }

    public synchronized void produce(){
        while ( exist ){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        count++;
        System.out.println(Thread.currentThread().getName()+"=====生产==="+name+count);
        exist = true;
        notifyAll();
    }

    public synchronized void consumer(){
        while ( !exist ){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName()+"=====消费==="+name+count);
        exist = false;
        notifyAll();
    }

}
