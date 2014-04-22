/*
 * 附件信息DAO 接口类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.04.17  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  tem. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.daointer;

import java.util.List;

import cn.com.softvan.bean.BaseFileBean;
import cn.com.softvan.dao.entity.IEntity;

/**
 * 附件信息DAO 接口类
 * @author wuxiaogang
 *
 */
public interface IBaseFileDao {
	/**
     * 附件信息列表 分页
     * @param instance
     * @return
     */
	public List<BaseFileBean> findBaseFileBeanIsPage(IEntity dto) throws Exception;
	/**
     * 附件信息列表 
     * @param instance
     * @return
     */
	public List<BaseFileBean> findBaseFileBeanIsList(IEntity dto) throws Exception;
	/**
     * 附件信息
     * @param instance
     * @return
     */
	public BaseFileBean findBaseFileBeanById(IEntity dto) throws Exception;
	
	/**
	 * 检查附件是否存在 信息id
	 * @param dto
	 * @throws Exception
	 */
	public int isBaseFileYN(IEntity dto) throws Exception;
	/**
	 * 新增附件信息
	 * @param dto
	 * @throws Exception
	 */
	public void insertBaseFile(IEntity dto) throws Exception;
	/**
	 * 更新附件信息
	 * @param dto
	 * @throws Exception
	 */
	public void updateBaseFile(IEntity dto) throws Exception;
	/**
	 * 删除附件
	 * @param dto
	 * @throws Exception
	 */
	public int deleteBaseFileByInfoId(IEntity dto) throws Exception;
}
