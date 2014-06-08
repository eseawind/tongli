/*
 *  BEAN   在线报名
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.06.08  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.course;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 在线报名
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCourseWebEnrollBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1020877789387699896L;

	/** id */
	private String id;
	/** 姓名 */
	private String name;
	/** 性别 */
	private String sex;
	/** 电话 */
	private String tel;
	/** 在线报名课程 */
	private String course;
	/** 状态0未完成1已完成 */
	private String status;
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
	 * 性别取得
	 * @return 性别
	 */
	public String getSex() {
	    return sex;
	}
	/**
	 * 性别设定
	 * @param sex 性别
	 */
	public void setSex(String sex) {
	    this.sex = sex;
	}
	/**
	 * 电话取得
	 * @return 电话
	 */
	public String getTel() {
	    return tel;
	}
	/**
	 * 电话设定
	 * @param tel 电话
	 */
	public void setTel(String tel) {
	    this.tel = tel;
	}
	/**
	 * 在线报名课程取得
	 * @return 在线报名课程
	 */
	public String getCourse() {
	    return course;
	}
	/**
	 * 在线报名课程设定
	 * @param course 在线报名课程
	 */
	public void setCourse(String course) {
	    this.course = course;
	}
	/**
	 * 状态0未完成1已完成取得
	 * @return 状态0未完成1已完成
	 */
	public String getStatus() {
	    return status;
	}
	/**
	 * 状态0未完成1已完成设定
	 * @param status 状态0未完成1已完成
	 */
	public void setStatus(String status) {
	    this.status = status;
	}

}