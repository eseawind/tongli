/*
 * 资讯信息 分类表 关联表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.25  wuxiaogang      程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.sys;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 资讯信息 分类表  关联表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcSysNewsVsType extends BaseEntity {
	/** new_id */
	private String new_id;
	/** type_id */
	private String type_id;
	/**
	 * new_id取得
	 * @return new_id
	 */
	public String getNew_id() {
	    return new_id;
	}
	/**
	 * new_id设定
	 * @param new_id new_id
	 */
	public void setNew_id(String new_id) {
	    this.new_id = new_id;
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
}