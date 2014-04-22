/*
 * 课程表-详情管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.07  wuxiaogang      程序・发布
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

import cn.com.softvan.bean.course.TcCourseSyllabusBean;
import cn.com.softvan.bean.course.TcCourseSyllabusItemsBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.course.ITcCourseSyllabusItemsDao;
import cn.com.softvan.dao.entity.course.TcCourseSyllabusItems;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.course.ICourseSyllabusItemsManager;
/**
 *<p>课程表-详情管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class CourseSyllabusItemsManager extends BaseManager implements ICourseSyllabusItemsManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(CourseSyllabusItemsManager.class);
	
	/**信息DAO 接口类*/
	private ITcCourseSyllabusItemsDao tcCourseSyllabusItemsDao;
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
	public String saveOrUpdateData(TcCourseSyllabusItemsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
				dto.setId(bean.getId());//id
				dto.setStudent_id(bean.getStudent_id());//学员id
				dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
				dto.setTeacher_id(bean.getTeacher_id());//教师id
				dto.setTeacher_score(bean.getTeacher_score());//教师得分
				dto.setTeacher_score_note(bean.getTeacher_score_note());//教师得分描述
				dto.setStudent_status(bean.getStudent_status());//学员状态
				dto.setStudent_status_note(bean.getStudent_status_note());//学员状态描述
				dto.setNote(bean.getNote());//备注
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//VERSION

				//判断数据是否存在
				if(tcCourseSyllabusItemsDao.isDataYN(dto)!=0){
					//数据存在
					tcCourseSyllabusItemsDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcCourseSyllabusItemsDao.insert(dto);
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
	public String deleteData(TcCourseSyllabusItemsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
				dto.setId(bean.getId());//id
				tcCourseSyllabusItemsDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcCourseSyllabusItemsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
				dto.setId(bean.getId());//ID
				tcCourseSyllabusItemsDao.deleteById(dto);
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
	public List<TcCourseSyllabusItemsBean> findDataIsPage(TcCourseSyllabusItemsBean bean){
		List<TcCourseSyllabusItemsBean> beans=null;
		try {
			TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
    	   if(bean!=null){
    		   dto.setId(bean.getId());//id
    		   dto.setStudent_id(bean.getStudent_id());//学员id
    		   dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
    		   dto.setTeacher_id(bean.getTeacher_id());//教师id
    		   dto.setTeacher_score(bean.getTeacher_score());//教师得分
    		   dto.setTeacher_score_note(bean.getTeacher_score_note());//教师得分描述
    		   dto.setStudent_status(bean.getStudent_status());//学员状态
    		   dto.setStudent_status_note(bean.getStudent_status_note());//学员状态描述
    		   dto.setNote(bean.getNote());//备注
    		   dto.setDate_created(bean.getDate_created());//数据输入日期
    		   dto.setCreate_id(bean.getCreate_id());//建立者ID
    		   dto.setCreate_ip(bean.getCreate_ip());//建立者IP
    		   dto.setLast_updated(bean.getLast_updated());//资料更新日期
    		   dto.setUpdate_id(bean.getUpdate_id());//修改者ID
    		   dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
    		   dto.setDel_flag(bean.getDel_flag());//是否删除
    		   dto.setVersion(bean.getVersion());//VERSION
				dto.setPageInfo(bean.getPageInfo());//分页
    	   }
			beans=tcCourseSyllabusItemsDao.findDataIsPage(dto);
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
	public List<TcCourseSyllabusItemsBean> findDataIsList(TcCourseSyllabusItemsBean bean){
		List<TcCourseSyllabusItemsBean> beans=null;
		try {
			TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
	    		   dto.setStudent_id(bean.getStudent_id());//学员id
	    		   dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
	    		   dto.setTeacher_id(bean.getTeacher_id());//教师id
	    		   dto.setTeacher_score(bean.getTeacher_score());//教师得分
	    		   dto.setTeacher_score_note(bean.getTeacher_score_note());//教师得分描述
	    		   dto.setStudent_status(bean.getStudent_status());//学员状态
	    		   dto.setStudent_status_note(bean.getStudent_status_note());//学员状态描述
	    		   dto.setNote(bean.getNote());//备注
	    		   dto.setDate_created(bean.getDate_created());//数据输入日期
	    		   dto.setCreate_id(bean.getCreate_id());//建立者ID
	    		   dto.setCreate_ip(bean.getCreate_ip());//建立者IP
	    		   dto.setLast_updated(bean.getLast_updated());//资料更新日期
	    		   dto.setUpdate_id(bean.getUpdate_id());//修改者ID
	    		   dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
	    		   dto.setDel_flag(bean.getDel_flag());//是否删除
	    		   dto.setVersion(bean.getVersion());//VERSION
	    		    
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=tcCourseSyllabusItemsDao.findDataIsList(dto);
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
	public TcCourseSyllabusItemsBean findDataById(TcCourseSyllabusItemsBean bean){
       TcCourseSyllabusItemsBean bean1=null;
       try {
    	   TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
    	   }
			bean1=tcCourseSyllabusItemsDao.selectByPrimaryKey(dto);
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
	public String recoveryDataById(TcCourseSyllabusItemsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
				dto.setId(bean.getId());//ID
				tcCourseSyllabusItemsDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前课程关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusItemsBean> findDataIsListStudent(TcCourseSyllabusItemsBean bean){
		List<TcCourseSyllabusItemsBean> beans=null;
		try {
			TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
	    		   dto.setStudent_id(bean.getStudent_id());//学员id
	    		   dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
	    		   dto.setTeacher_id(bean.getTeacher_id());//教师id
	    		   dto.setTeacher_score(bean.getTeacher_score());//教师得分
	    		   dto.setTeacher_score_note(bean.getTeacher_score_note());//教师得分描述
	    		   dto.setStudent_status(bean.getStudent_status());//学员状态
	    		   dto.setStudent_status_note(bean.getStudent_status_note());//学员状态描述
	    		   dto.setNote(bean.getNote());//备注
	    		   dto.setDate_created(bean.getDate_created());//数据输入日期
	    		   dto.setCreate_id(bean.getCreate_id());//建立者ID
	    		   dto.setCreate_ip(bean.getCreate_ip());//建立者IP
	    		   dto.setLast_updated(bean.getLast_updated());//资料更新日期
	    		   dto.setUpdate_id(bean.getUpdate_id());//修改者ID
	    		   dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
	    		   dto.setDel_flag(bean.getDel_flag());//是否删除
	    		   dto.setVersion(bean.getVersion());//VERSION
	    		    
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=tcCourseSyllabusItemsDao.findDataIsListStudent(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcCourseSyllabusItemsDao getTcCourseSyllabusItemsDao() {
	    return tcCourseSyllabusItemsDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcCourseSyllabusItemsDao 信息DAO 接口类
	 */
	public void setTcCourseSyllabusItemsDao(ITcCourseSyllabusItemsDao tcCourseSyllabusItemsDao) {
	    this.tcCourseSyllabusItemsDao = tcCourseSyllabusItemsDao;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前学员关联的课程列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusBean> findDataIsPageCourse(TcCourseSyllabusItemsBean bean){
		List<TcCourseSyllabusBean> beans=null;
		try {
			TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
	    		   dto.setStudent_id(bean.getStudent_id());//学员id
	    		   dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
	    		   dto.setTeacher_id(bean.getTeacher_id());//教师id
	    		   dto.setTeacher_score(bean.getTeacher_score());//教师得分
	    		   dto.setTeacher_score_note(bean.getTeacher_score_note());//教师得分描述
	    		   dto.setStudent_status(bean.getStudent_status());//学员状态
	    		   dto.setStudent_status_note(bean.getStudent_status_note());//学员状态描述
	    		   dto.setNote(bean.getNote());//备注
	    		   dto.setDate_created(bean.getDate_created());//数据输入日期
	    		   dto.setCreate_id(bean.getCreate_id());//建立者ID
	    		   dto.setCreate_ip(bean.getCreate_ip());//建立者IP
	    		   dto.setLast_updated(bean.getLast_updated());//资料更新日期
	    		   dto.setUpdate_id(bean.getUpdate_id());//修改者ID
	    		   dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
	    		   dto.setDel_flag(bean.getDel_flag());//是否删除
	    		   dto.setVersion(bean.getVersion());//VERSION
	    		   dto.setPageInfo(bean.getPageInfo());//分页
	    	   }
				beans=tcCourseSyllabusItemsDao.findDataIsPageCourse(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>教师给学员打分。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String updateDataByStudent(TcCourseSyllabusItemsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
				dto.setId(bean.getId());//id
				dto.setStudent_id(bean.getStudent_id());//学员id
				dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
				dto.setTeacher_id(bean.getTeacher_id());//教师id
				dto.setStudent_status(bean.getStudent_status());//学员状态
				dto.setStudent_status_note(bean.getStudent_status_note());//学员状态描述
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				//数据存在
				tcCourseSyllabusItemsDao.updateDataByStudent(dto);
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
	 * <div>学员给教师打分。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String updateDataByTeacher(TcCourseSyllabusItemsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
				dto.setId(bean.getId());//id
				dto.setStudent_id(bean.getStudent_id());//学员id
				dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
				dto.setTeacher_id(bean.getTeacher_id());//教师id
				dto.setTeacher_score(bean.getTeacher_score());//教师得分
				dto.setTeacher_score_note(bean.getTeacher_score_note());//教师得分描述
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP

				//数据存在
				tcCourseSyllabusItemsDao.updateDataByTeacher(dto);
			} catch (Exception e) {
				msg="信息保存失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>教师课程表 同时查询 当前课程关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCourseSyllabusBean> findDataIsPageCourse2(TcCourseSyllabusItemsBean bean){
		List<TcCourseSyllabusBean> beans=null;
		try {
			TcCourseSyllabusItems dto=new TcCourseSyllabusItems();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
	    		   dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
	    		   dto.setTeacher_id(bean.getTeacher_id());//教师id
	    		   dto.setDel_flag(bean.getDel_flag());//是否删除
	    		   dto.setPageInfo(bean.getPageInfo());//分页
	    	   }
				beans=tcCourseSyllabusItemsDao.findDataIsPageCourse2(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
}
