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
package cn.com.softvan.dao.daointer.customerservice;

import cn.com.softvan.bean.customerservice.TcCsCustomerServiceBean;
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
public interface ITcCsCustomerServiceDao  extends IBaseDao{
    /**
	 * <p>密码认证。</p>
	 * <ol>[功能概要] 
	 * <div>密码认证。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcCsCustomerServiceBean checkPWD(IEntity dto);
}