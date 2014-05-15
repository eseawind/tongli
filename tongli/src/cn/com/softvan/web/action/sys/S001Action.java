/*
 * 系统管理_资讯管理 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.24  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.sys;

import java.net.URLDecoder;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.customerservice.TcCsCustomerServiceBean;
import cn.com.softvan.bean.sys.TcSysNewsBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.sys.INewsManager;
import cn.com.softvan.service.sys.INewsTypeManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 系统管理_资讯管理 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class S001Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(S001Action.class);
	
	/**BEAN类  资讯信息*/
	private TcSysNewsBean bean;
	/**BEAN类  资讯信息 集合*/
	private List<TcSysNewsBean> beans;
	/**资讯信息管理 业务处理*/
	private INewsManager newsManager;
	/**资讯栏目信息管理 业务处理*/
	private INewsTypeManager newsTypeManager;
	/**信息来源0微信文章1系统资讯*/
	private final String info_source="1";
	public S001Action() {
		log.info("默认构造器......S001Action");
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
		log.info("S001Action init.........");
		String tid=request.getParameter("tid");
		request.setAttribute("tid",tid);
		String k=request.getParameter("k");
		try {
			if(Validator.notEmpty(k)){
				k=URLDecoder.decode(k, "UTF-8");
			}
		} catch (Exception e) {
			log.error("字符编码转化异常", e);
		}
		request.setAttribute("k",k);
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
		//资讯分类
		if(Validator.notEmpty(tid)){
			bean1.setType_id(tid);
		}
		if(Validator.notEmpty(k)){
			bean1.setKeyword(k);
		}
		//栏目资讯列表
		List<TcSysNewsBean> beans=newsManager.findDataIsPage(bean1);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		//栏目树
		request.setAttribute("tree",newsTypeManager.findDataIsTree(null));
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
		log.info("S001Action edit.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcSysNewsBean bean1=new TcSysNewsBean();
			bean1.setId(id);
			bean1.setInfo_source(info_source);
			bean=newsManager.findDataById(bean1);
			//当前资讯所在栏目
			request.setAttribute("news_type_array",newsManager.findTypeDataByIdIsList(bean1));
		}
		if(bean==null){
			bean=new TcSysNewsBean();
			bean.setId(IdUtils.createUUID(32));
		}
		//栏目树
		request.setAttribute("tree",newsTypeManager.findDataIsTree(null));
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
		log.info("S001Action del.........");
		String id=request.getParameter("id");
		TcSysNewsBean bean1=new TcSysNewsBean();
		bean1.setId(id);
		String msg="1";
		try {
			bean1.setInfo_source(info_source);
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
		log.info("S001Action edit.........");
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
		log.info("S001Action view.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcSysNewsBean bean1=new TcSysNewsBean();
			bean1.setId(id);
			bean=newsManager.findDataById(bean1);
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
		TcSysNewsBean bean1 = new TcSysNewsBean();
		bean1.setPageInfo(page);
		//已删除
		bean1.setDel_flag("1");
		bean1.setInfo_source(info_source);
		//栏目资讯列表
		List<TcSysNewsBean> beans=newsManager.findDataIsPage(bean1);
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
		TcSysNewsBean bean1=new TcSysNewsBean();
		bean1.setId(id);
		String msg="1";
		try {
			bean1.setInfo_source(info_source);
			msg=newsManager.recoveryDataById(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
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

	/**
	 * 信息来源0微信文章1系统资讯取得
	 * @return 信息来源0微信文章1系统资讯
	 */
	public String getInfo_source() {
	    return info_source;
	}
}
