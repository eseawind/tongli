/*
 * BEAN    学员信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.student;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 学员信息
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcStudentBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -444382383029995442L;
	/** 学员id */
	private String id;
	/** 会员id */
	private String user_id;
	/** 姓名 */
	private String name;
	/** 年龄 */
	private String age;
	/** 性别 */
	private String sex;
	/** 爱好 */
	private String hobby;
	/** 备注 */
	private String note;
	/** 详情 */
	private String detail_info;
	/** 头像 */
	private String pic_url;
	/** 生日 */
	private String birthdate;
	/**
	 * 学员id取得
	 * @return 学员id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * 学员id设定
	 * @param id 学员id
	 */
	public void setId(String id) {
	    this.id = id;
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
	 * 年龄取得
	 * @return 年龄
	 */
	public String getAge() {
	    return age;
	}
	/**
	 * 年龄设定
	 * @param age 年龄
	 */
	public void setAge(String age) {
	    this.age = age;
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
	 * 爱好取得
	 * @return 爱好
	 */
	public String getHobby() {
	    return hobby;
	}
	/**
	 * 爱好设定
	 * @param hobby 爱好
	 */
	public void setHobby(String hobby) {
	    this.hobby = hobby;
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
	 * 头像取得
	 * @return 头像
	 */
	public String getPic_url() {
	    return pic_url;
	}
	/**
	 * 头像设定
	 * @param pic_url 头像
	 */
	public void setPic_url(String pic_url) {
	    this.pic_url = pic_url;
	}
	/**
	 * 生日取得
	 * @return 生日
	 */
	public String getBirthdate() {
	    return birthdate;
	}
	/**
	 * 生日设定
	 * @param birthdate 生日
	 */
	public void setBirthdate(String birthdate) {
	    this.birthdate = birthdate;
	}

}