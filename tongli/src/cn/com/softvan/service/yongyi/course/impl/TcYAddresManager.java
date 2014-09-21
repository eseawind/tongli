
/*	
 * 课程地址信息表   业务处理实现类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.28      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.service.yongyi.course.impl;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.yongyi.course.TcYAddresBean;
import cn.com.softvan.bean.yongyi.course.TcYCourseVsAddresBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.yongyi.course.ITcYAddresDao;
import cn.com.softvan.dao.daointer.yongyi.course.ITcYCourseVsAddresDao;
import cn.com.softvan.dao.entity.addres.TcAddres;
import cn.com.softvan.dao.entity.yongyi.course.TcYCourseVsAddres;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.yongyi.course.ITcYAddresManager;
/**
 * <p>课程地址信息表   业务处理实现类。</p>	
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
public class TcYAddresManager extends BaseManager implements ITcYAddresManager{

	private static final transient Logger log = Logger.getLogger(TcYAddresManager.class);
	/**课程地址信息表 Dao接口类*/
	private ITcYAddresDao tcYAddresDao;
	/**课程_上课地点关联关系 数据库处理接口类。*/
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
	public String saveOrUpdateData(TcYAddresBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcAddres dto=new TcAddres();	
				dto.setId(bean.getId());//id	
				dto.setPic_url(bean.getPic_url());//标题图	
				dto.setDetail_info(bean.getDetail_info());//课程详情	
				dto.setTitle(bean.getTitle());//名称	
				dto.setAddres(bean.getAddres());//地址	
				dto.setNote(bean.getNote());//备注	
				dto.setDate_created(bean.getDate_created());//数据输入日期	
				dto.setCreate_id(bean.getCreate_id());//建立者ID	
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP	
				dto.setLast_updated(bean.getLast_updated());//资料更新日期	
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID	
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP	
				dto.setDel_flag(bean.getDel_flag());//0否1是	
				dto.setVersion(bean.getVersion());//VERSION	
					
				//判断数据是否存在	
				if(tcYAddresDao.isDataYN(dto)!=0){	
					//数据存在	
					tcYAddresDao.updateByPrimaryKeySelective(dto);	
				}else{	
					//新增	
					if(Validator.isEmpty(dto.getId())){	
						dto.setId(IdUtils.createUUID(32));	
					}	
					tcYAddresDao.insert(dto);	
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
	public String deleteData(TcYAddresBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcAddres dto=new TcAddres();	
				dto.setId(bean.getId());//id	
				tcYAddresDao.deleteByPrimaryKey(dto);	
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
	public String deleteDataById(TcYAddresBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcAddres dto=new TcAddres();	
				dto.setId(bean.getId());//ID	
				tcYAddresDao.deleteById(dto);	
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
	public List<TcYAddresBean> findDataIsPage(TcYAddresBean bean){	
		List<TcYAddresBean> beans=null;	
		try {	
		   TcAddres dto=new TcAddres();	
		   if(bean!=null){	
				dto.setId(bean.getId());//id	
				dto.setPic_url(bean.getPic_url());//标题图	
				dto.setDetail_info(bean.getDetail_info());//课程详情	
				dto.setTitle(bean.getTitle());//名称	
				dto.setAddres(bean.getAddres());//地址	
				dto.setNote(bean.getNote());//备注	
				dto.setDate_created(bean.getDate_created());//数据输入日期	
				dto.setCreate_id(bean.getCreate_id());//建立者ID	
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP	
				dto.setLast_updated(bean.getLast_updated());//资料更新日期	
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID	
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP	
				dto.setDel_flag(bean.getDel_flag());//0否1是	
				dto.setVersion(bean.getVersion());//VERSION	
				
				dto.setPageInfo(bean.getPageInfo());//
		   }	
			beans=(List<TcYAddresBean>) tcYAddresDao.findDataIsPage(dto);	
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
	public List<TcYAddresBean> findDataIsList(TcYAddresBean bean){	
		List<TcYAddresBean> beans=null;	
		try {	
			   TcAddres dto=new TcAddres();	
			   if(bean!=null){	
				dto.setId(bean.getId());//id	
				dto.setPic_url(bean.getPic_url());//标题图	
				dto.setDetail_info(bean.getDetail_info());//课程详情	
				dto.setTitle(bean.getTitle());//名称	
				dto.setAddres(bean.getAddres());//地址	
				dto.setNote(bean.getNote());//备注	
				dto.setDate_created(bean.getDate_created());//数据输入日期	
				dto.setCreate_id(bean.getCreate_id());//建立者ID	
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP	
				dto.setLast_updated(bean.getLast_updated());//资料更新日期	
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID	
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP	
				dto.setDel_flag(bean.getDel_flag());//0否1是	
				dto.setVersion(bean.getVersion());//VERSION	
			   }	
				beans=(List<TcYAddresBean>) tcYAddresDao.findDataIsList(dto);	
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
	public TcYAddresBean findDataById(TcYAddresBean bean){	
	   TcYAddresBean bean1=null;	
	   try {	
		   TcAddres dto=new TcAddres();	
		   if(bean!=null){	
				dto.setId(bean.getId());//id	
				dto.setPic_url(bean.getPic_url());//标题图	
				dto.setDetail_info(bean.getDetail_info());//课程详情	
				dto.setTitle(bean.getTitle());//名称	
				dto.setAddres(bean.getAddres());//地址	
				dto.setNote(bean.getNote());//备注	
				dto.setDate_created(bean.getDate_created());//数据输入日期	
				dto.setCreate_id(bean.getCreate_id());//建立者ID	
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP	
				dto.setLast_updated(bean.getLast_updated());//资料更新日期	
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID	
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP	
				dto.setDel_flag(bean.getDel_flag());//0否1是	
				dto.setVersion(bean.getVersion());//VERSION	
		   }	
			bean1=(TcYAddresBean) tcYAddresDao.selectByPrimaryKey(dto);	
			//if(bean1!=null){	
				//bean1.setDetail_info(IOHelper.readHtml(bean1.getDetail_info()));	
			//}	
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
	public String recoveryDataById(TcYAddresBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcAddres dto=new TcAddres();	
				dto.setId(bean.getId());//ID	
				tcYAddresDao.recoveryDataById(dto);	
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
	 * <div>当前课程关联的的地址列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcYAddresBean> findDataIsListAddres(TcYCourseVsAddresBean bean){
		List<TcYAddresBean> beans=null;
		try {
			TcYCourseVsAddres dto=new TcYCourseVsAddres();
	    	   if(bean!=null){
	    		    dto.setCourse_id(bean.getCourse_id());
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
			beans=tcYCourseVsAddresDao.findDataIsListAddres(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * 课程地址信息表 Dao接口类取得
	 * @return 课程地址信息表 Dao接口类
	 */
	public ITcYAddresDao getTcAddresDao() {	
		return tcYAddresDao;	
	}
	/**
	 * 课程地址信息表 Dao接口类设定
	 * @param tcYAddresDao 课程地址信息表 Dao接口类
	 */
	public void setTcAddresDao(ITcYAddresDao tcYAddresDao) {	
		this.tcYAddresDao = tcYAddresDao;	
	}
	/**
	 * 课程地址信息表 Dao接口类取得
	 * @return 课程地址信息表 Dao接口类
	 */
	public ITcYAddresDao getTcYAddresDao() {
	    return tcYAddresDao;
	}
	/**
	 * 课程地址信息表 Dao接口类设定
	 * @param tcYAddresDao 课程地址信息表 Dao接口类
	 */
	public void setTcYAddresDao(ITcYAddresDao tcYAddresDao) {
	    this.tcYAddresDao = tcYAddresDao;
	}
	/**
	 * 课程_上课地点关联关系 数据库处理接口类。取得
	 * @return 课程_上课地点关联关系 数据库处理接口类。
	 */
	public ITcYCourseVsAddresDao getTcYCourseVsAddresDao() {
	    return tcYCourseVsAddresDao;
	}
	/**
	 * 课程_上课地点关联关系 数据库处理接口类。设定
	 * @param tcYCourseVsAddresDao 课程_上课地点关联关系 数据库处理接口类。
	 */
	public void setTcYCourseVsAddresDao(ITcYCourseVsAddresDao tcYCourseVsAddresDao) {
	    this.tcYCourseVsAddresDao = tcYCourseVsAddresDao;
	}	
}