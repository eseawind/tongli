/*
 * 接口Dao类 会员与学员关联表信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer.member;

import java.util.List;

import cn.com.softvan.bean.member.TcMemberBean;
import cn.com.softvan.bean.student.TcStudentBean;
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
    
    /**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前会员关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	List<TcStudentBean> findDataIsListStudent(IEntity dto);
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前学员关联的会员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	List<TcMemberBean> findDataIsListMember(IEntity dto);
}