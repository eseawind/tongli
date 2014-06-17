/**
 * 操作日志记录  service实现类
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00     2014 下午5:11:32             wangzi              程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 picc  System. - All Rights Reserved.
 */
package cn.com.softvan.service.user.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.com.softvan.common.IdUtils;
import cn.com.softvan.dao.daointer.user.ITcUaUmOperationLogDao;
import cn.com.softvan.dao.entity.user.TcUaUmOperationLog;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.user.ITcUaUmOperationLogManager;

/**
 * <p> 操作日志记录 service实现层 <p>
 * @author wangzi
 *
 */
public class TcUaUmOperationLogManager  extends BaseManager implements ITcUaUmOperationLogManager {

	private static final transient Logger log = Logger.getLogger(TcUaUmOperationLogManager.class);
	/**
	 * 操作日志信息 dao层
	 */
	private ITcUaUmOperationLogDao tcUaUmOperationLogDao;
	
	/**
	 * 操作日志信息 dao层を取得します。
	 * @return 操作日志信息 dao层
	 */
	public ITcUaUmOperationLogDao getTcUaUmOperationLogDao() {
	    return tcUaUmOperationLogDao;
	}
	/**
	 * 操作日志信息 dao层を設定します。
	 * @param tcUaUmOperationLogDao 操作日志信息 dao层
	 */
	public void setTcUaUmOperationLogDao(ITcUaUmOperationLogDao tcUaUmOperationLogDao) {
	    this.tcUaUmOperationLogDao = tcUaUmOperationLogDao;
	}
	/**
	 * 新增
	 * @throws Exception 
	 */
	public void insertLogInfo(String type,String channel ,String msg){
		
		if(StringUtils.isNotBlank(msg)){
			try {
				TcUaUmOperationLog bean = new TcUaUmOperationLog();
				bean.setId(IdUtils.createUUID(32));
				bean.setType(type);
				bean.setChannel(channel);
				bean.setNote(msg);
				//初始化数据
				bean.init();
				//新增
				tcUaUmOperationLogDao.insert(bean);
				
			} catch (Exception e) {
				log.error("日志表新增失败...");
				e.printStackTrace();
			}
			
		}
	}
}
