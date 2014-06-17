/*
 * 基础Entity类   权限表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.user;

import cn.com.softvan.dao.entity.BaseEntity;
/**
 * <p> 权限表 <p>
 * @author wangzi
 *
 */
public class TcUaUmPermission  extends BaseEntity {
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 角色
	 */
	private String role_id;
	
    /**
     * 受限url
     */
    private String url;

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
	 * 角色取得
	 * @return 角色
	 */
	public String getRole_id() {
	    return role_id;
	}

	/**
	 * 角色设定
	 * @param role_id 角色
	 */
	public void setRole_id(String role_id) {
	    this.role_id = role_id;
	}

	/**
	 * 受限url取得
	 * @return 受限url
	 */
	public String getUrl() {
	    return url;
	}

	/**
	 * 受限url设定
	 * @param url 受限url
	 */
	public void setUrl(String url) {
	    this.url = url;
	}


}