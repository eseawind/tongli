/*
 * 基础Entity类  第三方机构
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.carmanager;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 第三方机构
 * <p>
 * 
 * @author wangzi
 * 
 */
public class TcCtDamageAddrs extends BaseEntity {
	/** id **/
	private String id;
	/** 类型 **/
	private String type;
	/** 名称 **/
	private String name;
	/** 地址 **/
	private String addrs;
	/** 电话 **/
	private String tel;
	/** 传真 **/
	private String fox;
	/** 地址经度 **/
	private String longitude;
	/** 地址纬度 **/
	private String latitude;
	/** 行政区划_省级 **/
	private String provinceId;
	/** 行政区划_市级 **/
	private String cityId;
	/** 行政区划_县级 **/
	private String countyId;
	/** 行政区划_镇级 **/
	private String townId;
	/** 行政区划_村级 **/
	private String villageId;
	/** 查询条数 */
	private Integer searchCount;
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
	 * 类型 *を取得します。
	 * @return 类型 *
	 */
	public String getType() {
	    return type;
	}
	/**
	 * 类型 *を設定します。
	 * @param type 类型 *
	 */
	public void setType(String type) {
	    this.type = type;
	}
	/**
	 * 名称 *を取得します。
	 * @return 名称 *
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 名称 *を設定します。
	 * @param name 名称 *
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 地址 *を取得します。
	 * @return 地址 *
	 */
	public String getAddrs() {
	    return addrs;
	}
	/**
	 * 地址 *を設定します。
	 * @param addrs 地址 *
	 */
	public void setAddrs(String addrs) {
	    this.addrs = addrs;
	}
	/**
	 * 电话 *を取得します。
	 * @return 电话 *
	 */
	public String getTel() {
	    return tel;
	}
	/**
	 * 电话 *を設定します。
	 * @param tel 电话 *
	 */
	public void setTel(String tel) {
	    this.tel = tel;
	}
	/**
	 * 传真 *を取得します。
	 * @return 传真 *
	 */
	public String getFox() {
	    return fox;
	}
	/**
	 * 传真 *を設定します。
	 * @param fox 传真 *
	 */
	public void setFox(String fox) {
	    this.fox = fox;
	}
	/**
	 * 地址经度 *を取得します。
	 * @return 地址经度 *
	 */
	public String getLongitude() {
	    return longitude;
	}
	/**
	 * 地址经度 *を設定します。
	 * @param longitude 地址经度 *
	 */
	public void setLongitude(String longitude) {
	    this.longitude = longitude;
	}
	/**
	 * 地址纬度 *を取得します。
	 * @return 地址纬度 *
	 */
	public String getLatitude() {
	    return latitude;
	}
	/**
	 * 地址纬度 *を設定します。
	 * @param latitude 地址纬度 *
	 */
	public void setLatitude(String latitude) {
	    this.latitude = latitude;
	}
	/**
	 * 行政区划_省级 *を取得します。
	 * @return 行政区划_省级 *
	 */
	public String getProvinceId() {
		return provinceId;
	}
	/**
	 * 行政区划_省级 *を設定します。
	 * @param provinceId 行政区划_省级 *
	 */
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	/**
	 * 行政区划_市级 *を取得します。
	 * @return 行政区划_市级 *
	 */
	public String getCityId() {
		return cityId;
	}
	/**
	 * 行政区划_市级 *を設定します。
	 * @param cityId 行政区划_市级 *
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	/**
	 * 行政区划_县级 *を取得します。
	 * @return 行政区划_县级 *
	 */
	public String getCountyId() {
		return countyId;
	}
	/**
	 * 行政区划_县级 *を設定します。
	 * @param countyId 行政区划_县级 *
	 */
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	/**
	 * 行政区划_镇级 *を取得します。
	 * @return 行政区划_镇级 *
	 */
	public String getTownId() {
		return townId;
	}
	/**
	 * 行政区划_镇级 *を設定します。
	 * @param townId 行政区划_镇级 *
	 */
	public void setTownId(String townId) {
		this.townId = townId;
	}
	/**
	 * 行政区划_村级 *を取得します。
	 * @return 行政区划_村级 *
	 */
	public String getVillageId() {
		return villageId;
	}
	/**
	 * 行政区划_村级 *を設定します。
	 * @param villageId 行政区划_村级 *
	 */
	public void setVillageId(String villageId) {
		this.villageId = villageId;
	}
	/**
	 * 查询条数を取得します。
	 * @return 查询条数
	 */
	public Integer getSearchCount() {
	    return searchCount;
	}
	/**
	 * 查询条数を設定します。
	 * @param searchCount 查询条数
	 */
	public void setSearchCount(Integer searchCount) {
	    this.searchCount = searchCount;
	}
}