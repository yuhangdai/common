package com.aotain.common.utils.model.msg;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * EU策略下发channel实体
 * @author Administrator
 *
 */
public class StrategySendChannel extends BaseVo {
    
    /**
     * 
     */
    private static final long serialVersionUID = 991868717214443187L;

    /**
     * 下发的EU IP地址
     */
    private List<String> ip = new ArrayList<String>();

    /**
     * 策略类型， 0-DPI、1-EU
     */
    private int ProbeType; 
    
    private int MessageType;

    @JSONField(jsonDirect = true)
    private String messageContent;

    public List<String> getIp() {
        return ip;
    }

    public void setIp(List<String> ip) {
        this.ip = ip;
    }

    public int getProbeType() {
        return ProbeType;
    }

    public void setProbeType(int probeType) {
        ProbeType = probeType;
    }

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int messageType) {
        MessageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

//    public String toJsonString(){
//        StringBuilder sb = new StringBuilder("");
//        sb.append("{");
//        sb.append("\"eU_IP\":"); sb.append(getEU_IP()+",");
//        sb.append("\"messageType\":"); sb.append(getMessageType()+",");
//        sb.append("\"probeType\":"); sb.append(getProbeType()+",");
//        sb.append("\"messageContent\":"); sb.append(getMessageContent());
//        return sb.toString();
//    }

//    public String toJsonString(){
//        return JSON.toJSONString(this);
//    }
}
