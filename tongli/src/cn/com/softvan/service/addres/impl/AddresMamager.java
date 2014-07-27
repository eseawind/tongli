
/*	
 * 课程地址信息表   业务处理实现类	
 *		
 * VERSION      DATE          BY              REASON		
 * -------- ----------- --------------- ------------------------------------------	
 * 1.00     2014.07.27      wuxiaogang         程序.发布		
 * -------- ----------- --------------- ------------------------------------------	
 * Copyright 2014 tongli  System. - All Rights Reserved.		
 *	
 */
package cn.com.softvan.service.addres.impl;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IOHelper;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.dao.entity.addres.TcAddres;
import cn.com.softvan.bean.addres.TcAddresBean;
import cn.com.softvan.dao.daointer.addres.ITcAddresDao;
import cn.com.softvan.service.addres.IAddresMamager;
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
public class AddresMamager extends BaseManager implements IAddresMamager{

	private static final transient Logger log = Logger.getLogger(AddresMamager.class);
	/**课程地址信息表 Dao接口类*/
	private ITcAddresDao tcAddresDao;
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
	public String saveOrUpdateData(TcAddresBean bean) throws Exception{	
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
				if(tcAddresDao.isDataYN(dto)!=0){	
					//数据存在	
					tcAddresDao.updateByPrimaryKeySelective(dto);	
				}else{	
					//新增	
					if(Validator.isEmpty(dto.getId())){	
						dto.setId(IdUtils.createUUID(32));	
					}	
					tcAddresDao.insert(dto);	
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
	public String deleteData(TcAddresBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcAddres dto=new TcAddres();	
				dto.setId(bean.getId());//id	
				tcAddresDao.deleteByPrimaryKey(dto);	
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
	public String deleteDataById(TcAddresBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcAddres dto=new TcAddres();	
				dto.setId(bean.getId());//ID	
				tcAddresDao.deleteById(dto);	
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
	public List<TcAddresBean> findDataIsPage(TcAddresBean bean){	
		List<TcAddresBean> beans=null;	
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
			beans=(List<TcAddresBean>) tcAddresDao.findDataIsPage(dto);	
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
	public List<TcAddresBean> findDataIsList(TcAddresBean bean){	
		List<TcAddresBean> beans=null;	
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
				beans=(List<TcAddresBean>) tcAddresDao.findDataIsList(dto);	
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
	public TcAddresBean findDataById(TcAddresBean bean){	
	   TcAddresBean bean1=null;	
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
			bean1=(TcAddresBean) tcAddresDao.selectByPrimaryKey(dto);	
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
	public String recoveryDataById(TcAddresBean bean) throws Exception{	
		String msg="1";	
		if(bean!=null){	
			try {	
				TcAddres dto=new TcAddres();	
				dto.setId(bean.getId());//ID	
				tcAddresDao.recoveryDataById(dto);	
			} catch (Exception e) {	
				msg="信息恢复失败,数据库处理错误!";	
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
	public ITcAddresDao gettcAddresDao() {	
		return tcAddresDao;	
	}	
	/**	
	 * 信息DAO 接口类设定	
	 * @param tcAddresDao 信息DAO 接口类	
	 */	
	public void settcAddresDao(ITcAddresDao tcAddresDao) {	
		this.tcAddresDao = tcAddresDao;	
	}	
}