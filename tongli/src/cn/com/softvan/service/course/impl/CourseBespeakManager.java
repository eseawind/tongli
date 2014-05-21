/*
 * 课程信息管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.01  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.course.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.course.TcCourseBespeakBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.course.ITcCourseBespeakDao;
import cn.com.softvan.dao.entity.course.TcCourseBespeak;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.course.ICourseBespeakManager;
/**
 *<p>课程信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class CourseBespeakManager extends BaseManager implements ICourseBespeakManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(CourseBespeakManager.class);
	
	/**信息DAO 接口类*/
	private ITcCourseBespeakDao tcCourseBespeakDao;
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
	public String saveOrUpdateData(TcCourseBespeakBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseBespeak dto=new TcCourseBespeak();
				dto.setId(bean.getId());//id
				dto.setName(bean.getName());//姓名
				dto.setSex(bean.getSex());//性别
				dto.setTel(bean.getTel());//电话
				dto.setDay(bean.getDay());//时间
				dto.setAddres(bean.getAddres());//需要参观的场馆
				dto.setCourse(bean.getCourse());//预约参观课程
				dto.setDetail_info(bean.getDetail_info());//详情
				dto.setNote(bean.getNote());//备注
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者id
				dto.setCreate_ip(bean.getCreate_ip());//建立者ip
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//version
				dto.setStatus(bean.getStatus());//状态0未完成1已完成

				//判断数据是否存在
				if(tcCourseBespeakDao.isDataYN(dto)!=0){
					//数据存在
					tcCourseBespeakDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcCourseBespeakDao.insert(dto);
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
	public String deleteData(TcCourseBespeakBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseBespeak dto=new TcCourseBespeak();
				dto.setId(bean.getId());//id
				tcCourseBespeakDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcCourseBespeakBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseBespeak dto=new TcCourseBespeak();
				dto.setId(bean.getId());//ID
				tcCourseBespeakDao.deleteById(dto);
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
	public List<TcCourseBespeakBean> findDataIsPage(TcCourseBespeakBean bean){
		List<TcCourseBespeakBean> beans=null;
		try {
    	   TcCourseBespeak dto=new TcCourseBespeak();
    	   if(bean!=null){
    		   dto.setId(bean.getId());//id
				dto.setName(bean.getName());//姓名
				dto.setSex(bean.getSex());//性别
				dto.setTel(bean.getTel());//电话
				dto.setDay(bean.getDay());//时间
				dto.setAddres(bean.getAddres());//需要参观的场馆
				dto.setCourse(bean.getCourse());//预约参观课程
				dto.setDetail_info(bean.getDetail_info());//详情
				dto.setNote(bean.getNote());//备注
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者id
				dto.setCreate_ip(bean.getCreate_ip());//建立者ip
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//version
				dto.setStatus(bean.getStatus());//状态0未完成1已完成
			dto.setPageInfo(bean.getPageInfo());//分页
    	   }
			beans=tcCourseBespeakDao.findDataIsPage(dto);
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
	public List<TcCourseBespeakBean> findDataIsList(TcCourseBespeakBean bean){
		List<TcCourseBespeakBean> beans=null;
		try {
	    	   TcCourseBespeak dto=new TcCourseBespeak();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
					dto.setName(bean.getName());//姓名
					dto.setSex(bean.getSex());//性别
					dto.setTel(bean.getTel());//电话
					dto.setDay(bean.getDay());//时间
					dto.setAddres(bean.getAddres());//需要参观的场馆
					dto.setCourse(bean.getCourse());//预约参观课程
					dto.setDetail_info(bean.getDetail_info());//详情
					dto.setNote(bean.getNote());//备注
					dto.setDate_created(bean.getDate_created());//数据输入日期
					dto.setCreate_id(bean.getCreate_id());//建立者id
					dto.setCreate_ip(bean.getCreate_ip());//建立者ip
					dto.setLast_updated(bean.getLast_updated());//资料更新日期
					dto.setUpdate_id(bean.getUpdate_id());//修改者id
					dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
					dto.setDel_flag(bean.getDel_flag());//是否删除
					dto.setVersion(bean.getVersion());//version
					dto.setStatus(bean.getStatus());//状态0未完成1已完成
	    		    
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=tcCourseBespeakDao.findDataIsList(dto);
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
	public TcCourseBespeakBean findDataById(TcCourseBespeakBean bean){
       TcCourseBespeakBean bean1=null;
       try {
    	   TcCourseBespeak dto=new TcCourseBespeak();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
    	   }
			bean1=tcCourseBespeakDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcCourseBespeakDao getTcCourseBespeakDao() {
	    return tcCourseBespeakDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcCourseBespeakDao 信息DAO 接口类
	 */
	public void setTcCourseBespeakDao(ITcCourseBespeakDao tcCourseBespeakDao) {
	    this.tcCourseBespeakDao = tcCourseBespeakDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcCourseBespeakBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseBespeak dto=new TcCourseBespeak();
				dto.setId(bean.getId());//ID
				tcCourseBespeakDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
}
