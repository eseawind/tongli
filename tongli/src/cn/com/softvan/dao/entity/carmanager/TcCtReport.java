/*
 * 基础Entity类 报案
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
 * <p> 报案 <p>
 * @author wangzi
 *
 */
public class TcCtReport extends BaseEntity {
	/**
    * 报案编号
    */
    private String id;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 保单号
     */
    private String policyno;

    /**
     * 报案号
     */
    private String reportno;

    /**
     * 保险公司编号
     */
    private String orgCode;



    /**
     * 案件状态 0报案1.定损2.理赔.3完成
     */
    private String casestatus;

    /**
     * 案件状态0未处理1.处理中2.已处理
     */
    private String dealstatus;

    /**
     * 车牌号
     */
    private String plateno;

    /**
     * 发动机号
     */
    private String engineno;

    /**
     * 报案时间
     */
    private Date reportdate;
    
    /**
     * 出险原因
     */
    private String accidentCause;

    /**
     * 出险时间
     */
    private Date accidenttime;

    /**
     * 出险人姓名
     */
    private String name;

    /**
     * 联系人电话
     */
    private String tel;

    /**
     * 出险地点
     */
    private String addrs;

    /**
     * 出险描述
     */
    private String accidentinfo;

    /**
     * 地址经度
     */
    private String longitude;

    /**
     * 地址纬度
     */
    private String latitude;

   /**
    * 地址精度
    */
    private String measuringAccuracy;
    /**
     * 报案的处理员
     */
    private String dealUserId;

/**
 * 报案编号を取得します。
 * @return 报案编号
 */
public String getId() {
return id;
}

/**
 * 报案编号を設定します。
 * @param id 报案编号
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
 * 保单号を取得します。
 * @return 保单号
 */
public String getPolicyno() {
return policyno;
}

/**
 * 保单号を設定します。
 * @param policyno 保单号
 */
public void setPolicyno(String policyno) {
this.policyno = policyno;
}

/**
 * 报案号を取得します。
 * @return 报案号
 */
public String getReportno() {
return reportno;
}

/**
 * 报案号を設定します。
 * @param reportno 报案号
 */
public void setReportno(String reportno) {
this.reportno = reportno;
}

/**
 * 保险公司编号を取得します。
 * @return 保险公司编号
 */
public String getOrgCode() {
return orgCode;
}

/**
 * 保险公司编号を設定します。
 * @param org_code 保险公司编号
 */
public void setOrgCode(String orgCode) {
this.orgCode = orgCode;
}

/**
 * 案件状态 0报案1.定损2.理赔.3完成を取得します。
 * @return 案件状态 0报案1.定损2.理赔.3完成
 */
public String getCasestatus() {
return casestatus;
}

/**
 * 案件状态 0报案1.定损2.理赔.3完成を設定します。
 * @param casestatus 案件状态 0报案1.定损2.理赔.3完成
 */
public void setCasestatus(String casestatus) {
this.casestatus = casestatus;
}

/**
 * 案件状态0未处理1.处理中2.已处理を取得します。
 * @return 案件状态0未处理1.处理中2.已处理
 */
public String getDealstatus() {
    return dealstatus;
}

/**
 * 案件状态0未处理1.处理中2.已处理を設定します。
 * @param dealstatus 案件状态0未处理1.处理中2.已处理
 */
public void setDealstatus(String dealstatus) {
    this.dealstatus = dealstatus;
}

/**
 * 车牌号を取得します。
 * @return 车牌号
 */
public String getPlateno() {
return plateno;
}

/**
 * 车牌号を設定します。
 * @param plateno 车牌号
 */
public void setPlateno(String plateno) {
this.plateno = plateno;
}

/**
 * 发动机号を取得します。
 * @return 发动机号
 */
public String getEngineno() {
return engineno;
}

/**
 * 发动机号を設定します。
 * @param engineno 发动机号
 */
public void setEngineno(String engineno) {
this.engineno = engineno;
}

/**
 * 出险原因 取得
 * @return 出险原因
 */
public String getAccidentCause() {
	return accidentCause;
}

/**
 * 出险原因 设定
 * @param accidentCause 出险原因
 */
public void setAccidentCause(String accidentCause) {
	this.accidentCause = accidentCause;
}

/**
 * 报案时间を取得します。
 * @return 报案时间
 */
public Date getReportdate() {
return reportdate;
}

/**
 * 报案时间を設定します。
 * @param reportdate 报案时间
 */
public void setReportdate(Date reportdate) {
this.reportdate = reportdate;
}

/**
 * 出险时间を取得します。
 * @return 出险时间
 */
public Date getAccidenttime() {
return accidenttime;
}

/**
 * 出险时间を設定します。
 * @param accidenttime 出险时间
 */
public void setAccidenttime(Date accidenttime) {
this.accidenttime = accidenttime;
}

/**
 * 出险人姓名を取得します。
 * @return 出险人姓名
 */
public String getName() {
return name;
}

/**
 * 出险人姓名を設定します。
 * @param name 出险人姓名
 */
public void setName(String name) {
this.name = name;
}

/**
 * 联系人电话を取得します。
 * @return 联系人电话
 */
public String getTel() {
return tel;
}

/**
 * 联系人电话を設定します。
 * @param tel 联系人电话
 */
public void setTel(String tel) {
this.tel = tel;
}

/**
 * 出险地点を取得します。
 * @return 出险地点
 */
public String getAddrs() {
return addrs;
}

/**
 * 出险地点を設定します。
 * @param addrs 出险地点
 */
public void setAddrs(String addrs) {
this.addrs = addrs;
}

/**
 * 出险描述を取得します。
 * @return 出险描述
 */
public String getAccidentinfo() {
return accidentinfo;
}

/**
 * 出险描述を設定します。
 * @param accidentinfo 出险描述
 */
public void setAccidentinfo(String accidentinfo) {
this.accidentinfo = accidentinfo;
}

/**
 * 地址经度を取得します。
 * @return 地址经度
 */
public String getLongitude() {
return longitude;
}

/**
 * 地址经度を設定します。
 * @param longitude 地址经度
 */
public void setLongitude(String longitude) {
this.longitude = longitude;
}

/**
 * 地址纬度を取得します。
 * @return 地址纬度
 */
public String getLatitude() {
return latitude;
}

/**
 * 地址纬度を設定します。
 * @param latitude 地址纬度
 */
public void setLatitude(String latitude) {
this.latitude = latitude;
}

/**
 * 地址精度を取得します。
 * @return 地址精度
 */
public String getMeasuringAccuracy() {
return measuringAccuracy;
}

/**
 * 地址精度を設定します。
 * @param measuring_accuracy 地址精度
 */
public void setMeasuringAccuracy(String measuringAccuracy) {
this.measuringAccuracy = measuringAccuracy;
}

/**
 * 报案的处理员を取得します。
 * @return 报案的处理员
 */
public String getDealUserId() {
return dealUserId;
}

/**
 * 报案的处理员を設定します。
 * @param dealUserId 报案的处理员
 */
public void setDealUserId(String dealUserId) {
this.dealUserId = dealUserId;
}

}