package com.aotain.mytest.thread.sync;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/16
 */
public class Main {
    public static void main(String[] args) {
        TicketThread ticketThread = new TicketThread();
        Thread t1 = new Thread(ticketThread);
        Thread t2 = new Thread(ticketThread);
        Thread t3 = new Thread(ticketThread);
        Thread t4 = new Thread(ticketThread);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}
