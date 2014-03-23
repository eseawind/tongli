/*
 * 基础Entity类  保单
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
 * 保单
 * <p>
 * 
 * @author wangzi
 * 
 */
public class TcCtPolicy extends BaseEntity {
	/** 保单id */
	private String policyid;
	/** 用户id */
	private String userid;
	/**车辆id*/
	private String vehicleid;
	/** 保单类型 */
	private String policytype;
	/** "保单号码 交强/商业	取值顺序与表定义顺序相同" */
	private String policyno;
	/** 保单状态 */
	private String policystatus;

	/** 保险公司 */
	private String insuranceorg;
	/** 保险起期 */
	private Date startdate;
	/** 保险止期 */
	private Date enddate;
	/** 标准保费 */
	private Float standardpremium;
	/** 固定保费 */
	private Float basedpremium;
	/** 赔偿限额 */
	private Float limitamount;
	/** 车辆使用性质 */
	private String usetype;
	/** 机动车车主 */
	private String carowner;
	/** 投保人 */
	private String policyholder;
	/** 被保险人 */
	private String insured;
	/** 签单日期 */
	private Date signdate;
	/** 投保确认码 */
	private String comfirmsequenceno;



	/**
	 * 保单idを取得します。
	 * @return 保单id
	 */
	public String getPolicyid() {
		return policyid;
	}

	/**
	 * 保单idを設定します。
	 * @param policyid 保单id
	 */
	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}

	/**
	 * 用户idを取得します。
	 * @return 用户id
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * 用户idを設定します。
	 * @param userid 用户id
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * 车辆idを取得します。
	 * @return 车辆id
	 */
	public String getVehicleid() {
	    return vehicleid;
	}

	/**
	 * 车辆idを設定します。
	 * @param vehicleid 车辆id
	 */
	public void setVehicleid(String vehicleid) {
	    this.vehicleid = vehicleid;
	}

	/**
	 * 保单类型を取得します。
	 * @return 保单类型
	 */
	public String getPolicytype() {
		return policytype;
	}

	/**
	 * 保单类型を設定します。
	 * @param policytype 保单类型
	 */
	public void setPolicytype(String policytype) {
		this.policytype = policytype;
	}

	/**
	 * "保单号码 交强/商业	取值顺序与表定义顺序相同"を取得します。
	 * @return "保单号码 交强/商业	取值顺序与表定义顺序相同"
	 */
	public String getPolicyno() {
		return policyno;
	}

	/**
	 * "保单号码 交强/商业	取值顺序与表定义顺序相同"を設定します。
	 * @param policyno "保单号码 交强/商业	取值顺序与表定义顺序相同"
	 */
	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}

	/**
	 * 保单状态を取得します。
	 * @return 保单状态
	 */
	public String getPolicystatus() {
		return policystatus;
	}

	/**
	 * 保单状态を設定します。
	 * @param policystatus 保单状态
	 */
	public void setPolicystatus(String policystatus) {
		this.policystatus = policystatus;
	}

	/**
	 * 保险公司を取得します。
	 * @return 保险公司
	 */
	public String getInsuranceorg() {
		return insuranceorg;
	}

	/**
	 * 保险公司を設定します。
	 * @param insuranceorg 保险公司
	 */
	public void setInsuranceorg(String insuranceorg) {
		this.insuranceorg = insuranceorg;
	}

	/**
	 * 保险起期を取得します。
	 * @return 保险起期
	 */
	public Date getStartdate() {
		return startdate;
	}

	/**
	 * 保险起期を設定します。
	 * @param startdate 保险起期
	 */
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	/**
	 * 保险止期を取得します。
	 * @return 保险止期
	 */
	public Date getEnddate() {
		return enddate;
	}

	/**
	 * 保险止期を設定します。
	 * @param enddate 保险止期
	 */
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	/**
	 * 标准保费を取得します。
	 * @return 标准保费
	 */
	public Float getStandardpremium() {
	    return standardpremium;
	}

	/**
	 * 标准保费を設定します。
	 * @param standardpremium 标准保费
	 */
	public void setStandardpremium(Float standardpremium) {
		this.standardpremium = standardpremium;
	}

	/**
	 * 固定保费を取得します。
	 * @return 固定保费
	 */
	public Float getBasedpremium() {
	    return basedpremium;
	}

	/**
	 * 固定保费を設定します。
	 * @param basedpremium 固定保费
	 */
	public void setBasedpremium(Float basedpremium) {
		this.basedpremium = basedpremium;
	}

	/**
	 * 赔偿限额を取得します。
	 * @return 赔偿限额
	 */
	public Float getLimitamount() {
	    return limitamount;
	}

	/**
	 * 赔偿限额を設定します。
	 * @param limitamount 赔偿限额
	 */
	public void setLimitamount(Float limitamount) {
		this.limitamount = limitamount;
	}

	/**
	 * 车辆使用性质を取得します。
	 * @return 车辆使用性质
	 */
	public String getUsetype() {
		return usetype;
	}

	/**
	 * 车辆使用性质を設定します。
	 * @param usetype 车辆使用性质
	 */
	public void setUsetype(String usetype) {
		this.usetype = usetype;
	}

	/**
	 * 机动车车主を取得します。
	 * @return 机动车车主
	 */
	public String getCarowner() {
		return carowner;
	}

	/**
	 * 机动车车主を設定します。
	 * @param carowner 机动车车主
	 */
	public void setCarowner(String carowner) {
		this.carowner = carowner;
	}

	/**
	 * 投保人を取得します。
	 * @return 投保人
	 */
	public String getPolicyholder() {
		return policyholder;
	}

	/**
	 * 投保人を設定します。
	 * @param policyholder 投保人
	 */
	public void setPolicyholder(String policyholder) {
		this.policyholder = policyholder;
	}

	/**
	 * 被保险人を取得します。
	 * @return 被保险人
	 */
	public String getInsured() {
		return insured;
	}

	/**
	 * 被保险人を設定します。
	 * @param insured 被保险人
	 */
	public void setInsured(String insured) {
		this.insured = insured;
	}

	/**
	 * 签单日期を取得します。
	 * @return 签单日期
	 */
	public Date getSigndate() {
		return signdate;
	}

	/**
	 * 签单日期を設定します。
	 * @param signdate 签单日期
	 */
	public void setSigndate(Date signdate) {
		this.signdate = signdate;
	}

	/**
	 * 投保确认码を取得します。
	 * @return 投保确认码
	 */
	public String getComfirmsequenceno() {
		return comfirmsequenceno;
	}

	/**
	 * 投保确认码を設定します。
	 * @param comfirmsequenceno 投保确认码
	 */
	public void setComfirmsequenceno(String comfirmsequenceno) {
		this.comfirmsequenceno = comfirmsequenceno;
	}

	
}