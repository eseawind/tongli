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
package cn.com.softvan.service.yongyi.course.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.yongyi.course.TcYCourseBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.yongyi.course.ITcYCourseDao;
import cn.com.softvan.dao.daointer.yongyi.course.ITcYCourseVsAddresDao;
import cn.com.softvan.dao.entity.yongyi.course.TcYCourse;
import cn.com.softvan.dao.entity.yongyi.course.TcYCourseVsAddres;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.yongyi.course.ITcYCourseManager;
/**
 *<p>课程信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class TcYCourseManager extends BaseManager implements ITcYCourseManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(TcYCourseManager.class);
	
	/**信息DAO 接口类*/
	private ITcYCourseDao tcYCourseDao;
	/**信息DAO 课程_上课地点关联关系 数据库处理接口类。*/
	private ITcYCourseVsAddresDao tcYCourseVsAddresDao;
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
	public String saveOrUpdateData(TcYCourseBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcYCourse dto=new TcYCourse();
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
				if(tcYCourseDao.isDataYN(dto)!=0){
					//数据存在
					tcYCourseDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcYCourseDao.insert(dto);
				}
				
				if(bean.getAids()!=null){
					TcYCourseVsAddres dto2=new TcYCourseVsAddres();
					dto2.setCourse_id(dto.getId());
					//清空关联关系
					tcYCourseVsAddresDao.deleteByPrimaryKey(dto2);
					for(String aid:bean.getAids()){
						if(Validator.notEmpty(aid)){
							dto2.setAddres_id(aid);
							tcYCourseVsAddresDao.insert(dto2);
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
	public String deleteData(TcYCourseBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcYCourse dto=new TcYCourse();
				dto.setId(bean.getId());//id
				tcYCourseDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcYCourseBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcYCourse dto=new TcYCourse();
				dto.setId(bean.getId());//ID
				tcYCourseDao.deleteById(dto);
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
	public List<TcYCourseBean> findDataIsPage(TcYCourseBean bean){
		List<TcYCourseBean> beans=null;
		try {
    	   TcYCourse dto=new TcYCourse();
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
			beans=(List<TcYCourseBean>) tcYCourseDao.findDataIsPage(dto);
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
	public List<TcYCourseBean> findDataIsList(TcYCourseBean bean){
		List<TcYCourseBean> beans=null;
		try {
	    	   TcYCourse dto=new TcYCourse();
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
				beans=(List<TcYCourseBean>) tcYCourseDao.findDataIsList(dto);
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
	public TcYCourseBean findDataById(TcYCourseBean bean){
       TcYCourseBean bean1=null;
       try {
    	   TcYCourse dto=new TcYCourse();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
    	   }
			bean1=(TcYCourseBean) tcYCourseDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcYCourseDao getTcYCourseDao() {
	    return tcYCourseDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcYCourseDao 信息DAO 接口类
	 */
	public void setTcYCourseDao(ITcYCourseDao tcYCourseDao) {
	    this.tcYCourseDao = tcYCourseDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcYCourseBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcYCourse dto=new TcYCourse();
				dto.setId(bean.getId());//ID
				tcYCourseDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
/**
	 * 信息DAO 课程_上课地点关联关系 数据库处理接口类。设定
	 * @param tcYCourseVsAddresDao 信息DAO 课程_上课地点关联关系 数据库处理接口类。
	 */
	public void setTcYCourseVsAddresDao(ITcYCourseVsAddresDao tcYCourseVsAddresDao) {
	    this.tcYCourseVsAddresDao = tcYCourseVsAddresDao;
	}
	/**
	 * 信息DAO 课程_上课地点关联关系 数据库处理接口类。取得
	 * @return 信息DAO 课程_上课地点关联关系 数据库处理接口类。
	 */
	//	/**
	//	 * <p>信息列表。</p>
	//	 * <ol>[功能概要] 
	//	 * <div>信息检索。</div>
	//	 * <div>当前课程关联的的班级列表。</div>
	//	 * </ol>
	//	 * @return 处理结果
	//	 */
	//	public List<TcYClassesBean> findDataIsListClasses(TcYCourseVsClassesBean bean){
	//		List<TcYClassesBean> beans=null;
	//		try {
	//			TcYCourseVsClasses dto=new TcYCourseVsClasses();
	//	    	   if(bean!=null){
	//	    		    dto.setCourse_id(bean.getCourse_id());
	//		   			dto.setLimit_s(bean.getLimit_s());
	//		   			dto.setLimit_e(bean.getLimit_e());
	//	    	   }
	//			beans=tcYCourseVsClassesDao.findDataIsListClasses(dto);
	//		} catch (Exception e) {
	//			log.error("信息查询失败,数据库错误!", e);
	//		}
	//		return beans;
	//	}
	/**
	 * 信息DAO 课程_上课地点关联关系 数据库处理接口类。取得
	 * @return 信息DAO 课程_上课地点关联关系 数据库处理接口类。
	 */
	public ITcYCourseVsAddresDao getTcYCourseVsAddresDao() {
	    return tcYCourseVsAddresDao;
	}
}
