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
 * @author admin
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
	private String sms_id;
	/** 发信人(操作DB数据用) */
	private String sms_src_id;
	/** 收信人(操作DB数据用) */
	private String sms_dst_id;
	/** 短信内容(操作DB数据用) */
	private String sms_content;
	/** 发送时间(操作DB数据用) */
	private Timestamp sms_send_time;
	/** 实际发送时间(操作DB数据用) */
	private Timestamp sms_sended_time;
	/** 实际发送次数(操作DB数据用) */
	private String sms_send_count;
	/** 状态(操作DB数据用) */
	private String sms_status;
	/** 状态 描述*/
	private String sms_note;
	
	/**
	 * 业务数据主键Id
	 */
	private String sms_target_id;
	/**
	 * 业务数据类型  1、保单 2、违章 3、注册
	 */
	private String sms_target_type;

	private Date sms_target_time;
	
	/**
	 * 转换从数据库中取得数据
	 */
	public void copyValue() {
		
		// 发信人
		this.srcTermId = this.sms_src_id;
		// 内容
		this.body = this.sms_content;
		// 发送时间
		if (!Validator.isNullEmpty(this.sms_send_time)) {
			this.sendDate = DateUtil.timestampToDate(this.sms_send_time);
		}
		
		// 收件人
		if (!Validator.isNullEmpty(this.sms_dst_id)) {
			if (this.sms_dst_id.indexOf(SmsConstant.SMS_SEPRATE_TO) != -1) {
				String[] to = this.sms_dst_id.split(SmsConstant.SMS_SEPRATE_TO);
				this.dstTermId = Arrays.asList(to);
			} else {
				this.dstTermId = Arrays.asList(new String[]{this.sms_dst_id});
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
	public String getSms_id() {
	    return sms_id;
	}

	/**
	 * 短信ID(操作DB数据用)设定
	 * @param sms_id 短信ID(操作DB数据用)
	 */
	public void setSms_id(String sms_id) {
	    this.sms_id = sms_id;
	}

	/**
	 * 发信人(操作DB数据用)取得
	 * @return 发信人(操作DB数据用)
	 */
	public String getSms_src_id() {
	    return sms_src_id;
	}

	/**
	 * 发信人(操作DB数据用)设定
	 * @param sms_src_id 发信人(操作DB数据用)
	 */
	public void setSms_src_id(String sms_src_id) {
	    this.sms_src_id = sms_src_id;
	}

	/**
	 * 收信人(操作DB数据用)取得
	 * @return 收信人(操作DB数据用)
	 */
	public String getSms_dst_id() {
	    return sms_dst_id;
	}

	/**
	 * 收信人(操作DB数据用)设定
	 * @param sms_dst_id 收信人(操作DB数据用)
	 */
	public void setSms_dst_id(String sms_dst_id) {
	    this.sms_dst_id = sms_dst_id;
	}

	/**
	 * 短信内容(操作DB数据用)取得
	 * @return 短信内容(操作DB数据用)
	 */
	public String getSms_content() {
	    return sms_content;
	}

	/**
	 * 短信内容(操作DB数据用)设定
	 * @param sms_content 短信内容(操作DB数据用)
	 */
	public void setSms_content(String sms_content) {
	    this.sms_content = sms_content;
	}

	/**
	 * 发送时间(操作DB数据用)取得
	 * @return 发送时间(操作DB数据用)
	 */
	public Timestamp getSms_send_time() {
	    return sms_send_time;
	}

	/**
	 * 发送时间(操作DB数据用)设定
	 * @param sms_send_time 发送时间(操作DB数据用)
	 */
	public void setSms_send_time(Timestamp sms_send_time) {
	    this.sms_send_time = sms_send_time;
	}

	/**
	 * 实际发送时间(操作DB数据用)取得
	 * @return 实际发送时间(操作DB数据用)
	 */
	public Timestamp getSms_sended_time() {
	    return sms_sended_time;
	}

	/**
	 * 实际发送时间(操作DB数据用)设定
	 * @param sms_sended_time 实际发送时间(操作DB数据用)
	 */
	public void setSms_sended_time(Timestamp sms_sended_time) {
	    this.sms_sended_time = sms_sended_time;
	}

	/**
	 * 实际发送次数(操作DB数据用)取得
	 * @return 实际发送次数(操作DB数据用)
	 */
	public String getSms_send_count() {
	    return sms_send_count;
	}

	/**
	 * 实际发送次数(操作DB数据用)设定
	 * @param sms_send_count 实际发送次数(操作DB数据用)
	 */
	public void setSms_send_count(String sms_send_count) {
	    this.sms_send_count = sms_send_count;
	}

	/**
	 * 状态(操作DB数据用)取得
	 * @return 状态(操作DB数据用)
	 */
	public String getSms_status() {
	    return sms_status;
	}

	/**
	 * 状态(操作DB数据用)设定
	 * @param sms_status 状态(操作DB数据用)
	 */
	public void setSms_status(String sms_status) {
	    this.sms_status = sms_status;
	}

	/**
	 * 状态 描述取得
	 * @return 状态 描述
	 */
	public String getSms_note() {
	    return sms_note;
	}

	/**
	 * 状态 描述设定
	 * @param sms_note 状态 描述
	 */
	public void setSms_note(String sms_note) {
	    this.sms_note = sms_note;
	}

	/**
	 * 业务数据主键Id取得
	 * @return 业务数据主键Id
	 */
	public String getSms_target_id() {
	    return sms_target_id;
	}

	/**
	 * 业务数据主键Id设定
	 * @param sms_target_id 业务数据主键Id
	 */
	public void setSms_target_id(String sms_target_id) {
	    this.sms_target_id = sms_target_id;
	}

	/**
	 * 业务数据类型  1、保单 2、违章 3、注册取得
	 * @return 业务数据类型  1、保单 2、违章 3、注册
	 */
	public String getSms_target_type() {
	    return sms_target_type;
	}

	/**
	 * 业务数据类型  1、保单 2、违章 3、注册设定
	 * @param sms_target_type 业务数据类型  1、保单 2、违章 3、注册
	 */
	public void setSms_target_type(String sms_target_type) {
	    this.sms_target_type = sms_target_type;
	}

	/**
	 * sms_target_time取得
	 * @return sms_target_time
	 */
	public Date getSms_target_time() {
	    return sms_target_time;
	}

	/**
	 * sms_target_time设定
	 * @param sms_target_time sms_target_time
	 */
	public void setSms_target_time(Date sms_target_time) {
	    this.sms_target_time = sms_target_time;
	}

	 
	 




	
}