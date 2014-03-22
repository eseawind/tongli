/*
 * 基础Entity类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.03  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity;

import java.util.Date;

import cn.com.softvan.web.tag.PageInfo;

/**
 * <p>基础Entity类</p>
 * @author wuxiaogang
 *
 */
public class BaseEntity implements IEntity {
	/**默认构造器*/
	public BaseEntity(){}
	/** 开始时间 */
	private String date1;
	/** 结束时间 */
	private String date2;
	/** 分页对象 */
	private PageInfo pageInfo;
	/** 备注 */
	private String note;
	/** 数据输入日期 */
	private Date date_created;
	/** 建立者id */
	private String create_id;
	/** 建立者ip */
	private String create_ip;
	/** 资料更新日期 */
	private Date last_updated;
	/** 修改者ip */
	private String update_ip;
	/** 修改者id */
	private String update_id;
	/** 查询关键字 */
	private String keyword;
	/** VERSION */
	private Integer version;
	/** 数据过期时间0:永不过期 **/
	private Long invalid_time;
	/** 0否1是 **/
	private String del_flag;
	/**
	 * 开始时间取得。
	 * @return 开始时间
	 */
	public String getDate1() {
	    return date1;
	}
	/**
	 * 开始时间设定。
	 * @param date1 开始时间
	 */
	public void setDate1(String date1) {
	    this.date1 = date1;
	}
	/**
	 * 结束时间取得。
	 * @return 结束时间
	 */
	public String getDate2() {
	    return date2;
	}
	/**
	 * 结束时间设定。
	 * @param date2 结束时间
	 */
	public void setDate2(String date2) {
	    this.date2 = date2;
	}
	/**
	 * 分页对象取得。
	 * @return 分页对象
	 */
	public PageInfo getPageInfo() {
	    return pageInfo;
	}
	/**
	 * 分页对象设定。
	 * @param pageInfo 分页对象
	 */
	public void setPageInfo(PageInfo pageInfo) {
	    this.pageInfo = pageInfo;
	}
	/**
	 * 备注取得。
	 * @return 备注
	 */
	public String getNote() {
	    return note;
	}
	/**
	 * 备注设定。
	 * @param note 备注
	 */
	public void setNote(String note) {
	    this.note = note;
	}
	/**
	 * 数据输入日期取得。
	 * @return 数据输入日期
	 */
	public Date getDate_created() {
	    return date_created;
	}
	/**
	 * 数据输入日期设定。
	 * @param date_created 数据输入日期
	 */
	public void setDate_created(Date date_created) {
	    this.date_created = date_created;
	}
	/**
	 * 建立者id取得。
	 * @return 建立者id
	 */
	public String getCreate_id() {
	    return create_id;
	}
	/**
	 * 建立者id设定。
	 * @param create_id 建立者id
	 */
	public void setCreate_id(String create_id) {
	    this.create_id = create_id;
	}
	/**
	 * 建立者ip取得。
	 * @return 建立者ip
	 */
	public String getCreate_ip() {
	    return create_ip;
	}
	/**
	 * 建立者ip设定。
	 * @param create_ip 建立者ip
	 */
	public void setCreate_ip(String create_ip) {
	    this.create_ip = create_ip;
	}
	/**
	 * 资料更新日期取得。
	 * @return 资料更新日期
	 */
	public Date getLast_updated() {
	    return last_updated;
	}
	/**
	 * 资料更新日期设定。
	 * @param last_updated 资料更新日期
	 */
	public void setLast_updated(Date last_updated) {
	    this.last_updated = last_updated;
	}
	/**
	 * 修改者ip取得。
	 * @return 修改者ip
	 */
	public String getUpdate_ip() {
	    return update_ip;
	}
	/**
	 * 修改者ip设定。
	 * @param update_ip 修改者ip
	 */
	public void setUpdate_ip(String update_ip) {
	    this.update_ip = update_ip;
	}
	/**
	 * 修改者id取得。
	 * @return 修改者id
	 */
	public String getUpdate_id() {
	    return update_id;
	}
	/**
	 * 修改者id设定。
	 * @param update_id 修改者id
	 */
	public void setUpdate_id(String update_id) {
	    this.update_id = update_id;
	}
	/**
	 * 查询关键字取得。
	 * @return 查询关键字
	 */
	public String getKeyword() {
	    return keyword;
	}
	/**
	 * 查询关键字设定。
	 * @param keyword 查询关键字
	 */
	public void setKeyword(String keyword) {
	    this.keyword = keyword;
	}
	/**
	 * VERSION取得。
	 * @return VERSION
	 */
	public Integer getVersion() {
	    return version;
	}
	/**
	 * VERSION设定。
	 * @param version VERSION
	 */
	public void setVersion(Integer version) {
	    this.version = version;
	}
	/**
	 * 数据过期时间0:永不过期 *取得。
	 * @return 数据过期时间0:永不过期 *
	 */
	public Long getInvalid_time() {
	    return invalid_time;
	}
	/**
	 * 数据过期时间0:永不过期 *设定。
	 * @param invalid_time 数据过期时间0:永不过期 *
	 */
	public void setInvalid_time(Long invalid_time) {
	    this.invalid_time = invalid_time;
	}
	/**
	 * 0否1是 *取得。
	 * @return 0否1是 *
	 */
	public String getDel_flag() {
	    return del_flag;
	}
	/**
	 * 0否1是 *设定。
	 * @param del_flag 0否1是 *
	 */
	public void setDel_flag(String del_flag) {
	    this.del_flag = del_flag;
	}
}
