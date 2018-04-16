package com.aotain.mytest.thread.deadlock;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/16
 */
public class Main {
    public static void main(String[] args) {
        DeadLockClassA deadLockClassA = new DeadLockClassA();
        DeadLockClassB deadLockClassB = new DeadLockClassB();
        Thread t1 = new Thread(deadLockClassA);
        Thread t2 = new Thread(deadLockClassB);
        t1.start();
        t2.start();
    }
}
