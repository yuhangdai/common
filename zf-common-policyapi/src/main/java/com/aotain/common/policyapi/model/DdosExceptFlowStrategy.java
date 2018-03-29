/**   
* @Title: DdosExceptFlowStrategy.java 
* @Package com.aotain.zf.common.policy.model 
* @Description: TODO(用一句话描述该文件做什么) 
* @author DongBoye   
* @date 2018年1月18日 上午9:07:21   
*/
package com.aotain.common.policyapi.model;

import com.aotain.common.policyapi.model.base.BaseVO;
import lombok.Getter;
import lombok.Setter;

/** 
* @ClassName: DdosExceptFlowStrategy 
* @Description: DDOS异常流量策略(这里用一句话描述这个类的作用) 
* @data
{
	"messageNo": 123,
	"messageSequenceNo": 122,
	"messageType": 133,
	"operationType": 1,
	"appAttackType": 2,
	"attackThreshold": 15,
	"attackControl": 10
}
* @author DongBoye
* @date 2018年1月18日 上午9:07:21 
*  
*/
@Getter
@Setter
public class DdosExceptFlowStrategy extends BaseVO {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = 3013479687781076240L;
	/**
	 * 攻击类型
	 */
	private Integer appAttackType;
	/**
	 * 判定为攻击的阈值
	 */
	private Long attackThreshold;
	/**
	 * 对攻击进行控制的阈值
	 */
	private Long attackControl;

}
