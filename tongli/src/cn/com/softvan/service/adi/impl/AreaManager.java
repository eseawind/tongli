/*
 * 资讯信息管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.25  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 jfq System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.adi.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.adi.AreaBean;
import cn.com.softvan.dao.daointer.adi.IAreaDao;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.adi.IAreaManager;
/**
 *<p>资讯信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class AreaManager extends BaseManager implements IAreaManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(AreaManager.class);
	
	/**信息DAO 接口类*/
	private IAreaDao areaDao;
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表 省。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListA(Map<String,String> map){
		List<AreaBean> beans=null;
		try {
				beans=areaDao.findDataIsListA(map);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表 市省。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListB(Map<String,String> map){
		List<AreaBean> beans=null;
		try {
				beans=areaDao.findDataIsListB(map);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表 县。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListC(Map<String,String> map){
		List<AreaBean> beans=null;
		try {
				beans=areaDao.findDataIsListC(map);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表 镇。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListD(Map<String,String> map){
		List<AreaBean> beans=null;
		try {
				beans=areaDao.findDataIsListD(map);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表 村。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<AreaBean> findDataIsListE(Map<String,String> map){
		List<AreaBean> beans=null;
		try {
				beans=areaDao.findDataIsListE(map);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public IAreaDao getAreaDao() {
	    return areaDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param areaDao 信息DAO 接口类
	 */
	public void setAreaDao(IAreaDao areaDao) {
	    this.areaDao = areaDao;
	}
	
}