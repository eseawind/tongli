/*
 * 接口Dao类  资讯信息 分类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.25  wuxiaogang      程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer.sys;

import java.util.List;

import cn.com.softvan.bean.sys.TcSysNewsTypeBean;
import cn.com.softvan.dao.daointer.IBaseDao;
import cn.com.softvan.dao.entity.IEntity;
/**
 * <p> 资讯信息 分类 Dao类 <p>
 * @author wuxiaogang
 *
 */
public interface ITcSysNewsTypeDao extends IBaseDao{
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>树。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysNewsTypeBean> findDataIsTree(IEntity dto);
}