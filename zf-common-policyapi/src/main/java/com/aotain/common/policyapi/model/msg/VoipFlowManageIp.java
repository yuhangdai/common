package com.aotain.common.policyapi.model.msg;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Setter
@Getter
@Table(name="zf_v2_voipflow_manage_ip")
public class VoipFlowManageIp {

    private Integer voipflowId;

    private Integer voipType;

    private String voipIp;
}
