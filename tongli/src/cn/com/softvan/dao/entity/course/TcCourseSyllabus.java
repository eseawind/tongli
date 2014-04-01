/*
 * 基础Entity类   课程表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.course;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 课程表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCourseSyllabus extends BaseEntity {

	/** id */
	private String id;
	/** 学员id */
	private String student_id;
	/** 课程id */
	private String course_id;
	/** 教师id */
	private String user_id;
	/** 课程日期 */
	private String day;
	/** 学员旷课状态 */
	private String truancy_status;
	/** 学员未到原因 */
	private String truancy_note;
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
	 * 课程id取得
	 * @return 课程id
	 */
	public String getCourse_id() {
	    return course_id;
	}
	/**
	 * 课程id设定
	 * @param course_id 课程id
	 */
	public void setCourse_id(String course_id) {
	    this.course_id = course_id;
	}
	/**
	 * 教师id取得
	 * @return 教师id
	 */
	public String getUser_id() {
	    return user_id;
	}
	/**
	 * 教师id设定
	 * @param user_id 教师id
	 */
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
	}
	/**
	 * 课程日期取得
	 * @return 课程日期
	 */
	public String getDay() {
	    return day;
	}
	/**
	 * 课程日期设定
	 * @param day 课程日期
	 */
	public void setDay(String day) {
	    this.day = day;
	}
	/**
	 * 学员旷课状态取得
	 * @return 学员旷课状态
	 */
	public String getTruancy_status() {
	    return truancy_status;
	}
	/**
	 * 学员旷课状态设定
	 * @param truancy_status 学员旷课状态
	 */
	public void setTruancy_status(String truancy_status) {
	    this.truancy_status = truancy_status;
	}
	/**
	 * 学员未到原因取得
	 * @return 学员未到原因
	 */
	public String getTruancy_note() {
	    return truancy_note;
	}
	/**
	 * 学员未到原因设定
	 * @param truancy_note 学员未到原因
	 */
	public void setTruancy_note(String truancy_note) {
	    this.truancy_note = truancy_note;
	}

}