/*
 * 基础Entity类   会员详细信息表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.member;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 会员详细信息表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcMemberDetail extends BaseEntity {
	/** 用户名 */
	private String user_id;
	/** 生日 */
	private String birthdate;
	/** 血型 */
	private String blood_type;
	/** 文化程度 */
	private String edu_level;
	/** 从事行业 */
	private String trade;
	/** 从事职业 */
	private String job;
	/** 收入水平 */
	private String income_level;
	/** 省 */
	private String province_id;
	/** 市 */
	private String city_id;
	/** 县 */
	private String county_id;
	/** 详细地址 */
	private String address;
	/** 邮政编码 */
	private String zipcode;
	/** 证件类型 */
	private String credential;
	/** 证件号码 */
	private String credential_code;
	/** QQ */
	private String qq;
	/** MSN */
	private String msn;
	/** 爱好 */
	private String hobby;
	/** ��后修改时间 */
	private String last_update_date;
	/**
	 * 用户名取得
	 * @return 用户名
	 */
	public String getUser_id() {
	    return user_id;
	}
	/**
	 * 用户名设定
	 * @param user_id 用户名
	 */
	public void setUser_id(String user_id) {
	    this.user_id = user_id;
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
	 * 血型取得
	 * @return 血型
	 */
	public String getBlood_type() {
	    return blood_type;
	}
	/**
	 * 血型设定
	 * @param blood_type 血型
	 */
	public void setBlood_type(String blood_type) {
	    this.blood_type = blood_type;
	}
	/**
	 * 文化程度取得
	 * @return 文化程度
	 */
	public String getEdu_level() {
	    return edu_level;
	}
	/**
	 * 文化程度设定
	 * @param edu_level 文化程度
	 */
	public void setEdu_level(String edu_level) {
	    this.edu_level = edu_level;
	}
	/**
	 * 从事行业取得
	 * @return 从事行业
	 */
	public String getTrade() {
	    return trade;
	}
	/**
	 * 从事行业设定
	 * @param trade 从事行业
	 */
	public void setTrade(String trade) {
	    this.trade = trade;
	}
	/**
	 * 从事职业取得
	 * @return 从事职业
	 */
	public String getJob() {
	    return job;
	}
	/**
	 * 从事职业设定
	 * @param job 从事职业
	 */
	public void setJob(String job) {
	    this.job = job;
	}
	/**
	 * 收入水平取得
	 * @return 收入水平
	 */
	public String getIncome_level() {
	    return income_level;
	}
	/**
	 * 收入水平设定
	 * @param income_level 收入水平
	 */
	public void setIncome_level(String income_level) {
	    this.income_level = income_level;
	}
	/**
	 * 省取得
	 * @return 省
	 */
	public String getProvince_id() {
	    return province_id;
	}
	/**
	 * 省设定
	 * @param province_id 省
	 */
	public void setProvince_id(String province_id) {
	    this.province_id = province_id;
	}
	/**
	 * 市取得
	 * @return 市
	 */
	public String getCity_id() {
	    return city_id;
	}
	/**
	 * 市设定
	 * @param city_id 市
	 */
	public void setCity_id(String city_id) {
	    this.city_id = city_id;
	}
	/**
	 * 县取得
	 * @return 县
	 */
	public String getCounty_id() {
	    return county_id;
	}
	/**
	 * 县设定
	 * @param county_id 县
	 */
	public void setCounty_id(String county_id) {
	    this.county_id = county_id;
	}
	/**
	 * 详细地址取得
	 * @return 详细地址
	 */
	public String getAddress() {
	    return address;
	}
	/**
	 * 详细地址设定
	 * @param address 详细地址
	 */
	public void setAddress(String address) {
	    this.address = address;
	}
	/**
	 * 邮政编码取得
	 * @return 邮政编码
	 */
	public String getZipcode() {
	    return zipcode;
	}
	/**
	 * 邮政编码设定
	 * @param zipcode 邮政编码
	 */
	public void setZipcode(String zipcode) {
	    this.zipcode = zipcode;
	}
	/**
	 * 证件类型取得
	 * @return 证件类型
	 */
	public String getCredential() {
	    return credential;
	}
	/**
	 * 证件类型设定
	 * @param credential 证件类型
	 */
	public void setCredential(String credential) {
	    this.credential = credential;
	}
	/**
	 * 证件号码取得
	 * @return 证件号码
	 */
	public String getCredential_code() {
	    return credential_code;
	}
	/**
	 * 证件号码设定
	 * @param credential_code 证件号码
	 */
	public void setCredential_code(String credential_code) {
	    this.credential_code = credential_code;
	}
	/**
	 * QQ取得
	 * @return QQ
	 */
	public String getQq() {
	    return qq;
	}
	/**
	 * QQ设定
	 * @param qq QQ
	 */
	public void setQq(String qq) {
	    this.qq = qq;
	}
	/**
	 * MSN取得
	 * @return MSN
	 */
	public String getMsn() {
	    return msn;
	}
	/**
	 * MSN设定
	 * @param msn MSN
	 */
	public void setMsn(String msn) {
	    this.msn = msn;
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
	 * ��后修改时间取得
	 * @return ��后修改时间
	 */
	public String getLast_update_date() {
	    return last_update_date;
	}
	/**
	 * ��后修改时间设定
	 * @param last_update_date ��后修改时间
	 */
	public void setLast_update_date(String last_update_date) {
	    this.last_update_date = last_update_date;
	}

}