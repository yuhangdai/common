package com.aotain.common.policyapi.model;


import com.alibaba.fastjson.annotation.JSONField;
import com.aotain.common.policyapi.model.base.BaseVO;
import com.aotain.common.policyapi.model.msg.BindMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * 
 * @ClassName: UserPolicyBindStrategy
 * @Description: 用户/应用策略绑定(这里用一句话描述这个类的作用)
 * @data 
{
	"messageNo": 123,
	"messageSequenceNo": 122,
	"messageType": 133,
	"bindAction": 1,
	"userType": 1,
	"userName": "bang",
	"bindInfo": [{
			"bindMessageNo": 123,
			"bindMessageType": 209
		},
		{
			"bindMessageNo": 124,
			"bindMessageType": 1
		}
	]
}


 * @author DongBoye
 * @date 2018年1月17日 上午10:16:08
 *
 */
@Getter
@Setter
public class UserPolicyBindStrategy extends BaseVO{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 9081565897698112305L;

	@JSONField(serialize = false)
	private Long bindId;

	@JSONField(serialize = false)
	private Long userGroupId;

	private Integer bindAction;

	private Integer userType;

	private String userName;
	
	/**
	 * 见msg包里的BindMessage
	 */
	private List<BindMessage> bindInfo;

	@JSONField(serialize = false)
	private Long userBindMessageNo;

	@JSONField(serialize = false)
	private Integer userBindMessageType;
	
	@JSONField(serialize = false)
    private String createOper;
	
	@JSONField(serialize = false)
    private String modifyOper;

	@JSONField(serialize = false)
    private Date createTime;

	@JSONField(serialize = false)
    private Date modifyTime;

}
