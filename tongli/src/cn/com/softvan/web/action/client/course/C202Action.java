/*
 * 课程-预约参观 Action Class
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.17  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.client.course;

import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.course.TcCourseBespeakBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.service.course.ICourseBespeakManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 课程-预约参观  ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C202Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C202Action.class);
	
	/** 课程-预约参观 管理 业务处理*/
	private ICourseBespeakManager courseBespeakManager;
	/**课程-预约参观 信息BEAN*/
	private TcCourseBespeakBean bean;
	/**课程-预约参观 信息BEAN集合*/
	private List<TcCourseBespeakBean> beans;
	//
	public C202Action() {
		log.info("默认构造器......C202Action");
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
		log.info("C202Action init.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcCourseBespeakBean bean1=new TcCourseBespeakBean();
			bean1.setId(id);
			bean=courseBespeakManager.findDataById(bean1);
		}
		if(bean==null){
			bean=new TcCourseBespeakBean();
			bean.setId(IdUtils.createUUID(32));
		}
		return "init";
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
		log.info("C102Action save.........");
		if(bean!=null){
			String msg="1";
			try {
				BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER_MEMBER_INFO);
				if(user!=null){
					bean.setCreate_ip(getIpAddr());
					bean.setCreate_id(user.getUser_id());
					bean.setUpdate_ip(getIpAddr());
					bean.setUpdate_id(user.getUser_id());
				}
				msg=courseBespeakManager.saveOrUpdateData(bean);
			} catch (Exception e) {
				msg=e.getMessage();
			}
			request.setAttribute("msg",msg);
		}else{
			request.setAttribute("msg", "信息保存失败!");
		}
		return "view";
	}
	/**
	 * <p>
	 * 课程详情。
	 * </p>
	 * <ol>
	 * [功能概要] <div>课程详情。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String view() {
		log.info("C202Action view.........");
		TcCourseBespeakBean bean1 = new TcCourseBespeakBean();
		bean1.setId(request.getParameter("cid"));
		bean=courseBespeakManager.findDataById(bean1);
		return "init";
	}

	/**
	 * 课程-预约参观 管理 业务处理取得
	 * @return 课程-预约参观 管理 业务处理
	 */
	public ICourseBespeakManager getCourseBespeakManager() {
	    return courseBespeakManager;
	}

	/**
	 * 课程-预约参观 管理 业务处理设定
	 * @param courseBespeakManager 课程-预约参观 管理 业务处理
	 */
	public void setCourseBespeakManager(ICourseBespeakManager courseBespeakManager) {
	    this.courseBespeakManager = courseBespeakManager;
	}

	/**
	 * 课程-预约参观 信息BEAN取得
	 * @return 课程-预约参观 信息BEAN
	 */
	public TcCourseBespeakBean getBean() {
	    return bean;
	}

	/**
	 * 课程-预约参观 信息BEAN设定
	 * @param bean 课程-预约参观 信息BEAN
	 */
	public void setBean(TcCourseBespeakBean bean) {
	    this.bean = bean;
	}

	/**
	 * 课程-预约参观 信息BEAN集合取得
	 * @return 课程-预约参观 信息BEAN集合
	 */
	public List<TcCourseBespeakBean> getBeans() {
	    return beans;
	}

	/**
	 * 课程-预约参观 信息BEAN集合设定
	 * @param beans 课程-预约参观 信息BEAN集合
	 */
	public void setBeans(List<TcCourseBespeakBean> beans) {
	    this.beans = beans;
	}
}
