/*
 * 友情链接 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.26  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.sys;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.sys.TcSysParterBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.sys.IParterManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 友情链接 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class S003Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(S003Action.class);
	
	/**BEAN类  友情链接信息*/
	private TcSysParterBean bean;
	/**BEAN类  友情链接信息 集合*/
	private List<TcSysParterBean> beans;
	/**友情链接 信息管理 业务处理*/
	private IParterManager parterManager;
	//
	public S003Action() {
		log.info("默认构造器......S003Action");
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
		log.info("S003Action init.........");
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
		page.setPageRowCount(15);
		TcSysParterBean bean1 = new TcSysParterBean();
		bean1.setPageInfo(page);
		//栏目资讯列表
		List<TcSysParterBean> beans=parterManager.findDataIsPage(bean1);
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
		log.info("S003Action edit.........");
		String id=request.getParameter("id");
		TcSysParterBean bean1=new TcSysParterBean();
		bean1.setId(id);
		bean=parterManager.findDataById(bean1);
		if(bean==null){
			bean=new TcSysParterBean();
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
		log.info("S003Action del.........");
		String id=request.getParameter("id");
		TcSysParterBean bean1=new TcSysParterBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=parterManager.deleteDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * <p>
	 * 删除。
	 * </p>
	 * <ol>
	 * [功能概要] <div>物理删除。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String delxx() {
		log.info("S003Action delxx.........");
		String id=request.getParameter("id");
		TcSysParterBean bean1=new TcSysParterBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=parterManager.deleteData(bean1);
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
		log.info("S003Action edit.........");
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
					msg=parterManager.saveOrUpdateData(bean);
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
		log.info("S003Action view.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcSysParterBean bean1=new TcSysParterBean();
			bean1.setId(id);
			bean=parterManager.findDataById(bean1);
		}
		return "view";
	}
	/**
	 * <p>
	 * 回收站。
	 * </p>
	 * <ol>
	 * [功能概要] <div>回收站。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String recycle() {
		log.info("S001Action recycle.........");
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
		page.setPageRowCount(15);
		TcSysParterBean bean1 = new TcSysParterBean();
		bean1.setPageInfo(page);
		//已删除
		bean1.setDel_flag("1");
		//栏目资讯列表
		List<TcSysParterBean> beans=parterManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "recycle";
	}
	/**
	 * <p>
	 * 恢复。
	 * </p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String recovery() {
		log.info("S001Action recovery.........");
		String id=request.getParameter("id");
		TcSysParterBean bean1=new TcSysParterBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=parterManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * BEAN类  友情链接信息取得
	 * @return BEAN类  友情链接信息
	 */
	public TcSysParterBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  友情链接信息设定
	 * @param bean BEAN类  友情链接信息
	 */
	public void setBean(TcSysParterBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  友情链接信息 集合取得
	 * @return BEAN类  友情链接信息 集合
	 */
	public List<TcSysParterBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  友情链接信息 集合设定
	 * @param beans BEAN类  友情链接信息 集合
	 */
	public void setBeans(List<TcSysParterBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 友情链接 信息管理 业务处理取得
	 * @return 友情链接 信息管理 业务处理
	 */
	public IParterManager getParterManager() {
	    return parterManager;
	}

	/**
	 * 友情链接 信息管理 业务处理设定
	 * @param parterManager 友情链接 信息管理 业务处理
	 */
	public void setParterManager(IParterManager parterManager) {
	    this.parterManager = parterManager;
	}
}
