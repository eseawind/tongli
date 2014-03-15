/*
 * 微信服务_公共账号 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.07  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.wechar.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.wechar.TcWxPublicUserBean;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.dao.daointer.wechar.ITcWxPublicUserDao;
import cn.com.softvan.dao.entity.wechar.TcWxPublicUser;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.wechar.ITcWxPublicUserManager;
/**
 *<p>微信服务_公共账号 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class TcWxPublicUserManager extends BaseManager implements ITcWxPublicUserManager {
	/** 日志 */
	private static final transient Logger log = Logger
			.getLogger(TcWxPublicUserManager.class);
	/**微信服务_公共账号  数据库处理 DAO*/
	private ITcWxPublicUserDao tcWxPublicUserDao;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String saveOrUpdateData(TcWxPublicUserBean bean){
		String msg="1";
		if(bean!=null){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TcWxPublicUser dto=new TcWxPublicUser();
				dto.setId(bean.getId());//id
				dto.setName(bean.getName());//公共号名称
				dto.setOpenid(bean.getOpenid());//公共号原始id
				dto.setUserid(bean.getUserid());//微信号
				dto.setPassword(bean.getPassword());//密码
				dto.setAppid(bean.getAppid());//AppId
				dto.setAppsecret(bean.getAppsecret());//AppSecret
				dto.setProvince(bean.getProvince());//省
				dto.setCity(bean.getCity());//市
				dto.setEmail(bean.getEmail());//邮箱
				dto.setType(bean.getType());//类型
				dto.setDate_created(sdf.parse(bean.getDate_created()));//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
				dto.setLast_updated(sdf.parse(bean.getLast_updated()));//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//del_flag
				//判断数据是否存在
				if(tcWxPublicUserDao.isDataYN(dto)!=0){
					//数据存在
					tcWxPublicUserDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					dto.setId(IdUtils.createUUID(32));
					tcWxPublicUserDao.insert(dto);
				}
			} catch (Exception e) {
				msg="信息保存失败,数据库处理错误!";
				log.error(msg, e);
			}
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
	public List<TcWxPublicUserBean> findDataIsList(TcWxPublicUserBean bean){
		List<TcWxPublicUserBean> beans=null;
		try {
			TcWxPublicUser dto=new TcWxPublicUser();
			beans=tcWxPublicUserDao.findDataIsList(dto);
		} catch (Exception e) {
			log.error("信息列表查询失败,数据库错误!", e);
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
	public TcWxPublicUserBean findDataById(TcWxPublicUserBean bean){
		TcWxPublicUserBean bean1=null;
		try {
			TcWxPublicUser dto=new TcWxPublicUser();
//			dto.setId(bean.getId());//id
			dto.setCreate_id(bean.getCreate_id());//管理员id
			bean1=tcWxPublicUserDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 微信服务_公共账号  数据库处理 DAO取得
	 * @return 微信服务_公共账号  数据库处理 DAO
	 */
	public ITcWxPublicUserDao getTcWxPublicUserDao() {
	    return tcWxPublicUserDao;
	}
	/**
	 * 微信服务_公共账号  数据库处理 DAO设定
	 * @param tcWxPublicUserDao 微信服务_公共账号  数据库处理 DAO
	 */
	public void setTcWxPublicUserDao(ITcWxPublicUserDao tcWxPublicUserDao) {
	    this.tcWxPublicUserDao = tcWxPublicUserDao;
	}
}
