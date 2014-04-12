/*
 * 前台首页 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.30  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 jfq  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.cilent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.sys.TcSysNewsBean;
import cn.com.softvan.service.sys.INewsManager;
import cn.com.softvan.service.sys.INewsTypeManager;
import cn.com.softvan.web.action.BaseAction;

/**
 * 前台首页 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class C001Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(C001Action.class);
	
	/**BEAN类  资讯信息*/
	private TcSysNewsBean bean;
	/**BEAN类  资讯信息 集合*/
	private List<TcSysNewsBean> beans;
	/**资讯信息管理 业务处理*/
	private INewsManager newsManager;
	/**资讯栏目信息管理 业务处理*/
	private INewsTypeManager newsTypeManager;
	//
	private String info_source="1";
	public C001Action() {
		log.info("默认构造器......C001Action");
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
		log.info("C001Action init.........");
		
//		3a5e43727219487cb6a44724f8cc0471	童励介绍
//		3f2b286347174e728d39169c212fe56b	新闻资讯 ==
//		6fba86e8436049e5b30123c538b7fc83	焦点图资讯 ==
//		894a47f564f04149b31c5b6f25d0b3e1	夏令营
//		eff3b3c2b2cc4fc0a5a0009ca1bc137c	游泳社团
//		26f1017792024a358c73639b08e74393	冬夏令营
//		6730aae5fc2c41f2984a339dff339a11	校区查询
//		690e3d1f73224bb7bd766b4648041798	图片资讯 ==
//		9845745635ab4354b05f1785b78a3862	冬令营
//		ca90c5f95a88475d877e2819b2cedfa6	球类社团
//		39d6a192e3004e6ba4972e029a398390	轮滑社团
//		48161e51b78a4dc78a57032362d5d4f3	联系方式
//		6690aceda07a405a9428e6e02ba2d416	运动课程
//		d76b4e2ba4b84e5db471988377f8ba52	视频资讯 ==
//		2eab49b782314c2bb645d2881bea69b3	武道社团
//		5b3b90343ddc4490acaa26023ec84721	假期营
//		1556c51b42cc4b16b1cd1d6b652578d4	科学探索营
//		966a13c753f34faa927510c610b5e0b6	关于我们
//		a534ef573de54297acd70f93937985c6	舞蹈社团
//		cff7aad3a82041d08a6a1565ac87fc7b	小小铁人三项训练营



		TcSysNewsBean bean1=new TcSysNewsBean();
		bean1.setInfo_source(info_source);
		bean1.setMsgtype("index");//首页展示 标记
		List<TcSysNewsBean> list=newsManager.findDataIsList(bean1);
		// 按照type_id对信息进行分组
		LinkedHashMap<String, List<TcSysNewsBean>> map = new LinkedHashMap<String, List<TcSysNewsBean>>();
		if (list!=null){
			for (TcSysNewsBean news : list) {
				if(map.containsKey(news.getType_id())){
					 map.get(news.getType_id()).add(news);
				}else{
					 List<TcSysNewsBean> tempList=new ArrayList<TcSysNewsBean>();
					 tempList.add(news);
					 map.put(news.getType_id(),tempList);
				}
			}
		}
		request.setAttribute("maps", map);
		return "init";
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
}
