/*
 * 基础Entity类  用户信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.carmanager;

import java.util.Date;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 用户信息
 * <p>
 * 
 * @author wangzi
 * 
 */
public class TcCTUser extends BaseEntity {
	/** 用户id **/
	private String userid;
	/**微信用户id*/
	private String openid;
	/** 用户名 **/
	private String username;
	/** 邮箱 **/
	private String email;
	/** 手机号码 **/
	private String telephoneno;
	/** 密码 **/
	private String password;
	/** 驾照号码 **/
	private String drivingFicenceNo;
	/** 驾照过期日期 **/
	private Date drivingLicenceExpire;
	/** 身份证号码 **/
	private String identityno;
	/** 车牌号码 **/
	private String plateno;
	/** 车主 **/
	private String carowner;
	/** 号牌种类 **/
	private String platetype;
	/** 设备编号 **/
	private String uuid;
	/** 创建日期 **/
	private Date createdate;
	/** 用户类型 1、公众用户，2、公会用户 **/
	private Integer usertype;
	/** 状态 0、无效，1、有效 **/
	private Integer status;
	/**
	 * "修改次数（大于8次 ，账号状态改为无效）"
	 **/
	private Integer modifyNum;
	/**保单到期提醒0否1是*/
	private String policyRemindFlag;
	/**违章提醒0否1是*/
	private String violationRemindFlag;
	/**驾照过期提醒0否1是*/
	private String drivingLicenceFlag;
	
	/**
	 * 绑定的手机号码
	 */
	private String bindMobile;
	
