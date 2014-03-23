/*
 * 基础Entity类  注册信息维护日志表
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
 * <p> 注册信息维护日志表 <p>
 * @author wangzi
 *
 */
public class TcCtRegMaintainLog extends BaseEntity {
	/**	id	*/
	private String	id;	
	/**	用户id	*/
	private String	userid;	
	/**	车辆用户关系id	*/
	private String	caruserid;	
	/**	操作类型	*/
	private String	operatetype;	
	/**	操作时间	*/
	private Date	operatetime;	
	/**	车主姓名	*/
	private String	carowner;	
	/**	发动机号	*/
	private String	engineno;	
	/**	车架号	*/
	private String	vincode;	
	/**	号牌种类	*/
	private String	platetype;	
	/**	车牌号码	*/
	private String	plateno;
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
	 * 用户id取得
	 * @return 用户id
	 */
	public String getUserid() {
	    return userid;
	}
	/**
	 * 用户id设定
	 * @param userid 用户id
	 */
	public void setUserid(String userid) {
	    this.userid = userid;
	}
	/**
	 * 车辆用户关系id取得
	 * @return 车辆用户关系id
	 */
	public String getCaruserid() {
	    return caruserid;
	}
	/**
	 * 车辆用户关系id设定
	 * @param caruserid 车辆用户关系id
	 */
	public void setCaruserid(String caruserid) {
	    this.caruserid = caruserid;
	}
	/**
	 * 操作类型取得
	 * @return 操作类型
	 */
	public String getOperatetype() {
	    return operatetype;
	}
	/**
	 * 操作类型设定
	 * @param operatetype 操作类型
	 */
	public void setOperatetype(String operatetype) {
	    this.operatetype = operatetype;
	}
	/**
	 * 操作时间取得
	 * @return 操作时间
	 */
	public Date getOperatetime() {
	    return operatetime;
	}
	/**
	 * 操作时间设定
	 * @param operatetime 操作时间
	 */
	public void setOperatetime(Date operatetime) {
	    this.operatetime = operatetime;
	}
	/**
	 * 车主姓名取得
	 * @return 车主姓名
	 */
	public String getCarowner() {
	    return carowner;
	}
	/**
	 * 车主姓名设定
	 * @param carowner 车主姓名
	 */
	public void setCarowner(String carowner) {
	    this.carowner = carowner;
	}
	/**
	 * 发动机号取得
	 * @return 发动机号
	 */
	public String getEngineno() {
	    return engineno;
	}
	/**
	 * 发动机号设定
	 * @param engineno 发动机号
	 */
	public void setEngineno(String engineno) {
	    this.engineno = engineno;
	}
	/**
	 * 车架号取得
	 * @return 车架号
	 */
	public String getVincode() {
	    return vincode;
	}
	/**
	 * 车架号设定
	 * @param vincode 车架号
	 */
	public void setVincode(String vincode) {
	    this.vincode = vincode;
	}
	/**
	 * 号牌种类取得
	 * @return 号牌种类
	 */
	public String getPlatetype() {
	    return platetype;
	}
	/**
	 * 号牌种类设定
	 * @param platetype 号牌种类
	 */
	public void setPlatetype(String platetype) {
	    this.platetype = platetype;
	}
	/**
	 * 车牌号码取得
	 * @return 车牌号码
	 */
	public String getPlateno() {
	    return plateno;
	}
	/**
	 * 车牌号码设定
	 * @param plateno 车牌号码
	 */
	public void setPlateno(String plateno) {
	    this.plateno = plateno;
	}
}