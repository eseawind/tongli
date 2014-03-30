/*
 * 微信服务_关注者账号 service 接口实现类
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

import org.apache.log4j.Logger;

import cn.com.softvan.bean.wechat.TcWxPublicUserBean;
import cn.com.softvan.bean.wechat.TcWxUserBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.JedisHelper;
import cn.com.softvan.common.Validator;
import cn.com.softvan.common.wechat.WxApiUtil;
import cn.com.softvan.dao.daointer.wechat.ITcWxUserDao;
import cn.com.softvan.dao.entity.wechat.TcWxUser;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.wechat.ITcWxPublicUserManager;
import cn.com.softvan.service.wechat.ITcWxUserManager;
/**
 *<p>微信服务_关注者账号 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class TcWxUserManager extends BaseManager implements ITcWxUserManager {
	/** 日志 */
	private static final transient Logger log = Logger
			.getLogger(TcWxUserManager.class);
	/**微信服务_关注者账号  数据库处理 DAO*/
	private ITcWxUserDao tcWxUserDao;
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
	 * @throws Exception 
	 */
	public void saveOrUpdateData(TcWxUserBean bean) throws Exception {
		TcWxUser dto=new TcWxUser();
		dto.setOpenid(bean.getOpenid());//唯一标示
		dto.setSubscribe(bean.getSubscribe());//是否订阅
		dto.setNickname(bean.getNickname());//昵称
		dto.setRemarkname(bean.getRemarkname());//备注名
		dto.setSex(bean.getSex());//性别
		dto.setLanguage(bean.getLanguage());//语言
		dto.setCountry(bean.getCountry());//国家
		dto.setProvince(bean.getProvince());//省份
		dto.setCity(bean.getCity());//城市
		dto.setHeadimgurl(bean.getHeadimgurl());//头像url
		dto.setSubscribe_time(bean.getSubscribe_time());//用户关注时间
		dto.setGroupid(bean.getGroupid());//用户所属的groupid
		//判断数据是否存在
		if(tcWxUserDao.isDataYN(dto)!=0){
			//数据存在
			tcWxUserDao.updateByPrimaryKeySelective(dto);
		}else{
			//新增
			tcWxUserDao.insert(dto);
		}
		//关注者openid放入缓存
		jedisHelper.set(bean.getOpenid(), "1");
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxUserBean> findDataIsList(TcWxUserBean bean){
		List<TcWxUserBean> beans=null;
		try {
			TcWxUser dto=new TcWxUser();
			beans=tcWxUserDao.findDataIsList(dto);
		} catch (Exception e) {
			log.error("信息列表查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxUserBean> findDataIsPage(TcWxUserBean bean){
		List<TcWxUserBean> beans=null;
		try {
			TcWxUser dto=new TcWxUser();
			dto.setPageInfo(bean.getPageInfo());
			beans=tcWxUserDao.findDataIsPage(dto);
		} catch (Exception e) {
			log.error("信息分页查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcWxUserBean findDataById(TcWxUserBean bean){
		TcWxUserBean bean1=null;
		try {
			TcWxUser dto=new TcWxUser();
			if(bean!=null){
				dto.setOpenid(bean.getOpenid());//唯一标示
				dto.setSubscribe(bean.getSubscribe());//是否订阅
				dto.setNickname(bean.getNickname());//昵称
				dto.setRemarkname(bean.getRemarkname());//备注名
				dto.setSex(bean.getSex());//性别
				dto.setLanguage(bean.getLanguage());//语言
				dto.setCountry(bean.getCountry());//国家
				dto.setProvince(bean.getProvince());//省份
				dto.setCity(bean.getCity());//城市
				dto.setHeadimgurl(bean.getHeadimgurl());//头像url
				dto.setSubscribe_time(bean.getSubscribe_time());//用户关注时间
				dto.setGroupid(bean.getGroupid());//用户所属的groupid
			}
			bean1=tcWxUserDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 微信服务_关注者账号  数据库处理 DAO取得
	 * @return 微信服务_关注者账号  数据库处理 DAO
	 */
	public ITcWxUserDao getTcWxUserDao() {
	    return tcWxUserDao;
	}
	/**
	 * 微信服务_关注者账号  数据库处理 DAO设定
	 * @param tcWxUserDao 微信服务_关注者账号  数据库处理 DAO
	 */
	public void setTcWxUserDao(ITcWxUserDao tcWxUserDao) {
	    this.tcWxUserDao = tcWxUserDao;
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
	 * <p>下载关注者(粉丝信息)。</p>
	 * <ol>[功能概要] 
	 * <div>保存入库。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String downUser(){
		String msg="1";
		try {
			TcWxPublicUserBean publicUserBean=(TcWxPublicUserBean) jedisHelper.get(CommonConstant.SESSION_WECHAT_BEAN);
			if((publicUserBean==null) || (publicUserBean.getId()==null)){
				publicUserBean=tcWxPublicUserManager.findDataById(null);
				jedisHelper.set(CommonConstant.SESSION_WECHAT_BEAN,publicUserBean);
			}
			WxApiUtil api=new WxApiUtil();
			String access_token=api.getAccess_token(false, jedisHelper,publicUserBean.getAppid(), publicUserBean.getAppsecret());
			String next_oid=null;
			Long total=0L;
			do{
				//第一次拉取
				List<TcWxUserBean> beans=api.getUserList(access_token, next_oid);
				if(beans!=null && beans.size()>0){
					TcWxUserBean bean=beans.get(0);
					//40001获取access_token时AppSecret错误，或者access_token无效
					//40014不合法的access_token
					//42001access_token超时
					if(api.isErrAccessToken(bean.getErrcode())){
						//重新获取access_token 并重新调用接口
						beans=api.getUserList(api.getAccess_token(true, jedisHelper,publicUserBean.getAppid(), publicUserBean.getAppsecret()), next_oid);
					}
				}
				if(beans!=null && beans.size()>0){
					for(TcWxUserBean bean:beans){
						if(bean!=null && bean.getErrcode()==null){
							try {
								//保存
								saveOrUpdateData(bean);
								//下一个
								next_oid=bean.getNext_openid();
								//总记录
								total=bean.getTotal();
							} catch (Exception e) {
								log.error("粉丝昵称="+bean.getNickname(), e);
								msg+=""+bean.getNickname()+"的信息拉取失败!;";
							}
						}else{
							msg=bean.getErrmsg();
						}
					}
				}else{
					msg="下载关注者(粉丝信息)(保存入库) 处理异常!";
				}
			}while(total>10000 && Validator.notEmpty(next_oid));
		} catch (Exception e) {
			msg="下载关注者(粉丝信息)(保存入库) 处理异常!";
			log.error(msg,e);
		}
		return msg;
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
