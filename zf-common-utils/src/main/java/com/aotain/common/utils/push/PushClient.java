package com.aotain.common.utils.push;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aotain.common.utils.constant.GlobalParams;
import com.aotain.common.utils.model.push.*;
import com.aotain.common.utils.model.smmscmd.*;
import com.aotain.common.utils.model.smmscmd.Command.Rule;
import com.aotain.common.utils.model.smmscmd.IdcInfoManage.CommandInfo;
import com.aotain.common.utils.model.smmscmd.LogQuery.CommandInfo.DestIp;
import com.aotain.common.utils.push.PushSecurityTool.PushEncryptResult;
import com.aotain.common.utils.push.PushSecurityTool.Tools;
import com.aotain.common.utils.push.dao.PushMapper;
import com.aotain.common.utils.tools.MonitorStatisticsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <pre>
 * 推送客户端工具
 * 为所有模块提供推送功能，用户提供推送的消息内容
 * </pre>
 * 
 * @author liuz@aotian.com
 * @date 2018年1月2日 下午2:30:02
 */
@Service
public class PushClient {
	// 推送接口URL
	private static final String PUSH_URL = PushConfigUtils.getPushUrl();
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private PushMapper pushMapper;
	private Logger logger = LoggerFactory.getLogger(PushClient.class);

	/**
	 * 推送指令接收结果
	 * @param type 指令类型，参见GlobalParams.COMMAND_*
	 * @param cmdJsonStr 指令详细信息
	 */
	public void pushCommand(int type, String cmdJsonStr) {
		CommandPushDetail msg = parseMessage(type, cmdJsonStr);
		SendData sendData = new SendData();
		sendData.setCommandType(msg.getTypeStr());
		// 组装机房信息
		StringBuilder sb = new StringBuilder();
		int flag = 0;
		for (String house : msg.getHouseStrList()) {
			if (flag > 0) {
				sb.append(",");
			}
			if (house.equals("全部机房")) {
				sb.append(house);
			} else {
				sb.append("[").append(house).append("]");
			}
			flag++;
		}
		sendData.setAlarmHouse(sb.toString());
		sendData.setContent(msg.getContent());
		// 调用推送方法
		pushMessage(sendData,1);
	}

