/*
 * 基础Entity类   会员yu学员关联表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.member;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 会员yu学员关联表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcMemberVsStudent extends BaseEntity {

	/** 学员id */
	private String student_id;
	/** 会员id */
	private String user_id;
	/**
	 * 学员id取得
	 * @return 学员id
	 */
	public String getStudent_id() {
	    return student_id;
	}
	/**
	 * 学员id设定
	 * @param student_id 学员id
	 */
	public void setStudent_id(String student_id) {
	    this.student_id = student_id;
	}
	/**
	 * 会员id取得
	 * @return 会员id
	 */
	public String getUser_id() {
	    return user_id;
	}
	/**
	 * 会员id设定
	 * @param user_id 会员id
	 */
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}

}