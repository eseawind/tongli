
/*	
 * 班级信息表   业务处理实现类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.26      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.service.classes.impl;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.classes.TcClassesBean;
import cn.com.softvan.bean.student.TcStudentBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IOHelper;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.classes.ITcClassesDao;
import cn.com.softvan.dao.daointer.classes.ITcClassesVsStudentDao;
import cn.com.softvan.dao.entity.classes.TcClasses;
import cn.com.softvan.dao.entity.classes.TcClassesVsStudent;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.classes.IClassesManager;
/**
 * <p>班级信息表   业务处理实现类。</p>	
 * <ol>[功能概要] 
 * <div>编辑(新增or修改)。</div> 
 * <div>详情检索。</div> 
 * <div>分页检索。</div> 
 * <div>列表检索。</div> 
 * <div>逻辑删除。</div> 
 * <div>物理删除。</div> 
 * <div>恢复逻辑删除。</div> 
 *</ol> 
 * @author wuxiaogang
 */
public class ClassesManager extends BaseManager implements IClassesManager{

	private static final transient Logger log = Logger.getLogger(ClassesManager.class);
	/**班级信息表 Dao接口类*/
	private ITcClassesDao tcClassesDao;
	/**班级_学员关联表 数据库处理接口类*/
	private ITcClassesVsStudentDao tcClassesVsStudentDao;
	
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
	public String saveOrUpdateData(TcClassesBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcClasses dto=new TcClasses();	
				dto.setId(bean.getId());//ID	
				dto.setName(bean.getName());//班级名称	
				dto.setVersion(bean.getVersion());//VERSION	
				dto.setSort_num(bean.getSort_num());//序号	
				dto.setKeyword(bean.getKeyword());//关键字	
				dto.setBrief_info(bean.getBrief_info());//摘要	
				dto.setDetail_info(bean.getDetail_info());//内容	
				dto.setNote(bean.getNote());//备注	
				dto.setDate_created(bean.getDate_created());//数据输入日期	
				dto.setCreate_id(bean.getCreate_id());//建立者ID	
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP	
				dto.setLast_updated(bean.getLast_updated());//资料更新日期	
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID	
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP	
				dto.setDel_flag(bean.getDel_flag());//0否1是	
				dto.setPic_url(bean.getPic_url());//pic_url	
					
				//判断数据是否存在	
				if(tcClassesDao.isDataYN(dto)!=0){	
					//数据存在	
					tcClassesDao.updateByPrimaryKeySelective(dto);	
				}else{	
					//新增	
					if(Validator.isEmpty(dto.getId())){	
						dto.setId(IdUtils.createUUID(32));	
					}	
					tcClassesDao.insert(dto);	
				}
				//TODO ------保存班级与学员关系-------
				//清空当前班级与学员的关联关系
				TcClassesVsStudent msDto=new TcClassesVsStudent();
				//班级id
				msDto.setClasses_id(dto.getId());
				//清空
				tcClassesVsStudentDao.deleteByPrimaryKey(msDto);
				if(bean.getSids()!=null){
					for(String sid:bean.getSids()){
						msDto.setStudent_id(sid);
						msDto.setCreate_id(bean.getCreate_id());
						msDto.setCreate_ip(bean.getCreate_ip());
						//写入数据库
						tcClassesVsStudentDao.insert(msDto);
					}
				}
					//
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
	public String deleteData(TcClassesBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcClasses dto=new TcClasses();	
				dto.setId(bean.getId());//id	
				tcClassesDao.deleteByPrimaryKey(dto);	
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
	public String deleteDataById(TcClassesBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcClasses dto=new TcClasses();	
				dto.setId(bean.getId());//ID	
				tcClassesDao.deleteById(dto);	
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
	public List<TcClassesBean> findDataIsPage(TcClassesBean bean){	
		List<TcClassesBean> beans=null;	
		try {	
		   TcClasses dto=new TcClasses();	
		   if(bean!=null){	
				dto.setId(bean.getId());//ID	
				dto.setName(bean.getName());//班级名称	
				dto.setVersion(bean.getVersion());//VERSION	
				dto.setSort_num(bean.getSort_num());//序号	
				dto.setKeyword(bean.getKeyword());//关键字	
				dto.setBrief_info(bean.getBrief_info());//摘要	
				dto.setDetail_info(bean.getDetail_info());//内容	
				dto.setNote(bean.getNote());//备注	
				dto.setDate_created(bean.getDate_created());//数据输入日期	
				dto.setCreate_id(bean.getCreate_id());//建立者ID	
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP	
				dto.setLast_updated(bean.getLast_updated());//资料更新日期	
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID	
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP	
				dto.setDel_flag(bean.getDel_flag());//0否1是	
				dto.setPic_url(bean.getPic_url());//pic_url	
				dto.setPageInfo(bean.getPageInfo());//
		   }	
			beans=(List<TcClassesBean>) tcClassesDao.findDataIsPage(dto);	
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
	public List<TcClassesBean> findDataIsList(TcClassesBean bean){	
		List<TcClassesBean> beans=null;	
		try {	
			   TcClasses dto=new TcClasses();	
			   if(bean!=null){	
				dto.setId(bean.getId());//ID	
				dto.setName(bean.getName());//班级名称	
				dto.setVersion(bean.getVersion());//VERSION	
				dto.setSort_num(bean.getSort_num());//序号	
				dto.setKeyword(bean.getKeyword());//关键字	
				dto.setBrief_info(bean.getBrief_info());//摘要	
				if(Validator.notEmpty(bean.getDetail_info())){
					IOHelper.deleteFile(bean.getDetail_info());//TODO=删除文件
					dto.setDetail_info(IOHelper.writeHtml("html",bean.getDetail_info()));//内容
				}
				dto.setNote(bean.getNote());//备注	
				dto.setDate_created(bean.getDate_created());//数据输入日期	
				dto.setCreate_id(bean.getCreate_id());//建立者ID	
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP	
				dto.setLast_updated(bean.getLast_updated());//资料更新日期	
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID	
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP	
				dto.setDel_flag(bean.getDel_flag());//0否1是	
				dto.setPic_url(bean.getPic_url());//pic_url	
			   }	
				beans=(List<TcClassesBean>) tcClassesDao.findDataIsList(dto);	
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
	public TcClassesBean findDataById(TcClassesBean bean){	
	   TcClassesBean bean1=null;	
	   try {	
		   TcClasses dto=new TcClasses();	
		   if(bean!=null){	
				dto.setId(bean.getId());//ID	
				dto.setName(bean.getName());//班级名称	
				dto.setVersion(bean.getVersion());//VERSION	
				dto.setSort_num(bean.getSort_num());//序号	
				dto.setKeyword(bean.getKeyword());//关键字	
				dto.setBrief_info(bean.getBrief_info());//摘要	
				dto.setDetail_info(bean.getDetail_info());//内容	
				dto.setNote(bean.getNote());//备注	
				dto.setDate_created(bean.getDate_created());//数据输入日期	
				dto.setCreate_id(bean.getCreate_id());//建立者ID	
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP	
				dto.setLast_updated(bean.getLast_updated());//资料更新日期	
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID	
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP	
				dto.setDel_flag(bean.getDel_flag());//0否1是	
				dto.setPic_url(bean.getPic_url());//pic_url	
		   }	
			bean1=(TcClassesBean) tcClassesDao.selectByPrimaryKey(dto);	
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
	public String recoveryDataById(TcClassesBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcClasses dto=new TcClasses();	
				dto.setId(bean.getId());//ID	
				tcClassesDao.recoveryDataById(dto);	
			} catch (Exception e) {	
				msg="信息恢复失败,数据库处理错误!";	
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
	 * <div>当前会员关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcStudentBean> findDataIsListStudent(TcClassesBean bean){
		List<TcStudentBean> beans=null;
		try {
			TcClassesVsStudent dto=new TcClassesVsStudent();
	    	   if(bean!=null){
	    		    dto.setClasses_id(bean.getId());
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
			beans=tcClassesVsStudentDao.findDataIsListStudent(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * 班级信息表 Dao接口类取得
	 * @return 班级信息表 Dao接口类
	 */
	public ITcClassesDao getTcClassesDao() {	
		return tcClassesDao;	
	}
	/**
	 * 班级信息表 Dao接口类设定
	 * @param tcClassesDao 班级信息表 Dao接口类
	 */
	public void setTcClassesDao(ITcClassesDao tcClassesDao) {	
		this.tcClassesDao = tcClassesDao;	
	}
	/**
	 * 班级_学员关联表 数据库处理接口类取得
	 * @return 班级_学员关联表 数据库处理接口类
	 */
	public ITcClassesVsStudentDao getTcClassesVsStudentDao() {
	    return tcClassesVsStudentDao;
	}
	/**
	 * 班级_学员关联表 数据库处理接口类设定
	 * @param tcClassesVsStudentDao 班级_学员关联表 数据库处理接口类
	 */
	public void setTcClassesVsStudentDao(ITcClassesVsStudentDao tcClassesVsStudentDao) {
	    this.tcClassesVsStudentDao = tcClassesVsStudentDao;
	}	
}