	/**
	 * 推送告警信息
	 * 
	 * @param message
	 * @param ptype 告警类型，1-指令告警，2-拨测告警，3-系统告警
	 */
	public void pushMessage(SendData message,int ptype) {
		// 1. 准备推送参数
		List<PushParam> pushParamList = pushMapper.getPushParams();
		List<Integer> types = Arrays.asList(1, 2, 3); // 1-邮件，2-短信，3-微信
		for (Integer pType : types) {
			List<Integer> flags = new ArrayList<Integer>();
			for (PushParam pushParam : pushParamList) {
				pushParam.getCommandValue();
				String tvalue = "0"; // 默认不推送
				if(ptype == 1){
					tvalue = pushParam.getCommandValue();
				}else if(ptype == 2) {
					tvalue = pushParam.getDailtestValue();
				}else{
					tvalue = pushParam.getSystemValue();
				}
				if ("1".equals(tvalue) && "mail".equalsIgnoreCase(pushParam.getType().trim())) {
					flags.add(1);
				} else if ("1".equals(tvalue)
						&& "shortmessage".equalsIgnoreCase(pushParam.getType().trim())) {
					flags.add(2);
				} else if ("1".equals(tvalue)
						&& "wechart".equalsIgnoreCase(pushParam.getType().trim())) {
					flags.add(3);
				}
			}
			logger.info("推送支持的业务类型[1-邮件，2-短信，3-微信]=" + flags);
			
			if (!flags.contains(pType)) {
				continue;
			}
			// 2. 将详情转化为"推送对象",需要存放到数据库中的记录
			PushMessage msgObj = format(message, pType);
			if (pushMapper.insertPushMessage(msgObj) < 1) {
				logger.warn("推送记录写数据库失败,放弃推送此条告警：" + message);
				continue;
			}

			// 3. 为“推送对象”添加推送细节，得到推送记录
			PushMessageRecord record = buildRecord(msgObj);
			if (record == null) { // 未知推送类型，将跳过不予推送
				logger.error("未知推送类型的推送请求，将不予推送:" + message);
				pushMapper.updatePushMessageStatus(msgObj.getPushId(), 3);
				continue;
			}

			if (record.getReceiver() == null || record.getReceiver().isEmpty()) {
				logger.error("收件人为空，将不予推送:" + message);
				pushMapper.updatePushMessageStatus(msgObj.getPushId(), 3);
				continue;
			}

			try {
				// 4. 将推送记录打包处理：压缩，加密
				PushSecurityTool tools = new PushSecurityTool();
				PushEncryptResult ers = tools.encrypt(JSON.toJSONString(record));

				// 5. 组装接口信息，并调用推送接口
				PushInterfaceMessage pushMsg = new PushInterfaceMessage(ers, msgObj.getPushType());
				try {
					PushResponse res = pushMessage(pushMsg);
					if (res == null) {
						pushMapper.updatePushMessageStatus(msgObj.getPushId(), 3);
						logger.error("推送服务异常或者网络异常：" + message);
					} else {
						// 6. 更新任务状态
						if (res.getResultCode() == 0) {
							pushMapper.updatePushMessageStatus(msgObj.getPushId(), 1);
							logger.info("推送请求已发送：" + message);
						} else {
							pushMapper.updatePushMessageStatus(msgObj.getPushId(), 3);
							logger.error("推送请求失败,resultCode:" + res.getResultCode() + ",msg:" + res.getMsg() + ",data="
									+ message);
						}
					}
				} catch (IOException e) {
					pushMapper.updatePushMessageStatus(msgObj.getPushId(), 3);
					logger.error("推送请求发送失败", e);
					MonitorStatisticsUtils.addEvent(e);
				}
			} catch (Exception e) {
				logger.error("推送请求发送失败", e);
				MonitorStatisticsUtils.addEvent(e);
			}
		}
	}

	/**
	 * 将指令告警格式化
	 * 
	 * @param msg
	 * @param pushType
	 * @return
	 */
	private PushMessage format(SendData msg, Integer pushType) {
		PushMessage tpl = new PushMessage();
		tpl.setPushType(pushType);
		tpl.setStatus(0); // 提交
		// 组装发送内容
		JSONObject obj = new JSONObject();
		obj.put("alarmHouse", msg.getAlarmHouse());
		obj.put("alarmTime", SDF.format(new Date()));

		// 组装详细内容
		if (pushType == 1) {
			obj.put("commandType", msg.getCommandType());
			obj.put("content", msg.getContent());
		} else if (pushType == 2) {
			obj.put("commandType", msg.getCommandType());
			obj.put("content", msg.getContent());
		} else if (pushType == 3) { // 微信的内容多一层结构
			// 微信推送类型：text（文本）、image（图片）、voice（声音）、video（视频）、file（文件）、
			// textcard（文本卡片）、news（图文）[暂时只支持text]
			obj.put("commandType", msg.getCommandType());
			obj.put("msgType", "text");
			obj.put("content", msg.getContent());
		}
		tpl.setSendData(obj.toJSONString());
		return tpl;
	}

