package com.aotain.common.policyapi.model;

import com.aotain.common.policyapi.model.base.BaseVO;
import lombok.Getter;
import lombok.Setter;

/**
 * <P>web 推送策略</P>
 * @author Chenzr
 *
 *  key: Strategy_0_65
    field：123
    value:
        {
        "messageNo":123,
        "messageSequenceNo":122,
        "messageType":133,
        "operationType":1,
        "advType":4,
        "advWhiteHostListId":345,
        "triggerHostListId":"",
        "triggerKwListId":12,
        "advId":134,
        "advFrameUrl":”http://www.baidu.com”,
        "advToken":10,
        "advDelay":24
        }
 *
 *
 */

@Getter
@Setter
public class WebPushStrategy extends BaseVO{

    /** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/
	private static final long serialVersionUID = 2938586901151283411L;

	private Integer advType;

    /**
     * 信息推送网站白名单ID，为0的时候，表示没有白名单
     */
    private Long advWhiteHostListId;

    /**
     * 触发信息推送的Host列表编号：空表示没有，多个以逗号隔开
     */
    private String triggerHostListId;

    /**
     * 触发关键词列表编号：空表示没有，多个以逗号隔开
     */
    private String triggerKwListId;

    private Long advId;

    private String advFrameUrl;

    private Integer advToken;

    private Integer advDelay;


}
