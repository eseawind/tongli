/*
 * 基础Entity类   角色
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
 * <p> 角色 <p>
 * @author wangzi
 *
 */
public class TcUaUmBaseRole extends BaseEntity {
    /**
     * 角色编号
     */
    private String id;

    /**
     * 角色名称
     */
    private String authority;

    /**
     * 角色描述
     */
    private String description;

	/**
	 * 角色编号取得
	 * @return 角色编号
	 */
	public String getId() {
	    return id;
	}

	/**
	 * 角色编号设定
	 * @param id 角色编号
	 */
	public void setId(String id) {
	    this.id = id;
	}

	/**
	 * 角色名称取得
	 * @return 角色名称
	 */
	public String getAuthority() {
	    return authority;
	}

	/**
	 * 角色名称设定
	 * @param authority 角色名称
	 */
	public void setAuthority(String authority) {
	    this.authority = authority;
	}

	/**
	 * 角色描述取得
	 * @return 角色描述
	 */
	public String getDescription() {
	    return description;
	}

	/**
	 * 角色描述设定
	 * @param description 角色描述
	 */
	public void setDescription(String description) {
	    this.description = description;
	}
   
}