package com.aotain.mytest.thread.createmethod;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/18
 */
public class CallableThreadTest {
    public static void main(String[] args) throws Exception{
        Callable callable = new CallableImpl("aaa");
        FutureTask<String> futureTask = new FutureTask<String>(callable);
        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println(futureTask.get());

    }
}
