/*
 * BEAN    课程表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.07  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.course;

import java.util.List;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 课程表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCourseSyllabusBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2482423928643657313L;
	/** id */
	private String id;
	/** 课程id */
	private String course_id;
	/** 教师id */
	private String teacher_id;
	/** 课程日期 */
	private String day;
	/** 开始时间 */
	private String begin_time;
	/** 结束时间 */
	private String end_time;
	/** 详细地址 */
	private String addres;
	/** 课程详情 */
	private String detail_info;
	/** 学员id集合 */
	private List<String> sids;
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
	public String getTeacher_id() {
	    return teacher_id;
	}
	/**
	 * 教师id设定
	 * @param teacher_id 教师id
	 */
	public void setTeacher_id(String teacher_id) {
	    this.teacher_id = teacher_id;
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
	 * 开始时间取得
	 * @return 开始时间
	 */
	public String getBegin_time() {
	    return begin_time;
	}
	/**
	 * 开始时间设定
	 * @param begin_time 开始时间
	 */
	public void setBegin_time(String begin_time) {
	    this.begin_time = begin_time;
	}
	/**
	 * 结束时间取得
	 * @return 结束时间
	 */
	public String getEnd_time() {
	    return end_time;
	}
	/**
	 * 结束时间设定
	 * @param end_time 结束时间
	 */
	public void setEnd_time(String end_time) {
	    this.end_time = end_time;
	}
	/**
	 * 详细地址取得
	 * @return 详细地址
	 */
	public String getAddres() {
	    return addres;
	}
	/**
	 * 详细地址设定
	 * @param addres 详细地址
	 */
	public void setAddres(String addres) {
	    this.addres = addres;
	}
	/**
	 * 课程详情取得
	 * @return 课程详情
	 */
	public String getDetail_info() {
	    return detail_info;
	}
	/**
	 * 课程详情设定
	 * @param detail_info 课程详情
	 */
	public void setDetail_info(String detail_info) {
	    this.detail_info = detail_info;
	}
	/**
	 * 学员id集合取得
	 * @return 学员id集合
	 */
	public List<String> getSids() {
	    return sids;
	}
	/**
	 * 学员id集合设定
	 * @param sids 学员id集合
	 */
	public void setSids(List<String> sids) {
	    this.sids = sids;
	}

}