/*
 * 附件管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.08.16  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseFileBean;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.dao.daointer.IBaseFileDao;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.IBaseFileManager;
import cn.com.softvan.web.tag.PageInfo;

/**
 * <p>
 * 附件管理 service类。
 * </p>
 * <ol>
 * [功能概要] <div>信息编辑。</div> <div>信息检索。</div>
 * </ol>
 * 
 * @author wuxiaogang
 */
public class BaseFileManager extends BaseManager implements IBaseFileManager {
	/** 日志 */
	private static final transient Logger log = Logger
			.getLogger(BaseFileManager.class);
	/** 附件信息DAO 接口类 */
	private IBaseFileDao baseFileDao;

	/**
	 * 附件信息DAO 接口类取得
	 * 
	 * @return 附件信息DAO 接口类
	 */
	public IBaseFileDao getBaseFileDao() {
		return baseFileDao;
	}

	/**
	 * 附件信息DAO 接口类设定
	 * 
	 * @param baseFileDao
	 *            附件信息DAO 接口类
	 */
	public void setBaseFileDao(IBaseFileDao baseFileDao) {
		this.baseFileDao = baseFileDao;
	}

	/**
	 * <p>
	 * 附件信息编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>新增附件信息。</div> <div>修改附件信息。</div>
	 * </ol>
	 * 
	 * @return 处理结果
	 */
	public String saveOrUpdateBaseFile(BaseFileBean bean) {
		String msg = "1";
//		if (bean != null) {
//			try {
//				BaseFile dto = new BaseFile();
//				dto.setId(bean.getId());// ID
//				dto.setVersions(bean.getVersions());// versions
//				dto.setInfo_id(bean.getInfo_id());// 信息id
//				dto.setName(bean.getName());// 附件名称
//				dto.setSort_num(bean.getSort_num());// sort_num
//				dto.setFile_size(bean.getFile_size());// 附件大小
//				dto.setNote(bean.getNote());// 附件描述
//				dto.setType(bean.getType());// 附件类型
//				dto.setLocal_url(bean.getLocal_url());// 本地存储路径
//				dto.setDate_created(bean.getDate_created());// 数据输入日期
//				dto.setCreate_id(bean.getCreate_id());// 建立者id
//				dto.setCreate_ip(bean.getCreate_ip());// 建立者ip
//				dto.setLast_updated(bean.getLast_updated());// 资料更新日期
//				dto.setUpdate_id(bean.getUpdate_id());// 修改者id
//				dto.setUpdate_ip(bean.getUpdate_ip());// 修改者ip
//				// 是否存在
//				if (baseFileDao.isBaseFileYN(dto) > 0) {
//					// 存在--> 更新
//					baseFileDao.updateBaseFile(dto);
//				} else {
//					// 不存在--> 新增
//					dto.setId(IdUtils.createUUID(32));
//					//
//					baseFileDao.insertBaseFile(dto);
//				}
//			} catch (Exception e) {
//				msg = "信息保存失败,数据库处理错误!";
//				log.error(msg, e);
//			}
//		}
		return msg;
	}

	/**
	 * <p>
	 * 附件信息编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>新增附件信息。</div>
	 * </ol>
	 * 
	 * @return 处理结果
	 */
	public synchronized String saveBaseFile(BaseFileBean bean) {
		String msg = null;
//		if (bean != null) {
//			try {
//				BaseFile dto = new BaseFile();
//				dto.setId(bean.getId());// ID
//				dto.setId(bean.getId());// ID
//				dto.setVersions(bean.getVersions());// versions
//				dto.setInfo_id(bean.getInfo_id());// 信息id
//				dto.setName(bean.getName());// 附件名称
//				dto.setSort_num(bean.getSort_num());// sort_num
//				dto.setFile_size(bean.getFile_size());// 附件大小
//				dto.setNote(bean.getNote());// 附件描述
//				dto.setType(bean.getType());// 附件类型
//				dto.setLocal_url(bean.getLocal_url());// 本地存储路径
//				dto.setDate_created(bean.getDate_created());// 数据输入日期
//				dto.setCreate_id(bean.getCreate_id());// 建立者id
//				dto.setCreate_ip(bean.getCreate_ip());// 建立者ip
//				dto.setLast_updated(bean.getLast_updated());// 资料更新日期
//				dto.setUpdate_id(bean.getUpdate_id());// 修改者id
//				dto.setUpdate_ip(bean.getUpdate_ip());// 修改者ip
//				// 新数据否
////				if (baseFileDao.isBaseFileYN2(dto) == 0) {
//					// 不存在--> 新增
//					msg = IdUtils.createUUID(32);
//					dto.setId(msg);
//					// 新数据
//					baseFileDao.insertBaseFile(dto);
////				}
//			} catch (Exception e) {
//				msg = null;
//				log.error("信息保存失败,数据库处理错误!", e);
//			}
//		}
		return msg;
	}

