package com.aotain.common.config;

import com.aotain.common.config.redis.BaseRedisDao;
import com.aotain.common.config.redis.BaseRedisService;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Map;

/**
 * Demo class
 *
 * @author daiyh@aotain.com
 * @date 2017/11/14
 */
public class BaseRedisServiceTest extends BaseTest{

    @Autowired
    private BaseRedisDao baseRedisDao;

    @Autowired
    private BaseRedisService baseRedisServiceImpl;

    private static final String IDC_JDCM_JKCS_CONFIG = "idc_jdcm_jkcs_config";

    @Test
    @Rollback(false)
    public void test(){
        baseRedisServiceImpl.putHash("aotainConfig","cs_key","cs_value");
        System.out.println("=====");
    }


    @Test
    @Rollback(false)
    public void initConfig(){

    }

    @Test
    public void testGetDataFromHash(){
        String value = (String)baseRedisServiceImpl.getHash(IDC_JDCM_JKCS_CONFIG,"version");
        System.out.println("======="+value);
    }

    @Test
    public void testStringIncr(){

        baseRedisServiceImpl.putString("abc","5");
        baseRedisServiceImpl.incr("abc");
        String result = (String)baseRedisServiceImpl.getString("abc");
        System.out.println("result==="+result);
//        baseRedisServiceImpl.incr("abc");
//        long abcd = (Long)baseRedisServiceImpl.getString("abc");
//        System.out.println("result==="+abcd);
    }

    @Test
    public void testHashIncr(){
        baseRedisServiceImpl.putHash("abcd","efg","5");
        long result = baseRedisServiceImpl.hincrByHash("abcd","efg",2L);
        System.out.println("result==="+result);
    }

    @Test
    public void testGetHashValueByHashKey(){
        String result = (String)LocalConfig.getInstance().getHashValueByHashKey("password");
        System.out.println("result=="+result);
    }

    @Test
    public void testTopic(){
        baseRedisServiceImpl.sendMessage("hello-channel2","abcd");
        System.out.println("========Done");
    }

    @Test
    public void testListOp(){
        String message = (String)baseRedisServiceImpl.getHash("exception_redis_key","isms_db_exception");
        System.out.println("========="+message);
        String message2 = (String) baseRedisServiceImpl.getHash("exception_redis_key","job_db_exception");
        System.out.println("========="+message2);
    }

    @Test
    public void testLPush(){
        String value = "{\"createTime\": 1517886727,\"probeType\": 0,\"softwareVersion\": 9999,\"deploySiteName\": \"EU-YF-IDC-01\",\"idcHouseId\": \"BJ-010-IDC\",\"deployMode\": 1,\"totalCapability\": 20,\n" +
                "\"slotNum\": 15,\"preProcSlotNum\": 4,\"analysisSlotNum\": 8,\"gPSlotNum\": 3,\"portsType\": [{\"portType\": 1,\"ports\": [{\"portNo\": 433,\"portDescription\": \"dsadas\",\"mLinkID\": 2000,\"mLinkDesc\": \"aaaaa\"},{\"portNo\": 54,\"portDescription\": \"dsf\",\"mLinkID\": 1000,\"mLinkDesc\": \"bbbbb\"}]}]}";
        baseRedisServiceImpl.rightPush("Strategy_0_197",value);

    }

    @Test
    public void addStrategyData(){
        String value = "{\"createTime\": 1517886727,\"totalPorts\": [{\"portNo\": 8787,\"portInfo\": \"22\",\"portUsage\": 30}],\"totalCPU\": [{\"cpuNo\": 8787,\"cpuUsage\": 30}]}";
        baseRedisServiceImpl.rightPush("Strategy_0_198",value);
    }

    @Test
    public void testPutAll(){
        String key = "putallhashkey";
        long before  = System.currentTimeMillis();
        Map<String,String> kvMap = Maps.newHashMap();
        for (int i=0;i<10000;i++){
            kvMap.put(i+"","value"+i);
        }
        baseRedisServiceImpl.putAllHash(key,kvMap);
        long after  = System.currentTimeMillis();
        System.out.println("it takes "+(after-before)+"ms");
    }

    @Test
    public void testPutForEach(){
        String key = "puthashkeyforeach";
        long before  = System.currentTimeMillis();
        Map<String,String> kvMap = Maps.newHashMap();
        for (int i=0;i<10000;i++){
            baseRedisServiceImpl.putHash(key,i+"","value_"+i);

        }

        long after  = System.currentTimeMillis();
        System.out.println("it takes "+(after-before)+"ms");
    }

}
