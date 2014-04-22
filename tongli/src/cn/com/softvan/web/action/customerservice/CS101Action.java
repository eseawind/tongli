/*
 * 客服管理  ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.16  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.customerservice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.customerservice.TcCsCustomerServiceBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.customerservice.ICustomerServiceManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 客服管理  ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class CS101Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(CS101Action.class);
	
	/**BEAN类  客服信息*/
	private TcCsCustomerServiceBean bean;
	/**BEAN类  客服 集合*/
	private List<TcCsCustomerServiceBean> beans;
	/** 客服管理 业务处理*/
	private ICustomerServiceManager customerServiceManager;
	//
	public CS101Action() {
		log.info("默认构造器......CS101Action");
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
		log.info("CS101Action init.........");
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
		TcCsCustomerServiceBean bean1=new TcCsCustomerServiceBean();
		bean1.setPageInfo(page);
		//列表
		List<TcCsCustomerServiceBean> beans=customerServiceManager.findDataIsPage(bean1);
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
		log.info("CS101Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcCsCustomerServiceBean bean1=new TcCsCustomerServiceBean();
			bean1.setId(id);
			bean=customerServiceManager.findDataById(bean1);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
			request.setAttribute("date", sdf.format(new Date()));
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
		log.info("CS101Action edit.........");
		if(bean!=null){
			String msg="1";
			try {
				if(Validator.isNullEmpty(bean.getUid())){
					msg="保存失败!信息为空!";
				}else{
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					msg=customerServiceManager.saveOrUpdateData(bean);
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
		log.info("CS101Action del.........");
		
		String id=request.getParameter("id");
		TcCsCustomerServiceBean bean1=new TcCsCustomerServiceBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=customerServiceManager.deleteDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		return SUCCESS;
	}

	/**
	 * BEAN类  客服信息取得
	 * @return BEAN类  客服信息
	 */
	public TcCsCustomerServiceBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  客服信息设定
	 * @param bean BEAN类  客服信息
	 */
	public void setBean(TcCsCustomerServiceBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  客服 集合取得
	 * @return BEAN类  客服 集合
	 */
	public List<TcCsCustomerServiceBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  客服 集合设定
	 * @param beans BEAN类  客服 集合
	 */
	public void setBeans(List<TcCsCustomerServiceBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 客服管理 业务处理取得
	 * @return 客服管理 业务处理
	 */
	public ICustomerServiceManager getCustomerServiceManager() {
	    return customerServiceManager;
	}

	/**
	 * 客服管理 业务处理设定
	 * @param customerServiceManager 客服管理 业务处理
	 */
	public void setCustomerServiceManager(ICustomerServiceManager customerServiceManager) {
	    this.customerServiceManager = customerServiceManager;
	}
}
