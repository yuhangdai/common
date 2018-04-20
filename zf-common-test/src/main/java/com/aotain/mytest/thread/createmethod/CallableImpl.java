package com.aotain.mytest.thread.createmethod;

import java.util.concurrent.Callable;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/18
 */
public class CallableImpl implements Callable<String>{

    private String result;

    public CallableImpl(String result){
        this.result = result;
    }

    public String call() throws Exception {
        return Thread.currentThread().getName()+"return the "+result;
    }
}
