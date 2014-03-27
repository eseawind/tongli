/*
 * 微信服务_自定义菜单 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.20  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.wechat.impl;

import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import cn.com.softvan.bean.wechat.TcWxMenuBean;
import cn.com.softvan.bean.wechat.TcWxPublicUserBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.JedisHelper;
import cn.com.softvan.common.Validator;
import cn.com.softvan.common.wechat.WxApiUtil;
import cn.com.softvan.dao.daointer.wechat.ITcWxMenuDao;
import cn.com.softvan.dao.entity.wechat.TcWxMenu;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.wechat.ITcWxMenuManager;
import cn.com.softvan.service.wechat.ITcWxPublicUserManager;
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
	/**redis缓存工具类*/
	protected JedisHelper jedisHelper;
	/**微信服务_公共账号 service */
	private ITcWxPublicUserManager tcWxPublicUserManager;
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
				dto.setInfo_source(bean.getInfo_source());
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
				dto.setInfo_source(bean.getInfo_source());
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
			TcWxMenu dto=new TcWxMenu();
			dto.setInfo_source(bean.getInfo_source());
			beans=tcWxMenuDao.findDataIsList(dto);
			if(beans!=null){
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
		String msg="1";
		try {
			//公共账号信息
			TcWxPublicUserBean publicUserBean=(TcWxPublicUserBean) jedisHelper.get(CommonConstant.SESSION_WECHAR_BEAN);
			if((publicUserBean==null) || (publicUserBean.getId()==null)){
				publicUserBean=tcWxPublicUserManager.findDataById(null);
				jedisHelper.set(CommonConstant.SESSION_WECHAR_BEAN,publicUserBean);
			}
			//TODO 设置菜单
			TcWxMenuBean bean=new TcWxMenuBean();
			bean.setInfo_source("0");
	    	List<TcWxMenuBean> beans=findDataIsTree(bean);
	    	if(beans!=null && beans.size()>0){
				StringBuffer menu=new StringBuffer("");
				menu.append("{");
				menu.append("\"button\":[");
				for(int i=0;i<beans.size();i++){
					TcWxMenuBean bean1=beans.get(i);
					List<TcWxMenuBean> beans1=bean1.getBeans();
					if(beans1!=null && beans1.size()>0){
						menu.append("{\"name\":\""+bean1.getMenu_name()+"\",");
						menu.append(" \"sub_button\":[");
						for(int n=0;n<beans1.size();n++){
							TcWxMenuBean bean2=beans1.get(n);
							menu.append("{");
							menu.append("\"type\":\""+bean2.getMenu_type()+"\",");
							menu.append("\"name\":\""+bean2.getMenu_name()+"\",");
							if("click".equals(bean2.getMenu_type())){
								menu.append("\"key\":\""+bean2.getMenu_key()+"\"");
							}else{
								menu.append("\"url\":\""+bean2.getMenu_url()+"\"");
							}
							menu.append("}");
							if(n<(beans1.size()-1)){
								menu.append(",");
							}
						}
						menu.append("]");
						menu.append("}");
					}else{
						menu.append("{");
						menu.append("\"type\":\""+bean1.getMenu_type()+"\",");
						menu.append("\"name\":\""+bean1.getMenu_name()+"\",");
						if("click".equals(bean1.getMenu_type())){
							menu.append("\"key\":\""+bean1.getMenu_key()+"\"");
						}else{
							menu.append("\"url\":\""+bean1.getMenu_url()+"\"");
						}
						menu.append("}");
					}
					if(i<(beans.size()-1)){
						menu.append(",");
					}
				}
				menu.append("]");
				menu.append("}");
				String appid=publicUserBean.getAppid();
//				System.out.println("----------1---------------");
				String secret=publicUserBean.getAppsecret();
//				System.out.println("----------2---------------");
				String access_token=new WxApiUtil().getAccess_token(false,jedisHelper,appid, secret);
//				System.out.println("----------3---------------");
				//set
				msg=setMenu(access_token, menu.toString());
				if(new WxApiUtil().isErrAccessToken(msg)){
					access_token=new WxApiUtil().getAccess_token(true,jedisHelper,appid, secret);
					msg=setMenu(access_token, menu.toString());
				}
//				System.out.println("----------4---------------");
	    	}
    	} catch (Exception e) {
			log.error("微信菜单生成失败!", e);
		}
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
    /**设置菜单*/
    @SuppressWarnings("deprecation")
	private String setMenu(String access_token,String menu){
    	log.debug("======access_token====="+access_token);
    	log.debug("======menu====="+menu);
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
		} catch (Exception e) {
			log.error("xxxxxxx", e);
		}
		log.info(respStr);
		JSONObject dataJson =JSONObject.fromObject(respStr);
    	return dataJson.getString("errcode");
    }
	/**
	 * redis缓存工具类取得
	 * @return redis缓存工具类
	 */
	public JedisHelper getJedisHelper() {
	    return jedisHelper;
	}
	/**
	 * redis缓存工具类设定
	 * @param jedisHelper redis缓存工具类
	 */
	public void setJedisHelper(JedisHelper jedisHelper) {
	    this.jedisHelper = jedisHelper;
	}
	/**
	 * 微信服务_公共账号 service取得
	 * @return 微信服务_公共账号 service
	 */
	public ITcWxPublicUserManager getTcWxPublicUserManager() {
	    return tcWxPublicUserManager;
	}
	/**
	 * 微信服务_公共账号 service设定
	 * @param tcWxPublicUserManager 微信服务_公共账号 service
	 */
	public void setTcWxPublicUserManager(ITcWxPublicUserManager tcWxPublicUserManager) {
	    this.tcWxPublicUserManager = tcWxPublicUserManager;
	}
}
