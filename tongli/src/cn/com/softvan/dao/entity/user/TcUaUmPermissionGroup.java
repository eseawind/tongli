/*
 * 基础Entity类   权限组
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
 * <p> 权限组 <p>
 * @author wangzi
 *
 */
public class TcUaUmPermissionGroup extends BaseEntity {
    /**
     * 权限组编号
     */
    private String id;

    /**
     * 权限组名
     */
    private String group_name;

    /**
     * 权限组描述
     */
    private String group_desc;

	/**
	 * 权限组编号取得
	 * @return 权限组编号
	 */
	public String getId() {
	    return id;
	}

	/**
	 * 权限组编号设定
	 * @param id 权限组编号
	 */
	public void setId(String id) {
	    this.id = id;
	}

	/**
	 * 权限组名取得
	 * @return 权限组名
	 */
	public String getGroup_name() {
	    return group_name;
	}

	/**
	 * 权限组名设定
	 * @param group_name 权限组名
	 */
	public void setGroup_name(String group_name) {
	    this.group_name = group_name;
	}

	/**
	 * 权限组描述取得
	 * @return 权限组描述
	 */
	public String getGroup_desc() {
	    return group_desc;
	}

	/**
	 * 权限组描述设定
	 * @param group_desc 权限组描述
	 */
	public void setGroup_desc(String group_desc) {
	    this.group_desc = group_desc;
	}


    
}