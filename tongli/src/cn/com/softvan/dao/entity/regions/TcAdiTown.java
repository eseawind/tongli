/*
 * 基础Entity类   行政区划_镇
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
 * <p> 行政区划_镇 <p>
 * @author wangzi
 *
 */
public class TcAdiTown extends BaseEntity {
	 /**
     * 城镇ID
     */
    private String town_id;

    /**
     * 城镇名称
     */
    private String town_name;

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
    private String latitude;

    /**
     * 国标代码
     */
    private String gb_code;

	/**
	 * 城镇IDを取得します。
	 * @return 城镇ID
	 */
	public String getTown_id() {
	    return town_id;
	}

	/**
	 * 城镇IDを設定します。
	 * @param town_id 城镇ID
	 */
	public void setTown_id(String town_id) {
	    this.town_id = town_id;
	}

	/**
	 * 城镇名称を取得します。
	 * @return 城镇名称
	 */
	public String getTown_name() {
	    return town_name;
	}

	/**
	 * 城镇名称を設定します。
	 * @param town_name 城镇名称
	 */
	public void setTown_name(String town_name) {
	    this.town_name = town_name;
	}

	/**
	 * 序列号を取得します。
	 * @return 序列号
	 */
	public Integer getSort_num() {
	    return sort_num;
	}

	/**
	 * 序列号を設定します。
	 * @param sort_num 序列号
	 */
	public void setSort_num(Integer sort_num) {
	    this.sort_num = sort_num;
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
	 * 国标代码を取得します。
	 * @return 国标代码
	 */
	public String getGb_code() {
	    return gb_code;
	}

	/**
	 * 国标代码を設定します。
	 * @param gb_code 国标代码
	 */
	public void setGb_code(String gb_code) {
	    this.gb_code = gb_code;
	}

}