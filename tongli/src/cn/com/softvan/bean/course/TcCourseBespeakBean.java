/*
 *  BEAN   预约参观
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.17  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.course;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 预约参观
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCourseBespeakBean extends BaseBean {
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
	/** 时间 */
	private String day;
	/** 需要参观的场馆 */
	private String addres;
	/** 预约参观课程 */
	private String course;
	/** 详情 */
	private String detail_info;
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
	 * 时间取得
	 * @return 时间
	 */
	public String getDay() {
	    return day;
	}
	/**
	 * 时间设定
	 * @param day 时间
	 */
	public void setDay(String day) {
	    this.day = day;
	}
	/**
	 * 需要参观的场馆取得
	 * @return 需要参观的场馆
	 */
	public String getAddres() {
	    return addres;
	}
	/**
	 * 需要参观的场馆设定
	 * @param addres 需要参观的场馆
	 */
	public void setAddres(String addres) {
	    this.addres = addres;
	}
	/**
	 * 预约参观课程取得
	 * @return 预约参观课程
	 */
	public String getCourse() {
	    return course;
	}
	/**
	 * 预约参观课程设定
	 * @param course 预约参观课程
	 */
	public void setCourse(String course) {
	    this.course = course;
	}
	/**
	 * 详情取得
	 * @return 详情
	 */
	public String getDetail_info() {
	    return detail_info;
	}
	/**
	 * 详情设定
	 * @param detail_info 详情
	 */
	public void setDetail_info(String detail_info) {
	    this.detail_info = detail_info;
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