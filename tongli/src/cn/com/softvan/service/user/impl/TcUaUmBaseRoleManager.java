/**
 * 操作日志记录  service实现类
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00     2014 下午5:11:32             wangzi              程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 picc  System. - All Rights Reserved.
 */
package cn.com.softvan.service.user.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.backuser.TcUaUmBaseRoleBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.user.ITcUaUmBaseRoleDao;
import cn.com.softvan.dao.entity.user.TcUaUmBaseRole;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.user.ITcUaUmBaseRoleManager;

/**
 * <p> 操作日志记录 service实现层 <p>
 * @author wangzi
 *
 */
public class TcUaUmBaseRoleManager extends BaseManager  implements ITcUaUmBaseRoleManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(TcUaUmBaseUserManager.class);
	
	/**信息DAO 接口类*/
	private ITcUaUmBaseRoleDao tcUaUmBaseRoleDao;
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = CommonConstant.DB_DEFAULT_TIMEOUT, rollbackFor = {
			Exception.class, RuntimeException.class })
	public String saveOrUpdateData(TcUaUmBaseRoleBean bean) throws Exception {
		
		String msg="1";
		if(bean!=null){
			try {
				 TcUaUmBaseRole dto=new  TcUaUmBaseRole();
				dto.setRole_id(bean.getRole_id());
				dto.setRo_name(bean.getRo_name());//角色名称
				if(bean.getRo_priority()!=null&&!bean.getRo_priority().equals("")){
					dto.setRo_priority(bean.getRo_priority());//排序
				}else{
					dto.setRo_priority("1");//排序
				}
				
				dto.setRo_super(bean.getRo_super());//所有权限
				dto.setDescription(bean.getDescription());
				dto.setCreate_id(bean.getCreate_id());//建立者id
				dto.setCreate_ip(bean.getCreate_ip());//建立者ip
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				dto.setDel_flag(bean.getDel_flag());//del_flag

				//判断数据是否存在
				if(tcUaUmBaseRoleDao.isDataYN(dto)!=0){
					//数据存在
					tcUaUmBaseRoleDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getRole_id())){
						dto.setRole_id(IdUtils.createUUID(32));
					}
					tcUaUmBaseRoleDao.insert(dto);
				}
				
				//==================保存角色和权限的关联关系============================================
				 
				if(bean.getPerms()!=null&&bean.getPerms().size()>0){
					tcUaUmBaseRoleDao.deletePermById(bean.getRole_id());
					tcUaUmBaseRoleDao.savePermission(bean.getRole_id(),bean.getCreate_id(),bean.getCreate_ip(),bean.getUpdate_id(),bean.getUpdate_ip(),bean.getPerms());
				}
			} catch (Exception e) {
				msg="信息保存失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}

	public String deleteData(TcUaUmBaseRoleBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String recoveryDataById(TcUaUmBaseRoleBean bean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = CommonConstant.DB_DEFAULT_TIMEOUT, rollbackFor = {
			Exception.class, RuntimeException.class })
	public String deleteDataById(TcUaUmBaseRoleBean bean) throws Exception {
		String msg="1";
		if(bean!=null){
			try {
				TcUaUmBaseRole dto=new TcUaUmBaseRole();
				dto.setRole_id(bean.getRole_id());//ID
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				tcUaUmBaseRoleDao.deleteById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}

	public List<TcUaUmBaseRoleBean> findDataIsPage(TcUaUmBaseRoleBean bean) {
		return null;
	}

	public List<TcUaUmBaseRoleBean> findDataIsList(TcUaUmBaseRoleBean bean) {
		
		
		List<TcUaUmBaseRoleBean> beans=null;
		try {
			TcUaUmBaseRole dto=new TcUaUmBaseRole();
    	   if(bean!=null){
    		    dto.setRole_id(bean.getRole_id());
				dto.setRo_name(bean.getRo_name());
				dto.setCreate_id(bean.getCreate_id());//建立者id
				dto.setCreate_ip(bean.getCreate_ip());//建立者ip
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				dto.setVersion(bean.getVersion());//version
				dto.setDel_flag(bean.getDel_flag());//del_flag
			
			dto.setPageInfo(bean.getPageInfo());
    	   }
			beans=(List<TcUaUmBaseRoleBean>) tcUaUmBaseRoleDao.findDataIsList(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}

	public TcUaUmBaseRoleBean findDataById(TcUaUmBaseRoleBean bean) {
		TcUaUmBaseRoleBean bean1=null;
	       try {
	    	   TcUaUmBaseRole dto=new TcUaUmBaseRole();
	    	   if(bean!=null){
	    		    dto.setRole_id(bean.getRole_id());
				}
				bean1=(TcUaUmBaseRoleBean) tcUaUmBaseRoleDao.selectByPrimaryKey(dto);
			} catch (Exception e) {
				log.error("信息详情查询失败,数据库错误!", e);
			}
			return bean1;
	}

	/**
	 * 信息DAO 接口类取得。
	 * @return 信息DAO 接口类
	 */
	public ITcUaUmBaseRoleDao getTcUaUmBaseRoleDao() {
	    return tcUaUmBaseRoleDao;
	}

	/**
	 * 信息DAO 接口类设定。
	 * @param tcUaUmBaseRoleDao 信息DAO 接口类
	 */
	public void setTcUaUmBaseRoleDao(ITcUaUmBaseRoleDao tcUaUmBaseRoleDao) {
	    this.tcUaUmBaseRoleDao = tcUaUmBaseRoleDao;
	}
 
}
