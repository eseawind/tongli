/*
 * 资讯信息 分类表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.25  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.sys;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 资讯信息 分类表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcSysNewsType extends BaseEntity {
	/** ID */
	private String id;
	/** 序号 */
	private String sort_num;
	/** 名称 */
	private String name;
	/** 内容 */
	private String detail_info;
	/** 父级分类 */
	private String parent_id;
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
	 * 父级分类取得
	 * @return 父级分类
	 */
	public String getParent_id() {
	    return parent_id;
	}
	/**
	 * 父级分类设定
	 * @param parent_id 父级分类
	 */
	public void setParent_id(String parent_id) {
	    this.parent_id = parent_id;
	}
}