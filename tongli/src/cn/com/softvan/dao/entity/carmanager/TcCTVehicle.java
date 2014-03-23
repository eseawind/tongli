/*
 * 基础Entity类  车辆信息
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
 * 车辆信息
 * <p>
 * 
 * @author wangzi
 * 
 */
public class TcCTVehicle extends BaseEntity {
	/** 车辆id */
	private String vehicleId;
	/** 号牌种类 */
	private String platetype;
	/** 车牌号码 */
	private String plateno;
	/** 车架号 */
	private String vincode;
	/** 机动车所有人 */
	private String carowner;
	/** 发动机号 */
	private String engineNo;
	/** 车辆类型 */
	private String vehicletype;
	/** 核定载质量 */
	private String ratequality;
	/** 核定载客 */
	private String ratepassenger;
	/** 车辆状态 */
	private String vehiclestatus;
	/** 出厂日期 */
	private Date manufacturedate;
	/** 初次登记日期 */
	private Date firstresdate;
	/** 制造厂名称 */
	private String carmaker;
	/** 车辆品牌1 */
	private String platform1;
	/** 车辆品牌2 */
	private String platform2;
	/** 车辆型号 */
	private String vehiclemodel;
	/** 整备质量 */
	private String maintainquality;
	/** 排量 */
	private String displacement;
	/** 使用性质 */
	private String usetype;
	/** 处理标志 1：数据校验 2： 提交修改 3：车辆校验 */
	private String processFlag;
	/** 车主姓名 */
	private String carowner2;
	/** 号牌底色 */
	private String platenocolor;
	/**
	 * 车辆id取得
	 * @return 车辆id
	 */
	public String getVehicleId() {
	    return vehicleId;
	}
	/**
	 * 车辆id设定
	 * @param vehicleId 车辆id
	 */
	public void setVehicleId(String vehicleId) {
	    this.vehicleId = vehicleId;
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
	 * 机动车所有人取得
	 * @return 机动车所有人
	 */
	public String getCarowner() {
	    return carowner;
	}
	/**
	 * 机动车所有人设定
	 * @param carowner 机动车所有人
	 */
	public void setCarowner(String carowner) {
	    this.carowner = carowner;
	}
	/**
	 * 发动机号取得
	 * @return 发动机号
	 */
	public String getEngineNo() {
	    return engineNo;
	}
	/**
	 * 发动机号设定
	 * @param engineNo 发动机号
	 */
	public void setEngineNo(String engineNo) {
	    this.engineNo = engineNo;
	}
	/**
	 * 车辆类型取得
	 * @return 车辆类型
	 */
	public String getVehicletype() {
	    return vehicletype;
	}
	/**
	 * 车辆类型设定
	 * @param vehicletype 车辆类型
	 */
	public void setVehicletype(String vehicletype) {
	    this.vehicletype = vehicletype;
	}
	/**
	 * 核定载质量取得
	 * @return 核定载质量
	 */
	public String getRatequality() {
	    return ratequality;
	}
	/**
	 * 核定载质量设定
	 * @param ratequality 核定载质量
	 */
	public void setRatequality(String ratequality) {
	    this.ratequality = ratequality;
	}
	/**
	 * 核定载客取得
	 * @return 核定载客
	 */
	public String getRatepassenger() {
	    return ratepassenger;
	}
	/**
	 * 核定载客设定
	 * @param ratepassenger 核定载客
	 */
	public void setRatepassenger(String ratepassenger) {
	    this.ratepassenger = ratepassenger;
	}
	/**
	 * 车辆状态取得
	 * @return 车辆状态
	 */
	public String getVehiclestatus() {
	    return vehiclestatus;
	}
	/**
	 * 车辆状态设定
	 * @param vehiclestatus 车辆状态
	 */
	public void setVehiclestatus(String vehiclestatus) {
	    this.vehiclestatus = vehiclestatus;
	}
	/**
	 * 出厂日期取得
	 * @return 出厂日期
	 */
	public Date getManufacturedate() {
	    return manufacturedate;
	}
	/**
	 * 出厂日期设定
	 * @param manufacturedate 出厂日期
	 */
	public void setManufacturedate(Date manufacturedate) {
	    this.manufacturedate = manufacturedate;
	}
	/**
	 * 初次登记日期取得
	 * @return 初次登记日期
	 */
	public Date getFirstresdate() {
	    return firstresdate;
	}
	/**
	 * 初次登记日期设定
	 * @param firstresdate 初次登记日期
	 */
	public void setFirstresdate(Date firstresdate) {
	    this.firstresdate = firstresdate;
	}
	/**
	 * 制造厂名称取得
	 * @return 制造厂名称
	 */
	public String getCarmaker() {
	    return carmaker;
	}
	/**
	 * 制造厂名称设定
	 * @param carmaker 制造厂名称
	 */
	public void setCarmaker(String carmaker) {
	    this.carmaker = carmaker;
	}
	/**
	 * 车辆品牌1取得
	 * @return 车辆品牌1
	 */
	public String getPlatform1() {
	    return platform1;
	}
	/**
	 * 车辆品牌1设定
	 * @param platform1 车辆品牌1
	 */
	public void setPlatform1(String platform1) {
	    this.platform1 = platform1;
	}
	/**
	 * 车辆品牌2取得
	 * @return 车辆品牌2
	 */
	public String getPlatform2() {
	    return platform2;
	}
	/**
	 * 车辆品牌2设定
	 * @param platform2 车辆品牌2
	 */
	public void setPlatform2(String platform2) {
	    this.platform2 = platform2;
	}
	/**
	 * 车辆型号取得
	 * @return 车辆型号
	 */
	public String getVehiclemodel() {
	    return vehiclemodel;
	}
	/**
	 * 车辆型号设定
	 * @param vehiclemodel 车辆型号
	 */
	public void setVehiclemodel(String vehiclemodel) {
	    this.vehiclemodel = vehiclemodel;
	}
	/**
	 * 整备质量取得
	 * @return 整备质量
	 */
	public String getMaintainquality() {
	    return maintainquality;
	}
	/**
	 * 整备质量设定
	 * @param maintainquality 整备质量
	 */
	public void setMaintainquality(String maintainquality) {
	    this.maintainquality = maintainquality;
	}
	/**
	 * 排量取得
	 * @return 排量
	 */
	public String getDisplacement() {
	    return displacement;
	}
	/**
	 * 排量设定
	 * @param displacement 排量
	 */
	public void setDisplacement(String displacement) {
	    this.displacement = displacement;
	}
	/**
	 * 使用性质取得
	 * @return 使用性质
	 */
	public String getUsetype() {
	    return usetype;
	}
	/**
	 * 使用性质设定
	 * @param usetype 使用性质
	 */
	public void setUsetype(String usetype) {
	    this.usetype = usetype;
	}
	/**
	 * 处理标志 1：数据校验 2： 提交修改 3：车辆校验取得
	 * @return 处理标志 1：数据校验 2： 提交修改 3：车辆校验
	 */
	public String getProcessFlag() {
	    return processFlag;
	}
	/**
	 * 处理标志 1：数据校验 2： 提交修改 3：车辆校验设定
	 * @param processFlag 处理标志 1：数据校验 2： 提交修改 3：车辆校验
	 */
	public void setProcessFlag(String processFlag) {
	    this.processFlag = processFlag;
	}
	/**
	 * 车主姓名取得
	 * @return 车主姓名
	 */
	public String getCarowner2() {
	    return carowner2;
	}
	/**
	 * 车主姓名设定
	 * @param carowner2 车主姓名
	 */
	public void setCarowner2(String carowner2) {
	    this.carowner2 = carowner2;
	}
	/**
	 * 号牌底色取得
	 * @return 号牌底色
	 */
	public String getPlatenocolor() {
	    return platenocolor;
	}
	/**
	 * 号牌底色设定
	 * @param platenocolor 号牌底色
	 */
	public void setPlatenocolor(String platenocolor) {
	    this.platenocolor = platenocolor;
	}
}