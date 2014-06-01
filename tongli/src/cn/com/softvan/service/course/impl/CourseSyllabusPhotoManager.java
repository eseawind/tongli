/*
 * 课程表管理-相册 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.21  wuxiaogang      程序・发布
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

import cn.com.softvan.bean.course.TcCourseSyllabusPhotoBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.course.ITcCourseSyllabusPhotoDao;
import cn.com.softvan.dao.entity.course.TcCourseSyllabusPhoto;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.course.ICourseSyllabusPhotoManager;
/**
 *<p>课程表管理-相册 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class CourseSyllabusPhotoManager extends BaseManager implements ICourseSyllabusPhotoManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(CourseSyllabusPhotoManager.class);
	
	/**信息DAO 接口类*/
	private ITcCourseSyllabusPhotoDao tcCourseSyllabusPhotoDao;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
//	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = CommonConstant.DB_DEFAULT_TIMEOUT, rollbackFor = {
//			Exception.class, RuntimeException.class })
	public String saveOrUpdateData(TcCourseSyllabusPhotoBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusPhoto dto=new TcCourseSyllabusPhoto();
				dto.setId(bean.getId());//id
				dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
				dto.setPic_url(bean.getPic_url());//图片路径
				dto.setPic_title(bean.getPic_title());//图片标题
				dto.setSort_num(bean.getSort_num());//
				dto.setNote(bean.getNote());//备注
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者id
				dto.setCreate_ip(bean.getCreate_ip());//建立者ip
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//version

				//判断数据是否存在
				if(tcCourseSyllabusPhotoDao.isDataYN(dto)!=0){
					//TODO----------------------------------------
					tcCourseSyllabusPhotoDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcCourseSyllabusPhotoDao.insert(dto);
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
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
//	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = CommonConstant.DB_DEFAULT_TIMEOUT, rollbackFor = {
//			Exception.class, RuntimeException.class })
	public String saveOrUpdateData(List<TcCourseSyllabusPhotoBean> beans) throws Exception{
		String msg="1";
		if(beans!=null){
			for(TcCourseSyllabusPhotoBean bean:beans){
				try {
					if("1".equals(bean.getDel_flag())){
						deleteData(bean);//删除
					}else{
						saveOrUpdateData(bean);//新增/修改
					}
				} catch (Exception e) {
					msg+=bean.getPic_title()+";";
					log.error(msg, e);
					//throw new Exception(msg);
				}
			}
		}
		if(!"1".equals(msg)){
			msg+="信息保存失败,数据库处理错误!";
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
	public String deleteData(TcCourseSyllabusPhotoBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusPhoto dto=new TcCourseSyllabusPhoto();
				dto.setId(bean.getId());//id
				tcCourseSyllabusPhotoDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcCourseSyllabusPhotoBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusPhoto dto=new TcCourseSyllabusPhoto();
				dto.setId(bean.getId());//ID
				tcCourseSyllabusPhotoDao.deleteById(dto);
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
	public List<TcCourseSyllabusPhotoBean> findDataIsPage(TcCourseSyllabusPhotoBean bean){
		List<TcCourseSyllabusPhotoBean> beans=null;
		try {
			TcCourseSyllabusPhoto dto=new TcCourseSyllabusPhoto();
    	   if(bean!=null){
    		   dto.setId(bean.getId());//id
    		   dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
    		   dto.setPic_url(bean.getPic_url());//图片路径
    		   dto.setPic_title(bean.getPic_title());//图片标题
    		   dto.setNote(bean.getNote());//备注
    		   dto.setDate_created(bean.getDate_created());//数据输入日期
    		   dto.setCreate_id(bean.getCreate_id());//建立者id
    		   dto.setCreate_ip(bean.getCreate_ip());//建立者ip
    		   dto.setLast_updated(bean.getLast_updated());//资料更新日期
    		   dto.setUpdate_id(bean.getUpdate_id());//修改者id
    		   dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
    		   dto.setDel_flag(bean.getDel_flag());//是否删除
    		   dto.setVersion(bean.getVersion());//version

				dto.setPageInfo(bean.getPageInfo());//分页
    	   }
			beans=(List<TcCourseSyllabusPhotoBean>) tcCourseSyllabusPhotoDao.findDataIsPage(dto);
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
	public List<TcCourseSyllabusPhotoBean> findDataIsList(TcCourseSyllabusPhotoBean bean){
		List<TcCourseSyllabusPhotoBean> beans=null;
		try {
			TcCourseSyllabusPhoto dto=new TcCourseSyllabusPhoto();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
	    		   dto.setCourse_syllabus_id(bean.getCourse_syllabus_id());//课程表id
	    		   dto.setPic_url(bean.getPic_url());//图片路径
	    		   dto.setPic_title(bean.getPic_title());//图片标题
	    		   dto.setNote(bean.getNote());//备注
	    		   dto.setDate_created(bean.getDate_created());//数据输入日期
	    		   dto.setCreate_id(bean.getCreate_id());//建立者id
	    		   dto.setCreate_ip(bean.getCreate_ip());//建立者ip
	    		   dto.setLast_updated(bean.getLast_updated());//资料更新日期
	    		   dto.setUpdate_id(bean.getUpdate_id());//修改者id
	    		   dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
	    		   dto.setDel_flag(bean.getDel_flag());//是否删除
	    		   dto.setVersion(bean.getVersion());//version
	    		    
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=(List<TcCourseSyllabusPhotoBean>) tcCourseSyllabusPhotoDao.findDataIsList(dto);
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
	public TcCourseSyllabusPhotoBean findDataById(TcCourseSyllabusPhotoBean bean){
       TcCourseSyllabusPhotoBean bean1=null;
       try {
    	   TcCourseSyllabusPhoto dto=new TcCourseSyllabusPhoto();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
    	   }
			bean1=(TcCourseSyllabusPhotoBean) tcCourseSyllabusPhotoDao.selectByPrimaryKey(dto);
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
	public String recoveryDataById(TcCourseSyllabusPhotoBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCourseSyllabusPhoto dto=new TcCourseSyllabusPhoto();
				dto.setId(bean.getId());//ID
				tcCourseSyllabusPhotoDao.recoveryDataById(dto);
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
	public ITcCourseSyllabusPhotoDao getTcCourseSyllabusPhotoDao() {
	    return tcCourseSyllabusPhotoDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcCourseSyllabusPhotoDao 信息DAO 接口类
	 */
	public void setTcCourseSyllabusPhotoDao(ITcCourseSyllabusPhotoDao tcCourseSyllabusPhotoDao) {
	    this.tcCourseSyllabusPhotoDao = tcCourseSyllabusPhotoDao;
	}
}
