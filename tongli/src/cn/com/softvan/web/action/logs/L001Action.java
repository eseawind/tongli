/*
 * 日志管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.03.10  wuxiaogang           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.logs;

import org.apache.log4j.Logger;

import cn.com.softvan.service.IUserLogsManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 日志管理 ActionClass
 * @author wuxiaogang
 *
 */
public class L001Action extends BaseAction{

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger
			.getLogger(L001Action.class);
	
	public L001Action(){
		log.info("日志管理默认构造器......LogsAction");
	}
	/**用户操作日志 业务处理*/
	private IUserLogsManager userLogsManager;
	/**
	 * 初始页面
	 * @return
	 */
	public String init(){
		log.info("L001 init.........");
		return "init";
	}
	/**
	 * 用户操作日志 业务处理取得
	 * @return 用户操作日志 业务处理
	 */
	public IUserLogsManager getUserLogsManager() {
	    return userLogsManager;
	}
	/**
	 * 用户操作日志 业务处理设定
	 * @param userLogsManager 用户操作日志 业务处理
	 */
	public void setUserLogsManager(IUserLogsManager userLogsManager) {
	    this.userLogsManager = userLogsManager;
	}
	
}
