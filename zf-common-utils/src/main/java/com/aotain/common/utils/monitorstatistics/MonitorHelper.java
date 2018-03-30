package com.aotain.common.utils.monitorstatistics;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/03/30
 */
public class MonitorHelper {

    private static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void setLocal(String module){
        THREAD_LOCAL.set(module);
    }

    public static String getLocal(){
        return THREAD_LOCAL.get();
    }

    public static void clearLocal(){
        THREAD_LOCAL.remove();
    }
}
