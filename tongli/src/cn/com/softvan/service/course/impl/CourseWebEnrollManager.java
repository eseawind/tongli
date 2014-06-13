/*
 * 在线报名管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.06.08  wuxiaogang      程序・发布
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

import cn.com.softvan.bean.course.TcCourseWebEnrollBean;
import cn.com.softvan.bean.sys.TcSysSmsBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.course.ITcCourseWebEnrollDao;
import cn.com.softvan.dao.entity.course.TcCourseWebEnroll;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.course.ICourseWebEnrollManager;
import cn.com.softvan.service.sys.ISmsManager;
/**
 *<p>在线报名管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class CourseWebEnrollManager extends BaseManager implements ICourseWebEnrollManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(CourseWebEnrollManager.class);
	
	/**信息DAO 接口类*/
	private ITcCourseWebEnrollDao tcCourseWebEnrollDao;
	/**短信 信息管理 业务处理*/
	private ISmsManager smsManager;
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
	public String saveOrUpdateData(TcCourseWebEnrollBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseWebEnroll dto=new TcCourseWebEnroll();
				dto.setId(bean.getId());//id
				dto.setName(bean.getName());//姓名
				dto.setSex(bean.getSex());//性别
				dto.setTel(bean.getTel());//电话
				dto.setCourse(bean.getCourse());//课程
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
				if(tcCourseWebEnrollDao.isDataYN(dto)!=0){
					//数据存在
					tcCourseWebEnrollDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcCourseWebEnrollDao.insert(dto);
				}
				//TODO 报名成功发送短信
				if("1".equals(dto.getStatus()) && Validator.notEmpty(dto.getTel()) && Validator.isMobile(dto.getTel())){
					try {
						TcSysSmsBean smsBean=new TcSysSmsBean();
						smsBean.setSms_dst_id(dto.getTel());
						smsBean.setSms_content("童励俱乐部提醒,您已成功报名;http://www.tlkidsclub.com");
						smsManager.saveOrUpdateData(smsBean);
					} catch (Exception e) {
						log.error("报名成功发送短信异常!", e);
					}
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
	public String deleteData(TcCourseWebEnrollBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseWebEnroll dto=new TcCourseWebEnroll();
				dto.setId(bean.getId());//id
				tcCourseWebEnrollDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcCourseWebEnrollBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseWebEnroll dto=new TcCourseWebEnroll();
				dto.setId(bean.getId());//ID
				tcCourseWebEnrollDao.deleteById(dto);
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
	public List<TcCourseWebEnrollBean> findDataIsPage(TcCourseWebEnrollBean bean){
		List<TcCourseWebEnrollBean> beans=null;
		try {
    	   TcCourseWebEnroll dto=new TcCourseWebEnroll();
    	   if(bean!=null){
    		   dto.setId(bean.getId());//id
				dto.setName(bean.getName());//姓名
				dto.setSex(bean.getSex());//性别
				dto.setTel(bean.getTel());//电话
				dto.setCourse(bean.getCourse());//课程
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
				
				dto.setKeyword(bean.getKeyword());
				dto.setDate1(bean.getDate1());
				dto.setDate2(bean.getDate2());
				
			dto.setPageInfo(bean.getPageInfo());//分页
    	   }
			beans=(List<TcCourseWebEnrollBean>) tcCourseWebEnrollDao.findDataIsPage(dto);
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
	public List<TcCourseWebEnrollBean> findDataIsList(TcCourseWebEnrollBean bean){
		List<TcCourseWebEnrollBean> beans=null;
		try {
	    	   TcCourseWebEnroll dto=new TcCourseWebEnroll();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
					dto.setName(bean.getName());//姓名
					dto.setSex(bean.getSex());//性别
					dto.setTel(bean.getTel());//电话
					dto.setCourse(bean.getCourse());//课程
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
				beans=(List<TcCourseWebEnrollBean>) tcCourseWebEnrollDao.findDataIsList(dto);
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
	public TcCourseWebEnrollBean findDataById(TcCourseWebEnrollBean bean){
       TcCourseWebEnrollBean bean1=null;
       try {
    	   TcCourseWebEnroll dto=new TcCourseWebEnroll();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
    	   }
			bean1=(TcCourseWebEnrollBean) tcCourseWebEnrollDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcCourseWebEnrollDao getTcCourseWebEnrollDao() {
	    return tcCourseWebEnrollDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcCourseWebEnrollDao 信息DAO 接口类
	 */
	public void setTcCourseWebEnrollDao(ITcCourseWebEnrollDao tcCourseWebEnrollDao) {
	    this.tcCourseWebEnrollDao = tcCourseWebEnrollDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcCourseWebEnrollBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseWebEnroll dto=new TcCourseWebEnroll();
				dto.setId(bean.getId());//ID
				tcCourseWebEnrollDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * 短信 信息管理 业务处理取得
	 * @return 短信 信息管理 业务处理
	 */
	public ISmsManager getSmsManager() {
	    return smsManager;
	}
	/**
	 * 短信 信息管理 业务处理设定
	 * @param smsManager 短信 信息管理 业务处理
	 */
	public void setSmsManager(ISmsManager smsManager) {
	    this.smsManager = smsManager;
	}
}
