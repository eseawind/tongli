/*
 * 基础Entity类  微信用户分组信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity.wechat;

import cn.com.softvan.dao.entity.BaseEntity;

/**
 * <p> 微信用户分组信息 <p>
 * @author wangzi
 *
 */
public class TcWxUserGroups extends BaseEntity {
    /**
     * id
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 计数
     */
    private String count;

    /**
	 * idを取得します。
	 * @return id
	 */
	public String getId() {
	    return id;
	}

	/**
	 * idを設定します。
	 * @param id id
	 */
	public void setId(String id) {
	    this.id = id;
	}

	/**
	 * 名称を取得します。
	 * @return 名称
	 */
	public String getName() {
	    return name;
	}

	/**
	 * 名称を設定します。
	 * @param name 名称
	 */
	public void setName(String name) {
	    this.name = name;
	}

	/**
	 * 计数を取得します。
	 * @return 计数
	 */
	public String getCount() {
	    return count;
	}

	/**
	 * 计数を設定します。
	 * @param count 计数
	 */
	public void setCount(String count) {
	    this.count = count;
	}
}