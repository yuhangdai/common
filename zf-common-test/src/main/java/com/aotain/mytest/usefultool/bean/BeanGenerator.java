package com.aotain.mytest.usefultool.bean;

import com.aotain.mytest.usefultool.RandomGenerator;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/20
 */
public class BeanGenerator {

    public static Object newInstance(Class clz){
        try {
            Object obj = clz.newInstance();

            Field[] fields = clz.getDeclaredFields();
            for (Field field:fields){
                // 设置属性可见性
                field.setAccessible(true);
                String property = field.getName();
                Type type = field.getGenericType();

                // 获取对应的set方法
                String methodName = "set"+StringUtils.capitalize(property);
                if ( ("java.lang.String").equals(type.getTypeName()) ){
                    Method method = clz.getDeclaredMethod(methodName,String.class);
                    method.invoke(obj, RandomGenerator.randomString());
                } else if ( ("java.lang.Boolean").equals(type.getTypeName()) ){
                    Method method = clz.getDeclaredMethod(methodName,Boolean.class);
                    method.invoke(obj,RandomGenerator.randomBoolean());
                } else if ( ("java.lang.Byte").equals(type.getTypeName()) ){
                    Method method = clz.getDeclaredMethod(methodName,Byte.class);
                    method.invoke(obj,RandomGenerator.randomByte());
                }  else if ( ("java.lang.Character").equals(type.getTypeName()) ) {
                    Method method = clz.getDeclaredMethod(methodName,Character.class);
                    method.invoke(obj,RandomGenerator.randomChar());
                }  else if ( ("java.lang.Short").equals(type.getTypeName()) ) {
                    Method method = clz.getDeclaredMethod(methodName,Short.class);
                    method.invoke(obj,RandomGenerator.randomShort());
                } else if ( ("java.lang.Integer").equals(type.getTypeName()) ) {
                    Method method = clz.getDeclaredMethod(methodName,Integer.class);
                    method.invoke(obj,RandomGenerator.randomInt());
                } else if ( ("java.lang.Long").equals(type.getTypeName()) ) {
                    Method method = clz.getDeclaredMethod(methodName,Long.class);
                    method.invoke(obj,RandomGenerator.randomLong());
                } else if ( ("java.lang.Float").equals(type.getTypeName()) ) {
                    Method method = clz.getDeclaredMethod(methodName,Float.class);
                    method.invoke(obj,RandomGenerator.randomFloat());
                } else if ( ("java.lang.Double").equals(type.getTypeName()) ) {
                    Method method = clz.getDeclaredMethod(methodName,Double.class);
                    method.invoke(obj,RandomGenerator.randomDouble());
                }
                System.out.println("======="+type.getTypeName());
            }
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Object();
    }

    public static void main(String[] args) {
        Boy boy = (Boy)BeanGenerator.newInstance(Boy.class);
        System.out.println(boy);
    }
}
