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
package cn.com.softvan.service.consult.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.consult.TcCsConsultMsgBean;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.consult.ITcCsConsultMsgDao;
import cn.com.softvan.dao.entity.consult.TcCsConsultMsg;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.consult.IConsultMsgManager;
/**
 *<p>咨询 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class ConsultMsgManager extends BaseManager implements IConsultMsgManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(ConsultMsgManager.class);
	/**咨询  数据库处理 DAO*/
	private ITcCsConsultMsgDao tcCsConsultMsgDao;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	public String saveOrUpdateData(TcCsConsultMsgBean bean){
		String msg="1";
		try {
			TcCsConsultMsg dto=new TcCsConsultMsg();
			dto.setId(bean.getId());//消息id
			dto.setConsult_id(bean.getConsult_id());//咨询id
			dto.setUser_id(bean.getUser_id());//用户id
			dto.setContent(bean.getContent());//内容
			dto.setPic_url(bean.getPic_url());//图片路径
			dto.setCs_id(bean.getCs_id());//操作人
//			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag
			dto.setInfo_source(bean.getInfo_source());//消息来源1,信息接收,2,客服回复
//			//判断数据是否存在
//			if(tcCsConsultMsgDao.isDataYN(dto)!=0){
//				//数据存在
//				tcCsConsultMsgDao.updateByPrimaryKeySelective(dto);
//			}else
			{
				if(Validator.isEmpty(dto.getId())){
					dto.setId(IdUtils.createUUID(32));
				}
				//新增
				tcCsConsultMsgDao.insert(dto);
			}
		} catch (Exception e) {
			msg="信息保存,数据库错误!";
			log.error(msg,e);
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
	public List<TcCsConsultMsgBean> findDataIsList(TcCsConsultMsgBean bean){
		List<TcCsConsultMsgBean> beans=null;
		try {
			TcCsConsultMsg dto=new TcCsConsultMsg();
			dto.setId(bean.getId());//消息id
			dto.setConsult_id(bean.getConsult_id());//咨询id
			dto.setUser_id(bean.getUser_id());//用户id
			dto.setContent(bean.getContent());//内容
			dto.setPic_url(bean.getPic_url());//图片路径
			dto.setCs_id(bean.getCs_id());//操作人
//			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag
			dto.setInfo_source(bean.getInfo_source());//消息来源1,信息接收,2,客服回复
			beans=tcCsConsultMsgDao.findDataIsList(dto);
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
	public List<TcCsConsultMsgBean> findDataIsPage(TcCsConsultMsgBean bean){
		List<TcCsConsultMsgBean> beans=null;
		try {
			TcCsConsultMsg dto=new TcCsConsultMsg();
			dto.setId(bean.getId());//消息id
			dto.setConsult_id(bean.getConsult_id());//咨询id
			dto.setUser_id(bean.getUser_id());//用户id
			dto.setContent(bean.getContent());//内容
			dto.setPic_url(bean.getPic_url());//图片路径
			dto.setCs_id(bean.getCs_id());//操作人
//			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag
			dto.setInfo_source(bean.getInfo_source());//消息来源1,信息接收,2,客服回复
			dto.setPageInfo(bean.getPageInfo());
			beans=tcCsConsultMsgDao.findDataIsPage(dto);
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
	public TcCsConsultMsgBean findDataById(TcCsConsultMsgBean bean){
		TcCsConsultMsgBean bean1=null;
		try {
			TcCsConsultMsg dto=new TcCsConsultMsg();
			if(bean!=null){
				dto.setId(bean.getId());//消息id
			}
			bean1=tcCsConsultMsgDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 咨询  数据库处理 DAO取得
	 * @return 咨询  数据库处理 DAO
	 */
	public ITcCsConsultMsgDao getTcCsConsultMsgDao() {
	    return tcCsConsultMsgDao;
	}
	/**
	 * 咨询  数据库处理 DAO设定
	 * @param tcCsConsultMsgDao 咨询  数据库处理 DAO
	 */
	public void setTcCsConsultMsgDao(ITcCsConsultMsgDao tcCsConsultMsgDao) {
	    this.tcCsConsultMsgDao = tcCsConsultMsgDao;
	}
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	public String updateData(TcCsConsultMsgBean bean){
		String msg="1";
		try {
			TcCsConsultMsg dto=new TcCsConsultMsg();
			dto.setId(bean.getId());//消息id
			dto.setConsult_id(bean.getConsult_id());//咨询id
			dto.setUser_id(bean.getUser_id());//用户id
			dto.setCs_id(bean.getCs_id());//操作人
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setVersion(bean.getVersion());//VERSION
			dto.setDel_flag(bean.getDel_flag());//del_flag
			dto.setInfo_source("1");//消息来源1,信息接收,2,客服回复
			//数据存在
			tcCsConsultMsgDao.updateByPrimaryKeySelective(dto);
		} catch (Exception e) {
			msg="信息保存,数据库错误!";
			log.error(msg,e);
		}
		return msg;
	}
}