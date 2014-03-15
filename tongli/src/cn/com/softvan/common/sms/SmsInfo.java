/*
 * 短消息Class
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2011.11.28  Huyunlin           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2011 softvan System. - All Rights Reserved.
 *
 */
package cn.com.softvan.common.sms;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cn.com.softvan.common.DateUtil;
import cn.com.softvan.common.Validator;



/**
 * <p>短消息实体类</p>
 * <ol>[提供机能]
 * <li>短消息信息。
 * </ol>
 *
 * @author Huyunlin
 */
public class SmsInfo {
	
	/** 发信号码 */
	private String srcTermId;
	
	/** 内容 */
	private String body;
	
	/** 收信人 */
	private List<String> dstTermId;

	/** 发送时间 */
	private Date sendDate;
	
	/** 短信ID(操作DB数据用) */
	private String SMS_ID;
	/** 发信人(操作DB数据用) */
	private String SMS_SRC_ID;
	/** 收信人(操作DB数据用) */
	private String SMS_DST_ID;
	/** 短信内容(操作DB数据用) */
	private String SMS_CONTENT;
	/** 发送时间(操作DB数据用) */
	private Timestamp SMS_SEND_TIME;
	/** 实际发送时间(操作DB数据用) */
	private Timestamp SMS_SENDED_TIME;
	/** 实际发送次数(操作DB数据用) */
	private String SMS_SEND_COUNT;
	/** 状态(操作DB数据用) */
	private String SMS_STATUS;

	
	/**
	 * 转换从数据库中取得数据
	 */
	public void copyValue() {
		
		// 发信人
		this.srcTermId = this.SMS_SRC_ID;
		// 内容
		this.body = this.SMS_CONTENT;
		// 发送时间
		if (!Validator.isNullEmpty(this.SMS_SEND_TIME)) {
			this.sendDate = DateUtil.timestampToDate(this.SMS_SEND_TIME);
		}
		
		// 收件人
		if (!Validator.isNullEmpty(this.SMS_DST_ID)) {
			if (this.SMS_DST_ID.indexOf(SmsConstant.SMS_SEPRATE_TO) != -1) {
				String[] to = this.SMS_DST_ID.split(SmsConstant.SMS_SEPRATE_TO);
				this.dstTermId = Arrays.asList(to);
			} else {
				this.dstTermId = Arrays.asList(new String[]{this.SMS_DST_ID});
			}
		}
	}
	
	/**
	 * 添加发送手机号码
	 * 
	 */
	public void addDstTermId(String dstId) {
		if (this.dstTermId == null) {
			this.dstTermId = new ArrayList<String>();
		}
		this.dstTermId.add(dstId);
	}



	/**
	 * 发信号码取得
	 * @return 发信号码
	 */
	public String getSrcTermId() {
	    return srcTermId;
	}



	/**
	 * 发信号码设定
	 * @param srcTermId 发信号码
	 */
	public void setSrcTermId(String srcTermId) {
	    this.srcTermId = srcTermId;
	}



	/**
	 * 内容取得
	 * @return 内容
	 */
	public String getBody() {
	    return body;
	}



	/**
	 * 内容设定
	 * @param body 内容
	 */
	public void setBody(String body) {
	    this.body = body;
	}



	/**
	 * 收信人取得
	 * @return 收信人
	 */
	public List<String> getDstTermId() {
	    return dstTermId;
	}



	/**
	 * 收信人设定
	 * @param dstTermId 收信人
	 */
	public void setDstTermId(List<String> dstTermId) {
	    this.dstTermId = dstTermId;
	}



	/**
	 * 发送时间取得
	 * @return 发送时间
	 */
	public Date getSendDate() {
	    return sendDate;
	}



	/**
	 * 发送时间设定
	 * @param sendDate 发送时间
	 */
	public void setSendDate(Date sendDate) {
	    this.sendDate = sendDate;
	}

