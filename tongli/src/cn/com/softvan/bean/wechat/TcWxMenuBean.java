/*
 * BEAN类  微信自定义菜单
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.wechat;

import java.util.List;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 微信自定义菜单
 * <p>
 * 
 * @author wangzi
 * 
 */
public class TcWxMenuBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = -354130443793365254L;
	/** id */
	private String id;
	/** 菜单KEY值，用于消息接口推送，不超过128字节 */
	private String menu_key;
	/** 菜单的响应动作类型，目前有click、view两种类型 */
	private String menu_type;
	/** 菜单标题，不超过16个字节，子菜单不超过40个字节 */
	private String menu_name;
	/** 网页链接，用户点击菜单可打开链接，不超过256字节 */
	private String menu_url;
	/** 备注 */
	private String note;
	/** 父id */
	private String parent_id;
	/** 序号 */
	private Integer sort_num;
	/**消息来源0,微信菜单,1,网站菜单*/
	private String info_source;
	/** 菜单列表 */
	private List<TcWxMenuBean> beans;
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
	 * 菜单KEY值，用于消息接口推送，不超过128字节取得
	 * @return 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	public String getMenu_key() {
	    return menu_key;
	}
	/**
	 * 菜单KEY值，用于消息接口推送，不超过128字节设定
	 * @param menu_key 菜单KEY值，用于消息接口推送，不超过128字节
	 */
	public void setMenu_key(String menu_key) {
	    this.menu_key = menu_key;
	}
	/**
	 * 菜单的响应动作类型，目前有click、view两种类型取得
	 * @return 菜单的响应动作类型，目前有click、view两种类型
	 */
	public String getMenu_type() {
	    return menu_type;
	}
	/**
	 * 菜单的响应动作类型，目前有click、view两种类型设定
	 * @param menu_type 菜单的响应动作类型，目前有click、view两种类型
	 */
	public void setMenu_type(String menu_type) {
	    this.menu_type = menu_type;
	}
	/**
	 * 菜单标题，不超过16个字节，子菜单不超过40个字节取得
	 * @return 菜单标题，不超过16个字节，子菜单不超过40个字节
	 */
	public String getMenu_name() {
	    return menu_name;
	}
	/**
	 * 菜单标题，不超过16个字节，子菜单不超过40个字节设定
	 * @param menu_name 菜单标题，不超过16个字节，子菜单不超过40个字节
	 */
	public void setMenu_name(String menu_name) {
	    this.menu_name = menu_name;
	}
	/**
	 * 网页链接，用户点击菜单可打开链接，不超过256字节取得
	 * @return 网页链接，用户点击菜单可打开链接，不超过256字节
	 */
	public String getMenu_url() {
	    return menu_url;
	}
	/**
	 * 网页链接，用户点击菜单可打开链接，不超过256字节设定
	 * @param menu_url 网页链接，用户点击菜单可打开链接，不超过256字节
	 */
	public void setMenu_url(String menu_url) {
	    this.menu_url = menu_url;
	}
	/**
	 * 备注取得
	 * @return 备注
	 */
	public String getNote() {
	    return note;
	}
	/**
	 * 备注设定
	 * @param note 备注
	 */
	public void setNote(String note) {
	    this.note = note;
	}
	/**
	 * 父id取得
	 * @return 父id
	 */
	public String getParent_id() {
	    return parent_id;
	}
	/**
	 * 父id设定
	 * @param parent_id 父id
	 */
	public void setParent_id(String parent_id) {
	    this.parent_id = parent_id;
	}
	/**
	 * 序号取得
	 * @return 序号
	 */
	public Integer getSort_num() {
	    return sort_num;
	}
	/**
	 * 序号设定
	 * @param sort_num 序号
	 */
	public void setSort_num(Integer sort_num) {
	    this.sort_num = sort_num;
	}
	/**
	 * 消息来源0,微信菜单,1,网站菜单取得
	 * @return 消息来源0,微信菜单,1,网站菜单
	 */
	public String getInfo_source() {
	    return info_source;
	}
	/**
	 * 消息来源0,微信菜单,1,网站菜单设定
	 * @param info_source 消息来源0,微信菜单,1,网站菜单
	 */
	public void setInfo_source(String info_source) {
	    this.info_source = info_source;
	}
	/**
	 * 菜单列表取得
	 * @return 菜单列表
	 */
	public List<TcWxMenuBean> getBeans() {
	    return beans;
	}
	/**
	 * 菜单列表设定
	 * @param beans 菜单列表
	 */
	public void setBeans(List<TcWxMenuBean> beans) {
	    this.beans = beans;
	}
}