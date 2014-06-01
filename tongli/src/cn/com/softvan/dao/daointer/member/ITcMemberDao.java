/*
 * 接口Dao类  会员信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer.member;

import cn.com.softvan.bean.member.TcMemberBean;
import cn.com.softvan.dao.daointer.IBaseDao;
import cn.com.softvan.dao.entity.IEntity;
/**
 * <p> 会员信息 Dao类 <p>
 * @author wuxiaogang
 *
 */
public interface ITcMemberDao  extends IBaseDao{
	/**
	 * <p>登录验证。</p>
	 * <ol>[功能概要] 
	 * <div>信息验证。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcMemberBean checkMemberPWD(IEntity dto);
}