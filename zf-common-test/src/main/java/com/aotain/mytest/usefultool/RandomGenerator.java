package com.aotain.mytest.usefultool;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 随机数生成器
 *
 * @author HP
 * @date 2018/04/20
 */
public class RandomGenerator {

    public static boolean randomBoolean(){
        return System.currentTimeMillis()%2==0?true:false;
    }

    public static byte randomByte(){
        return (byte)((Math.random()*256));
    }

    public static char randomChar(){
        return (char) ((Math.random()*Character.MAX_VALUE));
    }

    public static short randomShort(){
        return (short) ((Math.random()*Short.MAX_VALUE));
    }

    public static int randomInt(){
        return (int)((Math.random()*Integer.MAX_VALUE));
    }

    public static long randomLong(){
        return (int)((Math.random()*Long.MAX_VALUE));
    }

    public static float randomFloat(){
        return (float) Math.random()*Integer.MAX_VALUE;
    }

    public static double randomDouble(){
        return (double) Math.random()*Integer.MAX_VALUE;
    }

    public static String randomString(){
        return RandomStringUtils.randomAlphanumeric(5);
    }

    public static void main(String[] args) {
//        System.out.println(RandomGenerator.randomBoolean());
//        System.out.println(RandomGenerator.randomBoolean());
//        System.out.println(RandomGenerator.randomByte());
//        System.out.println(RandomGenerator.randomByte());
//        System.out.println(RandomGenerator.randomChar());
//        System.out.println(RandomGenerator.randomChar());
//        System.out.println(RandomGenerator.randomShort());
//        System.out.println(RandomGenerator.randomShort());
//        System.out.println(RandomGenerator.randomInt());
//        System.out.println(RandomGenerator.randomInt());
//        System.out.println(RandomGenerator.randomLong());
//        System.out.println(RandomGenerator.randomLong());
//        System.out.println(RandomGenerator.randomFloat());
//        System.out.println(RandomGenerator.randomFloat());
//        System.out.println(RandomGenerator.randomDouble());
//        System.out.println(RandomGenerator.randomDouble());
//        System.out.println((byte)(Math.random()*256));
//        System.out.println((byte)(Math.random()*256));
        System.out.println(RandomGenerator.randomString());
        System.out.println(RandomGenerator.randomString());
        System.out.println(RandomGenerator.randomString());
    }
}
