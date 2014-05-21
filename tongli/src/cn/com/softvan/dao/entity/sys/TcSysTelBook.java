/*
 * 基础Entity类 通讯录
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.20  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.sys;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 通讯录 信息表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcSysTelBook extends BaseEntity {

	/** id */
	private String id;
	/** 用户id */
	private String user_id;
	/** 姓名 */
	private String name;
	/** 电话号码 */
	private String tel;
	/** 地址 */
	private String addres;
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
	 * 姓名取得
	 * @return 姓名
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 姓名设定
	 * @param name 姓名
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 电话号码取得
	 * @return 电话号码
	 */
	public String getTel() {
	    return tel;
	}
	/**
	 * 电话号码设定
	 * @param tel 电话号码
	 */
	public void setTel(String tel) {
	    this.tel = tel;
	}
	/**
	 * 地址取得
	 * @return 地址
	 */
	public String getAddres() {
	    return addres;
	}
	/**
	 * 地址设定
	 * @param addres 地址
	 */
	public void setAddres(String addres) {
	    this.addres = addres;
	}

}