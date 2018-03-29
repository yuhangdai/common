package com.aotain.common.utils.push.dao;

import com.aotain.common.config.annotation.MyBatisDao;
import com.aotain.common.utils.model.push.PushMessage;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

/**
 * 推送信息数据库操作接口
 *
 * @author liuz@aotian.com
 * @date 2017年8月7日 下午4:13:08
 */
@MyBatisDao
public interface PushMapper {
	
	public List<com.aotain.common.utils.model.push.PushParam> getPushParams();
	
	/**
	 * 查询ID
	 * 
	 * @return
	 * @throws SQLException
	 */
	public Long getNextId();

	/**
	 * 写入推送记录
	 * 
	 * @param msg
	 * @return
	 */
	public int insertPushMessage(PushMessage msg);

	/**
	 * 更新推送状态
	 * 
	 * @param pushId
	 * @param status 0-未推送，1-推送中，2-推送成功，3-推送失败
	 * @return
	 */
	public int updatePushMessageStatus(@Param("pushId") Long pushId, @Param("status") Integer status);

	/**
	 * 根据机房ID获取机房名
	 * @param houseId
	 * @return
	 */
	public String getHouseNameById(Long houseId);

	/**
	 * 根据用户ID查询用户名
	 * @param userId
	 * @return
	 */
	public String getUserNameById(Long userId);
}
