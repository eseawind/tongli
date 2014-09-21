
/*	
 * 课程表_y  数据库entity类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.09.21      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.dao.entity.yongyi.course;
import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p>课程表_y  数据库entity类。</p>	
 * @author wuxiaogang
 */
public class TcYCourseSyllabus extends BaseEntity{

	/** id */
	private String id;
	/** 课程id */
	private String course_id;
	/** 教师id */
	private String teacher_id;
	/** 教师名称 */
	private String teacher_name;
	/** 课程日期 */
	private String day;
	/** 开始时间 */
	private String begin_time;
	/** 结束时间 */
	private String end_time;
	/** 详细地址 */
	private String addres;
	/** 详情 */
	private String detail_info;
	/** 课程状态 0未完成 1 已完成*/
	private String course_status;
	/** 简介 */
	private String brief_info;
	/** 类型(0课程1夏令营2冬令营) */
	private String type;
	/**
	 * id取得
	 * @return id
	 */
	public String getId(){
		return id;
	}
	/**
	 * id设定
	 * @param id id
	 */
	public void setId(String id){
		this.id=id;
	}
	/**
	 * 课程id取得
	 * @return 课程id
	 */
	public String getCourse_id(){
		return course_id;
	}
	/**
	 * 课程id设定
	 * @param course_id 课程id
	 */
	public void setCourse_id(String course_id){
		this.course_id=course_id;
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
	 * 教师名称取得
	 * @return 教师名称
	 */
	public String getTeacher_name() {
	    return teacher_name;
	}
	/**
	 * 教师名称设定
	 * @param teacher_name 教师名称
	 */
	public void setTeacher_name(String teacher_name) {
	    this.teacher_name = teacher_name;
	}
	/**
	 * 课程日期取得
	 * @return 课程日期
	 */
	public String getDay(){
		return day;
	}
	/**
	 * 课程日期设定
	 * @param day 课程日期
	 */
	public void setDay(String day){
		this.day=day;
	}
	/**
	 * 开始时间取得
	 * @return 开始时间
	 */
	public String getBegin_time(){
		return begin_time;
	}
	/**
	 * 开始时间设定
	 * @param begin_time 开始时间
	 */
	public void setBegin_time(String begin_time){
		this.begin_time=begin_time;
	}
	/**
	 * 结束时间取得
	 * @return 结束时间
	 */
	public String getEnd_time(){
		return end_time;
	}
	/**
	 * 结束时间设定
	 * @param end_time 结束时间
	 */
	public void setEnd_time(String end_time){
		this.end_time=end_time;
	}
	/**
	 * 详细地址取得
	 * @return 详细地址
	 */
	public String getAddres(){
		return addres;
	}
	/**
	 * 详细地址设定
	 * @param addres 详细地址
	 */
	public void setAddres(String addres){
		this.addres=addres;
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
	 * 课程状态 0未完成 1 已完成取得
	 * @return 课程状态 0未完成 1 已完成
	 */
	public String getCourse_status(){
		return course_status;
	}
	/**
	 * 课程状态 0未完成 1 已完成设定
	 * @param course_status 课程状态 0未完成 1 已完成
	 */
	public void setCourse_status(String course_status){
		this.course_status=course_status;
	}
	/**
	 * 简介取得
	 * @return 简介
	 */
	public String getBrief_info() {
	    return brief_info;
	}
	/**
	 * 简介设定
	 * @param brief_info 简介
	 */
	public void setBrief_info(String brief_info) {
	    this.brief_info = brief_info;
	}
	/**
	 * 类型(0课程1夏令营2冬令营)取得
	 * @return 类型(0课程1夏令营2冬令营)
	 */
	public String getType(){
		return type;
	}
	/**
	 * 类型(0课程1夏令营2冬令营)设定
	 * @param type 类型(0课程1夏令营2冬令营)
	 */
	public void setType(String type){
		this.type=type;
	}
}