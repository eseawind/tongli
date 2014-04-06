/*
 * 基础Entity类   会员基本信息表
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
 * 会员基本信息表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcMember extends BaseEntity {
	/** id */
	private String id;
	/** 用户名 */
	private String user_name;
	/** 密码 */
	private String passwd;
	/** 会员类型 */
	private String user_type;
	/** 用��昵称 */
	private String nickname;
	/** 登录次数 */
	private String login_count;
	/** 最后登入时间 */
	private String last_login;
	/** 绑定手机 */
	private String bind_mobile;
	/** 绑定邮箱 */
	private String bind_email;
	/** 性别 */
	private String sex;
	/** 电话 */
	private String tel;
	/** 真实姓名 */
	private String real_name;
	/** 是否可用 */
	private String is_enabled;
	/** 最后登录IP */
	private String last_login_ip;
	/** 头像 */
	private String pic_url;
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
	 * 用户名取得
	 * @return 用户名
	 */
	public String getUser_name() {
	    return user_name;
	}
	/**
	 * 用户名设定
	 * @param user_name 用户名
	 */
	public void setUser_name(String user_name) {
	    this.user_name = user_name;
	}
	/**
	 * 密码取得
	 * @return 密码
	 */
	public String getPasswd() {
	    return passwd;
	}
	/**
	 * 密码设定
	 * @param passwd 密码
	 */
	public void setPasswd(String passwd) {
	    this.passwd = passwd;
	}
	/**
	 * 会员类型取得
	 * @return 会员类型
	 */
	public String getUser_type() {
	    return user_type;
	}
	/**
	 * 会员类型设定
	 * @param user_type 会员类型
	 */
	public void setUser_type(String user_type) {
	    this.user_type = user_type;
	}
	/**
	 * 用��昵称取得
	 * @return 用��昵称
	 */
	public String getNickname() {
	    return nickname;
	}
	/**
	 * 用��昵称设定
	 * @param nickname 用��昵称
	 */
	public void setNickname(String nickname) {
	    this.nickname = nickname;
	}
	/**
	 * 登录次数取得
	 * @return 登录次数
	 */
	public String getLogin_count() {
	    return login_count;
	}
	/**
	 * 登录次数设定
	 * @param login_count 登录次数
	 */
	public void setLogin_count(String login_count) {
	    this.login_count = login_count;
	}
	/**
	 * 最后登入时间取得
	 * @return 最后登入时间
	 */
	public String getLast_login() {
	    return last_login;
	}
	/**
	 * 最后登入时间设定
	 * @param last_login 最后登入时间
	 */
	public void setLast_login(String last_login) {
	    this.last_login = last_login;
	}
	/**
	 * 绑定手机取得
	 * @return 绑定手机
	 */
	public String getBind_mobile() {
	    return bind_mobile;
	}
	/**
	 * 绑定手机设定
	 * @param bind_mobile 绑定手机
	 */
	public void setBind_mobile(String bind_mobile) {
	    this.bind_mobile = bind_mobile;
	}
	/**
	 * 绑定邮箱取得
	 * @return 绑定邮箱
	 */
	public String getBind_email() {
	    return bind_email;
	}
	/**
	 * 绑定邮箱设定
	 * @param bind_email 绑定邮箱
	 */
	public void setBind_email(String bind_email) {
	    this.bind_email = bind_email;
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
	 * 电话取得
	 * @return 电话
	 */
	public String getTel() {
	    return tel;
	}
	/**
	 * 电话设定
	 * @param tel 电话
	 */
	public void setTel(String tel) {
	    this.tel = tel;
	}
	/**
	 * 真实姓名取得
	 * @return 真实姓名
	 */
	public String getReal_name() {
	    return real_name;
	}
	/**
	 * 真实姓名设定
	 * @param real_name 真实姓名
	 */
	public void setReal_name(String real_name) {
	    this.real_name = real_name;
	}
	/**
	 * 是否可用取得
	 * @return 是否可用
	 */
	public String getIs_enabled() {
	    return is_enabled;
	}
	/**
	 * 是否可用设定
	 * @param is_enabled 是否可用
	 */
	public void setIs_enabled(String is_enabled) {
	    this.is_enabled = is_enabled;
	}
	/**
	 * 最后登录IP取得
	 * @return 最后登录IP
	 */
	public String getLast_login_ip() {
	    return last_login_ip;
	}
	/**
	 * 最后登录IP设定
	 * @param last_login_ip 最后登录IP
	 */
	public void setLast_login_ip(String last_login_ip) {
	    this.last_login_ip = last_login_ip;
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

}