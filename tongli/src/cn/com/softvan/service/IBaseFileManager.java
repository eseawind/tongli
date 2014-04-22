/*
 * 信息管理 service 接口类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.04.17  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service;

import java.util.List;

import cn.com.softvan.bean.BaseFileBean;


/**
 * 信息管理  service 接口类
 * @author wuxiaogang
 *
 */
public interface IBaseFileManager {
	/**
	 * <p>附件信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增附件信息。</div>
	 * <div>修改附件信息。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String saveOrUpdateBaseFile(BaseFileBean bean);
	/**
	 * <p>附件信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增附件信息。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String saveBaseFile(BaseFileBean bean);
	/**
	 * <p>附件信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<BaseFileBean> findBaseFileBeanIsPage(BaseFileBean bean);
	/**
	 * <p>附件信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<BaseFileBean> findBaseFileBeanIsList(BaseFileBean bean);
	/**
	 * <p>附件信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public BaseFileBean findBaseFileBeanById(BaseFileBean bean);
}
