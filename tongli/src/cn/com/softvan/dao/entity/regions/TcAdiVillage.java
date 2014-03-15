/*
 * 基础Entity类   行政区划_村
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.regions;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p> 行政区划_村 <p>
 * @author wangzi
 *
 */
public class TcAdiVillage extends BaseEntity {
	 /**
     * 县ID
     */
    private String village_id;

    /**
     * 县名称
     */
    private String village_name;

    /**
     * 序列号
     */
    private Integer sort_num;

    /**
     *地址经度
     */
    private String longitude;

    /**
     * 地址纬度
     */
    private String iatitude;

    /**
     * 国标代码
     */
    private String gb_code;

	/**
	 * 县ID取得
	 * @return 县ID
	 */
	public String getVillage_id() {
	    return village_id;
	}

	/**
	 * 县ID设定
	 * @param village_id 县ID
	 */
	public void setVillage_id(String village_id) {
	    this.village_id = village_id;
	}

	/**
	 * 县名称取得
	 * @return 县名称
	 */
	public String getVillage_name() {
	    return village_name;
	}

	/**
	 * 县名称设定
	 * @param village_name 县名称
	 */
	public void setVillage_name(String village_name) {
	    this.village_name = village_name;
	}

	/**
	 * 序列号取得
	 * @return 序列号
	 */
	public Integer getSort_num() {
	    return sort_num;
	}

	/**
	 * 序列号设定
	 * @param sort_num 序列号
	 */
	public void setSort_num(Integer sort_num) {
	    this.sort_num = sort_num;
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
	public String getIatitude() {
	    return iatitude;
	}

	/**
	 * 地址纬度设定
	 * @param iatitude 地址纬度
	 */
	public void setIatitude(String iatitude) {
	    this.iatitude = iatitude;
	}

	/**
	 * 国标代码取得
	 * @return 国标代码
	 */
	public String getGb_code() {
	    return gb_code;
	}

	/**
	 * 国标代码设定
	 * @param gb_code 国标代码
	 */
	public void setGb_code(String gb_code) {
	    this.gb_code = gb_code;
	}
}