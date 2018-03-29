package com.aotain.common.utils.model.push;

/**
 * 消息推送格式记录
 *
 * @author liuz@aotian.com
 * @date 2017年8月4日 下午5:49:48
 */
public class PushMessageRecord {
	private Long pushId;
	private String provinceShort; // 省份简称
	private String timeStamp;     // 时间戳
	private PushReceiver receiver; // 推送目标

	public Long getPushId() {
		return pushId;
	}

	public void setPushId(Long pushId) {
		this.pushId = pushId;
	}

	public String getProvinceShort() {
		return provinceShort;
	}

	public void setProvinceShort(String provinceShort) {
		this.provinceShort = provinceShort;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public PushReceiver getReceiver() {
		return receiver;
	}

	public void setReceiver(PushReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "PushMessageRecord [pushId=" + pushId + ", provinceShort=" + provinceShort + ", timeStamp=" + timeStamp + ", receiver=" + receiver + "]";
	}

}
