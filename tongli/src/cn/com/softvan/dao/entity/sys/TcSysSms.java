/*
 * 基础Entity类 短信息表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wuxiaogang           程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.sys;

import java.util.Date;

import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p> 短信息表 <p>
 * @author wuxiaogang
 *
 */
public class TcSysSms extends BaseEntity {
    /**
     * id
     */
    private String sms_id;

    /**
     * 发信人
     */
    private String sms_src_id;

    /**
     * 收信人
     */
    private String sms_dst_id;

    /**
     * 短信内容
     */
    private String sms_content;

    /**
     * 预定发送时间
     */
    private Date sms_send_time;

    /**
     * 实际发送时间
     */
    private Date sms_sended_time;

    /**
     * 重复最多发送次数
     */
    private Integer sms_send_count;

    /**
     * 1：未发送 2：发送中 3：已发送
     */
    private String sms_status;

	/**
	 * id取得
	 * @return id
	 */
	public String getSms_id() {
	    return sms_id;
	}

	/**
	 * id设定
	 * @param sms_id id
	 */
	public void setSms_id(String sms_id) {
	    this.sms_id = sms_id;
	}

	/**
	 * 发信人取得
	 * @return 发信人
	 */
	public String getSms_src_id() {
	    return sms_src_id;
	}

	/**
	 * 发信人设定
	 * @param sms_src_id 发信人
	 */
	public void setSms_src_id(String sms_src_id) {
	    this.sms_src_id = sms_src_id;
	}

	/**
	 * 收信人取得
	 * @return 收信人
	 */
	public String getSms_dst_id() {
	    return sms_dst_id;
	}

	/**
	 * 收信人设定
	 * @param sms_dst_id 收信人
	 */
	public void setSms_dst_id(String sms_dst_id) {
	    this.sms_dst_id = sms_dst_id;
	}

	/**
	 * 短信内容取得
	 * @return 短信内容
	 */
	public String getSms_content() {
	    return sms_content;
	}

	/**
	 * 短信内容设定
	 * @param sms_content 短信内容
	 */
	public void setSms_content(String sms_content) {
	    this.sms_content = sms_content;
	}

	/**
	 * 预定发送时间取得
	 * @return 预定发送时间
	 */
	public Date getSms_send_time() {
	    return sms_send_time;
	}

	/**
	 * 预定发送时间设定
	 * @param sms_send_time 预定发送时间
	 */
	public void setSms_send_time(Date sms_send_time) {
	    this.sms_send_time = sms_send_time;
	}

	/**
	 * 实际发送时间取得
	 * @return 实际发送时间
	 */
	public Date getSms_sended_time() {
	    return sms_sended_time;
	}

	/**
	 * 实际发送时间设定
	 * @param sms_sended_time 实际发送时间
	 */
	public void setSms_sended_time(Date sms_sended_time) {
	    this.sms_sended_time = sms_sended_time;
	}

	/**
	 * 重复最多发送次数取得
	 * @return 重复最多发送次数
	 */
	public Integer getSms_send_count() {
	    return sms_send_count;
	}

	/**
	 * 重复最多发送次数设定
	 * @param sms_send_count 重复最多发送次数
	 */
	public void setSms_send_count(Integer sms_send_count) {
	    this.sms_send_count = sms_send_count;
	}

	/**
	 * 1：未发送 2：发送中 3：已发送取得
	 * @return 1：未发送 2：发送中 3：已发送
	 */
	public String getSms_status() {
	    return sms_status;
	}

	/**
	 * 1：未发送 2：发送中 3：已发送设定
	 * @param sms_status 1：未发送 2：发送中 3：已发送
	 */
	public void setSms_status(String sms_status) {
	    this.sms_status = sms_status;
	}

   
}