	/**
	 * 构造需要推送的消息对象
	 * 
	 * @param msgObj
	 * @return
	 */
	private PushMessageRecord buildRecord(PushMessage msgObj) {
		int ptype = msgObj.getPushType();

		switch (ptype) {
		case 1: // Email
			EmailPushMessageRecord record = new EmailPushMessageRecord();

			record.setProvinceShort(PushConfigUtils.getProviceShort());
			record.setPushId(msgObj.getPushId());

			PushReceiver receiver = new PushReceiver();
			receiver.setEmailReceiver(PushConfigUtils.getPushCmdDstEmail());
			record.setReceiver(receiver);

			record.setTimeStamp(SDF.format(new Date())); // 上报时间戳

			record.setSubject(PushConfigUtils.getPushSubject());
			record.setSendData(msgObj.getSendData());
			return record;
		case 2: // 短信
			SMSPushMessageRecord record1 = new SMSPushMessageRecord();
			record1.setProvinceShort(PushConfigUtils.getProviceShort());
			record1.setPushId(msgObj.getPushId());

			PushReceiver receiver1 = new PushReceiver();
			receiver1.setPhoneReceiver(PushConfigUtils.getPushCmdDstPhone());
			record1.setReceiver(receiver1);

			record1.setTimeStamp(SDF.format(new Date())); // 上报时间戳
			record1.setGatewayType(1); // 1-电信
			record1.setSmgpVersion("v3.0");

			record1.setSendData(msgObj.getSendData());
			return record1;
		case 3: // wechat
			WechatPushMessageRecord record2 = new WechatPushMessageRecord();
			record2.setProvinceShort(PushConfigUtils.getProviceShort());
			record2.setPushId(msgObj.getPushId());

			PushReceiver receiver2 = new PushReceiver();
			receiver2.setDepartmentId(PushConfigUtils.getPushCmdDstWechat());
			record2.setReceiver(receiver2);

			record2.setTimeStamp(SDF.format(new Date())); // 上报时间戳

			JSONObject jd = JSONObject.parseObject(msgObj.getSendData());
			WeChatSendData tmp = new WeChatSendData();
			tmp.setMsgType(jd.getString("msgType"));
			tmp.setContent(msgObj.getSendData());
			record2.setSendData(tmp);
			return record2;
		default:
			return null;
		}
	}
	
	/**
	 * 调用推送服务
	 * 
	 * @param pushMsg
	 * @throws IOException
	 */
	private PushResponse pushMessage(PushInterfaceMessage pushMsg) throws IOException {
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();

		NameValuePair v1 = new BasicNameValuePair("randVal", pushMsg.getRandVal());
		NameValuePair v2 = new BasicNameValuePair("pwdHash", pushMsg.getPwdHash());
		NameValuePair v3 = new BasicNameValuePair("push", pushMsg.getPush());
		NameValuePair v4 = new BasicNameValuePair("pushHash", pushMsg.getPushHash());
		NameValuePair v5 = new BasicNameValuePair("encryptAlgorithm", String.valueOf(pushMsg.getEncryptAlgorithm()));
		NameValuePair v6 = new BasicNameValuePair("hashAlgorithm", String.valueOf(pushMsg.getHashAlgorithm()));
		NameValuePair v7 = new BasicNameValuePair("compressionFormat", String.valueOf(pushMsg.getCompressionFormat()));
		NameValuePair v8 = new BasicNameValuePair("pushType", String.valueOf(pushMsg.getPushType()));
		NameValuePair v9 = new BasicNameValuePair("pushSequence", String.valueOf(pushMsg.getPushSequence()));
		NameValuePair v10 = new BasicNameValuePair("pushVersion", pushMsg.getPushVersion());

		formparams.add(v1);
		formparams.add(v2);
		formparams.add(v3);
		formparams.add(v4);
		formparams.add(v5);
		formparams.add(v6);
		formparams.add(v7);
		formparams.add(v8);
		formparams.add(v9);
		formparams.add(v10);
		String rs = com.aotain.common.utils.tools.HttpUtils.postRequest(PUSH_URL, formparams, "UTF-8");
		try {
			return JSON.parseObject(rs, PushResponse.class);
		} catch (Exception e) {
			logger.error("json转换异常:" + rs, e);
			MonitorStatisticsUtils.addEvent(e);
			return null;
		}
	}
	
