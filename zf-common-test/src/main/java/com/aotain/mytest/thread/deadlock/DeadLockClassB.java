package com.aotain.mytest.thread.deadlock;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/16
 */
public class DeadLockClassB implements Runnable{

    public void run() {
        getBLockNeedALock();
    }

    public void getBLockNeedALock(){
        synchronized (B.class){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("get b lock");
            synchronized (A.class){
                System.out.println("need a lock");
            }
        }
    }
}
