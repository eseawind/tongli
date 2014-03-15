/*
 * 微信服务_自定义菜单 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.05  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.wechar.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import cn.com.softvan.bean.wechar.TcWxMenuBean;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.wechar.ITcWxMenuDao;
import cn.com.softvan.dao.entity.wechar.TcWxMenu;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.wechar.ITcWxMenuManager;
/**
 *<p>微信服务_自定义菜单 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class TcWxMenuManager extends BaseManager implements ITcWxMenuManager {
	/** 日志 */
	private static final transient Logger log = Logger
			.getLogger(TcWxMenuManager.class);
	/**微信服务_自定义菜单  数据库处理 DAO*/
	private ITcWxMenuDao tcWxMenuDao;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String saveOrUpdateData(TcWxMenuBean bean){
		String msg="1";
		if(bean!=null){
			try {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TcWxMenu dto=new TcWxMenu();
				dto.setId(bean.getId());//id
				dto.setMenu_key(bean.getMenu_key());//菜单KEY值，用于消息接口推送，不超过128字节
				dto.setMenu_type(bean.getMenu_type());//菜单的响应动作类型，目前有click、view两种类型
				dto.setMenu_name(bean.getMenu_name());//菜单标题，不超过16个字节，子菜单不超过40个字节
				dto.setMenu_url(bean.getMenu_url());//网页链接，用户点击菜单可打开链接，不超过256字节
				dto.setNote(bean.getNote());//备注
				dto.setParent_id(bean.getParent_id()!=null?bean.getParent_id().trim():null);//父id
				dto.setSort_num(bean.getSort_num());//序号
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setVersion(bean.getVersion());//VERSION
				dto.setDel_flag(bean.getDel_flag());//del_flag
				//判断数据是否存在
				if(tcWxMenuDao.isDataYN(dto)!=0){
					//数据存在
					tcWxMenuDao.updateByPrimaryKeySelective(dto);
				}else{
					boolean insert_falg=false;
					//TODO 判断顶级菜单是否超过3个,2级菜单是否超过5个
					int munu_count=tcWxMenuDao.getDataCount(dto);
					//是否顶级菜单
					if(Validator.notEmpty(dto.getParent_id())){
						//否
						if(munu_count<5){
							insert_falg=true;
						}else{
							msg="2级菜单最多5个";
							return msg;
						}
					}else{
						if(munu_count<3){
							insert_falg=true;
						}else{
							msg="1级菜单最多3个";
							return msg;
						}
					}
					if(insert_falg){
						//新增
						dto.setId(IdUtils.createUUID(32));
						tcWxMenuDao.insert(dto);
					}
				}
			} catch (Exception e) {
				msg="信息保存失败,数据库处理错误!";
				log.error(msg, e);
			}
		}
		return msg;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxMenuBean> findDataIsList(TcWxMenuBean bean){
		List<TcWxMenuBean> beans=null;
		try {
			TcWxMenu dto=new TcWxMenu();
			if(bean!=null){
				dto.setId(bean.getId());//id
				dto.setMenu_type(bean.getMenu_type());//菜单的响应动作类型，目前有click、view两种类型
				dto.setParent_id(bean.getParent_id());//父id
			}
			beans=tcWxMenuDao.findDataIsList(dto);
		} catch (Exception e) {
			log.error("信息列表查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息树。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>树。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxMenuBean> findDataIsTree(TcWxMenuBean bean){
		List<TcWxMenuBean> beans=null;
		try {
			beans=tcWxMenuDao.findDataIsList(null);
			if(beans!=null){
				TcWxMenu dto=null;
				for(TcWxMenuBean bean1:beans){
					if(bean1!=null){
						dto=new TcWxMenu();
						dto.setParent_id(bean1.getId());//父id
						bean1.setBeans(tcWxMenuDao.findDataIsList(dto));
					}
				}
			}
		} catch (Exception e) {
			log.error("信息列表查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息删除。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcWxMenuBean bean){
		String msg="1";
		if(bean!=null){
			try {
				TcWxMenu dto=new TcWxMenu();
				dto.setId(bean.getId());//id
				tcWxMenuDao.deleteByPrimaryKey(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
			}
		}
		return msg;
	}
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcWxMenuBean findDataById(TcWxMenuBean bean){
		TcWxMenuBean bean1=null;
		try {
			TcWxMenu dto=new TcWxMenu();
			dto.setId(bean.getId());//id
			bean1=tcWxMenuDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * <p>发布微信菜单。</p>
	 * <ol>[功能概要] 
	 * <div>发布上传。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String uploadMenu(){
		String msg="";
		String appid="";
    	String secret="";
    	String access_token=getAccessToken(appid, secret);
//    	System.err.println(access_token);
    		/**
    		 * 设置菜单
    		 */
    		String menu = "{"+
    				"\"button\":["+
////    				---------------a---------------------------------------------
//    					"{\"name\":\"初始回复\","+
//
//    					" \"sub_button\":["+
//    					"	{"+
//	    				"	   \"type\":\"click\","+
//	    				"	   \"name\":\"玩家的奥特莱斯\","+
//	    				"	   \"key\":\"a1\""+
//	    				"	}"+
//	    				","+
//	    				"	{"+
//    					"		\"type\":\"click\","+
//    					"		\"name\":\"玩品宣言\","+
//    					"		\"key\":\"a2\""+
//    					"	}"+
//    					","+
//	    				"	{"+
//    					"		\"type\":\"click\","+
//    					"		\"name\":\"使用向导\","+
//    					"		\"key\":\"a3\""+
//    					"	}"+
//    					","+
//	    				"	{"+
//    					"		\"type\":\"click\","+
//    					"		\"name\":\"特惠速查\","+
//    					"		\"key\":\"a4\""+
//    					"	}"+
//    					","+
//	    				"	{"+
//    					"		\"type\":\"click\","+
//    					"		\"name\":\"“过时不候”是什么\","+
//    					"		\"key\":\"a5\""+
//    					"	}"+
//    					","+
//	    				"	{"+
//    					"		\"type\":\"click\","+
//    					"		\"name\":\"身边特惠\","+
//    					"		\"key\":\"a6\""+
//    					"	}"+
//    					","+
//	    				"	{"+
//    					"		\"type\":\"click\","+
//    					"		\"name\":\"当季特惠精选\","+
//    					"		\"key\":\"a7\""+
//    					"	}"+
//    					","+
//	    				"	{"+
//    					"		\"type\":\"click\","+
//    					"		\"name\":\"戳！神奇的品哥\","+
//    					"		\"key\":\"a8\""+
//    					"	}"+
//	    				"]"+
//    					"}"+
////    					-----------b---------------------------------------------------
//						","+
//						"{\"name\":\"找特惠\","+
//    					"\"sub_button\":["+
//    					"{"+
//    					"   \"type\":\"click\","+
//    					"   \"name\":\"身边\","+
//    					"   \"key\":\"b1\""+
//    					"}"+
//    					","+
//    					"{"+
//    					"   \"type\":\"click\","+
//    					"   \"name\":\"当季\","+
//    					"   \"key\":\"b2\""+
//    					"}"+
//    					","+
//    					"{"+
//    					"   \"type\":\"click\","+
//    					"   \"name\":\"景点门票\","+
//    					"   \"key\":\"b3\""+
//    					"}"+
//    					","+
//    					"{"+
//    					"   \"type\":\"click\","+
//    					"   \"name\":\"机票/酒店/线路\","+
//    					"   \"key\":\"b4\""+
//    					"}"+
//    					","+
//    					"{"+
//    					"   \"type\":\"click\","+
//    					"   \"name\":\"各种享受\","+
//    					"   \"key\":\"b5\""+
//    					"}"+
//    					"]"+
//    					"}"+
////    					-----------c---------------------------------------------------
//						","+
//    					"{\"name\":\"过时不候\","+
//    					"\"sub_button\":["+
//    					"	{"+
//	    				"	   \"type\":\"click\","+
//	    				"	   \"name\":\"产品列表\","+
//	    				"	   \"key\":\"c1\""+
//	    				"	}" +
//	    				"]"+
//    					"}"+
////    					------------d--------------------------------------------------
//						","+
//						"{\"name\":\"我\","+
//    					"\"sub_button\":["+
//    					"	{"+
//	    				"	   \"type\":\"click\","+
//	    				"	   \"name\":\"我的特惠玩\","+
//	    				"	   \"key\":\"d1\""+
//	    				"	}" +
//	    				","+
//	    				"	{"+
//	    				"	   \"type\":\"click\","+
//	    				"	   \"name\":\"想去\","+
//	    				"	   \"key\":\"d2\""+
//	    				"	}" +
//	    				","+
//	    				"	{"+
//	    				"	   \"type\":\"click\","+
//	    				"	   \"name\":\"戳！神奇的品哥\","+
//	    				"	   \"key\":\"d3\""+
//	    				"	}" +
//	    				","+
//	    				"	{"+
//	    				"	   \"type\":\"click\","+
//	    				"	   \"name\":\"找客服\","+
//	    				"	   \"key\":\"d4\""+
//	    				"	}" +
//	    				"]"+
//    					"}"+
////    					-----------e---------------------------------------------------
//						","+
//						"{	"+
//    				     "     \"type\":\"click\","+
//    				     "     \"name\":\"每月推送\","+
//    				     "     \"key\":\"e1\""+
//    				     "}"+
    				"]"+
    			"}";
    		//set
    		setMenu(access_token, menu);
		return msg;
	}
	/**
	 * <p>下载微信菜单。</p>
	 * <ol>[功能概要] 
	 * <div>菜单下载。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String downMenu(){
		String msg="";
		
		return msg;
	}
	/**
	 * 微信服务_自定义菜单  数据库处理 DAO取得
	 * @return 微信服务_自定义菜单  数据库处理 DAO
	 */
	public ITcWxMenuDao getTcWxMenuDao() {
	    return tcWxMenuDao;
	}
	/**
	 * 微信服务_自定义菜单  数据库处理 DAO设定
	 * @param tcWxMenuDao 微信服务_自定义菜单  数据库处理 DAO
	 */
	public void setTcWxMenuDao(ITcWxMenuDao tcWxMenuDao) {
	    this.tcWxMenuDao = tcWxMenuDao;
	}
	/**获取access_token*/
    private String getAccessToken(String appid,String secret){
		String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+secret;
		//构建http构建[使用HttpClient的jar,怎么获得自己百度]
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.getParams().setContentCharset("utf-8");
		//发送http请求
		String respStr = "";
		try {
			client.executeMethod(post);
			respStr = post.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject dataJson =new JSONObject().fromObject(respStr);
    	return dataJson.getString("access_token");
    }
    /**设置菜单*/
    private String setMenu(String access_token,String menu){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+access_token;
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(url);
		post.setRequestBody(menu);
		post.getParams().setContentCharset("utf-8");
		//发送http请求
		String respStr = "";
		try {
			client.executeMethod(post);
			respStr = post.getResponseBodyAsString();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(respStr);
		JSONObject dataJson =new JSONObject().fromObject(respStr);
    	return dataJson.getString("errmsg");
    }
}