	/**
	 * 消息转换
	 * @param type
	 * @return
	 */
	private CommandPushDetail parseMessage(int type, String cmdJsonStr) {
		CommandPushDetail msg = new CommandPushDetail();
		String typeStr = "未知指令类型";
		List<Long> houseIdList = new ArrayList<Long>();
		String content = null;
		// 1. 基础数据管理指令
		if (GlobalParams.COMMAND_BASIC_DATA_QUWERY_TYPE == type) {
			IdcInfoManage idcInfoMng = JSON.parseObject(cmdJsonStr,IdcInfoManage.class);
			msg.setCommandId(String.valueOf(idcInfoMng.getCommandId()));
			typeStr = "基础数据查询指令";
			List<Long> tmp = idcInfoMng.getCommandInfo().getHouseId();
			if (tmp != null && !tmp.isEmpty()) {
				houseIdList.addAll(tmp);
			} else {
				houseIdList.add(0L);
			}

			CommandInfo info = idcInfoMng.getCommandInfo();
			// 用户详情
			List<Long> userIds = info.getId();
			content = parseUsers(userIds);
		}
		// 2. 信息安全管理指令
		else if (GlobalParams.COMMAND_INFO_MANAGE_TYPE == type) {
			Command idcCommand = JSON.parseObject(cmdJsonStr,Command.class);
			int commandType = idcCommand.getType().intValue();
			int operationType = idcCommand.getOperationType().intValue();
			msg.setCommandId(String.valueOf(idcCommand.getCommandId()));
			if (commandType == 1) {
				if (operationType == 0) {
					typeStr = "违法信息安全管理指令-监测";
				} else if (operationType == 1) {
					typeStr = "违法信息安全管理指令-解除监测";
				} else {
					typeStr = "违法信息安全管理指令-监测";
				}
			} else if (commandType == 2) {
				if (operationType == 0) {
					typeStr = "违法信息安全管理指令-封堵";
				} else if (operationType == 1) {
					typeStr = "违法信息安全管理指令-解除封堵";
				} else {
					typeStr = "违法信息安全管理指令-封堵";
				}
			} else {
				typeStr = "违法信息安全管理指令";
			}
			houseIdList.addAll(idcCommand.getRange().getHouseId());

			// 规则详情
			content = parserRules(idcCommand.getRule());
		}
		// 3. 访问日志查询指令
		else if (GlobalParams.COMMAND_LOG_QUERY_TYPE == type) {
			LogQuery logQuery = JSON.parseObject(cmdJsonStr,LogQuery.class);
			typeStr = "访问日志查询指令";
			houseIdList.add(logQuery.getCommandInfo().getHouseId());
			msg.setCommandId(String.valueOf(logQuery.getCommandId()));
			
			content = paserQueryCmd(logQuery.getCommandInfo());
		}
		// 4. 信息安全管理指令查询指令（此指令过时了，不再支持）
		/*
		 * else if (GlobalParams.XFT_COMMAND_QUERY.equals(type)) { typeStr =
		 * "信息安全管理指令查询指令"; return null; }
		 */
		// 5. 处理代码发布指令
		else if (GlobalParams.COMMAND_CODE_RELEASE_TYPE == type) {
			CodeList codeList = JSON.parseObject(cmdJsonStr,CodeList.class);
			typeStr = "代码发布指令";
			houseIdList.add(0L);
			msg.setCommandId(String.valueOf(codeList.getCommandId()));
			content = null;
		}
		// 6. 基础数据核验回退指令
		else if (GlobalParams.COMMAND_BASIC_DATA_VALIDATE_TYPE == type) {
			ReturnInfo returnInfo = JSON.parseObject(cmdJsonStr,ReturnInfo.class);
			typeStr = "基础数据核验处理指令";
			msg.setCommandId(String.valueOf(returnInfo.getIdcId()));
			if (returnInfo.getReturnCode() == 0L) {
				content = "上报记录通过核验";
			} else {
				content = returnInfo.getReturnMsg();
			}
		}
		// 7. 免过滤域名列表指令
		else if (GlobalParams.COMMAND_INFO_NO_FILTER_TYPE == type) {
			NoFilter noFilter = JSON.parseObject(cmdJsonStr,NoFilter.class);
			msg.setCommandId(String.valueOf(noFilter.getIdcId()));
			typeStr = "免过滤网站列表指令";
			houseIdList.add(0L);

			content = noFilter.getContents();
		}
		// 8. 违法网站列表指令
		else if (GlobalParams.COMMAND_INFO_BLACK_TYPE == type) {
			Blacklist blacklist = JSON.parseObject(cmdJsonStr,Blacklist.class);
			msg.setCommandId(String.valueOf(blacklist.getCommandId()));
			typeStr = "违法网站列表指令";
			houseIdList.add(0L);

			content = blacklist.getContents();
		}
		// 9. 活跃资源访问量查询指令
		else if (GlobalParams.COMMAND_INFO_QUERY_VIEW_TYPE == type) {
			QueryView queryView = JSON.parseObject(cmdJsonStr,QueryView.class);
			msg.setCommandId(String.valueOf(queryView.getCommandId()));
			typeStr = "活跃资源访问量查询指令";
			houseIdList.add(0L);

			content = queryView.getContent();
		}
		// 10. 违法管理指令执行记录指令
		else if (GlobalParams.COMMAND_INFO_BLACK_RECORD_TYPE == type) {
			CommandRecord commandRecord = JSON.parseObject(cmdJsonStr,CommandRecord.class);
			msg.setCommandId(String.valueOf(commandRecord.getCommandId()));
			typeStr = "违法管理指令执行记录指令";
			houseIdList.add(0L);

			content = "[指令ID：" + commandRecord.getControlsId() + "]";
		}
		msg.setTypeStr(typeStr);
		msg.setHouseIdList(houseIdList);
		msg.setHouseStrList(parseHouseStr(houseIdList));
		msg.setContent(content);
		return msg;
	}
	
