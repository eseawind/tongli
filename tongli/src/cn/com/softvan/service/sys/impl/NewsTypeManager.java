/*
 * 资讯 分类信息 管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.25  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.sys.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.sys.TcSysNewsTypeBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IOHelper;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.sys.ITcSysNewsTypeDao;
import cn.com.softvan.dao.entity.sys.TcSysNewsType;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.sys.INewsTypeManager;
/**
 *<p>资讯 分类信息 管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class NewsTypeManager extends BaseManager implements INewsTypeManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(NewsTypeManager.class);
	
	/**信息DAO 接口类*/
	private ITcSysNewsTypeDao tcSysNewsDao;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = CommonConstant.DB_DEFAULT_TIMEOUT, rollbackFor = {
			Exception.class, RuntimeException.class })
	public String saveOrUpdateData(TcSysNewsTypeBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysNewsType dto=new TcSysNewsType();
				dto.setId(bean.getId());//ID
				dto.setName(bean.getName());//name
				dto.setParent_id(bean.getParent_id());//父级分类id
				dto.setVersion(bean.getVersion());//VERSION
				dto.setSort_num(bean.getSort_num()==null?"0":bean.getSort_num());//序号
				if(Validator.notEmpty(bean.getDetail_info())){
				IOHelper.deleteFile(bean.getDetail_info());//TODO=删除文件
				dto.setDetail_info(IOHelper.writeHtml("html",bean.getDetail_info()));//内容
				}
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				//判断数据是否存在
				if(tcSysNewsDao.isDataYN(dto)!=0){
					//数据存在
					tcSysNewsDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcSysNewsDao.insert(dto);
				}
			} catch (Exception e) {
				msg="信息保存失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcSysNewsTypeBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysNewsType dto=new TcSysNewsType();
				dto.setId(bean.getId());//id
				tcSysNewsDao.deleteByPrimaryKey(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
			}
		}
		return msg;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>逻辑删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = CommonConstant.DB_DEFAULT_TIMEOUT, rollbackFor = {
			Exception.class, RuntimeException.class })
	public String deleteDataById(TcSysNewsTypeBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysNewsType dto=new TcSysNewsType();
				dto.setId(bean.getId());//ID
				tcSysNewsDao.deleteById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysNewsTypeBean> findDataIsPage(TcSysNewsTypeBean bean){
		List<TcSysNewsTypeBean> beans=null;
		try {
    	   TcSysNewsType dto=new TcSysNewsType();
    	   if(bean!=null){
    		dto.setId(bean.getId());//ID
			dto.setName(bean.getName());//name
			dto.setParent_id(bean.getParent_id());//父级分类id
			dto.setDel_flag(bean.getDel_flag());//是否删除
    	   }
			beans=tcSysNewsDao.findDataIsPage(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysNewsTypeBean> findDataIsList(TcSysNewsTypeBean bean){
		List<TcSysNewsTypeBean> beans=null;
		try {
	    	   TcSysNewsType dto=new TcSysNewsType();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//ID
			   		dto.setName(bean.getName());//name
			   		dto.setParent_id(bean.getParent_id());//父级分类id
			   		dto.setDel_flag(bean.getDel_flag());//是否删除
	    	   }
				beans=tcSysNewsDao.findDataIsList(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>树。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysNewsTypeBean> findDataIsTree(TcSysNewsTypeBean bean){
		List<TcSysNewsTypeBean> beans=null;
		try {
	    	   TcSysNewsType dto=new TcSysNewsType();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//ID
			   		dto.setName(bean.getName());//name
			   		dto.setParent_id(bean.getParent_id());//父级分类id
			   		dto.setDel_flag(bean.getDel_flag());//是否删除
	    	   }
				beans=tcSysNewsDao.findDataIsTree(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcSysNewsTypeBean findDataById(TcSysNewsTypeBean bean){
       TcSysNewsTypeBean bean1=null;
       try {
    	   TcSysNewsType dto=new TcSysNewsType();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
    			dto.setName(bean.getName());//name
    			dto.setParent_id(bean.getParent_id());//父级分类id
    			dto.setDel_flag(bean.getDel_flag());//是否删除
    	   }
			bean1=tcSysNewsDao.selectByPrimaryKey(dto);
			if(bean1!=null){
				bean1.setDetail_info(IOHelper.readHtml(bean1.getDetail_info()));
			}
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcSysNewsTypeDao getTcSysNewsTypeDao() {
	    return tcSysNewsDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcSysNewsDao 信息DAO 接口类
	 */
	public void setTcSysNewsTypeDao(ITcSysNewsTypeDao tcSysNewsDao) {
	    this.tcSysNewsDao = tcSysNewsDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcSysNewsTypeBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysNewsType dto=new TcSysNewsType();
				dto.setId(bean.getId());//ID
				tcSysNewsDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
}
