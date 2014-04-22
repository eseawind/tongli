/*
 * 咨询 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.16  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.customerservice.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.customerservice.TcCsCustomerServiceBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.customerservice.ITcCsCustomerServiceDao;
import cn.com.softvan.dao.entity.customerservice.TcCsCustomerService;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.customerservice.ICustomerServiceManager;
/**
 *<p>咨询 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class CustomerServiceManager extends BaseManager implements ICustomerServiceManager {
	/** 日志 */
	private static final transient Logger log = Logger
			.getLogger(CustomerServiceManager.class);
	/** 客服  数据库处理 DAO*/
	private ITcCsCustomerServiceDao tcCsCustomerServiceDao;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	public String saveOrUpdateData(TcCsCustomerServiceBean bean){
		String msg="1";
		try {
			TcCsCustomerService dto=new TcCsCustomerService();
			dto.setId(bean.getId());//id
			dto.setUid(bean.getUid());//登录id
			dto.setPwd(bean.getPwd());//密码
			dto.setName(bean.getName());//名称
			dto.setCs_state(bean.getCs_state());//客服状态
			dto.setLogin_state(bean.getLogin_state());//登录状态
			dto.setLogin_date(bean.getLogin_date());//登录时间
			dto.setCs_count(bean.getCs_count());//当前接入客户
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag

			//判断数据是否存在
			if(tcCsCustomerServiceDao.isDataYN(dto)!=0){
				//数据存在
				tcCsCustomerServiceDao.updateByPrimaryKeySelective(dto);
			}else{
				if(Validator.isEmpty(dto.getId())){
					dto.setId(IdUtils.createUUID(32));
				}
				//新增
				tcCsCustomerServiceDao.insert(dto);
			}
		} catch (Exception e) {
			log.error("信息保存,数据库错误!",e);
		}
		return msg;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCsCustomerServiceBean> findDataIsList(TcCsCustomerServiceBean bean){
		List<TcCsCustomerServiceBean> beans=null;
		try {
			TcCsCustomerService dto=new TcCsCustomerService();
			dto.setId(bean.getId());//id
			dto.setUid(bean.getUid());//登录id
			dto.setPwd(bean.getPwd());//密码
			dto.setName(bean.getName());//名称
			dto.setCs_state(bean.getCs_state());//客服状态
			dto.setLogin_state(bean.getLogin_state());//登录状态
			dto.setLogin_date(bean.getLogin_date());//登录时间
			dto.setCs_count(bean.getCs_count());//当前接入客户
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag
			beans=tcCsCustomerServiceDao.findDataIsList(dto);
		} catch (Exception e) {
			log.error("信息列表查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcCsCustomerServiceBean> findDataIsPage(TcCsCustomerServiceBean bean){
		List<TcCsCustomerServiceBean> beans=null;
		try {
			TcCsCustomerService dto=new TcCsCustomerService();
			dto.setId(bean.getId());//id
			dto.setUid(bean.getUid());//登录id
			dto.setPwd(bean.getPwd());//密码
			dto.setName(bean.getName());//名称
			dto.setCs_state(bean.getCs_state());//客服状态
			dto.setLogin_state(bean.getLogin_state());//登录状态
			dto.setLogin_date(bean.getLogin_date());//登录时间
			dto.setCs_count(bean.getCs_count());//当前接入客户
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag
			dto.setPageInfo(bean.getPageInfo());
			beans=tcCsCustomerServiceDao.findDataIsPage(dto);
		} catch (Exception e) {
			log.error("信息分页查询失败,数据库错误!", e);
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
	public TcCsCustomerServiceBean findDataById(TcCsCustomerServiceBean bean){
		TcCsCustomerServiceBean bean1=null;
		try {
			TcCsCustomerService dto=new TcCsCustomerService();
			if(bean!=null){
				dto.setId(bean.getId());//id
				dto.setUid(bean.getUid());//登录id
				dto.setPwd(bean.getPwd());//密码
				dto.setName(bean.getName());//名称
				dto.setCs_state(bean.getCs_state());//客服状态
				dto.setLogin_state(bean.getLogin_state());//登录状态
				dto.setLogin_date(bean.getLogin_date());//登录时间
				dto.setCs_count(bean.getCs_count());//当前接入客户
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setVersion(bean.getVersion());//VERSION
				dto.setDel_flag(bean.getDel_flag());//del_flag
			}
			bean1=tcCsCustomerServiceDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * <p>密码认证。</p>
	 * <ol>[功能概要] 
	 * <div>密码认证。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcCsCustomerServiceBean checkPWD(TcCsCustomerServiceBean bean){
		TcCsCustomerServiceBean bean1=null;
		try {
			TcCsCustomerService dto=new TcCsCustomerService();
			if(bean!=null){
				dto.setId(bean.getId());//id
				dto.setUid(bean.getUid());//登录id
				dto.setPwd(bean.getPwd());//密码
				dto.setName(bean.getName());//名称
				dto.setCs_state(bean.getCs_state());//客服状态
				dto.setLogin_state(bean.getLogin_state());//登录状态
				dto.setLogin_date(bean.getLogin_date());//登录时间
				dto.setCs_count(bean.getCs_count());//当前接入客户
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setVersion(bean.getVersion());//VERSION
				dto.setDel_flag(bean.getDel_flag());//del_flag
			}
			bean1=tcCsCustomerServiceDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 客服  数据库处理 DAO取得
	 * @return 客服  数据库处理 DAO
	 */
	public ITcCsCustomerServiceDao getTcCsCustomerServiceDao() {
	    return tcCsCustomerServiceDao;
	}
	/**
	 * 客服  数据库处理 DAO设定
	 * @param tcCsCustomerServiceDao 客服  数据库处理 DAO
	 */
	public void setTcCsCustomerServiceDao(ITcCsCustomerServiceDao tcCsCustomerServiceDao) {
	    this.tcCsCustomerServiceDao = tcCsCustomerServiceDao;
	}
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcCsCustomerServiceBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCsCustomerService dto=new TcCsCustomerService();
				dto.setId(bean.getId());//id
				tcCsCustomerServiceDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcCsCustomerServiceBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCsCustomerService dto=new TcCsCustomerService();
				dto.setId(bean.getId());//ID
				tcCsCustomerServiceDao.deleteById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcCsCustomerServiceBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcCsCustomerService dto=new TcCsCustomerService();
				dto.setId(bean.getId());//ID
				tcCsCustomerServiceDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息恢复失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
}