	private String parserRules(List<Rule> rule) {
		// 规则类别（1—域名、2—URL、3—关键字、4—源IP、5—目标IP、6—源端口、7—目标端口）
		StringBuilder sb = new StringBuilder();
		int flag = 0;
		for (Rule r : rule) {
			String name = null;
			String content = null;
			switch (r.getSubtype().intValue()) {
			case 1:
				name = "域名";
				content = r.getValueStart();
				break;
			case 2:
				name = "URL";
				content = r.getValueStart();
				break;
			case 3:
				name = "关键字";
				content = r.getValueStart();
				break;
			case 4:
				name = "源IP";
				content = r.getValueStart() + (StringUtils.isBlank(r.getValueEnd()) ? "" : ":" + r.getValueEnd());
				break;
			case 5:
				name = "目标IP";
				content = r.getValueStart() + (StringUtils.isBlank(r.getValueEnd()) ? "" : ":" + r.getValueEnd());
				break;
			case 6:
				name = "源端口";
				content = r.getValueStart() + (StringUtils.isBlank(r.getValueEnd()) ? "" : ":" + r.getValueEnd());
				break;
			case 7:
				name = "目标端口";
				content = r.getValueStart() + (StringUtils.isBlank(r.getValueEnd()) ? "" : ":" + r.getValueEnd());
				break;
			}

			if (name != null && content != null) {
				if ("".equals(content.trim())) {
					continue;
				}
				if (flag > 0) {
					sb.append(",");
				}
				sb.append("[").append(name).append("：").append(content).append("]");
				flag++;
			}
		}
		if (flag == 0) {
			return null;
		}
		return sb.toString();
	}
	
	private String parseUsers(List<Long> userIds) {
		StringBuilder sb = new StringBuilder();
		int flag = 0;
		for (Long userId : userIds) {
			String userName = pushMapper.getUserNameById(userId);
			if (userName != null) {
				if (flag > 0) {
					sb.append(",");
				}
				sb.append("[").append(userName).append("]");
				flag++;
			}
		}
		if (flag == 0) {
			return null;
		}
		return sb.toString();
	}
	
