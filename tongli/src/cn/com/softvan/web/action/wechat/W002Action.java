/*
 * 微信服务_自动回复(关键字)_图文消息 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.10  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.wechat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.BaseUserBean;
import cn.com.softvan.bean.sys.TcSysNewsBean;
import cn.com.softvan.bean.wechat.TcWxInfoBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.wechat.ITcWxInfoManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 微信服务_自动回复(关键字)_图文消息 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class W002Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(W002Action.class);
	/**BEAN类  微信资源信息*/
	private TcWxInfoBean bean;
	/**BEAN类  微信资源信息 集合*/
	private List<TcWxInfoBean> beans;
	/**微信服务_资源信息管理 业务处理*/
	private ITcWxInfoManager tcWxInfoManager;
	/** 信息类型 */
	private final String msgType="news";
	/**信息来源0微信文章1系统资讯*/
	private final String info_source="0";
	public W002Action() {
		log.info("默认构造器......W002Action");
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
		log.info("W002Action init.........");
		
		TcWxInfoBean bean1=new TcWxInfoBean();
		bean1.setInfo_source(info_source);
		bean1.setMsgtype(msgType);
		List<TcWxInfoBean> beans=tcWxInfoManager.findDataIsList(bean1);
		
		if (beans!=null){
			// 按照articles_id对信息进行分组
			LinkedHashMap<String, List<TcWxInfoBean>> map = new LinkedHashMap<String, List<TcWxInfoBean>>();
			for (TcWxInfoBean bean : beans) {
				if(map.containsKey(bean.getArticles_id())){
					 map.get(bean.getArticles_id()).add(bean);
				}else{
					 List<TcWxInfoBean> tempList=new ArrayList<TcWxInfoBean>();
					 tempList.add(bean);
					 map.put(bean.getArticles_id(),tempList);
				}
			}
			request.setAttribute("map", map);
		}
		return "init";
	}
	/**
	 * <p>
	 * 信息修改页面
	 * </p>
	 * <ol>
	 * [功能概要] <div>修改页面。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String edit() {
		log.info("W002Action edit.........");
		
		String articles_id=request.getParameter("aid");
		if(articles_id!=null){
			TcWxInfoBean bean1=new TcWxInfoBean();
			bean1.setArticles_id(articles_id);
			bean1.setInfo_source(info_source);
			bean1.setMsgtype(msgType);
			beans=tcWxInfoManager.findDataIsList(bean1);
			if(beans!=null){
				bean=beans.get(0);
			}
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
		log.info("W002Action edit.........");
		if(bean!=null){
			//获取id集合
			String[] ids=request.getParameterValues("id");
			if(ids!=null){
				List<TcWxInfoBean> beans=new ArrayList<TcWxInfoBean>();
				TcWxInfoBean bean1=new TcWxInfoBean();
				BaseUserBean user = (BaseUserBean) request.getSession().getAttribute(CommonConstant.SESSION_KEY_USER);
				if(user!=null){
					bean1.setCreate_ip(getIpAddr());
					bean1.setCreate_id(user.getUser_id());
					bean1.setUpdate_ip(getIpAddr());
					bean1.setUpdate_id(user.getUser_id());
				}
				bean1.setArticles_id(bean.getArticles_id());
				bean1.setMsgtype(msgType);//图文
				bean1.setDefault_flag(bean.getDefault_flag());
				bean1.setSubscribe_flag(bean.getSubscribe_flag());
				bean1.setInfo_source(info_source);
				bean1.setKeyword(bean.getKeyword());
				bean1.setCreatetime(""+System.currentTimeMillis());
				bean1.setArticlecount(""+ids.length);//图文消息个数，限制为10条以内
				TcWxInfoBean bean2=null;
				for(int i=0;i<ids.length;i++){
					//对象克隆
					bean2=(TcWxInfoBean) bean1.clone();
					bean2.setId(ids[i]);//id
					bean2.setTitle(request.getParameter("title"+ids[i]));//消息的标题
					bean2.setPicurl(request.getParameter("picurl"+ids[i]));//图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
					bean2.setUrl(request.getParameter("url"+ids[i]));//点击图文消息跳转链接
					bean2.setDel_flag(request.getParameter("del_flag"+ids[i]));
					bean2.setDescription(request.getParameter("description"+ids[i]));//简介
					bean2.setSort_num(""+i);
					//list add
					if(Validator.notEmpty(bean2.getTitle())
							&& Validator.notEmpty(bean2.getPicurl())
							&& Validator.notEmpty(bean2.getUrl())){
						beans.add(bean2);
					}
				}
				String msg="1";
				try {
					if(beans==null || beans.size()==0){
						msg="保存失败!信息为空!";
					}else{
						msg=tcWxInfoManager.saveOrUpdateData(beans);
					}
				} catch (Exception e) {
					msg=e.getMessage();
				}
				request.setAttribute("msg",msg);
			}
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
		log.info("W002Action del.........");
		
		String articles_id=request.getParameter("aid");
		TcWxInfoBean bean1=new TcWxInfoBean();
		bean1.setArticles_id(articles_id);
		bean1.setInfo_source(info_source);
		bean1.setMsgtype(msgType);
		String msg="1";
		try {
			msg=tcWxInfoManager.deleteDataByAid(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		return SUCCESS;
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
