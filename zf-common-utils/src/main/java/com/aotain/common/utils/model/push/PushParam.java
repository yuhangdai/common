package com.aotain.common.utils.model.push;

import org.apache.commons.lang3.StringUtils;

public class PushParam {
	private String type;
	private String commandValue;
	private String dailtestValue;
	private String systemValue;
	private String value;

	@Override
	public String toString() {
		return "PushParam [type=" + type + ", commandValue=" + commandValue + ", dailtestValue=" + dailtestValue
				+ ", systemValue=" + systemValue + ", value=" + value + "]";
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
		if (!StringUtils.isBlank(value) && value.contains(",")) {
			String[] vals = value.split(",");
			if (vals.length > 2) {
				this.commandValue = StringUtils.trim(vals[0]);
				this.dailtestValue = StringUtils.trim(vals[1]);
				this.systemValue = StringUtils.trim(vals[2]);
			}
		}
	}

	public String getDailtestValue() {
		return dailtestValue;
	}

	public void setDailtestValue(String dailtestValue) {
		this.dailtestValue = dailtestValue;
	}

	public String getSystemValue() {
		return systemValue;
	}

	public void setSystemValue(String systemValue) {
		this.systemValue = systemValue;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCommandValue() {
		return commandValue;
	}

	public void setCommandValue(String commandValue) {
		this.commandValue = commandValue;
	}

	public String getType() {
		return type;
	}

}
