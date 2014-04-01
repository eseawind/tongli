/*
 * 基础Entity类  课程表详情
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
 * 课程表详情
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCourseSyllabusItems extends BaseEntity {
	/** id */
	private String id;
	/** 课程表id */
	private String syllabus_id;
	/** 开始时间 */
	private String begin_time;
	/** 结束时间 */
	private String end_time;
	/** 备注 */
	private String note;
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
	 * 课程表id取得
	 * @return 课程表id
	 */
	public String getSyllabus_id() {
	    return syllabus_id;
	}
	/**
	 * 课程表id设定
	 * @param syllabus_id 课程表id
	 */
	public void setSyllabus_id(String syllabus_id) {
	    this.syllabus_id = syllabus_id;
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
	 * 备注取得
	 * @return 备注
	 */
	public String getNote() {
	    return note;
	}
	/**
	 * 备注设定
	 * @param note 备注
	 */
	public void setNote(String note) {
	    this.note = note;
	}

}