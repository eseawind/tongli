/*
 * 基础Entity类   权限组
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wuxiaogang           程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.user;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p>
 * 权限组
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcUaUmPermissionGroupRole extends BaseEntity {

	/**
	 * 权限组编号
	 */
	private String power_group_authorities_id;

	/**
	 * 角色编号
	 */
	private String base_role_id;

	/**
	 * 权限组编号取得
	 * @return 权限组编号
	 */
	public String getPower_group_authorities_id() {
	    return power_group_authorities_id;
	}

	/**
	 * 权限组编号设定
	 * @param power_group_authorities_id 权限组编号
	 */
	public void setPower_group_authorities_id(String power_group_authorities_id) {
	    this.power_group_authorities_id = power_group_authorities_id;
	}

	/**
	 * 角色编号取得
	 * @return 角色编号
	 */
	public String getBase_role_id() {
	    return base_role_id;
	}

	/**
	 * 角色编号设定
	 * @param base_role_id 角色编号
	 */
	public void setBase_role_id(String base_role_id) {
	    this.base_role_id = base_role_id;
	}


}