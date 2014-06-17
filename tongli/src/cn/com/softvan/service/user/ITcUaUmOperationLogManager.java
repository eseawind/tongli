/**
 * 操作日志记录  service接口类
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00     2014 下午5:09:01             wangzi              程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014-04-28 picc  System. - All Rights Reserved.
 */
package cn.com.softvan.service.user;


/**
 * <p> 操作日志记录 <p>
 * @author wangzi
 *
 */
public interface ITcUaUmOperationLogManager {
	/**
	 * 新增操作日志信息
	 * @param type   操作类型
	 * @param channel   操作栏目
	 * @param msg   描述
	 * @throws Exception 
	 */
	public void insertLogInfo(String type,String channel ,String msg);
}
