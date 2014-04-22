/*
 * 咨询 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.04  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.consult.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.consult.TcCsConsultBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.consult.ITcCsConsultDao;
import cn.com.softvan.dao.entity.consult.TcCsConsult;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.consult.IConsultManager;
/**
 *<p>咨询 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class ConsultManager extends BaseManager implements IConsultManager {
	/** 日志 */
	private static final transient Logger log = Logger
			.getLogger(ConsultManager.class);
	/**咨询  数据库处理 DAO*/
	private ITcCsConsultDao tcCsConsultDao;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	public String saveOrUpdateData(TcCsConsultBean bean){
		String msg="1";
		try {
			TcCsConsult dto=new TcCsConsult();
			dto.setId(bean.getId());//咨询id
			dto.setUser_id(bean.getUser_id());//用户id
			dto.setCs_id(bean.getCs_id());//客服ID
			dto.setConsult_status(bean.getConsult_status());//状态
			dto.setFinish_time(bean.getFinish_time());//完成时间
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag
			//判断数据是否存在
			if(tcCsConsultDao.isDataYN(dto)!=0){
				
				//数据存在
				tcCsConsultDao.updateByPrimaryKeySelective(dto);
			}else{
				if(Validator.isEmpty(dto.getId())){
					dto.setId(IdUtils.createUUID(32));
				}
				//新增
				tcCsConsultDao.insert(dto);
			}
		} catch (Exception e) {
			log.error("信息保存,数据库错误!",e);
		}
		return msg;
	}
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	public String updateData(TcCsConsultBean bean){
		String msg="1";
		try {
			TcCsConsult dto=new TcCsConsult();
			dto.setId(bean.getId());//咨询id
			dto.setCs_id(bean.getCs_id());//客服ID
			dto.setConsult_status(bean.getConsult_status());//状态
			dto.setFinish_time(bean.getFinish_time());//完成时间
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			//数据存在
			tcCsConsultDao.updateByPrimaryKeySelective(dto);
		} catch (Exception e) {
			msg="信息保存,数据库错误!";
			log.error(msg,e);
		}
		return msg;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCsConsultBean> findDataIsList(TcCsConsultBean bean){
		List<TcCsConsultBean> beans=null;
		try {
			TcCsConsult dto=new TcCsConsult();
			dto.setId(bean.getId());//咨询id
			dto.setUser_id(bean.getUser_id());//用户id
			dto.setCs_id(bean.getCs_id());//客服ID
			dto.setConsult_status(bean.getConsult_status());//状态
			dto.setFinish_time(bean.getFinish_time());//完成时间
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag
			beans=tcCsConsultDao.findDataIsList(dto);
		} catch (Exception e) {
			log.error("信息列表查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCsConsultBean> findDataIsPage(TcCsConsultBean bean){
		List<TcCsConsultBean> beans=null;
		try {
			TcCsConsult dto=new TcCsConsult();
			dto.setId(bean.getId());//咨询id
			dto.setUser_id(bean.getUser_id());//用户id
			dto.setCs_id(bean.getCs_id());//客服ID
			dto.setConsult_status(bean.getConsult_status());//状态
			dto.setFinish_time(bean.getFinish_time());//完成时间
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag
			dto.setPageInfo(bean.getPageInfo());
			beans=tcCsConsultDao.findDataIsPage(dto);
		} catch (Exception e) {
			log.error("信息分页查询失败,数据库错误!", e);
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
	public TcCsConsultBean findDataById(TcCsConsultBean bean){
		TcCsConsultBean bean1=null;
		try {
			TcCsConsult dto=new TcCsConsult();
			if(bean!=null){
				dto.setId(bean.getId());//咨询id
				dto.setUser_id(bean.getUser_id());//用户id
				dto.setCs_id(bean.getCs_id());//客服ID
				dto.setConsult_status(bean.getConsult_status());//状态
				dto.setFinish_time(bean.getFinish_time());//完成时间
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setVersion(bean.getVersion());//VERSION
				dto.setDel_flag(bean.getDel_flag());//del_flag
			}
			bean1=tcCsConsultDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索 根据客服openid和最近修改时间查询咨询详情。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcCsConsultBean findDataByUserIdAndLastDate(TcCsConsultBean bean){
		TcCsConsultBean bean1=null;
		try {
			TcCsConsult dto=new TcCsConsult();
			if(bean!=null){
				dto.setId(bean.getId());//咨询id
				dto.setUser_id(bean.getUser_id());//用户id
				dto.setCs_id(bean.getCs_id());//客服ID
				dto.setConsult_status(bean.getConsult_status());//状态
				dto.setFinish_time(bean.getFinish_time());//完成时间
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setVersion(bean.getVersion());//VERSION
				dto.setDel_flag(bean.getDel_flag());//del_flag
			}
			bean1=tcCsConsultDao.findDataByUserIdAndLastDate(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 咨询  数据库处理 DAO取得
	 * @return 咨询  数据库处理 DAO
	 */
	public ITcCsConsultDao getTcCsConsultDao() {
	    return tcCsConsultDao;
	}
	/**
	 * 咨询  数据库处理 DAO设定
	 * @param tcCsConsultDao 咨询  数据库处理 DAO
	 */
	public void setTcCsConsultDao(ITcCsConsultDao tcCsConsultDao) {
	    this.tcCsConsultDao = tcCsConsultDao;
	}
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcCsConsultBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCsConsult dto=new TcCsConsult();
				dto.setId(bean.getId());//id
				tcCsConsultDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcCsConsultBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCsConsult dto=new TcCsConsult();
				dto.setId(bean.getId());//ID
				tcCsConsultDao.deleteById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcCsConsultBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCsConsult dto=new TcCsConsult();
				dto.setId(bean.getId());//ID
				tcCsConsultDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息恢复失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
}