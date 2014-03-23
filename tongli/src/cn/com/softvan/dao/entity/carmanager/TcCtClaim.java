/*
 * 基础Entity类  理赔
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
 * 
 * <p> 理赔 <p>
 * @author wangzi
 *
 */
public class TcCtClaim extends BaseEntity {
	/**	ID	**/
	private String	id;	
	/**用户id*/
	private String userid;
	/**	报案编号	**/
	private String	reportid;	
	/**	报案号	**/
	private String	reportno;	
	/**	保单号	**/
	private String	policyno;	
	/**	保单id	**/
	private String	policyid;	
	/**	案件号	**/
	private String	caseno;	
	/**	赔款金额	**/
	private Float	reparationamount;	
	/**	报案时间	**/
	private Date	reportdate;	
	/**	出险时间	**/
	private Date	happentime;	
	/**	立案时间	**/
	private Date	casestarttime;	
	/**	结案时间	**/
	private Date	closedtime;	
	/**	赔案类型	1 交强 2商业**/
	private String	casetype;	
	/**	理赔类型	**/
	private String	claimtype;
	/**	案件状态	**/
	private String	casestatus;	
	/**	拒赔原因	**/
	private String	refusepayreason;	
	/**	出险驾驶员姓名	**/
	private String	drivername;	
	/**	出险地点	**/
	private String	happenlocation;	
	/**	出险经过	**/
	private String	course;	
	/**	事故责任划分	**/
	private String	resdivision;	
	/**	理赔赔付日期	**/
	private Date	casepaydate;
	/**
	 * ID	*を取得します。
	 * @return ID	*
	 */
	public String getId() {
	    return id;
	}
	/**
	 * ID	*を設定します。
	 * @param id ID	*
	 */
	public void setId(String id) {
	    this.id = id;
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
	 * 报案编号	*を取得します。
	 * @return 报案编号	*
	 */
	public String getReportid() {
	    return reportid;
	}
	/**
	 * 报案编号	*を設定します。
	 * @param reportid 报案编号	*
	 */
	public void setReportid(String reportid) {
	    this.reportid = reportid;
	}
	/**
	 * 报案号	*を取得します。
	 * @return 报案号	*
	 */
	public String getReportno() {
	    return reportno;
	}
	/**
	 * 报案号	*を設定します。
	 * @param reportno 报案号	*
	 */
	public void setReportno(String reportno) {
	    this.reportno = reportno;
	}
	/**
	 * 保单号	*を取得します。
	 * @return 保单号	*
	 */
	public String getPolicyno() {
	    return policyno;
	}
	/**
	 * 保单号	*を設定します。
	 * @param policyno 保单号	*
	 */
	public void setPolicyno(String policyno) {
	    this.policyno = policyno;
	}
	/**
	 * 保单id	*を取得します。
	 * @return 保单id	*
	 */
	public String getPolicyid() {
	    return policyid;
	}
	/**
	 * 保单id	*を設定します。
	 * @param policyid 保单id	*
	 */
	public void setPolicyid(String policyid) {
	    this.policyid = policyid;
	}
	/**
	 * 案件号	*を取得します。
	 * @return 案件号	*
	 */
	public String getCaseno() {
	    return caseno;
	}
	/**
	 * 案件号	*を設定します。
	 * @param caseno 案件号	*
	 */
	public void setCaseno(String caseno) {
	    this.caseno = caseno;
	}
	/**
	 * 赔款金额	*を取得します。
	 * @return 赔款金额	*
	 */
	public Float getReparationamount() {
	    return reparationamount;
	}
	/**
	 * 赔款金额	*を設定します。
	 * @param reparationamount 赔款金额	*
	 */
	public void setReparationamount(Float reparationamount) {
	    this.reparationamount = reparationamount;
	}
	/**
	 * 报案时间	*を取得します。
	 * @return 报案时间	*
	 */
	public Date getReportdate() {
	    return reportdate;
	}
	/**
	 * 报案时间	*を設定します。
	 * @param reportdate 报案时间	*
	 */
	public void setReportdate(Date reportdate) {
	    this.reportdate = reportdate;
	}
	/**
	 * 出险时间	*を取得します。
	 * @return 出险时间	*
	 */
	public Date getHappentime() {
	    return happentime;
	}
	/**
	 * 出险时间	*を設定します。
	 * @param happentime 出险时间	*
	 */
	public void setHappentime(Date happentime) {
	    this.happentime = happentime;
	}
	/**
	 * 立案时间	*を取得します。
	 * @return 立案时间	*
	 */
	public Date getCasestarttime() {
	    return casestarttime;
	}
	/**
	 * 立案时间	*を設定します。
	 * @param casestarttime 立案时间	*
	 */
	public void setCasestarttime(Date casestarttime) {
	    this.casestarttime = casestarttime;
	}
	/**
	 * 结案时间	*を取得します。
	 * @return 结案时间	*
	 */
	public Date getClosedtime() {
	    return closedtime;
	}
	/**
	 * 结案时间	*を設定します。
	 * @param closedtime 结案时间	*
	 */
	public void setClosedtime(Date closedtime) {
	    this.closedtime = closedtime;
	}
	/**
	 * 赔案类型	1 交强 2商业*を取得します。
	 * @return 赔案类型	1 交强 2商业*
	 */
	public String getCasetype() {
	    return casetype;
	}
	/**
	 * 赔案类型	1 交强 2商业*を設定します。
	 * @param casetype 赔案类型	1 交强 2商业*
	 */
	public void setCasetype(String casetype) {
	    this.casetype = casetype;
	}
	/**
	 * 理赔类型	*を取得します。
	 * @return 理赔类型	*
	 */
	public String getClaimtype() {
	    return claimtype;
	}
	/**
	 * 理赔类型	*を設定します。
	 * @param claimtype 理赔类型	*
	 */
	public void setClaimtype(String claimtype) {
	    this.claimtype = claimtype;
	}
	/**
	 * 案件状态	*を取得します。
	 * @return 案件状态	*
	 */
	public String getCasestatus() {
	    return casestatus;
	}
	/**
	 * 案件状态	*を設定します。
	 * @param casestatus 案件状态	*
	 */
	public void setCasestatus(String casestatus) {
	    this.casestatus = casestatus;
	}
	/**
	 * 拒赔原因	*を取得します。
	 * @return 拒赔原因	*
	 */
	public String getRefusepayreason() {
	    return refusepayreason;
	}
	/**
	 * 拒赔原因	*を設定します。
	 * @param refusepayreason 拒赔原因	*
	 */
	public void setRefusepayreason(String refusepayreason) {
	    this.refusepayreason = refusepayreason;
	}
	/**
	 * 出险驾驶员姓名	*を取得します。
	 * @return 出险驾驶员姓名	*
	 */
	public String getDrivername() {
	    return drivername;
	}
	/**
	 * 出险驾驶员姓名	*を設定します。
	 * @param drivername 出险驾驶员姓名	*
	 */
	public void setDrivername(String drivername) {
	    this.drivername = drivername;
	}
	/**
	 * 出险地点	*を取得します。
	 * @return 出险地点	*
	 */
	public String getHappenlocation() {
	    return happenlocation;
	}
	/**
	 * 出险地点	*を設定します。
	 * @param happenlocation 出险地点	*
	 */
	public void setHappenlocation(String happenlocation) {
	    this.happenlocation = happenlocation;
	}
	/**
	 * 出险经过	*を取得します。
	 * @return 出险经过	*
	 */
	public String getCourse() {
	    return course;
	}
	/**
	 * 出险经过	*を設定します。
	 * @param course 出险经过	*
	 */
	public void setCourse(String course) {
	    this.course = course;
	}
	/**
	 * 事故责任划分	*を取得します。
	 * @return 事故责任划分	*
	 */
	public String getResdivision() {
	    return resdivision;
	}
	/**
	 * 事故责任划分	*を設定します。
	 * @param resdivision 事故责任划分	*
	 */
	public void setResdivision(String resdivision) {
	    this.resdivision = resdivision;
	}
	/**
	 * 理赔赔付日期	*を取得します。
	 * @return 理赔赔付日期	*
	 */
	public Date getCasepaydate() {
	    return casepaydate;
	}
	/**
	 * 理赔赔付日期	*を設定します。
	 * @param casepaydate 理赔赔付日期	*
	 */
	public void setCasepaydate(Date casepaydate) {
	    this.casepaydate = casepaydate;
	}	


}