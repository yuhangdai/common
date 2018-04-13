package com.aotain.mytest.annotation.method;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/12
 */
public class MyTestClass {

    @LogAnnotation(module = 1,operationType = 1,messageNo = 110)
    public void test(){
        System.out.println("==========");
    }
}
