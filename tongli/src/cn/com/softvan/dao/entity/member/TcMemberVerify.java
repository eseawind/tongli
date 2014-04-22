/*
 * 基础Entity类   会员认证信息表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.member;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 会员认证信息表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcMemberVerify extends BaseEntity {

	/** 用户名 */
	private String user_id;
	/** 信息类型 */
	private String target_type;
	/** 发送地址 */
	private String target_addr;
	/** 验证内容 */
	private String detail_info;
	/**
	 * 用户名取得
	 * @return 用户名
	 */
	public String getUser_id() {
	    return user_id;
	}
	/**
	 * 用户名设定
	 * @param user_id 用户名
	 */
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
	/**
	 * 信息类型取得
	 * @return 信息类型
	 */
	public String getTarget_type() {
	    return target_type;
	}
	/**
	 * 信息类型设定
	 * @param target_type 信息类型
	 */
	public void setTarget_type(String target_type) {
	    this.target_type = target_type;
	}
	/**
	 * 发送地址取得
	 * @return 发送地址
	 */
	public String getTarget_addr() {
	    return target_addr;
	}
	/**
	 * 发送地址设定
	 * @param target_addr 发送地址
	 */
	public void setTarget_addr(String target_addr) {
	    this.target_addr = target_addr;
	}
	/**
	 * 验证内容取得
	 * @return 验证内容
	 */
	public String getDetail_info() {
	    return detail_info;
	}
	/**
	 * 验证内容设定
	 * @param detail_info 验证内容
	 */
	public void setDetail_info(String detail_info) {
	    this.detail_info = detail_info;
	}

}