/*
 * 基础Entity类   用户
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wuxiaogang           程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.user;

import java.util.Date;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 用户
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcUaUmBaseUser extends BaseEntity {
	/** 用户编号 **/
	private String id;
	/** 生日 **/
	private Date birthday;
	/** 注册日期 **/
	private Date reg_date;
	/** 电子邮件 **/
	private String email;
	/** 0是1否 **/
	private String enabled;
	/** 最后登入时间 **/
	private Date last_login;
	/** 手机 **/
	private String mobile;
	/** 昵称 **/
	private String nickname;
	/** 密码 **/
	private String passwd;
	/** 权限组编号 **/
	private String power_group_id;
	/** 1:男2女3保密 **/
	private String sex;
	/** 用户类型 **/
	private String use_type;
	/** 用户名 **/
	private String username;
	/** 年龄 **/
	private String age;
	/** 手机号码 **/
	private String mobile_phone;
	/** 工作电话 **/
	private String work_phone;
	/** 其他电话 **/
	private String other_phone;
	/** 住址 **/
	private String address;
	/** 详细地址 **/
	private String detail_address;
	/** 邮编 **/
	private String post_code;
	/** 证件号码 **/
	private String credential_code;
	/** 证件类型 **/
	private String credential;
	/**
	 * 用户编号 *取得
	 * @return 用户编号 *
	 */
	public String getId() {
	    return id;
	}
	/**
	 * 用户编号 *设定
	 * @param id 用户编号 *
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 生日 *取得
	 * @return 生日 *
	 */
	public Date getBirthday() {
	    return birthday;
	}
	/**
	 * 生日 *设定
	 * @param birthday 生日 *
	 */
	public void setBirthday(Date birthday) {
	    this.birthday = birthday;
	}
	/**
	 * 注册日期 *取得
	 * @return 注册日期 *
	 */
	public Date getReg_date() {
	    return reg_date;
	}
	/**
	 * 注册日期 *设定
	 * @param reg_date 注册日期 *
	 */
	public void setReg_date(Date reg_date) {
	    this.reg_date = reg_date;
	}
	/**
	 * 电子邮件 *取得
	 * @return 电子邮件 *
	 */
	public String getEmail() {
	    return email;
	}
	/**
	 * 电子邮件 *设定
	 * @param email 电子邮件 *
	 */
	public void setEmail(String email) {
	    this.email = email;
	}
	/**
	 * 0是1否 *取得
	 * @return 0是1否 *
	 */
	public String getEnabled() {
	    return enabled;
	}
	/**
	 * 0是1否 *设定
	 * @param enabled 0是1否 *
	 */
	public void setEnabled(String enabled) {
	    this.enabled = enabled;
	}
	/**
	 * 最后登入时间 *取得
	 * @return 最后登入时间 *
	 */
	public Date getLast_login() {
	    return last_login;
	}
	/**
	 * 最后登入时间 *设定
	 * @param last_login 最后登入时间 *
	 */
	public void setLast_login(Date last_login) {
	    this.last_login = last_login;
	}
	/**
	 * 手机 *取得
	 * @return 手机 *
	 */
	public String getMobile() {
	    return mobile;
	}
	/**
	 * 手机 *设定
	 * @param mobile 手机 *
	 */
	public void setMobile(String mobile) {
	    this.mobile = mobile;
	}
	/**
	 * 昵称 *取得
	 * @return 昵称 *
	 */
	public String getNickname() {
	    return nickname;
	}
	/**
	 * 昵称 *设定
	 * @param nickname 昵称 *
	 */
	public void setNickname(String nickname) {
	    this.nickname = nickname;
	}
	/**
	 * 密码 *取得
	 * @return 密码 *
	 */
	public String getPasswd() {
	    return passwd;
	}
	/**
	 * 密码 *设定
	 * @param passwd 密码 *
	 */
	public void setPasswd(String passwd) {
	    this.passwd = passwd;
	}
	/**
	 * 权限组编号 *取得
	 * @return 权限组编号 *
	 */
	public String getPower_group_id() {
	    return power_group_id;
	}
	/**
	 * 权限组编号 *设定
	 * @param power_group_id 权限组编号 *
	 */
	public void setPower_group_id(String power_group_id) {
	    this.power_group_id = power_group_id;
	}
	/**
	 * 1:男2女3保密 *取得
	 * @return 1:男2女3保密 *
	 */
	public String getSex() {
	    return sex;
	}
	/**
	 * 1:男2女3保密 *设定
	 * @param sex 1:男2女3保密 *
	 */
	public void setSex(String sex) {
	    this.sex = sex;
	}
	/**
	 * 用户类型 *取得
	 * @return 用户类型 *
	 */
	public String getUse_type() {
	    return use_type;
	}
	/**
	 * 用户类型 *设定
	 * @param use_type 用户类型 *
	 */
	public void setUse_type(String use_type) {
	    this.use_type = use_type;
	}
	/**
	 * 用户名 *取得
	 * @return 用户名 *
	 */
	public String getUsername() {
	    return username;
	}
	/**
	 * 用户名 *设定
	 * @param username 用户名 *
	 */
	public void setUsername(String username) {
	    this.username = username;
	}
	/**
	 * 年龄 *取得
	 * @return 年龄 *
	 */
	public String getAge() {
	    return age;
	}
	/**
	 * 年龄 *设定
	 * @param age 年龄 *
	 */
	public void setAge(String age) {
	    this.age = age;
	}
	/**
	 * 手机号码 *取得
	 * @return 手机号码 *
	 */
	public String getMobile_phone() {
	    return mobile_phone;
	}
	/**
	 * 手机号码 *设定
	 * @param mobile_phone 手机号码 *
	 */
	public void setMobile_phone(String mobile_phone) {
	    this.mobile_phone = mobile_phone;
	}
	/**
	 * 工作电话 *取得
	 * @return 工作电话 *
	 */
	public String getWork_phone() {
	    return work_phone;
	}
	/**
	 * 工作电话 *设定
	 * @param work_phone 工作电话 *
	 */
	public void setWork_phone(String work_phone) {
	    this.work_phone = work_phone;
	}
	/**
	 * 其他电话 *取得
	 * @return 其他电话 *
	 */
	public String getOther_phone() {
	    return other_phone;
	}
	/**
	 * 其他电话 *设定
	 * @param other_phone 其他电话 *
	 */
	public void setOther_phone(String other_phone) {
	    this.other_phone = other_phone;
	}
	/**
	 * 住址 *取得
	 * @return 住址 *
	 */
	public String getAddress() {
	    return address;
	}
	/**
	 * 住址 *设定
	 * @param address 住址 *
	 */
	public void setAddress(String address) {
	    this.address = address;
	}
	/**
	 * 详细地址 *取得
	 * @return 详细地址 *
	 */
	public String getDetail_address() {
	    return detail_address;
	}
	/**
	 * 详细地址 *设定
	 * @param detail_address 详细地址 *
	 */
	public void setDetail_address(String detail_address) {
	    this.detail_address = detail_address;
	}
	/**
	 * 邮编 *取得
	 * @return 邮编 *
	 */
	public String getPost_code() {
	    return post_code;
	}
	/**
	 * 邮编 *设定
	 * @param post_code 邮编 *
	 */
	public void setPost_code(String post_code) {
	    this.post_code = post_code;
	}
	/**
	 * 证件号码 *取得
	 * @return 证件号码 *
	 */
	public String getCredential_code() {
	    return credential_code;
	}
	/**
	 * 证件号码 *设定
	 * @param credential_code 证件号码 *
	 */
	public void setCredential_code(String credential_code) {
	    this.credential_code = credential_code;
	}
	/**
	 * 证件类型 *取得
	 * @return 证件类型 *
	 */
	public String getCredential() {
	    return credential;
	}
	/**
	 * 证件类型 *设定
	 * @param credential 证件类型 *
	 */
	public void setCredential(String credential) {
	    this.credential = credential;
	}

}