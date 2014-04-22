/*
 * 基础Entity类   商品分类表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.eb;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 商品分类表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcEbProductType extends BaseEntity {

	/** 编号 */
	private String id;
	/** 序号 */
	private String sort_num;
	/** 分类代码 */
	private String code;
	/** 分类名称 */
	private String name;
	/** 父分类编号 */
	private String parent_id;
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
	 * 分类代码取得
	 * @return 分类代码
	 */
	public String getCode() {
	    return code;
	}
	/**
	 * 分类代码设定
	 * @param code 分类代码
	 */
	public void setCode(String code) {
	    this.code = code;
	}
	/**
	 * 分类名称取得
	 * @return 分类名称
	 */
	public String getName() {
	    return name;
	}
	/**
	 * 分类名称设定
	 * @param name 分类名称
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * 父分类编号取得
	 * @return 父分类编号
	 */
	public String getParent_id() {
	    return parent_id;
	}
	/**
	 * 父分类编号设定
	 * @param parent_id 父分类编号
	 */
	public void setParent_id(String parent_id) {
	    this.parent_id = parent_id;
	}

}