/*
 * 行政区划 
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.03.26  wuxiaogang           程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.adi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.adi.AreaBean;
import cn.com.softvan.service.adi.IAreaManager;
import cn.com.softvan.web.action.BaseAction;

public class AreaAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6103432072290645133L;
	private static final transient Logger log = Logger
			.getLogger(AreaAction.class);
	/** 行政区划 */
	private IAreaManager areaManager;
	/** 默认的构造函数 */
	public AreaAction() {
		log.info("AreaAction constructed");
	}
	/**
	 * 省 列表
	 */
	public String a() throws Exception {
		log.info("AreaAction A");
		StringBuffer outStr = new StringBuffer("");
		List<AreaBean> beans= areaManager.findDataIsListA(null);
		 if(beans!=null){
			 for(AreaBean bean:beans){
				 outStr.append("<option value=\"" + bean.getId() + "\">"+ bean.getName() + "</option>");
			 }
		}
		getWriter().print(outStr);
		return null;
	}

	/**
	 * 市 列表
	 */
	public String  b() throws Exception {

		log.info("AreaAction B");
		Map<String,String> map=new HashMap<String,String>();
		map.put("id", request.getParameter("id"));
		StringBuffer outStr = new StringBuffer("");
		List<AreaBean> beans= areaManager.findDataIsListB(map);
		 if(beans!=null){
			 for(AreaBean bean:beans){
				 outStr.append("<option value=\"" + bean.getId() + "\">"+ bean.getName() + "</option>");
			 }
		}
		getWriter().print(outStr);
		return null;
	}

	/**
	 * 县 列表
	 */
	public String c() throws Exception {
		log.info("AreaAction C");
		Map<String,String> map=new HashMap<String,String>();
		map.put("id", request.getParameter("id"));
		StringBuffer outStr = new StringBuffer("");
		List<AreaBean> beans= areaManager.findDataIsListC(map);
		 if(beans!=null){
			 for(AreaBean bean:beans){
				 outStr.append("<option value=\"" + bean.getId() + "\">"+ bean.getName() + "</option>");
			 }
		}
		getWriter().print(outStr);
		return null;
	}
	/**
	 * 镇 列表
	 */
	public String d() throws Exception {
		log.info("AreaAction D");
		Map<String,String> map=new HashMap<String,String>();
		map.put("id", request.getParameter("id"));
		StringBuffer outStr = new StringBuffer("");
		List<AreaBean> beans= areaManager.findDataIsListD(map);
		 if(beans!=null){
			 for(AreaBean bean:beans){
				 outStr.append("<option value=\"" + bean.getId() + "\">"+ bean.getName() + "</option>");
			 }
		}
		getWriter().print(outStr);
		return null;
	}
	/**
	 * 村 列表
	 */
	public String e() throws Exception {
		log.info("AreaAction E");
		Map<String,String> map=new HashMap<String,String>();
		map.put("id", request.getParameter("id"));
		StringBuffer outStr = new StringBuffer("");
		List<AreaBean> beans= areaManager.findDataIsListE(map);
		 if(beans!=null){
			 for(AreaBean bean:beans){
				 outStr.append("<option value=\"" + bean.getId() + "\">"+ bean.getName() + "</option>");
			 }
		}
		getWriter().print(outStr);
		return null;
	}
	
	/**
	 * 行政区划取得
	 * @return 行政区划
	 */
	public IAreaManager getAreaManager() {
	    return areaManager;
	}
	/**
	 * 行政区划设定
	 * @param areaManager 行政区划
	 */
	public void setAreaManager(IAreaManager areaManager) {
	    this.areaManager = areaManager;
	}
}
