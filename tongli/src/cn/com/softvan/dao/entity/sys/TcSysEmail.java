/*
 * 基础Entity类  电子邮件信息表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.sys;

import java.util.Date;

import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p> 电子邮件信息表 <p>
 * @author wangzi
 *
 */
public class TcSysEmail extends BaseEntity {
    /**
     * id
     */
    private String mail_id;

    /**
     * 发送人
     */
    private String mail_from;

    /**
     * 收件人
     */
    private String mail_to;

    /**
     * 主题
     */
    private String mail_subject;

    /**
     *短内容
     */
    private String mail_scontent;

    /**
     * 长内容
     */
    private String mail_lcontent;

    /**
     * 预定发送时间
     */
    private Date mail_send_time;

    /**
     * 实际发送时间
     */
    private Date mail_sended_time;

    /**
     * 实际发送次数
     */
    private Integer mail_send_count;

    /**
     * 邮件状态
     */
    private String mail_status;

	/**
	 * idを取得します。
	 * @return id
	 */
	public String getMail_id() {
	    return mail_id;
	}

	/**
	 * idを設定します。
	 * @param mail_id id
	 */
	public void setMail_id(String mail_id) {
	    this.mail_id = mail_id;
	}

	/**
	 * 发送人を取得します。
	 * @return 发送人
	 */
	public String getMail_from() {
	    return mail_from;
	}

	/**
	 * 发送人を設定します。
	 * @param mail_from 发送人
	 */
	public void setMail_from(String mail_from) {
	    this.mail_from = mail_from;
	}

	/**
	 * 收件人を取得します。
	 * @return 收件人
	 */
	public String getMail_to() {
	    return mail_to;
	}

	/**
	 * 收件人を設定します。
	 * @param mail_to 收件人
	 */
	public void setMail_to(String mail_to) {
	    this.mail_to = mail_to;
	}

	/**
	 * 主题を取得します。
	 * @return 主题
	 */
	public String getMail_subject() {
	    return mail_subject;
	}

	/**
	 * 主题を設定します。
	 * @param mail_subject 主题
	 */
	public void setMail_subject(String mail_subject) {
	    this.mail_subject = mail_subject;
	}

	/**
	 * 短内容を取得します。
	 * @return 短内容
	 */
	public String getMail_scontent() {
	    return mail_scontent;
	}

	/**
	 * 短内容を設定します。
	 * @param mail_scontent 短内容
	 */
	public void setMail_scontent(String mail_scontent) {
	    this.mail_scontent = mail_scontent;
	}

	/**
	 * 长内容を取得します。
	 * @return 长内容
	 */
	public String getMail_lcontent() {
	    return mail_lcontent;
	}

	/**
	 * 长内容を設定します。
	 * @param mail_lcontent 长内容
	 */
	public void setMail_lcontent(String mail_lcontent) {
	    this.mail_lcontent = mail_lcontent;
	}

	/**
	 * 预定发送时间を取得します。
	 * @return 预定发送时间
	 */
	public Date getMail_send_time() {
	    return mail_send_time;
	}

	/**
	 * 预定发送时间を設定します。
	 * @param mail_send_time 预定发送时间
	 */
	public void setMail_send_time(Date mail_send_time) {
	    this.mail_send_time = mail_send_time;
	}

	/**
	 * 实际发送时间を取得します。
	 * @return 实际发送时间
	 */
	public Date getMail_sended_time() {
	    return mail_sended_time;
	}

	/**
	 * 实际发送时间を設定します。
	 * @param mail_sended_time 实际发送时间
	 */
	public void setMail_sended_time(Date mail_sended_time) {
	    this.mail_sended_time = mail_sended_time;
	}

	/**
	 * 实际发送次数を取得します。
	 * @return 实际发送次数
	 */
	public Integer getMail_send_count() {
	    return mail_send_count;
	}

	/**
	 * 实际发送次数を設定します。
	 * @param mail_send_count 实际发送次数
	 */
	public void setMail_send_count(Integer mail_send_count) {
	    this.mail_send_count = mail_send_count;
	}

	/**
	 * 邮件状态を取得します。
	 * @return 邮件状态
	 */
	public String getMail_status() {
	    return mail_status;
	}

	/**
	 * 邮件状态を設定します。
	 * @param mail_status 邮件状态
	 */
	public void setMail_status(String mail_status) {
	    this.mail_status = mail_status;
	}

}