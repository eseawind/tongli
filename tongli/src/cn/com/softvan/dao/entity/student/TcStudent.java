/*
 * 基础Entity类   学员信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.student;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 学员信息
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcStudent extends BaseEntity {

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
	/** 头像 */
	private String pic_url;
	/** 详情 */
	private String detail_info;
	/** 生日 */
	private String birthdate;

	/** 身高 */
	private String height;
	/** 体重 */
	private String weight;
	/** 国籍 */
	private String nationality;
	/** 监护人姓名 */
	private String tutor;
	/** 紧急联系电话 */
	private String tel;
	/** 家庭住址 */
	private String addres;
	/** 所在学校或幼儿园 */
	private String school;
	/** 喜欢的运动 */
	private String like_sports;
	/** 曾经参加的运动 */
	private String once_in_motion;
	/** 伤病史 */
	private String injury_history;
	/** 家长的期许 */
	private String parents_expectations;

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

	/**
	 * 身高取得
	 * @return 身高
	 */
	public String getHeight() {
	    return height;
	}

	/**
	 * 身高设定
	 * @param height 身高
	 */
	public void setHeight(String height) {
	    this.height = height;
	}

	/**
	 * 体重取得
	 * @return 体重
	 */
	public String getWeight() {
	    return weight;
	}

	/**
	 * 体重设定
	 * @param weight 体重
	 */
	public void setWeight(String weight) {
	    this.weight = weight;
	}

	/**
	 * 国籍取得
	 * @return 国籍
	 */
	public String getNationality() {
	    return nationality;
	}

	/**
	 * 国籍设定
	 * @param nationality 国籍
	 */
	public void setNationality(String nationality) {
	    this.nationality = nationality;
	}

	/**
	 * 监护人姓名取得
	 * @return 监护人姓名
	 */
	public String getTutor() {
	    return tutor;
	}

	/**
	 * 监护人姓名设定
	 * @param tutor 监护人姓名
	 */
	public void setTutor(String tutor) {
	    this.tutor = tutor;
	}

	/**
	 * 紧急联系电话取得
	 * @return 紧急联系电话
	 */
	public String getTel() {
	    return tel;
	}

	/**
	 * 紧急联系电话设定
	 * @param tel 紧急联系电话
	 */
	public void setTel(String tel) {
	    this.tel = tel;
	}

	/**
	 * 家庭住址取得
	 * @return 家庭住址
	 */
	public String getAddres() {
	    return addres;
	}

	/**
	 * 家庭住址设定
	 * @param addres 家庭住址
	 */
	public void setAddres(String addres) {
	    this.addres = addres;
	}

	/**
	 * 所在学校或幼儿园取得
	 * @return 所在学校或幼儿园
	 */
	public String getSchool() {
	    return school;
	}

	/**
	 * 所在学校或幼儿园设定
	 * @param school 所在学校或幼儿园
	 */
	public void setSchool(String school) {
	    this.school = school;
	}

	/**
	 * 喜欢的运动取得
	 * @return 喜欢的运动
	 */
	public String getLike_sports() {
	    return like_sports;
	}

	/**
	 * 喜欢的运动设定
	 * @param like_sports 喜欢的运动
	 */
	public void setLike_sports(String like_sports) {
	    this.like_sports = like_sports;
	}

	/**
	 * 曾经参加的运动取得
	 * @return 曾经参加的运动
	 */
	public String getOnce_in_motion() {
	    return once_in_motion;
	}

	/**
	 * 曾经参加的运动设定
	 * @param once_in_motion 曾经参加的运动
	 */
	public void setOnce_in_motion(String once_in_motion) {
	    this.once_in_motion = once_in_motion;
	}

	/**
	 * 伤病史取得
	 * @return 伤病史
	 */
	public String getInjury_history() {
	    return injury_history;
	}

	/**
	 * 伤病史设定
	 * @param injury_history 伤病史
	 */
	public void setInjury_history(String injury_history) {
	    this.injury_history = injury_history;
	}

	/**
	 * 家长的期许取得
	 * @return 家长的期许
	 */
	public String getParents_expectations() {
	    return parents_expectations;
	}

	/**
	 * 家长的期许设定
	 * @param parents_expectations 家长的期许
	 */
	public void setParents_expectations(String parents_expectations) {
	    this.parents_expectations = parents_expectations;
	}

}