	/**
	 * 短信ID(操作DB数据用)取得
	 * @return 短信ID(操作DB数据用)
	 */
	public String getSMS_ID() {
	    return SMS_ID;
	}

	/**
	 * 短信ID(操作DB数据用)设定
	 * @param SMS_ID 短信ID(操作DB数据用)
	 */
	public void setSMS_ID(String SMS_ID) {
	    this.SMS_ID = SMS_ID;
	}

	/**
	 * 发信人(操作DB数据用)取得
	 * @return 发信人(操作DB数据用)
	 */
	public String getSMS_SRC_ID() {
	    return SMS_SRC_ID;
	}

	/**
	 * 发信人(操作DB数据用)设定
	 * @param SMS_SRC_ID 发信人(操作DB数据用)
	 */
	public void setSMS_SRC_ID(String SMS_SRC_ID) {
	    this.SMS_SRC_ID = SMS_SRC_ID;
	}

	/**
	 * 收信人(操作DB数据用)取得
	 * @return 收信人(操作DB数据用)
	 */
	public String getSMS_DST_ID() {
	    return SMS_DST_ID;
	}

	/**
	 * 收信人(操作DB数据用)设定
	 * @param SMS_DST_ID 收信人(操作DB数据用)
	 */
	public void setSMS_DST_ID(String SMS_DST_ID) {
	    this.SMS_DST_ID = SMS_DST_ID;
	}

	/**
	 * 短信内容(操作DB数据用)取得
	 * @return 短信内容(操作DB数据用)
	 */
	public String getSMS_CONTENT() {
	    return SMS_CONTENT;
	}

	/**
	 * 短信内容(操作DB数据用)设定
	 * @param SMS_CONTENT 短信内容(操作DB数据用)
	 */
	public void setSMS_CONTENT(String SMS_CONTENT) {
	    this.SMS_CONTENT = SMS_CONTENT;
	}

	/**
	 * 发送时间(操作DB数据用)取得
	 * @return 发送时间(操作DB数据用)
	 */
	public Timestamp getSMS_SEND_TIME() {
	    return SMS_SEND_TIME;
	}

	/**
	 * 发送时间(操作DB数据用)设定
	 * @param SMS_SEND_TIME 发送时间(操作DB数据用)
	 */
	public void setSMS_SEND_TIME(Timestamp SMS_SEND_TIME) {
	    this.SMS_SEND_TIME = SMS_SEND_TIME;
	}

	/**
	 * 实际发送时间(操作DB数据用)取得
	 * @return 实际发送时间(操作DB数据用)
	 */
	public Timestamp getSMS_SENDED_TIME() {
	    return SMS_SENDED_TIME;
	}

	/**
	 * 实际发送时间(操作DB数据用)设定
	 * @param SMS_SENDED_TIME 实际发送时间(操作DB数据用)
	 */
	public void setSMS_SENDED_TIME(Timestamp SMS_SENDED_TIME) {
	    this.SMS_SENDED_TIME = SMS_SENDED_TIME;
	}

	/**
	 * 实际发送次数(操作DB数据用)取得
	 * @return 实际发送次数(操作DB数据用)
	 */
	public String getSMS_SEND_COUNT() {
	    return SMS_SEND_COUNT;
	}

	/**
	 * 实际发送次数(操作DB数据用)设定
	 * @param SMS_SEND_COUNT 实际发送次数(操作DB数据用)
	 */
	public void setSMS_SEND_COUNT(String SMS_SEND_COUNT) {
	    this.SMS_SEND_COUNT = SMS_SEND_COUNT;
	}

	/**
	 * 状态(操作DB数据用)取得
	 * @return 状态(操作DB数据用)
	 */
	public String getSMS_STATUS() {
	    return SMS_STATUS;
	}

	/**
	 * 状态(操作DB数据用)设定
	 * @param SMS_STATUS 状态(操作DB数据用)
	 */
	public void setSMS_STATUS(String SMS_STATUS) {
	    this.SMS_STATUS = SMS_STATUS;
	}




	
}