/*
 * 基础Entity类  课程表详情
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
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
	private String course_syllabus_id;
	/** 学员id */
	private String student_id;
	/** 教师id */
	private String teacher_id;
	/** 教师得分 */
	private String teacher_score;
	/** 教师得分描述 */
	private String teacher_score_note;
	/** 学员状态 */
	private String student_status;
	/** 学员状态描述 */
	private String student_status_note;
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
	public String getCourse_syllabus_id() {
	    return course_syllabus_id;
	}
	/**
	 * 课程表id设定
	 * @param course_syllabus_id 课程表id
	 */
	public void setCourse_syllabus_id(String course_syllabus_id) {
	    this.course_syllabus_id = course_syllabus_id;
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
	 * 教师得分取得
	 * @return 教师得分
	 */
	public String getTeacher_score() {
	    return teacher_score;
	}
	/**
	 * 教师得分设定
	 * @param teacher_score 教师得分
	 */
	public void setTeacher_score(String teacher_score) {
	    this.teacher_score = teacher_score;
	}
	/**
	 * 教师得分描述取得
	 * @return 教师得分描述
	 */
	public String getTeacher_score_note() {
	    return teacher_score_note;
	}
	/**
	 * 教师得分描述设定
	 * @param teacher_score_note 教师得分描述
	 */
	public void setTeacher_score_note(String teacher_score_note) {
	    this.teacher_score_note = teacher_score_note;
	}
	/**
	 * 学员状态取得
	 * @return 学员状态
	 */
	public String getStudent_status() {
	    return student_status;
	}
	/**
	 * 学员状态设定
	 * @param student_status 学员状态
	 */
	public void setStudent_status(String student_status) {
	    this.student_status = student_status;
	}
	/**
	 * 学员状态描述取得
	 * @return 学员状态描述
	 */
	public String getStudent_status_note() {
	    return student_status_note;
	}
	/**
	 * 学员状态描述设定
	 * @param student_status_note 学员状态描述
	 */
	public void setStudent_status_note(String student_status_note) {
	    this.student_status_note = student_status_note;
	}

}