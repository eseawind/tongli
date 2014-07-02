/*
 * 通讯录 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.20  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.sys;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.sys.TcSysTelBookBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.sys.ITelBookManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 通讯录 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class S004Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(S004Action.class);
	
	/**BEAN类  通讯录信息*/
	private TcSysTelBookBean bean;
	/**BEAN类  通讯录信息 集合*/
	private List<TcSysTelBookBean> beans;
	/**通讯录 信息管理 业务处理*/
	private ITelBookManager telBookManager;
	//
	public S004Action() {
		log.info("默认构造器......S004Action");
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
		log.info("S004Action init.........");
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
		TcSysTelBookBean bean1 = new TcSysTelBookBean();
		bean1.setPageInfo(page);
		//栏目资讯列表
		List<TcSysTelBookBean> beans=telBookManager.findDataIsPage(bean1);
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
		log.info("S004Action edit.........");
		String id=request.getParameter("id");
		TcSysTelBookBean bean1=new TcSysTelBookBean();
		bean1.setId(id);
		bean=telBookManager.findDataById(bean1);
		if(bean==null){
			bean=new TcSysTelBookBean();
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
		log.info("S004Action del.........");
		String id=request.getParameter("id");
		TcSysTelBookBean bean1=new TcSysTelBookBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=telBookManager.deleteDataById(bean1);
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
		log.info("S004Action delxx.........");
		String id=request.getParameter("id");
		TcSysTelBookBean bean1=new TcSysTelBookBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=telBookManager.deleteData(bean1);
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
		log.info("S004Action edit.........");
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
					msg=telBookManager.saveOrUpdateData(bean);
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
		log.info("S004Action view.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcSysTelBookBean bean1=new TcSysTelBookBean();
			bean1.setId(id);
			bean=telBookManager.findDataById(bean1);
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
		TcSysTelBookBean bean1 = new TcSysTelBookBean();
		bean1.setPageInfo(page);
		//已删除
		bean1.setDel_flag("1");
		//栏目资讯列表
		List<TcSysTelBookBean> beans=telBookManager.findDataIsPage(bean1);
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
		TcSysTelBookBean bean1=new TcSysTelBookBean();
		bean1.setId(id);
		String msg="1";
		try {
			msg=telBookManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}

	/**
	 * BEAN类  通讯录信息取得
	 * @return BEAN类  通讯录信息
	 */
	public TcSysTelBookBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  通讯录信息设定
	 * @param bean BEAN类  通讯录信息
	 */
	public void setBean(TcSysTelBookBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  通讯录信息 集合取得
	 * @return BEAN类  通讯录信息 集合
	 */
	public List<TcSysTelBookBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  通讯录信息 集合设定
	 * @param beans BEAN类  通讯录信息 集合
	 */
	public void setBeans(List<TcSysTelBookBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 通讯录 信息管理 业务处理取得
	 * @return 通讯录 信息管理 业务处理
	 */
	public ITelBookManager getTelBookManager() {
	    return telBookManager;
	}

	/**
	 * 通讯录 信息管理 业务处理设定
	 * @param telBookManager 通讯录 信息管理 业务处理
	 */
	public void setTelBookManager(ITelBookManager telBookManager) {
	    this.telBookManager = telBookManager;
	}
}
