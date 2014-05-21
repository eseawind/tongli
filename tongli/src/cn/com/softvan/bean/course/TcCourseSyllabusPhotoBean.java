/*
 * BEAN    课程表-相册
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.21  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.course;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 课程表-相册
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcCourseSyllabusPhotoBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2482423928643657313L;
	/** id */
	private String id;
	/** 课程表id */
	private String course_syllabus_id;
	/** 图片路径 */
	private String pic_url;
	/** 图片标题 */
	private String pic_title;
	/** 排序 序号 */
	private String sort_num;
	
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
	 * 图片路径取得
	 * @return 图片路径
	 */
	public String getPic_url() {
	    return pic_url;
	}
	/**
	 * 图片路径设定
	 * @param pic_url 图片路径
	 */
	public void setPic_url(String pic_url) {
	    this.pic_url = pic_url;
	}
	/**
	 * 图片标题取得
	 * @return 图片标题
	 */
	public String getPic_title() {
	    return pic_title;
	}
	/**
	 * 图片标题设定
	 * @param pic_title 图片标题
	 */
	public void setPic_title(String pic_title) {
	    this.pic_title = pic_title;
	}
	/**
	 * 排序 序号取得
	 * @return 排序 序号
	 */
	public String getSort_num() {
	    return sort_num;
	}
	/**
	 * 排序 序号设定
	 * @param sort_num 排序 序号
	 */
	public void setSort_num(String sort_num) {
	    this.sort_num = sort_num;
	}

}