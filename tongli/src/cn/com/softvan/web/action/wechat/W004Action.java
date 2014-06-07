/*
 * 素材管理_文章消息 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.18  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.wechat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.sys.TcSysNewsBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.sys.INewsManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 素材管理_文章消息 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class W004Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(W004Action.class);
	
	/**BEAN类  资讯信息*/
	private TcSysNewsBean bean;
	/**BEAN类  资讯信息 集合*/
	private List<TcSysNewsBean> beans;
	/**资讯信息管理 业务处理*/
	private INewsManager newsManager;
	/**信息来源0微信文章1系统资讯*/
	private final String info_source="0";
	//
	public W004Action() {
		log.info("默认构造器......W004Action");
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
		log.info("W004Action init.........");
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
		TcSysNewsBean bean1 = new TcSysNewsBean();
		bean1.setPageInfo(page);
		bean1.setInfo_source(info_source);
		//栏目资讯列表
		List<TcSysNewsBean> beans=newsManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "init";
	}
	/**
	 * <p>
	 * 信息选择页面
	 * </p>
	 * <ol>
	 * [功能概要] <div>选择页面。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list1() {
		log.info("W004Action list1.........");
		int offset = 0;
		// 分页偏移量
		if (!Validator.isNullEmpty(request.getParameter("offset"))
				&& Validator.isNum(request.getParameter("offset"))) {
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		//关键字
		String keyword=request.getParameter("keyword");
		
		PageInfo page = new PageInfo(); 
		//当前页
		page.setCurrOffset(offset);
		//每页显示条数
		page.setPageRowCount(5);
		TcSysNewsBean bean1 = new TcSysNewsBean();
		bean1.setPageInfo(page);
		bean1.setInfo_source(info_source);
		bean1.setKeyword(keyword);
		//栏目资讯列表
		List<TcSysNewsBean> beans=newsManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "list1";
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
		log.info("W004Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcSysNewsBean bean1=new TcSysNewsBean();
			bean1.setId(id);
			bean1.setInfo_source(info_source);
			bean=newsManager.findDataById(bean1);
		}else{
			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
			request.setAttribute("date", sdf.format(new Date()));
		}
		if(bean==null){
			bean=new TcSysNewsBean();
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
		log.info("W004Action del.........");
		String id=request.getParameter("id");
		TcSysNewsBean bean1=new TcSysNewsBean();
		bean1.setId(id);
		bean1.setInfo_source(info_source);
		String msg="1";
		try {
			msg=newsManager.deleteDataById(bean1);
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
		log.info("W003Action edit.........");
		if(bean!=null){
			String msg="1";
			try {
				if(Validator.isEmpty(bean.getTitle())||Validator.isEmpty(bean.getDetail_info())){
					msg="保存失败!信息为空!";
				}else{
					BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
					if(user!=null){
						bean.setCreate_ip(getIpAddr());
						bean.setCreate_id(user.getUser_id());
						bean.setUpdate_ip(getIpAddr());
						bean.setUpdate_id(user.getUser_id());
					}
					bean.setInfo_source(info_source);
					msg=newsManager.saveOrUpdateData(bean);
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
		log.info("W004Action view.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcSysNewsBean bean1=new TcSysNewsBean();
			bean1.setId(id);
			bean1.setInfo_source(info_source);
			bean=newsManager.findDataById(bean1);
		}
		return "view";
	}

	/**
	 * BEAN类  资讯信息取得
	 * @return BEAN类  资讯信息
	 */
	public TcSysNewsBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  资讯信息设定
	 * @param bean BEAN类  资讯信息
	 */
	public void setBean(TcSysNewsBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  资讯信息 集合取得
	 * @return BEAN类  资讯信息 集合
	 */
	public List<TcSysNewsBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  资讯信息 集合设定
	 * @param beans BEAN类  资讯信息 集合
	 */
	public void setBeans(List<TcSysNewsBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 资讯信息管理 业务处理取得
	 * @return 资讯信息管理 业务处理
	 */
	public INewsManager getNewsManager() {
	    return newsManager;
	}

	/**
	 * 资讯信息管理 业务处理设定
	 * @param newsManager 资讯信息管理 业务处理
	 */
	public void setNewsManager(INewsManager newsManager) {
	    this.newsManager = newsManager;
	}
}
