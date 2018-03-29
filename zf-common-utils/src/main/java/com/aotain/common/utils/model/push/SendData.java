package com.aotain.common.utils.model.push;

import org.apache.commons.lang3.StringUtils;

public class SendData {

	private String alarmTime;
	private String alarmHouse;
	private String commandType;
	private String content;

	public String getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(String alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmHouse() {
		return alarmHouse;
	}

	public void setAlarmHouse(String alarmHouse) {
		this.alarmHouse = alarmHouse;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "告警时间：" + (StringUtils.isBlank(alarmTime) ? "" : alarmTime) + "\n" + 
			   "告警机房：" + (StringUtils.isBlank(alarmHouse) ? "" : alarmHouse) + "\n" + 
			   "指令类型：" + (StringUtils.isBlank(commandType) ? "" : commandType) + "\n" + 
			   "告警内容：" + (StringUtils.isBlank(content) ? "" : content);
	}

}
