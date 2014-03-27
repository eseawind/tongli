/*
 *  行政区划信息表 BEAN
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.26  wuxiaogang      程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 jfq  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.adi;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 行政区划信息表
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class AreaBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4777693662110182133L;
	/** id */
	private String id;
	/** 名称 */
	private String name;
	/**
	 * id取得
	 * @return id
	 */
	public String getId() {
	    return id;
	}
	/**
	 * id设定
	 * @param id id
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
}