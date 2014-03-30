/*
 * 前台首页 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.30  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 jfq  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.course;

import org.apache.log4j.Logger;

import cn.com.softvan.web.action.BaseAction;

/**
 * 前台首页 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C001Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C001Action.class);
	
	public C001Action() {
		log.info("默认构造器......S001Action");
	}

	/**
	 * <p>
	 * 初始化处理。
	 * </p>
	 * <ol>
	 * [功能概要] <div>初始化处理。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String init() {
		log.info("S001Action init.........");
		return "init";
	}


}
