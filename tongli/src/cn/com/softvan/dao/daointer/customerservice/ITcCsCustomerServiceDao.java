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

import java.util.List;

import cn.com.softvan.bean.customerservice.TcCsCustomerServiceBean;
import cn.com.softvan.dao.entity.IEntity;
/**
 * <p>
 * 咨询 DAO
 * </p>
 * 
 * @author wuxiaogang
 * 
 */
public interface ITcCsCustomerServiceDao {
	 /**
     * 根据主键id删除信息
     */
    int deleteByPrimaryKey(IEntity dto) throws Exception;
    /**
     * 根据aid 逻辑删除信息
     */
    int deleteByAid(IEntity dto) throws Exception;
    /**
     * 根据Aid获取对象
     */
    TcCsCustomerServiceBean selectByAid(IEntity dto) throws Exception;
    /**
     * 根据z主键id 逻辑删除信息
     */
    int deleteById(IEntity dto) throws Exception;
    /**
     * 新增信息(全字段)
     */
    int insert(IEntity dto) throws Exception;

    /**
     * 新增信息(部分字段)
     */
    int insertSelective(IEntity dto) throws Exception;

    /**
     * 根据主键获取对象
     */
    TcCsCustomerServiceBean selectByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 更新对象
     */
    int updateByPrimaryKeySelective(IEntity dto) throws Exception;
    /**
     * 更新 媒体id
     */
    int updateMediaIdByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 判断信息是否存在
     */
    int isDataYN(IEntity dto) throws Exception;
    /**
     * 查询信息 列表
     */
    List<TcCsCustomerServiceBean> findDataIsList(IEntity dto) throws Exception;
    /**
     * 查询信息 分页
     */
    List<TcCsCustomerServiceBean> findDataIsPage(IEntity dto) throws Exception;
    /**
     * 恢复逻辑删除的数据
     */
    int recoveryDataById(IEntity dto);
    /**
	 * <p>密码认证。</p>
	 * <ol>[功能概要] 
	 * <div>密码认证。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcCsCustomerServiceBean checkPWD(IEntity dto);
}