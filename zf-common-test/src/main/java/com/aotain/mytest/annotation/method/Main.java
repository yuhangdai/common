package com.aotain.mytest.annotation.method;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/12
 */
public class Main {
    public static void main(String[] args) throws Exception {
        MyTestClass myTestClass = new MyTestClass();
        Method method = MyTestClass.class.getDeclaredMethod("test",null);
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        //获取 foo 这个代理实例所持有的 InvocationHandler
        InvocationHandler h = Proxy.getInvocationHandler(logAnnotation);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field hField = h.getClass().getDeclaredField("memberValues");
        // 因为这个字段事 private final 修饰，所以要打开权限
        hField.setAccessible(true);
        // 获取 memberValues
        Map memberValues = (Map) hField.get(h);
        // 修改 value 属性值
        memberValues.put("messageNo", 101);
        // 获取 foo 的 value 属性值
        int messageNo = logAnnotation.messageNo();
        System.out.println(messageNo+"");
    }
}
