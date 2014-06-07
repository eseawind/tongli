/*
 * 基础Manager类接口
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2012.04.17  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service;

import java.util.Observable;

import org.springframework.core.task.TaskExecutor;

import cn.com.softvan.common.JedisHelper;
import cn.com.softvan.service.sys.IVariableManager;

/**
 * 基础Manager类接口
 * @author {wuxiaogang}
 *
 */
public abstract class BaseManager extends Observable {
	/** 管理员操作日志  service类 */
	protected IUserLogsManager userLogsManager;
	/**线程池 */
	protected TaskExecutor taskExecutor;
	/**redis缓存工具类*/
	protected JedisHelper jedisHelper;
	/** 数据字典管理 service  业务处理 */
	protected IVariableManager variableManager;
	/**
	 * 管理员操作日志  service类取得
	 * @return 管理员操作日志  service类
	 */
	public IUserLogsManager getUserLogsManager() {
	    return userLogsManager;
	}

	/**
	 * 管理员操作日志  service类设定
	 * @param userLogsManager 管理员操作日志  service类
	 */
	public void setUserLogsManager(IUserLogsManager userLogsManager) {
	    this.userLogsManager = userLogsManager;
	}

	/**
	 * 线程池取得
	 * @return 线程池
	 */
	public TaskExecutor getTaskExecutor() {
	    return taskExecutor;
	}

	/**
	 * 线程池设定
	 * @param taskExecutor 线程池
	 */
	public void setTaskExecutor(TaskExecutor taskExecutor) {
	    this.taskExecutor = taskExecutor;
	}

	/**
	 * redis缓存工具类取得
	 * @return redis缓存工具类
	 */
	public JedisHelper getJedisHelper() {
	    return jedisHelper;
	}

	/**
	 * redis缓存工具类设定
	 * @param jedisHelper redis缓存工具类
	 */
	public void setJedisHelper(JedisHelper jedisHelper) {
	    this.jedisHelper = jedisHelper;
	}

	/**
	 * 数据字典管理 service  业务处理取得
	 * @return 数据字典管理 service  业务处理
	 */
	public IVariableManager getVariableManager() {
	    return variableManager;
	}

	/**
	 * 数据字典管理 service  业务处理设定
	 * @param variableManager 数据字典管理 service  业务处理
	 */
	public void setVariableManager(IVariableManager variableManager) {
	    this.variableManager = variableManager;
	}
}
