/*
 * BEAN类 咨询-消息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.16  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.consult;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 咨询-消息 BEAN
 * </p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCsConsultMsg extends BaseEntity {

	/** 消息id */
	private String id;
	/** 咨询id */
	private String consult_id;
	/** 用户id */
	private String user_id;
	/** 内容 */
	private String content;
	/** 图片路径 */
	private String pic_url;
	/** 操作人 */
	private String cs_id;
	/** 消息来源1,信息接收,2,客服回复 */
	private String info_source;
	/**
	 * 消息id取得
	 * @return 消息id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * 消息id设定
	 * @param id 消息id
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 咨询id取得
	 * @return 咨询id
	 */
	public String getConsult_id() {
	    return consult_id;
	}
	/**
	 * 咨询id设定
	 * @param consult_id 咨询id
	 */
	public void setConsult_id(String consult_id) {
	    this.consult_id = consult_id;
	}
	/**
	 * 用户id取得
	 * @return 用户id
	 */
	public String getUser_id() {
	    return user_id;
	}
	/**
	 * 用户id设定
	 * @param user_id 用户id
	 */
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
	/**
	 * 内容取得
	 * @return 内容
	 */
	public String getContent() {
	    return content;
	}
	/**
	 * 内容设定
	 * @param content 内容
	 */
	public void setContent(String content) {
	    this.content = content;
	}
	/**
	 * 图片路径取得
	 * @return 图片路径
	 */
	public String getPic_url() {
	    return pic_url;
	}
	/**
	 * 图片路径设定
	 * @param pic_url 图片路径
	 */
	public void setPic_url(String pic_url) {
	    this.pic_url = pic_url;
	}
	/**
	 * 操作人取得
	 * @return 操作人
	 */
	public String getCs_id() {
	    return cs_id;
	}
	/**
	 * 操作人设定
	 * @param cs_id 操作人
	 */
	public void setCs_id(String cs_id) {
	    this.cs_id = cs_id;
	}
	/**
	 * 消息来源1,信息接收,2,客服回复取得
	 * @return 消息来源1,信息接收,2,客服回复
	 */
	public String getInfo_source() {
	    return info_source;
	}
	/**
	 * 消息来源1,信息接收,2,客服回复设定
	 * @param info_source 消息来源1,信息接收,2,客服回复
	 */
	public void setInfo_source(String info_source) {
	    this.info_source = info_source;
	}
}