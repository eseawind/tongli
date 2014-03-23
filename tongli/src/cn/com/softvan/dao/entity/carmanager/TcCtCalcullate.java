/*
 * 基础Entity类  保费试算
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.carmanager;

import java.math.BigDecimal;
import java.util.Date;

import cn.com.softvan.dao.entity.BaseEntity;
/**
 * 
 * <p> 保费试算 <p>
 * @author wangzi
 *
 */
public class TcCtCalcullate extends BaseEntity {
	/**	ID	**/
	private String	id;	
	/**	用户id	**/
	private String	userid;	
	/**	车辆id	**/
	private String	vehicleid;	
	/**	车辆种类	**/
	private String	vehicletype;	
	/**	使用性质	**/
	private String	natureuse;	
	/**	车船税标志	**/
	private String	taxflag;	
	/**	投保查询码	**/
	private String	policyqueryno;	
	/**	号牌号码	**/
	private String	plateno;	
	/**	车架号	**/
	private String	vincode;	
	/**	交强险责任限额	**/
	private BigDecimal	trafficlimitliability;	
	/**	保险起期	**/
	private Date	startdate;	
	/**	保险止期	**/
	private Date	enddate;	
	/**	基准保费	**/
	private BigDecimal	basepremium;	
	/**	核定保费	**/
	private BigDecimal	limitpremium;	
	/**	交通违法调整系数	**/
	private String	trafficadjustmodulus;	
	/**	事故调整系数	**/
	private String	accidentadjustmodulus;	
	/**	保费计算公式	**/
	private String	premiumformula;	
	/**	车船税	**/
	private String	traveltax;	
	/**	应缴金额	**/
	private BigDecimal	payamount;
	/**
	 * ID	*取得
	 * @return ID	*
	 */
	public String getId() {
	    return id;
	}
	/**
	 * ID	*设定
	 * @param id ID	*
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 用户id	*取得
	 * @return 用户id	*
	 */
	public String getUserid() {
	    return userid;
	}
	/**
	 * 用户id	*设定
	 * @param userid 用户id	*
	 */
	public void setUserid(String userid) {
	    this.userid = userid;
	}
	/**
	 * 车辆id	*取得
	 * @return 车辆id	*
	 */
	public String getVehicleid() {
	    return vehicleid;
	}
	/**
	 * 车辆id	*设定
	 * @param vehicleid 车辆id	*
	 */
	public void setVehicleid(String vehicleid) {
	    this.vehicleid = vehicleid;
	}
	/**
	 * 车辆种类	*取得
	 * @return 车辆种类	*
	 */
	public String getVehicletype() {
	    return vehicletype;
	}
	/**
	 * 车辆种类	*设定
	 * @param vehicletype 车辆种类	*
	 */
	public void setVehicletype(String vehicletype) {
	    this.vehicletype = vehicletype;
	}
	/**
	 * 使用性质	*取得
	 * @return 使用性质	*
	 */
	public String getNatureuse() {
	    return natureuse;
	}
	/**
	 * 使用性质	*设定
	 * @param natureuse 使用性质	*
	 */
	public void setNatureuse(String natureuse) {
	    this.natureuse = natureuse;
	}
	/**
	 * 车船税标志	*取得
	 * @return 车船税标志	*
	 */
	public String getTaxflag() {
	    return taxflag;
	}
	/**
	 * 车船税标志	*设定
	 * @param taxflag 车船税标志	*
	 */
	public void setTaxflag(String taxflag) {
	    this.taxflag = taxflag;
	}
	/**
	 * 投保查询码	*取得
	 * @return 投保查询码	*
	 */
	public String getPolicyqueryno() {
	    return policyqueryno;
	}
	/**
	 * 投保查询码	*设定
	 * @param policyqueryno 投保查询码	*
	 */
	public void setPolicyqueryno(String policyqueryno) {
	    this.policyqueryno = policyqueryno;
	}
	/**
	 * 号牌号码	*取得
	 * @return 号牌号码	*
	 */
	public String getPlateno() {
	    return plateno;
	}
	/**
	 * 号牌号码	*设定
	 * @param plateno 号牌号码	*
	 */
	public void setPlateno(String plateno) {
	    this.plateno = plateno;
	}
	/**
	 * 车架号	*取得
	 * @return 车架号	*
	 */
	public String getVincode() {
	    return vincode;
	}
	/**
	 * 车架号	*设定
	 * @param vincode 车架号	*
	 */
	public void setVincode(String vincode) {
	    this.vincode = vincode;
	}
	/**
	 * 交强险责任限额	*取得
	 * @return 交强险责任限额	*
	 */
	public BigDecimal getTrafficlimitliability() {
	    return trafficlimitliability;
	}
	/**
	 * 交强险责任限额	*设定
	 * @param trafficlimitliability 交强险责任限额	*
	 */
	public void setTrafficlimitliability(BigDecimal trafficlimitliability) {
	    this.trafficlimitliability = trafficlimitliability;
	}
	/**
	 * 保险起期	*取得
	 * @return 保险起期	*
	 */
	public Date getStartdate() {
	    return startdate;
	}
	/**
	 * 保险起期	*设定
	 * @param startdate 保险起期	*
	 */
	public void setStartdate(Date startdate) {
	    this.startdate = startdate;
	}
	/**
	 * 保险止期	*取得
	 * @return 保险止期	*
	 */
	public Date getEnddate() {
	    return enddate;
	}
	/**
	 * 保险止期	*设定
	 * @param enddate 保险止期	*
	 */
	public void setEnddate(Date enddate) {
	    this.enddate = enddate;
	}
	/**
	 * 基准保费	*取得
	 * @return 基准保费	*
	 */
	public BigDecimal getBasepremium() {
	    return basepremium;
	}
	/**
	 * 基准保费	*设定
	 * @param basepremium 基准保费	*
	 */
	public void setBasepremium(BigDecimal basepremium) {
	    this.basepremium = basepremium;
	}
	/**
	 * 核定保费	*取得
	 * @return 核定保费	*
	 */
	public BigDecimal getLimitpremium() {
	    return limitpremium;
	}
	/**
	 * 核定保费	*设定
	 * @param limitpremium 核定保费	*
	 */
	public void setLimitpremium(BigDecimal limitpremium) {
	    this.limitpremium = limitpremium;
	}
	/**
	 * 交通违法调整系数	*取得
	 * @return 交通违法调整系数	*
	 */
	public String getTrafficadjustmodulus() {
	    return trafficadjustmodulus;
	}
	/**
	 * 交通违法调整系数	*设定
	 * @param trafficadjustmodulus 交通违法调整系数	*
	 */
	public void setTrafficadjustmodulus(String trafficadjustmodulus) {
	    this.trafficadjustmodulus = trafficadjustmodulus;
	}
	/**
	 * 事故调整系数	*取得
	 * @return 事故调整系数	*
	 */
	public String getAccidentadjustmodulus() {
	    return accidentadjustmodulus;
	}
	/**
	 * 事故调整系数	*设定
	 * @param accidentadjustmodulus 事故调整系数	*
	 */
	public void setAccidentadjustmodulus(String accidentadjustmodulus) {
	    this.accidentadjustmodulus = accidentadjustmodulus;
	}
	/**
	 * 保费计算公式	*取得
	 * @return 保费计算公式	*
	 */
	public String getPremiumformula() {
	    return premiumformula;
	}
	/**
	 * 保费计算公式	*设定
	 * @param premiumformula 保费计算公式	*
	 */
	public void setPremiumformula(String premiumformula) {
	    this.premiumformula = premiumformula;
	}
	/**
	 * 车船税	*取得
	 * @return 车船税	*
	 */
	public String getTraveltax() {
	    return traveltax;
	}
	/**
	 * 车船税	*设定
	 * @param traveltax 车船税	*
	 */
	public void setTraveltax(String traveltax) {
	    this.traveltax = traveltax;
	}
	/**
	 * 应缴金额	*取得
	 * @return 应缴金额	*
	 */
	public BigDecimal getPayamount() {
	    return payamount;
	}
	/**
	 * 应缴金额	*设定
	 * @param payamount 应缴金额	*
	 */
	public void setPayamount(BigDecimal payamount) {
	    this.payamount = payamount;
	}	
}