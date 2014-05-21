/*
 * 通讯录信息管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.20  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.sys.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.sys.TcSysTelBookBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.sys.ITcSysTelBookDao;
import cn.com.softvan.dao.entity.sys.TcSysTelBook;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.sys.ITelBookManager;
/**
 *<p>通讯录信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class TelBookManager extends BaseManager implements ITelBookManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(TelBookManager.class);
	
	/**信息DAO 接口类*/
	private ITcSysTelBookDao tcSysTelBookDao;
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
	public String saveOrUpdateData(TcSysTelBookBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysTelBook dto=new TcSysTelBook();
				dto.setId(bean.getId());//id
				dto.setUser_id(bean.getUser_id());//用户id
				dto.setName(bean.getName());//姓名
				dto.setTel(bean.getTel());//电话号码
				dto.setAddres(bean.getAddres());//地址
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
				if(tcSysTelBookDao.isDataYN(dto)!=0){
					//数据存在
					tcSysTelBookDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcSysTelBookDao.insert(dto);
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
	public String deleteData(TcSysTelBookBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysTelBook dto=new TcSysTelBook();
				dto.setId(bean.getId());//id
				tcSysTelBookDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcSysTelBookBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysTelBook dto=new TcSysTelBook();
				dto.setId(bean.getId());//ID
				tcSysTelBookDao.deleteById(dto);
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
	public List<TcSysTelBookBean> findDataIsPage(TcSysTelBookBean bean){
		List<TcSysTelBookBean> beans=null;
		try {
    	   TcSysTelBook dto=new TcSysTelBook();
    	   if(bean!=null){
    		   dto.setId(bean.getId());//id
    		   dto.setUser_id(bean.getUser_id());//用户id
    		   dto.setName(bean.getName());//姓名
    		   dto.setTel(bean.getTel());//电话号码
    		   dto.setAddres(bean.getAddres());//地址
    		   dto.setNote(bean.getNote());//备注
    		   dto.setDate_created(bean.getDate_created());//数据输入日期
    		   dto.setCreate_id(bean.getCreate_id());//建立者ID
    		   dto.setCreate_ip(bean.getCreate_ip());//建立者IP
    		   dto.setLast_updated(bean.getLast_updated());//资料更新日期
    		   dto.setUpdate_id(bean.getUpdate_id());//修改者ID
    		   dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
    		   dto.setDel_flag(bean.getDel_flag());//是否删除
    		   dto.setVersion(bean.getVersion());//VERSION

			dto.setPageInfo(bean.getPageInfo());//分页对象
    	   }
			beans=tcSysTelBookDao.findDataIsPage(dto);
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
	public List<TcSysTelBookBean> findDataIsList(TcSysTelBookBean bean){
		List<TcSysTelBookBean> beans=null;
		try {
	    	   TcSysTelBook dto=new TcSysTelBook();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
	    		   dto.setUser_id(bean.getUser_id());//用户id
	    		   dto.setName(bean.getName());//姓名
	    		   dto.setTel(bean.getTel());//电话号码
	    		   dto.setAddres(bean.getAddres());//地址
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
				beans=tcSysTelBookDao.findDataIsList(dto);
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
	public TcSysTelBookBean findDataById(TcSysTelBookBean bean){
       TcSysTelBookBean bean1=null;
       try {
    	   TcSysTelBook dto=new TcSysTelBook();
    	   if(bean!=null){
    		   dto.setId(bean.getId());//id
    		   dto.setUser_id(bean.getUser_id());//用户id
    		   dto.setName(bean.getName());//姓名
    		   dto.setTel(bean.getTel());//电话号码
    		   dto.setAddres(bean.getAddres());//地址
    		   dto.setNote(bean.getNote());//备注
    		   dto.setDate_created(bean.getDate_created());//数据输入日期
    		   dto.setCreate_id(bean.getCreate_id());//建立者ID
    		   dto.setCreate_ip(bean.getCreate_ip());//建立者IP
    		   dto.setLast_updated(bean.getLast_updated());//资料更新日期
    		   dto.setUpdate_id(bean.getUpdate_id());//修改者ID
    		   dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
    		   dto.setDel_flag(bean.getDel_flag());//是否删除
    		   dto.setVersion(bean.getVersion());//VERSION

    	   }
			bean1=tcSysTelBookDao.selectByPrimaryKey(dto);
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
	public String recoveryDataById(TcSysTelBookBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysTelBook dto=new TcSysTelBook();
				dto.setId(bean.getId());//ID
				tcSysTelBookDao.recoveryDataById(dto);
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
	public ITcSysTelBookDao getTcSysTelBookDao() {
	    return tcSysTelBookDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcSysTelBookDao 信息DAO 接口类
	 */
	public void setTcSysTelBookDao(ITcSysTelBookDao tcSysTelBookDao) {
	    this.tcSysTelBookDao = tcSysTelBookDao;
	}
}
