/*
 * DAO类 咨询信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.04  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer.consult;

import cn.com.softvan.bean.consult.TcCsConsultBean;
import cn.com.softvan.dao.daointer.IBaseDao;
import cn.com.softvan.dao.entity.IEntity;
/**
 * <p>
 * 咨询 DAO
 * </p>
 * 
 * @author wuxiaogang
 * 
 */
public interface ITcCsConsultDao extends IBaseDao{
    /**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索 根据客服openid和最近修改时间查询咨询详情。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcCsConsultBean findDataByUserIdAndLastDate(IEntity dto);
}