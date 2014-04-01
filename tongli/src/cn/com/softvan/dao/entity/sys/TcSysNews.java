/*
 * 资讯信息表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.18  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.sys;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 资讯信息表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcSysNews extends BaseEntity {
	/** ID */
	private String id;
	/** 序号 */
	private String sort_num;
	/** 标题 */
	private String title;
	/** 副标题 */
	private String second_title;
	/** 摘要 */
	private String brief_info;
	/** 内容 */
	private String detail_info;
	/** 推荐置顶 */
	private String is_ontop;
	/** 点击量 */
	private String click_count;
	/** 消息类型 */
	private String msgtype;
	/** type_id */
	private String type_id;
	/** 标题图url */
	private String pic_url;
	/** 消息来源0:微信,1:网站 */
	private String info_source;
	/** limit 起 */
	private Integer limit_s;
	/** limit 止 */
	private Integer limit_e;
	/**
	 * ID取得
	 * @return ID
	 */
	public String getId() {
	    return id;
	}
	/**
	 * ID设定
	 * @param id ID
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 序号取得
	 * @return 序号
	 */
	public String getSort_num() {
	    return sort_num;
	}
	/**
	 * 序号设定
	 * @param sort_num 序号
	 */
	public void setSort_num(String sort_num) {
	    this.sort_num = sort_num;
	}
	/**
	 * 标题取得
	 * @return 标题
	 */
	public String getTitle() {
	    return title;
	}
	/**
	 * 标题设定
	 * @param title 标题
	 */
	public void setTitle(String title) {
	    this.title = title;
	}
	/**
	 * 副标题取得
	 * @return 副标题
	 */
	public String getSecond_title() {
	    return second_title;
	}
	/**
	 * 副标题设定
	 * @param second_title 副标题
	 */
	public void setSecond_title(String second_title) {
	    this.second_title = second_title;
	}
	/**
	 * 摘要取得
	 * @return 摘要
	 */
	public String getBrief_info() {
	    return brief_info;
	}
	/**
	 * 摘要设定
	 * @param brief_info 摘要
	 */
	public void setBrief_info(String brief_info) {
	    this.brief_info = brief_info;
	}
	/**
	 * 内容取得
	 * @return 内容
	 */
	public String getDetail_info() {
	    return detail_info;
	}
	/**
	 * 内容设定
	 * @param detail_info 内容
	 */
	public void setDetail_info(String detail_info) {
	    this.detail_info = detail_info;
	}
	/**
	 * 推荐置顶取得
	 * @return 推荐置顶
	 */
	public String getIs_ontop() {
	    return is_ontop;
	}
	/**
	 * 推荐置顶设定
	 * @param is_ontop 推荐置顶
	 */
	public void setIs_ontop(String is_ontop) {
	    this.is_ontop = is_ontop;
	}
	/**
	 * 点击量取得
	 * @return 点击量
	 */
	public String getClick_count() {
	    return click_count;
	}
	/**
	 * 点击量设定
	 * @param click_count 点击量
	 */
	public void setClick_count(String click_count) {
	    this.click_count = click_count;
	}
	/**
	 * 消息类型取得
	 * @return 消息类型
	 */
	public String getMsgtype() {
	    return msgtype;
	}
	/**
	 * 消息类型设定
	 * @param msgtype 消息类型
	 */
	public void setMsgtype(String msgtype) {
	    this.msgtype = msgtype;
	}
	/**
	 * type_id取得
	 * @return type_id
	 */
	public String getType_id() {
	    return type_id;
	}
	/**
	 * type_id设定
	 * @param type_id type_id
	 */
	public void setType_id(String type_id) {
	    this.type_id = type_id;
	}
	/**
	 * 标题图url取得
	 * @return 标题图url
	 */
	public String getPic_url() {
	    return pic_url;
	}
	/**
	 * 标题图url设定
	 * @param pic_url 标题图url
	 */
	public void setPic_url(String pic_url) {
	    this.pic_url = pic_url;
	}
	/**
	 * 消息来源0:微信,1:网站取得
	 * @return 消息来源0:微信,1:网站
	 */
	public String getInfo_source() {
	    return info_source;
	}
	/**
	 * 消息来源0:微信,1:网站设定
	 * @param info_source 消息来源0:微信,1:网站
	 */
	public void setInfo_source(String info_source) {
	    this.info_source = info_source;
	}
	/**
	 * limit 起取得
	 * @return limit 起
	 */
	public Integer getLimit_s() {
	    return limit_s;
	}
	/**
	 * limit 起设定
	 * @param limit_s limit 起
	 */
	public void setLimit_s(Integer limit_s) {
	    this.limit_s = limit_s;
	}
	/**
	 * limit 止取得
	 * @return limit 止
	 */
	public Integer getLimit_e() {
	    return limit_e;
	}
	/**
	 * limit 止设定
	 * @param limit_e limit 止
	 */
	public void setLimit_e(Integer limit_e) {
	    this.limit_e = limit_e;
	}
}