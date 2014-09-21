/*
 * 课程表管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.07  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.yongyi.course.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.yongyi.course.TcYCourseSyllabusBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IOHelper;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.yongyi.course.ITcYCourseSyllabusDao;
import cn.com.softvan.dao.entity.yongyi.course.TcYCourseSyllabus;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.yongyi.course.ITcYCourseSyllabusManager;
/**
 *<p>课程表管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class TcYCourseSyllabusManager extends BaseManager implements ITcYCourseSyllabusManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(TcYCourseSyllabusManager.class);
	
	/**信息DAO 接口类*/
	private ITcYCourseSyllabusDao tcYCourseSyllabusDao;
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
	public String saveOrUpdateData(TcYCourseSyllabusBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcYCourseSyllabus dto=new TcYCourseSyllabus();
				dto.setId(bean.getId());//id
				dto.setCourse_id(bean.getCourse_id());//课程id
				dto.setDay(bean.getDay());//课程日期
				dto.setBegin_time(bean.getBegin_time());//开始时间
				dto.setEnd_time(bean.getEnd_time());//结束时间
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//VERSION
				dto.setType(bean.getType());//类型(0课程1夏令营2冬令营)
				if(Validator.notEmpty(bean.getDetail_info())){
					IOHelper.deleteFile(bean.getDetail_info());//TODO=删除文件
					dto.setDetail_info(IOHelper.writeHtml("html",bean.getDetail_info()));//内容
				}
				dto.setAddres(bean.getAddres());//地址
				//判断数据是否存在
				if(tcYCourseSyllabusDao.isDataYN(dto)!=0){
					TcYCourseSyllabusBean bean1=(TcYCourseSyllabusBean) tcYCourseSyllabusDao.selectByPrimaryKey(dto);
					// 必须 课程状态未完成==0  已完成的课程不许修改
					if("0".equals(bean1.getCourse_status())){
						//数据存在 课程表修改
						tcYCourseSyllabusDao.updateByPrimaryKeySelective(dto);
					}
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcYCourseSyllabusDao.insert(dto);
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
	public String deleteData(TcYCourseSyllabusBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcYCourseSyllabus dto=new TcYCourseSyllabus();
				dto.setId(bean.getId());//id
				tcYCourseSyllabusDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcYCourseSyllabusBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcYCourseSyllabus dto=new TcYCourseSyllabus();
				dto.setId(bean.getId());//ID
				tcYCourseSyllabusDao.deleteById(dto);
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
	public List<TcYCourseSyllabusBean> findDataIsPage(TcYCourseSyllabusBean bean){
		List<TcYCourseSyllabusBean> beans=null;
		try {
			TcYCourseSyllabus dto=new TcYCourseSyllabus();
    	   if(bean!=null){
				dto.setId(bean.getId());//id
				dto.setCourse_id(bean.getCourse_id());//课程id
				dto.setDay(bean.getDay());//课程日期
				dto.setBegin_time(bean.getBegin_time());//开始时间
				dto.setEnd_time(bean.getEnd_time());//结束时间
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//VERSION
				dto.setAddres(bean.getAddres());//地址
				dto.setCourse_status(bean.getCourse_status());//状态
				dto.setType(bean.getType());//类型(0课程1夏令营2冬令营)
				
				dto.setPageInfo(bean.getPageInfo());//分页
    	   }
			beans=(List<TcYCourseSyllabusBean>) tcYCourseSyllabusDao.findDataIsPage(dto);
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
	public List<TcYCourseSyllabusBean> findDataIsList(TcYCourseSyllabusBean bean){
		List<TcYCourseSyllabusBean> beans=null;
		try {
			TcYCourseSyllabus dto=new TcYCourseSyllabus();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
					dto.setCourse_id(bean.getCourse_id());//课程id
					dto.setDay(bean.getDay());//课程日期
					dto.setBegin_time(bean.getBegin_time());//开始时间
					dto.setEnd_time(bean.getEnd_time());//结束时间
					dto.setNote(bean.getNote());//备注
//					dto.setDate_created(bean.getDate_created());//数据输入日期
					dto.setCreate_id(bean.getCreate_id());//建立者ID
					dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//					dto.setLast_updated(bean.getLast_updated());//资料更新日期
					dto.setUpdate_id(bean.getUpdate_id());//修改者ID
					dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
					dto.setDel_flag(bean.getDel_flag());//是否删除
					dto.setAddres(bean.getAddres());//地址
					dto.setVersion(bean.getVersion());//VERSION
					dto.setCourse_status(bean.getCourse_status());//状态
					dto.setType(bean.getType());//类型(0课程1夏令营2冬令营)
					
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=(List<TcYCourseSyllabusBean>) tcYCourseSyllabusDao.findDataIsList(dto);
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
	public List<TcYCourseSyllabusBean> findDataIsListDate(TcYCourseSyllabusBean bean){
		List<TcYCourseSyllabusBean> beans=null;
		try {
			TcYCourseSyllabus dto=new TcYCourseSyllabus();
	    	   if(bean!=null){
					dto.setCourse_id(bean.getCourse_id());//课程id
					dto.setType(bean.getType());//类型(0课程1夏令营2冬令营)
					
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=(List<TcYCourseSyllabusBean>) tcYCourseSyllabusDao.findDataIsListDate(dto);
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
	public TcYCourseSyllabusBean findDataById(TcYCourseSyllabusBean bean){
       TcYCourseSyllabusBean bean1=null;
       try {
    	   TcYCourseSyllabus dto=new TcYCourseSyllabus();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
    	   }
			bean1=(TcYCourseSyllabusBean) tcYCourseSyllabusDao.selectByPrimaryKey(dto);
			if(bean1!=null && Validator.notEmpty(bean1.getDetail_info())){	
				bean1.setDetail_info(IOHelper.readHtml(bean1.getDetail_info()));
			}
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcYCourseSyllabusBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcYCourseSyllabus dto=new TcYCourseSyllabus();
				dto.setId(bean.getId());//ID
				tcYCourseSyllabusDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcYCourseSyllabusDao getTcYCourseSyllabusDao() {
	    return tcYCourseSyllabusDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcYCourseSyllabusDao 信息DAO 接口类
	 */
	public void setTcYCourseSyllabusDao(ITcYCourseSyllabusDao tcYCourseSyllabusDao) {
	    this.tcYCourseSyllabusDao = tcYCourseSyllabusDao;
	}
}
