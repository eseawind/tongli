/*
 * BEAN类  微信公共号信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序.发布
 * 1.01     2014.03.07  wuxiaogang       程序.修改  新增 password, appid, appsecret
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.wechat;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 微信公共号信息
 * <p>
 * 
 * @author wangzi
 * 
 */
public class TcWxPublicUserBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6707340674006383991L;
	/** id **/
	private String id;
	/** 公共号名称 **/
	private String name;
	/** 公共号原始id **/
	private String openid;
	/** 微信号 **/
	private String userid;
	/** 微信号密码 **/
	private String password;
	/** 开发者凭据appid **/
	private String appid;
	/** 开发者凭据 appsecret**/
	private String appsecret;
	/** 省 **/
	private String province;
	/** 市 **/
	private String city;
	/** 邮箱 **/
	private String email;
	/** 类型 **/
	private String type;
	/**
	 * id *取得
	 * @return id *
	 */
	public String getId() {
	    return id;
	}
	/**
	 * id *设定
	 * @param id id *
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 公共号名称 *取得
	 * @return 公共号名称 *
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 公共号名称 *设定
	 * @param name 公共号名称 *
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 公共号原始id *取得
	 * @return 公共号原始id *
	 */
	public String getOpenid() {
	    return openid;
	}
	/**
	 * 公共号原始id *设定
	 * @param openid 公共号原始id *
	 */
	public void setOpenid(String openid) {
	    this.openid = openid;
	}
	/**
	 * 微信号 *取得
	 * @return 微信号 *
	 */
	public String getUserid() {
	    return userid;
	}
	/**
	 * 微信号 *设定
	 * @param userid 微信号 *
	 */
	public void setUserid(String userid) {
	    this.userid = userid;
	}
	/**
	 * 微信号密码 *取得
	 * @return 微信号密码 *
	 */
	public String getPassword() {
	    return password;
	}
	/**
	 * 微信号密码 *设定
	 * @param password 微信号密码 *
	 */
	public void setPassword(String password) {
	    this.password = password;
	}
	/**
	 * 开发者凭据appid *取得
	 * @return 开发者凭据appid *
	 */
	public String getAppid() {
	    return appid;
	}
	/**
	 * 开发者凭据appid *设定
	 * @param appid 开发者凭据appid *
	 */
	public void setAppid(String appid) {
	    this.appid = appid;
	}
	/**
	 * 开发者凭据 appsecret*取得
	 * @return 开发者凭据 appsecret*
	 */
	public String getAppsecret() {
	    return appsecret;
	}
	/**
	 * 开发者凭据 appsecret*设定
	 * @param appsecret 开发者凭据 appsecret*
	 */
	public void setAppsecret(String appsecret) {
	    this.appsecret = appsecret;
	}
	/**
	 * 省 *取得
	 * @return 省 *
	 */
	public String getProvince() {
	    return province;
	}
	/**
	 * 省 *设定
	 * @param province 省 *
	 */
	public void setProvince(String province) {
	    this.province = province;
	}
	/**
	 * 市 *取得
	 * @return 市 *
	 */
	public String getCity() {
	    return city;
	}
	/**
	 * 市 *设定
	 * @param city 市 *
	 */
	public void setCity(String city) {
	    this.city = city;
	}
	/**
	 * 邮箱 *取得
	 * @return 邮箱 *
	 */
	public String getEmail() {
	    return email;
	}
	/**
	 * 邮箱 *设定
	 * @param email 邮箱 *
	 */
	public void setEmail(String email) {
	    this.email = email;
	}
	/**
	 * 类型 *取得
	 * @return 类型 *
	 */
	public String getType() {
	    return type;
	}
	/**
	 * 类型 *设定
	 * @param type 类型 *
	 */
	public void setType(String type) {
	    this.type = type;
	}
}