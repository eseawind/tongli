/*
 * BEAN类 客服信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.16  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.customerservice;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 客服信息
 * </p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCsCustomerService extends BaseEntity {

	/** id */
	private String id;
	/** 登录id */
	private String uid;
	/** 密码 */
	private String pwd;
	/** 名称 */
	private String name;
	/** 客服状态 */
	private String cs_state;
	/** 登录状态 */
	private String login_state;
	/** 登录时间 */
	private String login_date;
	/** 当前接入客户 */
	private String cs_count;
	/**
	 * id取得
	 * @return id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * id设定
	 * @param id id
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 登录id取得
	 * @return 登录id
	 */
	public String getUid() {
	    return uid;
	}
	/**
	 * 登录id设定
	 * @param uid 登录id
	 */
	public void setUid(String uid) {
	    this.uid = uid;
	}
	/**
	 * 密码取得
	 * @return 密码
	 */
	public String getPwd() {
	    return pwd;
	}
	/**
	 * 密码设定
	 * @param pwd 密码
	 */
	public void setPwd(String pwd) {
	    this.pwd = pwd;
	}
	/**
	 * 名称取得
	 * @return 名称
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 名称设定
	 * @param name 名称
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 客服状态取得
	 * @return 客服状态
	 */
	public String getCs_state() {
	    return cs_state;
	}
	/**
	 * 客服状态设定
	 * @param cs_state 客服状态
	 */
	public void setCs_state(String cs_state) {
	    this.cs_state = cs_state;
	}
	/**
	 * 登录状态取得
	 * @return 登录状态
	 */
	public String getLogin_state() {
	    return login_state;
	}
	/**
	 * 登录状态设定
	 * @param login_state 登录状态
	 */
	public void setLogin_state(String login_state) {
	    this.login_state = login_state;
	}
	/**
	 * 登录时间取得
	 * @return 登录时间
	 */
	public String getLogin_date() {
	    return login_date;
	}
	/**
	 * 登录时间设定
	 * @param login_date 登录时间
	 */
	public void setLogin_date(String login_date) {
	    this.login_date = login_date;
	}
	/**
	 * 当前接入客户取得
	 * @return 当前接入客户
	 */
	public String getCs_count() {
	    return cs_count;
	}
	/**
	 * 当前接入客户设定
	 * @param cs_count 当前接入客户
	 */
	public void setCs_count(String cs_count) {
	    this.cs_count = cs_count;
	}

}