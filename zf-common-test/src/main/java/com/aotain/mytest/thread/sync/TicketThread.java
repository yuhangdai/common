package com.aotain.mytest.thread.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/16
 */
public class TicketThread implements Runnable{
    private volatile static int ticketNum = 20;

    private Lock lock = new ReentrantLock(true);

    public void run() {

        while (true){
            try{
                if (ticketNum>0){
//                    lock.lock();
                    ticketNum--;
                    Thread.sleep(500);
                    System.out.println(Thread.currentThread().getName()+" bought one ticket,is still remaining "+ticketNum+"ticket.");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
//                lock.unlock();
            }
        }
    }
}
