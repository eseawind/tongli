/*
 * 基础Entity类  车辆定损明细
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

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * 
 * <p>
 * 车辆定损明细
 * <p>
 * 
 * @author wangzi
 * 
 */
public class TcCtCarDamage extends BaseEntity {
	/** id **/
	private String id;
	/** 用户id **/
	private String userid;
	/** 车辆id **/
	private String vehicleid;
	/** 报案号 **/
	private String reportno;
	/** 案件号 **/
	private String caseno;
	/** 设备名称 **/
	private String name;
	/** 数量 **/
	private Integer num;
	/** 单价 **/
	private BigDecimal price;
	/** 合计 **/
	private BigDecimal total;
	/**
	 * id *を取得します。
	 * @return id *
	 */
	public String getId() {
	    return id;
	}
	/**
	 * id *を設定します。
	 * @param id id *
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 用户id *を取得します。
	 * @return 用户id *
	 */
	public String getUserid() {
	    return userid;
	}
	/**
	 * 用户id *を設定します。
	 * @param userid 用户id *
	 */
	public void setUserid(String userid) {
	    this.userid = userid;
	}
	/**
	 * 车辆id *を取得します。
	 * @return 车辆id *
	 */
	public String getVehicleid() {
	    return vehicleid;
	}
	/**
	 * 车辆id *を設定します。
	 * @param vehicleid 车辆id *
	 */
	public void setVehicleid(String vehicleid) {
	    this.vehicleid = vehicleid;
	}
	/**
	 * 报案号 *を取得します。
	 * @return 报案号 *
	 */
	public String getReportno() {
	    return reportno;
	}
	/**
	 * 报案号 *を設定します。
	 * @param reportno 报案号 *
	 */
	public void setReportno(String reportno) {
	    this.reportno = reportno;
	}
	/**
	 * 案件号 *を取得します。
	 * @return 案件号 *
	 */
	public String getCaseno() {
	    return caseno;
	}
	/**
	 * 案件号 *を設定します。
	 * @param caseno 案件号 *
	 */
	public void setCaseno(String caseno) {
	    this.caseno = caseno;
	}
	/**
	 * 设备名称 *を取得します。
	 * @return 设备名称 *
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 设备名称 *を設定します。
	 * @param name 设备名称 *
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 数量 *を取得します。
	 * @return 数量 *
	 */
	public Integer getNum() {
	    return num;
	}
	/**
	 * 数量 *を設定します。
	 * @param num 数量 *
	 */
	public void setNum(Integer num) {
	    this.num = num;
	}
	/**
	 * 单价 *を取得します。
	 * @return 单价 *
	 */
	public BigDecimal getPrice() {
	    return price;
	}
	/**
	 * 单价 *を設定します。
	 * @param price 单价 *
	 */
	public void setPrice(BigDecimal price) {
	    this.price = price;
	}
	/**
	 * 合计 *を取得します。
	 * @return 合计 *
	 */
	public BigDecimal getTotal() {
	    return total;
	}
	/**
	 * 合计 *を設定します。
	 * @param total 合计 *
	 */
	public void setTotal(BigDecimal total) {
	    this.total = total;
	}
}