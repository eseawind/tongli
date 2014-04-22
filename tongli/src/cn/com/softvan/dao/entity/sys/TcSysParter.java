/*
 * 基础Entity类  友情链接
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.26  wuxiaogang           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.sys;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 友情链接
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcSysParter extends BaseEntity {
	/** 编号 */
	private String id;
	/** 名称 */
	private String name;
	/** 链接地址 */
	private String url;
	/** 图片链接 */
	private String pic_url;
	/** 合作商类型0积分发行商1市场合作商 */
	private String type;
	/** 排序 */
	private String sort_num;
	/** 推荐置顶 */
	private String is_ontop;
	/** 是否展示 */
	private String is_show;
	/**
	 * 编号取得
	 * @return 编号
	 */
	public String getId() {
	    return id;
	}
	/**
	 * 编号设定
	 * @param id 编号
	 */
	public void setId(String id) {
	    this.id = id;
	}
	/**
	 * 名称取得
	 * @return 名称
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 名称设定
	 * @param name 名称
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 链接地址取得
	 * @return 链接地址
	 */
	public String getUrl() {
	    return url;
	}
	/**
	 * 链接地址设定
	 * @param url 链接地址
	 */
	public void setUrl(String url) {
	    this.url = url;
	}
	/**
	 * 图片链接取得
	 * @return 图片链接
	 */
	public String getPic_url() {
	    return pic_url;
	}
	/**
	 * 图片链接设定
	 * @param pic_url 图片链接
	 */
	public void setPic_url(String pic_url) {
	    this.pic_url = pic_url;
	}
	/**
	 * 合作商类型0积分发行商1市场合作商取得
	 * @return 合作商类型0积分发行商1市场合作商
	 */
	public String getType() {
	    return type;
	}
	/**
	 * 合作商类型0积分发行商1市场合作商设定
	 * @param type 合作商类型0积分发行商1市场合作商
	 */
	public void setType(String type) {
	    this.type = type;
	}
	/**
	 * 排序取得
	 * @return 排序
	 */
	public String getSort_num() {
	    return sort_num;
	}
	/**
	 * 排序设定
	 * @param sort_num 排序
	 */
	public void setSort_num(String sort_num) {
	    this.sort_num = sort_num;
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
	 * 是否展示取得
	 * @return 是否展示
	 */
	public String getIs_show() {
	    return is_show;
	}
	/**
	 * 是否展示设定
	 * @param is_show 是否展示
	 */
	public void setIs_show(String is_show) {
	    this.is_show = is_show;
	}
}