/*
 * BEAN    学员意向运动项目
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.student;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 学员意向运动项目
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcStudentHobbyItemsBean extends BaseBean {
	/** id */
	private String id;
	/** 学员id */
	private String student_id;
	/** 项目名称 */
	private String name;
	/** 项目基础程度 */
	private String familiarity;
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
	 * 项目名称取得
	 * @return 项目名称
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 项目名称设定
	 * @param name 项目名称
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 项目基础程度取得
	 * @return 项目基础程度
	 */
	public String getFamiliarity() {
	    return familiarity;
	}
	/**
	 * 项目基础程度设定
	 * @param familiarity 项目基础程度
	 */
	public void setFamiliarity(String familiarity) {
	    this.familiarity = familiarity;
	}

}