/*
 * BEAN    会员基本信息表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.member;

import java.util.List;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 会员基本信息表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcMemberBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4679696927369503566L;
	/** id */
	private String id;
	/** 用户名 */
	private String user_id;
	/** 密码 */
	private String passwd;
	/** 会员类型 */
	private String user_type;
	/** 姓名 */
	private String name;
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
	/** 头像 */
	private String pic_url;
	/** 是否可用 */
	private String is_enabled;
	/** 最后登录IP */
	private String last_login_ip;
	/** 备注 */
	private String note;
	/** 简介 */
	private String brief_info;
	/** 详情 */
	private String detail_info;

	// -----------------------------------------------------------------------------------------------
	/** 学员id集合 */
	private List<String> sids;

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
	 * 学员id集合取得
	 * @return 学员id集合
	 */
	public List<String> getSids() {
	    return sids;
	}

	/**
	 * 学员id集合设定
	 * @param sids 学员id集合
	 */
	public void setSids(List<String> sids) {
	    this.sids = sids;
	}

}