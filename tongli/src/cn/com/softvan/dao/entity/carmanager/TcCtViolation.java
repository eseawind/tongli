/*
 * 基础Entity类   违章
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * 1.01     2014.03.11  陈亮                        新增status字段
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.carmanager;

import java.math.BigDecimal;
import java.util.Date;

import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p> 违章 <p>
 * @author wangzi
 *
 */
public class TcCtViolation extends BaseEntity {
    /**
     *id
     */
    private String id;

    /**
     * 车辆id
     */
    private String vehicleid;

    /**
     * 车辆种类
     */
    private String vehicletype;

    /**
     * 状态（0:未受理 ; 1:已受理）
     */
    private String status;
    
    /**
     * 使用性质
     */
    private String natureuse;

    /**
     *车船税标志
     */
    private String taxflag;

    /**
     *受理日期
     */
    private Date acceptdate;

    /**
     * 违章地点
     */
    private String peccancyplace;

    /**
     * 违章时间
     */
    private Date peccancytime;

    /**
     * 违法行为代码描述
     */
    private String peccancydes;

    /**
     * 处罚金额
     */
    private BigDecimal financialpenalty;

    /**
     * 处罚分数
     */
    private Integer deduction;

    /**
     * 地址经度
     */
    private String longitude;

    /**
     * 地址纬度
     */
    private String latitude;

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
	 * 车辆id取得
	 * @return 车辆id
	 */
	public String getVehicleid() {
	    return vehicleid;
	}

	/**
	 * 车辆id设定
	 * @param vehicleid 车辆id
	 */
	public void setVehicleid(String vehicleid) {
	    this.vehicleid = vehicleid;
	}

	/**
	 * 车辆种类取得
	 * @return 车辆种类
	 */
	public String getVehicletype() {
	    return vehicletype;
	}

	/**
	 * 车辆种类设定
	 * @param vehicletype 车辆种类
	 */
	public void setVehicletype(String vehicletype) {
	    this.vehicletype = vehicletype;
	}

	/**
	 * 状态（0:未受理 ; 1:已受理）取得
	 * @return 状态（0:未受理 ; 1:已受理）
	 */
	public String getStatus() {
	    return status;
	}

	/**
	 * 状态（0:未受理 ; 1:已受理）設定
	 * @param status 状态（0:未受理 ; 1:已受理）
	 */
	public void setStatus(String status) {
	    this.status = status;
	}

	/**
	 * 使用性质取得
	 * @return 使用性质
	 */
	public String getNatureuse() {
	    return natureuse;
	}

	/**
	 * 使用性质设定
	 * @param natureuse 使用性质
	 */
	public void setNatureuse(String natureuse) {
	    this.natureuse = natureuse;
	}

	/**
	 * 车船税标志取得
	 * @return 车船税标志
	 */
	public String getTaxflag() {
	    return taxflag;
	}

	/**
	 * 车船税标志设定
	 * @param taxflag 车船税标志
	 */
	public void setTaxflag(String taxflag) {
	    this.taxflag = taxflag;
	}

	/**
	 * 受理日期取得
	 * @return 受理日期
	 */
	public Date getAcceptdate() {
	    return acceptdate;
	}

	/**
	 * 受理日期设定
	 * @param acceptdate 受理日期
	 */
	public void setAcceptdate(Date acceptdate) {
	    this.acceptdate = acceptdate;
	}

	/**
	 * 违章地点取得
	 * @return 违章地点
	 */
	public String getPeccancyplace() {
	    return peccancyplace;
	}

	/**
	 * 违章地点设定
	 * @param peccancyplace 违章地点
	 */
	public void setPeccancyplace(String peccancyplace) {
	    this.peccancyplace = peccancyplace;
	}

	/**
	 * 违章时间取得
	 * @return 违章时间
	 */
	public Date getPeccancytime() {
	    return peccancytime;
	}

	/**
	 * 违章时间设定
	 * @param peccancytime 违章时间
	 */
	public void setPeccancytime(Date peccancytime) {
	    this.peccancytime = peccancytime;
	}

	/**
	 * 违法行为代码描述取得
	 * @return 违法行为代码描述
	 */
	public String getPeccancydes() {
	    return peccancydes;
	}

	/**
	 * 违法行为代码描述设定
	 * @param peccancydes 违法行为代码描述
	 */
	public void setPeccancydes(String peccancydes) {
	    this.peccancydes = peccancydes;
	}

	/**
	 * 处罚金额取得
	 * @return 处罚金额
	 */
	public BigDecimal getFinancialpenalty() {
	    return financialpenalty;
	}

	/**
	 * 处罚金额设定
	 * @param financialpenalty 处罚金额
	 */
	public void setFinancialpenalty(BigDecimal financialpenalty) {
	    this.financialpenalty = financialpenalty;
	}

	/**
	 * 处罚分数取得
	 * @return 处罚分数
	 */
	public Integer getDeduction() {
	    return deduction;
	}

	/**
	 * 处罚分数设定
	 * @param deduction 处罚分数
	 */
	public void setDeduction(Integer deduction) {
	    this.deduction = deduction;
	}

	/**
	 * 地址经度取得
	 * @return 地址经度
	 */
	public String getLongitude() {
	    return longitude;
	}

	/**
	 * 地址经度设定
	 * @param longitude 地址经度
	 */
	public void setLongitude(String longitude) {
	    this.longitude = longitude;
	}

	/**
	 * 地址纬度取得
	 * @return 地址纬度
	 */
	public String getLatitude() {
	    return latitude;
	}

	/**
	 * 地址纬度设定
	 * @param latitude 地址纬度
	 */
	public void setLatitude(String latitude) {
	    this.latitude = latitude;
	}
}