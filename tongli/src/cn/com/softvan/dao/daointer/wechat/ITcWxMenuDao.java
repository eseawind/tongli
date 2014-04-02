/*
 * 微信服务_自定义菜单  DAO
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.25  wangzi          程序・发布
 * 1.01     2014.03.05  wuxiaogang      程序・更新
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */package cn.com.softvan.dao.daointer.wechat;

import java.util.List;

import cn.com.softvan.bean.wechat.TcWxMenuBean;
import cn.com.softvan.dao.entity.IEntity;
/**
 * 微信服务_自定义菜单  DAO
 * @author wuxiaogang 
 *
 */
public interface ITcWxMenuDao {
    /**
     * 根据主键id删除信息
     */
    int deleteByPrimaryKey(IEntity dto) throws Exception;
    /**
     * 根据菜单来源类型 删除所有菜单信息
     */
    int deleteAllByInfoSource(IEntity dto) throws Exception;
    
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
    TcWxMenuBean selectByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 更新对象
     */
    int updateByPrimaryKeySelective(IEntity dto) throws Exception;

    /**
     * 判断信息是否存在
     */
    int isDataYN(IEntity dto) throws Exception;
    /**
     * 查询信息列表
     */
    List<TcWxMenuBean> findDataIsList(IEntity dto) throws Exception;
    /**
     * 获取信息数量
     */
    int getDataCount(IEntity dto) throws Exception;
    
}