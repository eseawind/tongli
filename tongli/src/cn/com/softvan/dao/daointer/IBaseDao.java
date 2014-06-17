/*
 *  基础接口Dao类 
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.06.01  wuxiaogang      程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer;

import java.util.List;

import cn.com.softvan.dao.entity.IEntity;
/**
 * <p> 基础接口Dao类  <p>
 * @author wuxiaogang
 *
 */
public interface IBaseDao {
    /**
     * 根据主键 物理删除
     */
    int deleteByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 新增
     */
    int insert(IEntity dto) throws Exception;
    /**
     * 新增 批量
     */
    int insert(List<IEntity> dtos) throws Exception;
    /**
     * 插入数据
     */
    int insertSelective(IEntity dto) throws Exception;
    /**
     * 详情
     */
    Object selectByPrimaryKey(IEntity dto) throws Exception;

    /**
     * 更新
     */
    int updateByPrimaryKeySelective(IEntity dto) throws Exception;
    /**
     * 判断是否存在
     */
    int isDataYN(IEntity dto) throws Exception;
    /**
     * 逻辑删除
     */
    int deleteById(IEntity dto) throws Exception;
    /**
     * 恢复逻辑删除的数据
     */
    int recoveryDataById(IEntity dto) throws Exception;
    /**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<?> findDataIsPage(IEntity dto) throws Exception;
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<?> findDataIsList(IEntity dto) throws Exception;
}