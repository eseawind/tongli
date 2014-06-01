/*
 * 接口Dao类  数据字典
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.21  wuxiaogang           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer.sys;

import java.util.List;

import cn.com.softvan.bean.sys.TcSysVariableBean;
import cn.com.softvan.dao.daointer.IBaseDao;
import cn.com.softvan.dao.entity.IEntity;
/**
 * <p> 数据字典 <p>
 * @author wangzi
 *
 */
public interface ITcSysVariableDao extends IBaseDao{
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysVariableBean> findDataIsListTop(IEntity dto);
}