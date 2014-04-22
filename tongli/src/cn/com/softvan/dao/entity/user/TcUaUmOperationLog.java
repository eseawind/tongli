/*
 * 基础Entity类   操作日志表
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
 * <p> 操作日志表 <p>
 * @author wuxiaogang
 *
 */
public class TcUaUmOperationLog  extends BaseEntity{
    /**
     * 日序号志
     */
    private String id;

    /**
     * 操作类型
     */
    private String type;

    /**
     * 操作栏目
     */
    private String channel;

    /**
     * 操作描述
     */
    private String note;

	/**
	 * 日序号志取得
	 * @return 日序号志
	 */
	public String getId() {
	    return id;
	}

	/**
	 * 日序号志设定
	 * @param id 日序号志
	 */
	public void setId(String id) {
	    this.id = id;
	}

	/**
	 * 操作类型取得
	 * @return 操作类型
	 */
	public String getType() {
	    return type;
	}

	/**
	 * 操作类型设定
	 * @param type 操作类型
	 */
	public void setType(String type) {
	    this.type = type;
	}

	/**
	 * 操作栏目取得
	 * @return 操作栏目
	 */
	public String getChannel() {
	    return channel;
	}

	/**
	 * 操作栏目设定
	 * @param channel 操作栏目
	 */
	public void setChannel(String channel) {
	    this.channel = channel;
	}

	/**
	 * 操作描述取得
	 * @return 操作描述
	 */
	public String getNote() {
	    return note;
	}

	/**
	 * 操作描述设定
	 * @param note 操作描述
	 */
	public void setNote(String note) {
	    this.note = note;
	}

}