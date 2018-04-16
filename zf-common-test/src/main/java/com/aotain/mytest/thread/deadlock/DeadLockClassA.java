package com.aotain.mytest.thread.deadlock;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/16
 */
public class DeadLockClassA implements Runnable{

    public void run() {
        getALockNeedBLock();
    }

    public void getALockNeedBLock(){
        synchronized (A.class){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("get a lock");
            synchronized (B.class){
                System.out.println("need b lock");
            }
        }
    }

}