	/**
	 * <p>
	 * 附件信息列表 分页。
	 * </p>
	 * <ol>
	 * [功能概要] <div>信息检索。</div> <div>分页。</div>
	 * </ol>
	 * 
	 * @return 处理结果
	 */
	public List<BaseFileBean> findBaseFileBeanIsPage(BaseFileBean bean) {
		List<BaseFileBean> beans = null;
//		try {
//			BaseFile dto = new BaseFile();
//			if (bean != null) {
//				// dto.setId(bean.getId());//ID
//				// dto.setVersions(bean.getVersions());//versions
//				dto.setInfo_id(bean.getInfo_id());// 信息id
//				dto.setName(bean.getName());// 附件名称
//				dto.setSort_num(bean.getSort_num());// sort_num
//				dto.setFile_size(bean.getFile_size());// 附件大小
//				dto.setNote(bean.getNote());// 附件描述
//				dto.setType(bean.getType());// 附件类型
//				// dto.setLocal_url(bean.getLocal_url());//本地存储路径
//				// dto.setDate_created(bean.getDate_created());//数据输入日期
//				// dto.setCreate_id(bean.getCreate_id());//建立者id
//				// dto.setCreate_ip(bean.getCreate_ip());//建立者ip
//				// dto.setLast_updated(bean.getLast_updated());//资料更新日期
//				// dto.setUpdate_id(bean.getUpdate_id());//修改者id
//				// dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
//				dto.setPageInfo(bean.getPageInfo());// 分页对象
//			} else {
//				PageInfo pageFile = new PageInfo();
//				//
//				pageFile.setCurrOffset(0);
//				//
//				pageFile.setPageRowCount(15);
//				//
//				dto.setPageInfo(pageFile);// 分页对象
//			}
//			beans = baseFileDao.findBaseFileBeanIsPage(dto);
//		} catch (Exception e) {
//			log.error("信息查询失败!", e);
//		}
		return beans;
	}

	/**
	 * <p>
	 * 附件信息列表。
	 * </p>
	 * <ol>
	 * [功能概要] <div>信息检索。</div> <div>列表。</div>
	 * </ol>
	 * 
	 * @return 处理结果
	 */
	public List<BaseFileBean> findBaseFileBeanIsList(BaseFileBean bean) {
		List<BaseFileBean> beans = null;
//		try {
//			BaseFile dto = new BaseFile();
//			if (bean != null) {
//				// dto.setId(bean.getId());//ID
//				// dto.setVersions(bean.getVersions());//versions
//				dto.setInfo_id(bean.getInfo_id());// 信息id
//				dto.setName(bean.getName());// 附件名称
//				dto.setSort_num(bean.getSort_num());// sort_num
//				dto.setFile_size(bean.getFile_size());// 附件大小
//				dto.setNote(bean.getNote());// 附件描述
//				dto.setType(bean.getType());// 附件类型
//				// dto.setLocal_url(bean.getLocal_url());//本地存储路径
//				// dto.setDate_created(bean.getDate_created());//数据输入日期
//				// dto.setCreate_id(bean.getCreate_id());//建立者id
//				// dto.setCreate_ip(bean.getCreate_ip());//建立者ip
//				// dto.setLast_updated(bean.getLast_updated());//资料更新日期
//				// dto.setUpdate_id(bean.getUpdate_id());//修改者id
//				// dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
//			}
//			beans = baseFileDao.findBaseFileBeanIsList(dto);
//		} catch (Exception e) {
//			log.error("信息查询失败!", e);
//		}
		return beans;
	}

	/**
	 * <p>
	 * 附件信息详情。
	 * </p>
	 * <ol>
	 * [功能概要] <div>信息检索。</div> <div>详情。</div>
	 * </ol>
	 * 
	 * @return 处理结果
	 */
	public BaseFileBean findBaseFileBeanById(BaseFileBean bean) {
		BaseFileBean bean1 = null;
//		try {
//			BaseFile dto = new BaseFile();
//			if (bean != null) {
//				dto.setId(bean.getId());// ID
//				// dto.setVersions(bean.getVersions());//versions
//				// dto.setSite_id(bean.getSite_id());//站点id
//				// dto.setInfo_id(bean.getInfo_id());//信息id
//				// dto.setName(bean.getName());//附件名称
//				// dto.setSort_num(bean.getSort_num());//sort_num
//				// dto.setFile_size(bean.getFile_size());//附件大小
//				// dto.setNote(bean.getNote());//附件描述
//				// dto.setType(bean.getType());//附件类型
//				// dto.setLocal_url(bean.getLocal_url());//本地存储路径
//				// dto.setDate_created(bean.getDate_created());//数据输入日期
//				// dto.setCreate_id(bean.getCreate_id());//建立者id
//				// dto.setCreate_ip(bean.getCreate_ip());//建立者ip
//				// dto.setLast_updated(bean.getLast_updated());//资料更新日期
//				// dto.setUpdate_id(bean.getUpdate_id());//修改者id
//				// dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
//				//
//				bean1 = baseFileDao.findBaseFileBeanById(dto);
//			}
//		} catch (Exception e) {
//			log.error("信息查询失败!", e);
//		}
		return bean1;
	}
}
