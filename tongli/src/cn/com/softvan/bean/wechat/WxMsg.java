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
package cn.com.softvan.bean.wechat;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>基础BEAN类</p>
 * @author wuxiaogang
 *
 */
public class WxMsg extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8361741787593770559L;

	/**
	 * 序列化
	 */
	/**默认构造器*/
	public WxMsg(){}
	private String toUser;
	private String fromUser;
	private String createDt;
	private String msgType;
	
	public WxMsg(String toUser,String fromUser,String createDt,String msgType) {
		this.toUser = toUser;
		this.fromUser = fromUser;
		this.createDt = createDt;
		this.msgType = msgType;
	}
	
	public String getToUser() {
		return toUser;
	}
	public void setToUser(String toUser) {
		this.toUser = toUser;
	}
	public String getFromUser() {
		return fromUser;
	}
	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}
	public String getCreateDt() {
		return createDt;
	}
	public void setCreateDt(String createDt) {
		this.createDt = createDt;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
}
