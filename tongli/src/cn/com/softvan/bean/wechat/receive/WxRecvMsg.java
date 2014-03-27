/*
 * 基础BEAN类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.08.16  wuxiaogang           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.wechat.receive;

import cn.com.softvan.bean.wechat.WxMsg;


/**
 * <p>
 * 信息接收 基础BEAN类
 * </p>
 * 
 * @author wuxiaogang
 * 
 */
public class WxRecvMsg extends WxMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8361741787593770559L;
	private String msgId;

	public WxRecvMsg(String toUser, String fromUser, String createDt,
			String msgType, String msgId) {
		super(toUser, fromUser, createDt, msgType);
		this.msgId = msgId;
	}
	public WxRecvMsg() {
	}
	public WxRecvMsg(WxRecvMsg msg) {
		this(msg.getToUser(), msg.getFromUser(), msg.getCreateDt(), msg
				.getMsgType(), msg.getMsgId());
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
}
