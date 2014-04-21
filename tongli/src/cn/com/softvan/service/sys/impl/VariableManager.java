/*
 * 数据字典管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.21  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.sys.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.sys.TcSysVariableBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.sys.ITcSysVariableDao;
import cn.com.softvan.dao.entity.sys.TcSysVariable;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.sys.IVariableManager;
/**
 *<p>数据字典管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class VariableManager extends BaseManager implements IVariableManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(VariableManager.class);
	
	/**信息DAO 接口类*/
	private ITcSysVariableDao tcSysVariableDao;
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
	public String saveOrUpdateData(TcSysVariableBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysVariable dto=new TcSysVariable();
				dto.setVariable_id(bean.getVariable_id());//变量关键字
				dto.setVariable_name(bean.getVariable_name());//变量名称
				dto.setVariable_sub_id(bean.getVariable_sub_id());//变量子项关键字
				dto.setVariable_sub_name(bean.getVariable_sub_name());//变量子项名称
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者id
				dto.setCreate_ip(bean.getCreate_ip());//建立者ip
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				dto.setVersion(bean.getVersion());//version
				dto.setDel_flag(bean.getDel_flag());//是否删除
				//判断数据是否存在
				if(tcSysVariableDao.isDataYN(dto)!=0){
					//数据存在
					tcSysVariableDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getVariable_sub_id())){
						dto.setVariable_sub_id(IdUtils.createUUID(16));
					}
					tcSysVariableDao.insert(dto);
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
	public String deleteData(TcSysVariableBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysVariable dto=new TcSysVariable();
				dto.setVariable_id(bean.getVariable_id());//ID1
				dto.setVariable_sub_id(bean.getVariable_sub_id());//id2
				tcSysVariableDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcSysVariableBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysVariable dto=new TcSysVariable();
				dto.setVariable_id(bean.getVariable_id());//ID1
				dto.setVariable_sub_id(bean.getVariable_sub_id());//id2
				tcSysVariableDao.deleteById(dto);
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
	public List<TcSysVariableBean> findDataIsPage(TcSysVariableBean bean){
		List<TcSysVariableBean> beans=null;
		try {
    	   TcSysVariable dto=new TcSysVariable();
    	   if(bean!=null){
    		    dto.setVariable_id(bean.getVariable_id());//变量关键字
    		    dto.setVariable_name(bean.getVariable_name());//变量名称
    		    dto.setVariable_sub_id(bean.getVariable_sub_id());//变量子项关键字
    		    dto.setVariable_sub_name(bean.getVariable_sub_name());//变量子项名称
    		    dto.setDate_created(bean.getDate_created());//数据输入日期
    		    dto.setCreate_id(bean.getCreate_id());//建立者id
    		    dto.setCreate_ip(bean.getCreate_ip());//建立者ip
    		    dto.setLast_updated(bean.getLast_updated());//资料更新日期
    		    dto.setUpdate_id(bean.getUpdate_id());//修改者id
    		    dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
    		    dto.setVersion(bean.getVersion());//version
    		    dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setPageInfo(bean.getPageInfo());//分页
    	   }
			beans=tcSysVariableDao.findDataIsPage(dto);
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
	public List<TcSysVariableBean> findDataIsList(TcSysVariableBean bean){
		List<TcSysVariableBean> beans=null;
		try {
	    	   TcSysVariable dto=new TcSysVariable();
	    	   if(bean!=null){
	    		   dto.setVariable_id(bean.getVariable_id());//变量关键字
	    		   dto.setVariable_name(bean.getVariable_name());//变量名称
	    		   dto.setVariable_sub_id(bean.getVariable_sub_id());//变量子项关键字
	    		   dto.setVariable_sub_name(bean.getVariable_sub_name());//变量子项名称
	    		   dto.setDate_created(bean.getDate_created());//数据输入日期
	    		   dto.setCreate_id(bean.getCreate_id());//建立者id
	    		   dto.setCreate_ip(bean.getCreate_ip());//建立者ip
	    		   dto.setLast_updated(bean.getLast_updated());//资料更新日期
	    		   dto.setUpdate_id(bean.getUpdate_id());//修改者id
	    		   dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
	    		   dto.setVersion(bean.getVersion());//version
	    		   dto.setDel_flag(bean.getDel_flag());//是否删除
	    	   }
				beans=tcSysVariableDao.findDataIsList(dto);
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
	public TcSysVariableBean findDataById(TcSysVariableBean bean){
       TcSysVariableBean bean1=null;
       try {
    	   TcSysVariable dto=new TcSysVariable();
    	   if(bean!=null){
    		   dto.setVariable_id(bean.getVariable_id());//ID1
				dto.setVariable_sub_id(bean.getVariable_sub_id());//id2
	   			dto.setDel_flag(bean.getDel_flag());//是否删除
    	   }
			bean1=tcSysVariableDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcSysVariableDao getTcSysVariableDao() {
	    return tcSysVariableDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcSysVariableDao 信息DAO 接口类
	 */
	public void setTcSysVariableDao(ITcSysVariableDao tcSysVariableDao) {
	    this.tcSysVariableDao = tcSysVariableDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcSysVariableBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysVariable dto=new TcSysVariable();
				dto.setVariable_id(bean.getVariable_id());//ID1
				dto.setVariable_sub_id(bean.getVariable_sub_id());//id2
				tcSysVariableDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
}