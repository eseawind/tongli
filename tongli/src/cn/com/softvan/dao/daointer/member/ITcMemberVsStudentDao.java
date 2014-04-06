/*
 * 接口Dao类 会员与学员关联表信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer.member;

import cn.com.softvan.dao.entity.IEntity;
/**
 * <p> 会员与学员关联表信息 Dao类 <p>
 * @author wuxiaogang
 *
 */
public interface ITcMemberVsStudentDao {
    /**
     * 根据主键 物理删除
     */
    int deleteByPrimaryKey(IEntity dto);

    /**
     * 新增
     */
    int insert(IEntity dto);
}