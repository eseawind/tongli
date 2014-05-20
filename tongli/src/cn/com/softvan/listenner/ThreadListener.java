/*
 * 线程监听类 (用于监听线程的启动情况)
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2012.09.11  WuXiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2012 softVan System. - All Rights Reserved.
 *
 */
package cn.com.softvan.listenner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.com.softvan.thread.SmsAutoSendBatch;

public class ThreadListener implements ServletContextListener {
	private static ExecutorService exec = Executors.newCachedThreadPool();

	// 线程池
	// 关闭tomcat前关闭线程
	public void contextDestroyed(ServletContextEvent arg0) {
		exec.shutdownNow();
	}

	// 开启tomcat前执行线程
	public void contextInitialized(ServletContextEvent arg0) {
		// 定时从数据库取得短消息，执行发送动作。
		exec.execute(smsAutoSendBatch);
	}

	
	/**
	 * 定时从数据库取得短消息，执行发送动作。
	 */
	private static final SmsAutoSendBatch smsAutoSendBatch=new SmsAutoSendBatch();
	
}