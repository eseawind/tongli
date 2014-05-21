/*
 * 微信服务_自动回复(关键字)_图片消息 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.04  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.wechat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.wechat.TcWxInfoBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.JedisHelper;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.wechat.ITcWxInfoManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 微信服务_自动回复(关键字)_图片消息 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class W003Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(W003Action.class);
	/**BEAN类  微信资源信息*/
	private TcWxInfoBean bean;
	/**BEAN类  微信资源信息 集合*/
	private List<TcWxInfoBean> beans;
	/**微信服务_资源信息管理 业务处理*/
	private ITcWxInfoManager tcWxInfoManager;
	/** 信息类型 */
	private final String msgType="image";
	/**信息来源0微信文章1系统资讯*/
	private final String info_source="0";
	//
	public W003Action() {
		log.info("默认构造器......W003Action");
	}

	/**
	 * <p>
	 * 初始化处理。
	 * </p>
	 * <ol>
	 * [功能概要] <div>初始化处理。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String init() {
		log.info("W003Action init.........");
		int offset = 0;
		// 分页偏移量
		if (!Validator.isNullEmpty(request.getParameter("offset"))
				&& Validator.isNum(request.getParameter("offset"))) {
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		PageInfo page = new PageInfo(); 
		//当前页
		page.setCurrOffset(offset);
		//每页显示条数
		page.setPageRowCount(14);
		TcWxInfoBean bean1=new TcWxInfoBean();
		bean1.setMsgtype(msgType);
		bean1.setPageInfo(page);
		bean1.setInfo_source(info_source);
		//栏目资讯列表
		List<TcWxInfoBean> beans=tcWxInfoManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "init";
	}
	/**
	 * <p>
	 * 编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>编辑。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String edit() {
		log.info("W003Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcWxInfoBean bean1=new TcWxInfoBean();
			bean1.setId(id);
			bean1.setInfo_source("0");
			bean1.setMsgtype(msgType);
			bean=tcWxInfoManager.findDataById(bean1);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
			request.setAttribute("date", sdf.format(new Date()));
		}
		if(bean==null){
			bean=new TcWxInfoBean();
			bean.setId(IdUtils.createUUID(32));
		}
		return "edit";
	}
	/**
	 * <p>
	 * 信息保存
	 * </p>
	 * <ol>
	 * [功能概要] <div>新增。</div>
	 * <div>修改。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String save() {
		log.info("W003Action edit.........");
		if(bean!=null){
			String msg="1";
			try {
				if(!Validator.notEmpty(bean.getPicurl())){
					msg="保存失败!信息为空!";
				}else{
					List<TcWxInfoBean> beans=new ArrayList<TcWxInfoBean>();
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					bean.setMsgtype(msgType);
					bean.setInfo_source("0");
					bean.setSort_num(""+0);
					beans.add(bean);
					msg=tcWxInfoManager.saveOrUpdateData(beans);
				}
			} catch (Exception e) {
				msg=e.getMessage();
			}
			request.setAttribute("msg",msg);
		}else{
			request.setAttribute("msg", "信息保存失败!");
		}
		return SUCCESS;
	}
	/**
	 * <p>
	 * 删除。
	 * </p>
	 * <ol>
	 * [功能概要] <div>删除。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String del() {
		log.info("W003Action del.........");
		
		String id=request.getParameter("id");
		TcWxInfoBean bean1=new TcWxInfoBean();
		bean1.setId(id);
		bean1.setInfo_source("0");
		bean1.setMsgtype(msgType);
		String msg="1";
		try {
			msg=tcWxInfoManager.deleteDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		return SUCCESS;
	}
	/**
	 * <p>
	 * 更新所有缓存。
	 * </p>
	 * <ol>
	 * [功能概要] <div>更新所有缓存。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String uc() throws IOException {
		log.info("W003Action updateAllMsgCache.........");
		(new JedisHelper()).flushDB();
		tcWxInfoManager.updateAllMsgCache();
		getWriter().print("缓存已更新!所有数据将自动缓存3天!");
		return null;
	}
	/**
	 * BEAN类  微信资源信息取得
	 * @return BEAN类  微信资源信息
	 */
	public TcWxInfoBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  微信资源信息设定
	 * @param bean BEAN类  微信资源信息
	 */
	public void setBean(TcWxInfoBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  微信资源信息 集合取得
	 * @return BEAN类  微信资源信息 集合
	 */
	public List<TcWxInfoBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  微信资源信息 集合设定
	 * @param beans BEAN类  微信资源信息 集合
	 */
	public void setBeans(List<TcWxInfoBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 微信服务_资源信息管理 业务处理取得
	 * @return 微信服务_资源信息管理 业务处理
	 */
	public ITcWxInfoManager getTcWxInfoManager() {
	    return tcWxInfoManager;
	}

	/**
	 * 微信服务_资源信息管理 业务处理设定
	 * @param tcWxInfoManager 微信服务_资源信息管理 业务处理
	 */
	public void setTcWxInfoManager(ITcWxInfoManager tcWxInfoManager) {
	    this.tcWxInfoManager = tcWxInfoManager;
	}
}
