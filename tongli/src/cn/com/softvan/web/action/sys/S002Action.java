/*
 * 资讯管理_栏目管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.25  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.sys;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.customerservice.TcCsCustomerServiceBean;
import cn.com.softvan.bean.sys.TcSysNewsTypeBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.sys.INewsTypeManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 资讯管理_栏目管理 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class S002Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(S002Action.class);
	
	/**BEAN类  资讯栏目信息*/
	private TcSysNewsTypeBean bean;
	/**BEAN类  资讯栏目信息 集合*/
	private List<TcSysNewsTypeBean> beans;
	/**资讯栏目信息管理 业务处理*/
	private INewsTypeManager newsTypeManager;
	//
	public S002Action() {
		log.info("默认构造器......S002Action");
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
		log.info("S002Action init.........");
		TcSysNewsTypeBean bean1=new TcSysNewsTypeBean();
		//栏目树
		beans=newsTypeManager.findDataIsTree(bean1);
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
		log.info("S002Action edit.........");
		String id=request.getParameter("id");
		TcSysNewsTypeBean bean1=new TcSysNewsTypeBean();
		bean1.setId(id);
		bean=newsTypeManager.findDataById(bean1);
		//栏目树
		beans=newsTypeManager.findDataIsTree(null);
		if(bean==null){
			bean=new TcSysNewsTypeBean();
			bean.setId(IdUtils.createUUID(32));
		}
		return "edit";
	}
	/**
	 * <p>
	 * 删除。
	 * </p>
	 * <ol>
	 * [功能概要] <div>逻辑删除。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String del() {
		log.info("S002Action del.........");
		String id=request.getParameter("id");
		TcSysNewsTypeBean bean1=new TcSysNewsTypeBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=newsTypeManager.deleteDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
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
		log.info("S002Action edit.........");
		if(bean!=null){
			String msg="1";
			try {
				if(Validator.isEmpty(bean.getName())){
					msg="保存失败!信息为空!";
				}else{
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					msg=newsTypeManager.saveOrUpdateData(bean);
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
	 * 预览。
	 * </p>
	 * <ol>
	 * [功能概要] <div>预览。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String view() {
		log.info("S002Action view.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcSysNewsTypeBean bean1=new TcSysNewsTypeBean();
			bean1.setId(id);
			bean=newsTypeManager.findDataById(bean1);
		}
		return "view";
	}

	/**
	 * BEAN类  资讯栏目信息取得
	 * @return BEAN类  资讯栏目信息
	 */
	public TcSysNewsTypeBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  资讯栏目信息设定
	 * @param bean BEAN类  资讯栏目信息
	 */
	public void setBean(TcSysNewsTypeBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  资讯栏目信息 集合取得
	 * @return BEAN类  资讯栏目信息 集合
	 */
	public List<TcSysNewsTypeBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  资讯栏目信息 集合设定
	 * @param beans BEAN类  资讯栏目信息 集合
	 */
	public void setBeans(List<TcSysNewsTypeBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 资讯栏目信息管理 业务处理取得
	 * @return 资讯栏目信息管理 业务处理
	 */
	public INewsTypeManager getNewsTypeManager() {
	    return newsTypeManager;
	}

	/**
	 * 资讯栏目信息管理 业务处理设定
	 * @param newsTypeManager 资讯栏目信息管理 业务处理
	 */
	public void setNewsTypeManager(INewsTypeManager newsTypeManager) {
	    this.newsTypeManager = newsTypeManager;
	}
}
