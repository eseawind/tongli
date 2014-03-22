/*
 * 基础Entity类   行政区划_市
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
 * <p> 行政区划_市 <p>
 * @author wangzi
 *
 */
public class TcAdiCity extends BaseEntity {
    /**
     * 城市ID
     */
    private String city_id;

    /**
     * 城市名称
     */
    private String city_name;

    /**
     *序列号
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
     * VERSION
     */
    private Integer version;

    /**
     * 数据过期时间0永不过期
     */
    private Long invalid_time;

    /**
     * 0否1是
     */
    private String del_flag;

	/**
	 * 城市IDを取得します。
	 * @return 城市ID
	 */
	public String getCity_id() {
	    return city_id;
	}

	/**
	 * 城市IDを設定します。
	 * @param city_id 城市ID
	 */
	public void setCity_id(String city_id) {
	    this.city_id = city_id;
	}

	/**
	 * 城市名称を取得します。
	 * @return 城市名称
	 */
	public String getCity_name() {
	    return city_name;
	}

	/**
	 * 城市名称を設定します。
	 * @param city_name 城市名称
	 */
	public void setCity_name(String city_name) {
	    this.city_name = city_name;
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

	/**
	 * VERSIONを取得します。
	 * @return VERSION
	 */
	public Integer getVersion() {
	    return version;
	}

	/**
	 * VERSIONを設定します。
	 * @param version VERSION
	 */
	public void setVersion(Integer version) {
	    this.version = version;
	}

	/**
	 * 数据过期时间0永不过期を取得します。
	 * @return 数据过期时间0永不过期
	 */
	public Long getInvalid_time() {
	    return invalid_time;
	}

	/**
	 * 数据过期时间0永不过期を設定します。
	 * @param invalid_time 数据过期时间0永不过期
	 */
	public void setInvalid_time(Long invalid_time) {
	    this.invalid_time = invalid_time;
	}

	/**
	 * 0否1是を取得します。
	 * @return 0否1是
	 */
	public String getDel_flag() {
	    return del_flag;
	}

	/**
	 * 0否1是を設定します。
	 * @param del_flag 0否1是
	 */
	public void setDel_flag(String del_flag) {
	    this.del_flag = del_flag;
	}

   
}