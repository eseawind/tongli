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

import cn.com.softvan.bean.classes.TcClassesBean;
import cn.com.softvan.bean.course.TcCourseBean;
import cn.com.softvan.bean.course.TcCourseVsClassesBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.addres.ITcCourseVsAddresDao;
import cn.com.softvan.dao.daointer.course.ITcCourseDao;
import cn.com.softvan.dao.daointer.course.ITcCourseVsClassesDao;
import cn.com.softvan.dao.entity.addres.TcCourseVsAddres;
import cn.com.softvan.dao.entity.course.TcCourse;
import cn.com.softvan.dao.entity.course.TcCourseVsClasses;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.course.ICourseManager;
/**
 *<p>课程信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class CourseManager extends BaseManager implements ICourseManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(CourseManager.class);
	
	/**信息DAO 接口类*/
	private ITcCourseDao tcCourseDao;
	/**信息DAO 课程与班级关联关系 数据库处理接口类。*/
	private ITcCourseVsClassesDao tcCourseVsClassesDao;
	/**信息DAO 课程_上课地点关联关系 数据库处理接口类。*/
	private ITcCourseVsAddresDao tcCourseVsAddresDao;
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
	public String saveOrUpdateData(TcCourseBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourse dto=new TcCourse();
				dto.setId(bean.getId());//课程编号
				dto.setTitle(bean.getTitle());//课程名称
				dto.setSubject_id(bean.getSubject_id());//主题
				dto.setCourse_type(bean.getCourse_type());//课种
				dto.setDuration(bean.getDuration());//时长
				dto.setDuration_unit(bean.getDuration_unit());//时长单位
				dto.setAge_group(bean.getAge_group());//年龄段
				dto.setNumber(bean.getNumber());//人数
				dto.setMarket_price(bean.getMarket_price());//非会员价
				dto.setMember_price(bean.getMember_price());//会员价
				dto.setIs_indoor(Validator.isEmpty(bean.getIs_indoor())?"0":"1");//是否室内
				dto.setIs_site_fee(Validator.isEmpty(bean.getIs_site_fee())?"0":"1");//是否包含场地费
				dto.setAddres(bean.getAddres());//课程地址
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//VERSION
				dto.setPic_url(bean.getPic_url());//标题图
				dto.setDetail_info(bean.getDetail_info());//课程详情

				//判断数据是否存在
				if(tcCourseDao.isDataYN(dto)!=0){
					//数据存在
					tcCourseDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcCourseDao.insert(dto);
				}
				
				if(bean.getAids()!=null){
					TcCourseVsAddres dto2=new TcCourseVsAddres();
					dto2.setCourse_id(dto.getId());
					//清空关联关系
					tcCourseVsAddresDao.deleteByPrimaryKey(dto2);
					for(String aid:bean.getAids()){
						if(Validator.notEmpty(aid)){
							dto2.setAddres_id(aid);
							tcCourseVsAddresDao.insert(dto2);
						}
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
	public String deleteData(TcCourseBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourse dto=new TcCourse();
				dto.setId(bean.getId());//id
				tcCourseDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcCourseBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourse dto=new TcCourse();
				dto.setId(bean.getId());//ID
				tcCourseDao.deleteById(dto);
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
	public List<TcCourseBean> findDataIsPage(TcCourseBean bean){
		List<TcCourseBean> beans=null;
		try {
    	   TcCourse dto=new TcCourse();
    	   if(bean!=null){
    		dto.setId(bean.getId());//课程编号
    		dto.setTitle(bean.getTitle());//课程名称
			dto.setSubject_id(bean.getSubject_id());//主题
			dto.setCourse_type(bean.getCourse_type());//课种
			dto.setDuration(bean.getDuration());//时长
			dto.setDuration_unit(bean.getDuration_unit());//时长单位
			dto.setAge_group(bean.getAge_group());//年龄段
			dto.setNumber(bean.getNumber());//人数
			dto.setMarket_price(bean.getMarket_price());//非会员价
			dto.setMember_price(bean.getMember_price());//会员价
			dto.setIs_indoor(bean.getIs_indoor());//是否室内
			dto.setIs_site_fee(bean.getIs_site_fee());//是否包含场地费
			dto.setAddres(bean.getAddres());//课程地址
			dto.setNote(bean.getNote());//备注
//			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setDel_flag(bean.getDel_flag());//是否删除
			dto.setVersion(bean.getVersion());//VERSION
			dto.setPic_url(bean.getPic_url());//标题图
			dto.setDetail_info(bean.getDetail_info());//课程详情
			dto.setPageInfo(bean.getPageInfo());//分页
    	   }
			beans=(List<TcCourseBean>) tcCourseDao.findDataIsPage(dto);
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
	public List<TcCourseBean> findDataIsList(TcCourseBean bean){
		List<TcCourseBean> beans=null;
		try {
	    	   TcCourse dto=new TcCourse();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//课程编号
	    		   dto.setTitle(bean.getTitle());//课程名称
					dto.setSubject_id(bean.getSubject_id());//主题
					dto.setCourse_type(bean.getCourse_type());//课种
					dto.setDuration(bean.getDuration());//时长
					dto.setDuration_unit(bean.getDuration_unit());//时长单位
					dto.setAge_group(bean.getAge_group());//年龄段
					dto.setNumber(bean.getNumber());//人数
					dto.setMarket_price(bean.getMarket_price());//非会员价
					dto.setMember_price(bean.getMember_price());//会员价
					dto.setIs_indoor(bean.getIs_indoor());//是否室内
					dto.setIs_site_fee(bean.getIs_site_fee());//是否包含场地费
					dto.setAddres(bean.getAddres());//课程地址
					dto.setNote(bean.getNote());//备注
//					dto.setDate_created(bean.getDate_created());//数据输入日期
					dto.setCreate_id(bean.getCreate_id());//建立者ID
					dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//					dto.setLast_updated(bean.getLast_updated());//资料更新日期
					dto.setUpdate_id(bean.getUpdate_id());//修改者ID
					dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
					dto.setDel_flag(bean.getDel_flag());//是否删除
					dto.setVersion(bean.getVersion());//VERSION
					dto.setPic_url(bean.getPic_url());//标题图
					dto.setDetail_info(bean.getDetail_info());//课程详情
	    		    
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=(List<TcCourseBean>) tcCourseDao.findDataIsList(dto);
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
	public TcCourseBean findDataById(TcCourseBean bean){
       TcCourseBean bean1=null;
       try {
    	   TcCourse dto=new TcCourse();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
    	   }
			bean1=(TcCourseBean) tcCourseDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcCourseDao getTcCourseDao() {
	    return tcCourseDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcCourseDao 信息DAO 接口类
	 */
	public void setTcCourseDao(ITcCourseDao tcCourseDao) {
	    this.tcCourseDao = tcCourseDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcCourseBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourse dto=new TcCourse();
				dto.setId(bean.getId());//ID
				tcCourseDao.recoveryDataById(dto);
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
	 * <div>当前课程关联的的班级列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcClassesBean> findDataIsListClasses(TcCourseVsClassesBean bean){
		List<TcClassesBean> beans=null;
		try {
			TcCourseVsClasses dto=new TcCourseVsClasses();
	    	   if(bean!=null){
	    		    dto.setCourse_id(bean.getCourse_id());
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
			beans=tcCourseVsClassesDao.findDataIsListClasses(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * 信息DAO 课程与班级关联关系 数据库处理接口类。取得
	 * @return 信息DAO 课程与班级关联关系 数据库处理接口类。
	 */
	public ITcCourseVsClassesDao getTcCourseVsClassesDao() {
	    return tcCourseVsClassesDao;
	}
	/**
	 * 信息DAO 课程与班级关联关系 数据库处理接口类。设定
	 * @param tcCourseVsClassesDao 信息DAO 课程与班级关联关系 数据库处理接口类。
	 */
	public void setTcCourseVsClassesDao(ITcCourseVsClassesDao tcCourseVsClassesDao) {
	    this.tcCourseVsClassesDao = tcCourseVsClassesDao;
	}
	/**
	 * 信息DAO 课程_上课地点关联关系 数据库处理接口类。取得
	 * @return 信息DAO 课程_上课地点关联关系 数据库处理接口类。
	 */
	public ITcCourseVsAddresDao getTcCourseVsAddresDao() {
	    return tcCourseVsAddresDao;
	}
	/**
	 * 信息DAO 课程_上课地点关联关系 数据库处理接口类。设定
	 * @param tcCourseVsAddresDao 信息DAO 课程_上课地点关联关系 数据库处理接口类。
	 */
	public void setTcCourseVsAddresDao(ITcCourseVsAddresDao tcCourseVsAddresDao) {
	    this.tcCourseVsAddresDao = tcCourseVsAddresDao;
	}
}
