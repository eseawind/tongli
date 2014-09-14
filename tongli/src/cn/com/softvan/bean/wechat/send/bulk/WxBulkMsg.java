/*
 * BEAN类 群发信息 基
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.09.14  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.wechat.send.bulk;

import cn.com.softvan.bean.wechat.WxMsg;
/**
 * 群发信息 基
 * @author wuxiaogang 
 *
 */
public class WxBulkMsg extends WxMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9180438288300488808L;
	// FuncFlag 位0x0001被标志时，星标刚收到的消息。
	private boolean star;
	
	public WxBulkMsg(String toUser,String fromUser,String createDt,String msgType,boolean star) {
		super(toUser, fromUser, createDt, msgType);
		this.star = star;
	}
	public WxBulkMsg() {
	}
	public WxBulkMsg(WxMsg msg) {
		this(msg.getToUser(),msg.getFromUser(),msg.getCreateDt(),msg.getMsgType(),false);
	}
	
	public WxBulkMsg(WxBulkMsg msg) {
		this(msg.getToUser(), msg.getFromUser(), msg.getCreateDt(), msg.getMsgType(), msg.isStar());
	}
	
	public boolean isStar() {
		return star;
	}
	public void setStar(boolean star) {
		this.star = star;
	}
	
	
	public String toJson() {
		StringBuffer sb=new StringBuffer();
		return sb.toString();
	}
	
}