	private String paserQueryCmd(com.aotain.common.utils.model.smmscmd.LogQuery.CommandInfo info) {
		StringBuilder sb = new StringBuilder();
		int pt = info.getProtocolType().intValue();
		int flag = 0;
		if (pt != 0) {
			sb.append("[").append("协议类型：" + ((pt == 1) ? "TCP" : "UDP")).append("]");
			flag++;
		}

		String tmp = info.getStartTime();
		if (!StringUtils.isBlank(tmp)) {
			sb.append(flag > 0 ? ",[" : "[").append("起始时间 ：" + tmp).append("]");
			flag++;
		}

		tmp = info.getEndTime();
		if (!StringUtils.isBlank(tmp)) {
			sb.append(flag > 0 ? ",[" : "[").append("结束时间 ：" + tmp).append("]");
			flag++;
		}

		tmp = info.getUrl();
		if (!StringUtils.isBlank(tmp)) {
			sb.append(flag > 0 ? ",[" : "[").append("URL：" + Tools.decodeBase64(tmp)).append("]");
			flag++;
		}

		com.aotain.common.utils.model.smmscmd.LogQuery.CommandInfo.SrcIp tmp1 = info.getSrcIp();
		if (tmp1 != null) {
			String sip = null, eip = null;
			if (!StringUtils.isBlank(tmp1.getStartIp())) {
				sb.append(flag > 0 ? "," : "");
				sip = tmp1.getStartIp();
				if (!StringUtils.isBlank(tmp1.getEndIp())) {
					eip = tmp1.getEndIp();
					if (sip.equals(eip)) {
						sb.append("[").append("源IP：").append(sip).append("]");
					} else {
						sb.append("[").append("源IP区间 ：").append(sip).append("-").append(eip).append("]");
					}
				} else {
					sb.append("[").append("源IP：").append(sip).append("]");
				}
				flag++;
			}
		}

		tmp = String.valueOf(info.getSrcPort());
		if (!StringUtils.isBlank(tmp)) {
			sb.append(flag > 0 ? ",[" : "[").append("源端口：" + tmp).append("]");
			flag++;
		}

		DestIp tmp2 = info.getDestIp();
		if (tmp1 != null) {
			String sip = null, eip = null;
			if (!StringUtils.isBlank(tmp2.getStartIp())) {
				sb.append(flag > 0 ? "," : "");
				sip = tmp2.getStartIp();
				if (!StringUtils.isBlank(tmp2.getEndIp())) {
					eip = tmp2.getEndIp();
					if (sip.equals(eip)) {
						sb.append("[").append("目标IP：").append(sip).append("]");
					} else {
						sb.append("[").append("目标IP区间 ：").append(sip).append("-").append(eip).append("]");
					}
				} else {
					sb.append("[").append("目标IP：").append(sip).append("]");
				}
				flag++;
			}
		}

		tmp = String.valueOf(info.getDstPort());
		if (!StringUtils.isBlank(tmp)) {
			sb.append(flag > 0 ? ",[" : "[").append("目标端口：" + tmp).append("]");
			flag++;
		}

		if (flag == 0)
			return null;
		return sb.toString();
	}
	
	private List<String> parseHouseStr(List<Long> houseIdList) {
		List<String> houseStrList = new ArrayList<String>();
		for (Long houseId : houseIdList) {
			if (houseId.equals(0L)) {
				houseStrList.add("全部机房");
				break;
			} else {
				String houseName = pushMapper.getHouseNameById(houseId);
				if (null != houseName && !houseName.isEmpty()) {
					houseStrList.add(houseName);
				} else {
					houseStrList.add("未知机房" + houseId);
				}
			}
		}
		return houseStrList;
	}
}