	/**
	 * 用户id *取得。
	 * @return 用户id *
	 */
	public String getUserid() {
	    return userid;
	}
	/**
	 * 用户id *设定。
	 * @param userid 用户id *
	 */
	public void setUserid(String userid) {
	    this.userid = userid;
	}
	/**
	 * 微信用户id取得。
	 * @return 微信用户id
	 */
	public String getOpenid() {
	    return openid;
	}
	/**
	 * 微信用户id设定。
	 * @param openid 微信用户id
	 */
	public void setOpenid(String openid) {
	    this.openid = openid;
	}
	/**
	 * 用户名 *取得。
	 * @return 用户名 *
	 */
	public String getUsername() {
	    return username;
	}
	/**
	 * 用户名 *设定。
	 * @param username 用户名 *
	 */
	public void setUsername(String username) {
	    this.username = username;
	}
	/**
	 * 邮箱 *取得。
	 * @return 邮箱 *
	 */
	public String getEmail() {
	    return email;
	}
	/**
	 * 邮箱 *设定。
	 * @param email 邮箱 *
	 */
	public void setEmail(String email) {
	    this.email = email;
	}
	/**
	 * 手机号码 *取得。
	 * @return 手机号码 *
	 */
	public String getTelephoneno() {
	    return telephoneno;
	}
	/**
	 * 手机号码 *设定。
	 * @param telephoneno 手机号码 *
	 */
	public void setTelephoneno(String telephoneno) {
	    this.telephoneno = telephoneno;
	}
	/**
	 * 密码 *取得。
	 * @return 密码 *
	 */
	public String getPassword() {
	    return password;
	}
	/**
	 * 密码 *设定。
	 * @param password 密码 *
	 */
	public void setPassword(String password) {
	    this.password = password;
	}
	/**
	 * 驾照号码 *取得。
	 * @return 驾照号码 *
	 */
	public String getDrivingFicenceNo() {
	    return drivingFicenceNo;
	}
	/**
	 * 驾照号码 *设定。
	 * @param drivingFicenceNo 驾照号码 *
	 */
	public void setDrivingFicenceNo(String drivingFicenceNo) {
	    this.drivingFicenceNo = drivingFicenceNo;
	}
	/**
	 * 驾照过期日期 *取得。
	 * @return 驾照过期日期 *
	 */
	public Date getDrivingLicenceExpire() {
	    return drivingLicenceExpire;
	}
	/**
	 * 驾照过期日期 *设定。
	 * @param drivingLicenceExpire 驾照过期日期 *
	 */
	public void setDrivingLicenceExpire(Date drivingLicenceExpire) {
	    this.drivingLicenceExpire = drivingLicenceExpire;
	}
	/**
	 * 身份证号码 *取得。
	 * @return 身份证号码 *
	 */
	public String getIdentityno() {
	    return identityno;
	}
	/**
	 * 身份证号码 *设定。
	 * @param identityno 身份证号码 *
	 */
	public void setIdentityno(String identityno) {
	    this.identityno = identityno;
	}
	/**
	 * 车牌号码 *取得。
	 * @return 车牌号码 *
	 */
	public String getPlateno() {
	    return plateno;
	}
	/**
	 * 车牌号码 *设定。
	 * @param plateno 车牌号码 *
	 */
	public void setPlateno(String plateno) {
	    this.plateno = plateno;
	}
	/**
	 * 车主 *取得。
	 * @return 车主 *
	 */
	public String getCarowner() {
	    return carowner;
	}
	/**
	 * 车主 *设定。
	 * @param carowner 车主 *
	 */
	public void setCarowner(String carowner) {
	    this.carowner = carowner;
	}
	/**
	 * 号牌种类 *取得。
	 * @return 号牌种类 *
	 */
	public String getPlatetype() {
	    return platetype;
	}
	/**
	 * 号牌种类 *设定。
	 * @param platetype 号牌种类 *
	 */
	public void setPlatetype(String platetype) {
	    this.platetype = platetype;
	}
	/**
	 * 设备编号 *取得。
	 * @return 设备编号 *
	 */
	public String getUuid() {
	    return uuid;
	}
	/**
	 * 设备编号 *设定。
	 * @param uuid 设备编号 *
	 */
	public void setUuid(String uuid) {
	    this.uuid = uuid;
	}
	/**
	 * 创建日期 *取得。
	 * @return 创建日期 *
	 */
	public Date getCreatedate() {
	    return createdate;
	}
	/**
	 * 创建日期 *设定。
	 * @param createdate 创建日期 *
	 */
	public void setCreatedate(Date createdate) {
	    this.createdate = createdate;
	}
	/**
	 * 用户类型 1、公众用户，2、公会用户 *取得。
	 * @return 用户类型 1、公众用户，2、公会用户 *
	 */
	public Integer getUsertype() {
	    return usertype;
	}
	/**
	 * 用户类型 1、公众用户，2、公会用户 *设定。
	 * @param usertype 用户类型 1、公众用户，2、公会用户 *
	 */
	public void setUsertype(Integer usertype) {
	    this.usertype = usertype;
	}
	/**
	 * 状态 0、无效，1、有效 *取得。
	 * @return 状态 0、无效，1、有效 *
	 */
	public Integer getStatus() {
	    return status;
	}
	/**
	 * 状态 0、无效，1、有效 *设定。
	 * @param status 状态 0、无效，1、有效 *
	 */
	public void setStatus(Integer status) {
	    this.status = status;
	}
	/**
	 * "修改次数（大于8次 ，账号状态改为无效）"取得。
	 * @return "修改次数（大于8次 ，账号状态改为无效）"
	 */
	public Integer getModifyNum() {
	    return modifyNum;
	}
	/**
	 * "修改次数（大于8次 ，账号状态改为无效）"设定。
	 * @param modifyNum "修改次数（大于8次 ，账号状态改为无效）"
	 */
	public void setModifyNum(Integer modifyNum) {
	    this.modifyNum = modifyNum;
	}
	/**
	 * 保单到期提醒0否1是取得。
	 * @return 保单到期提醒0否1是
	 */
	public String getPolicyRemindFlag() {
	    return policyRemindFlag;
	}
	/**
	 * 保单到期提醒0否1是设定。
	 * @param policyRemindFlag 保单到期提醒0否1是
	 */
	public void setPolicyRemindFlag(String policyRemindFlag) {
	    this.policyRemindFlag = policyRemindFlag;
	}
	/**
	 * 违章提醒0否1是取得。
	 * @return 违章提醒0否1是
	 */
	public String getViolationRemindFlag() {
	    return violationRemindFlag;
	}
	/**
	 * 违章提醒0否1是设定。
	 * @param violationRemindFlag 违章提醒0否1是
	 */
	public void setViolationRemindFlag(String violationRemindFlag) {
	    this.violationRemindFlag = violationRemindFlag;
	}
	/**
	 * 驾照过期提醒0否1是取得。
	 * @return 驾照过期提醒0否1是
	 */
	public String getDrivingLicenceFlag() {
	    return drivingLicenceFlag;
	}
	/**
	 * 驾照过期提醒0否1是设定。
	 * @param drivingLicenceFlag 驾照过期提醒0否1是
	 */
	public void setDrivingLicenceFlag(String drivingLicenceFlag) {
	    this.drivingLicenceFlag = drivingLicenceFlag;
	}
	/**
	 * 绑定的手机号码取得。
	 * @return 绑定的手机号码
	 */
	public String getBindMobile() {
	    return bindMobile;
	}
	/**
	 * 绑定的手机号码设定。
	 * @param bindMobile 绑定的手机号码
	 */
	public void setBindMobile(String bindMobile) {
	    this.bindMobile = bindMobile;
	}
	
}