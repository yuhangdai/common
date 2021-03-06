package com.aotain.common.utils.constant;

/**
 * Demo class
 *
 * @author daiyh@aotain.com
 * @date 2018/01/30
 */
public class RedisKeyConstant {

    public static final String MESSAGE_NO_KEY = "global_policy_messageno";

    public static final String MESSAGE_SEQ_NO_KEY = "global_policy_sequenceno";

    public static final String TASK_ID_KEY = "zf_global_task_id";

    public static final String THREAD_MONITOR_REDIS_KEY = "thread_monitor_redis";

    public  static final String THREAD_MONITOR_HASHKEY = "%s_%s_monitor";

    public static final String REDIS_TASK_STATUS_KEY = "zfjobstatus";
    
    public static final String CLASS_FILE_TYPE = "global_policy_%d";

    public static final String REDIS_KEY_DPI_SERVER_STATUS = "ServerStatus_0_193";

    /**
     * 策略发送ack key的格式：StrategyACKQueue_${ProbeType}_${MessageType}_${MessageNo}
     */
    public static String REDIS_KEY_POLICY_ACK = "StrategyACKQueue_%d_%d_%d";

    /** 策略发送channel */
    public static String REDIS_KEY_POLICY_PUBLISH_CHANNEL = "StrategySendChannel";

    /**
     * 策略存在redis中的key
     */
    public static String REDIS_POLICY_HASH_KEY = "Strategy_%d_%d";

    /**
     * 策略的顺序
     */
    public static String REDIS_POLICY_STRATEGY_HASH_KEY = "StrategySorted_%d_%d";
    
    /**
     * DPI上报数据设备信息
     */
    public static String DPI_ATTRIBUTE_INFO = "dpi_attribute_info";

    /**
     * 策略下发服务器
     */
    public static String ZF_GENERAL_INFORMATION = "ZFGeneralInformation";
    
    /**
     * 部署位置
     */
    public static String SYSTEM_DEPLOY_MODE = "system.deploy.mode";

    /**
     * HTTP GET 域名黑名单，域名白名单，URL黑名单 更新提示
     */
    public static String GLOBAL_CLASSINFO_ALARM = "global_classinfo_alarm";
}
