/*
 * 微信服务_关键字自动回复信息管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.10  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.wechat.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.wechat.TcWxInfoBean;
import cn.com.softvan.bean.wechat.TcWxPublicUserBean;
import cn.com.softvan.bean.wechat.reply.WxReplyMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyMusicMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyNewsMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyPicMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyTextMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyVideoMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyVoiceMsg;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.JedisHelper;
import cn.com.softvan.common.Validator;
import cn.com.softvan.common.wechat.WxApiUtil;
import cn.com.softvan.dao.daointer.wechat.ITcWxInfoDao;
import cn.com.softvan.dao.entity.wechat.TcWxInfo;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.wechat.ITcWxInfoManager;
/**
 *<p>微信服务_关键字自动回复信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class TcWxInfoManager extends BaseManager implements ITcWxInfoManager {
	/** 日志 */
	private static final transient Logger log = Logger
			.getLogger(TcWxInfoManager.class);
	
	/**信息DAO 接口类*/
	private ITcWxInfoDao tcWxInfoDao;
	/**redis缓存工具类*/
	private JedisHelper jedisHelper;
	/** 微信服务_公共账号 service 接口类*/
	private TcWxPublicUserManager tcWxPublicUserManager;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	public String saveOrUpdateData(TcWxInfoBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				TcWxInfo dto=new TcWxInfo();
				dto.setId(bean.getId());//id
				dto.setKeyword(bean.getKeyword());//关键字
				dto.setMsgtype(bean.getMsgtype());//消息类型
				dto.setTousername(bean.getTousername());//接收方帐号（收到的OpenID）
				dto.setFromusername(bean.getFromusername());//开发者微信号
				dto.setCreatetime(Validator.notEmpty(bean.getCreatetime())?bean.getCreatetime():""+System.currentTimeMillis()/1000);//消息创建时间 （整型）
				dto.setContent(bean.getContent());//消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
				dto.setMediaid(bean.getMediaid());//通过上传多媒体文件，得到的id。
				dto.setTitle(bean.getTitle());//消息的标题
				dto.setDescription(bean.getDescription());//消息的描述
				dto.setMusicurl(bean.getMusicurl());//音乐链接
				dto.setHqmusicurl(bean.getHqmusicurl());//高质量音乐链接，WIFI环境优先使用该链接播放音乐
				dto.setThumbmediaid(bean.getThumbmediaid());//缩略图的媒体id，通过上传多媒体文件，得到的id
				dto.setFormat(bean.getFormat());//语音格式，如amr，speex等
				dto.setLocation_x(bean.getLocation_x());//地理位置维度
				dto.setLocation_y(bean.getLocation_y());//地理位置经度
				dto.setScale(bean.getScale());//地图缩放大小
				dto.setLabel(bean.getLabel());//地理位置信息
				dto.setArticlecount(bean.getArticlecount());//图文消息个数，限制为10条以内
				dto.setArticles_id(bean.getArticles_id());//图文信息组ID
				dto.setPicurl(bean.getPicurl());//图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
				dto.setUrl(bean.getUrl());//点击图文消息跳转链接
//				dto.setDate_created(sdf.parse(bean.getDate_created()));//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(sdf.parse(bean.getLast_updated()));//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//del_flag
				dto.setInfo_source(bean.getInfo_source());//消息来源0,自动回复,1,信息接收,2,客服回复
				dto.setDefault_flag(bean.getDefault_flag());
				dto.setSubscribe_flag(bean.getSubscribe_flag());
				dto.setSort_num(Validator.isEmpty(bean.getSort_num())?"0":bean.getSort_num());
				dto.setLocation_precision(bean.getLocation_precision());
				dto.setEvent(bean.getEvent());
				dto.setEventkey(bean.getEventkey());
				dto.setTicket(bean.getTicket());
				dto.setConsult_flag(bean.getConsult_flag());//
				//判断数据是否存在
				if(tcWxInfoDao.isDataYN(dto)!=0){
					//数据存在
					tcWxInfoDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
						bean.setId(dto.getId());
					}
					tcWxInfoDao.insert(dto);
				}
			} catch (Exception e) {
				msg="信息保存失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = CommonConstant.DB_DEFAULT_TIMEOUT, rollbackFor = {
			Exception.class, RuntimeException.class })
	public String saveOrUpdateData(List<TcWxInfoBean> beans) throws Exception{
		String msg="1";
		if(beans!=null){
			for(TcWxInfoBean bean:beans){
				if(Validator.isEmpty(bean.getId())){
					bean.setId(IdUtils.createUUID(32));
				}
				msg=saveOrUpdateData(bean);
			}
		}
		setMsgCache(beans);
		return msg;
	}
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcWxInfoBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcWxInfo dto=new TcWxInfo();
				dto.setId(bean.getId());//id
				tcWxInfoDao.deleteByPrimaryKey(dto);
				deleteMsgCache(bean);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
			}
		}
		return msg;
	}
	/**
	 * <p>信息 集合。</p>
	 * <ol>[功能概要] 
	 * <div>逻辑删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = CommonConstant.DB_DEFAULT_TIMEOUT, rollbackFor = {
			Exception.class, RuntimeException.class })
	public String deleteDataByAid(TcWxInfoBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcWxInfo dto=new TcWxInfo();
				dto.setMsgtype(bean.getMsgtype());//消息类型
				dto.setArticles_id(bean.getArticles_id());//图文信息组ID
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				tcWxInfoDao.deleteByAid(dto);
				deleteMsgCache(bean);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>逻辑删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = CommonConstant.DB_DEFAULT_TIMEOUT, rollbackFor = {
			Exception.class, RuntimeException.class })
	public String deleteDataById(TcWxInfoBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcWxInfo dto=new TcWxInfo();
				dto.setMsgtype(bean.getMsgtype());//消息类型
				dto.setId(bean.getId());//信息ID
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				tcWxInfoDao.deleteById(dto);
				deleteMsgCache(bean);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息列表 分页。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>分页。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxInfoBean> findDataIsPage(TcWxInfoBean bean){
		List<TcWxInfoBean> beans=null;
		try {
    	   TcWxInfo dto=new TcWxInfo();
    	   if(bean!=null){
    		   dto.setId(bean.getId());//id
				dto.setKeyword(bean.getKeyword());//关键字
				dto.setMsgtype(bean.getMsgtype());//消息类型
				dto.setTousername(bean.getTousername());//接收方帐号（收到的OpenID）
				dto.setFromusername(bean.getFromusername());//开发者微信号
				dto.setCreatetime(bean.getCreatetime());//消息创建时间 （整型）
				dto.setContent(bean.getContent());//消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
				dto.setMediaid(bean.getMediaid());//通过上传多媒体文件，得到的id。
				dto.setTitle(bean.getTitle());//消息的标题
				dto.setDescription(bean.getDescription());//消息的描述
				dto.setMusicurl(bean.getMusicurl());//音乐链接
				dto.setHqmusicurl(bean.getHqmusicurl());//高质量音乐链接，WIFI环境优先使用该链接播放音乐
				dto.setThumbmediaid(bean.getThumbmediaid());//缩略图的媒体id，通过上传多媒体文件，得到的id
				dto.setFormat(bean.getFormat());//语音格式，如amr，speex等
				dto.setLocation_x(bean.getLocation_x());//地理位置维度
				dto.setLocation_y(bean.getLocation_y());//地理位置经度
				dto.setScale(bean.getScale());//地图缩放大小
				dto.setLabel(bean.getLabel());//地理位置信息
				dto.setArticlecount(bean.getArticlecount());//图文消息个数，限制为10条以内
				dto.setArticles_id(bean.getArticles_id());//图文信息组ID
				dto.setPicurl(bean.getPicurl());//图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
				dto.setUrl(bean.getUrl());//点击图文消息跳转链接
//				dto.setDate_created(sdf.parse(bean.getDate_created()));//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(sdf.parse(bean.getLast_updated()));//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//del_flag
				dto.setInfo_source(bean.getInfo_source());//消息来源0,自动回复,1,信息接收,2,客服回复
				dto.setDefault_flag(bean.getDefault_flag());
				dto.setSubscribe_flag(bean.getSubscribe_flag());
				dto.setSort_num(bean.getSort_num());
				dto.setNote(bean.getNote());
				dto.setConsult_flag(bean.getConsult_flag());//
				dto.setPageInfo(bean.getPageInfo());
    	   }
			beans=tcWxInfoDao.findDataIsPage(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcWxInfoBean> findDataIsList(TcWxInfoBean bean){
		List<TcWxInfoBean> beans=null;
		try {
	    	   TcWxInfo dto=new TcWxInfo();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//id
					dto.setKeyword(bean.getKeyword());//关键字
					dto.setMsgtype(bean.getMsgtype());//消息类型
					dto.setTousername(bean.getTousername());//接收方帐号（收到的OpenID）
					dto.setFromusername(bean.getFromusername());//开发者微信号
					dto.setCreatetime(bean.getCreatetime());//消息创建时间 （整型）
					dto.setContent(bean.getContent());//消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
					dto.setMediaid(bean.getMediaid());//通过上传多媒体文件，得到的id。
					dto.setTitle(bean.getTitle());//消息的标题
					dto.setDescription(bean.getDescription());//消息的描述
					dto.setMusicurl(bean.getMusicurl());//音乐链接
					dto.setHqmusicurl(bean.getHqmusicurl());//高质量音乐链接，WIFI环境优先使用该链接播放音乐
					dto.setThumbmediaid(bean.getThumbmediaid());//缩略图的媒体id，通过上传多媒体文件，得到的id
					dto.setFormat(bean.getFormat());//语音格式，如amr，speex等
					dto.setLocation_x(bean.getLocation_x());//地理位置维度
					dto.setLocation_y(bean.getLocation_y());//地理位置经度
					dto.setScale(bean.getScale());//地图缩放大小
					dto.setLabel(bean.getLabel());//地理位置信息
					dto.setArticlecount(bean.getArticlecount());//图文消息个数，限制为10条以内
					dto.setArticles_id(bean.getArticles_id());//图文信息组ID
					dto.setPicurl(bean.getPicurl());//图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
					dto.setUrl(bean.getUrl());//点击图文消息跳转链接
//					dto.setDate_created(sdf.parse(bean.getDate_created()));//数据输入日期
					dto.setCreate_id(bean.getCreate_id());//建立者ID
					dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//					dto.setLast_updated(sdf.parse(bean.getLast_updated()));//资料更新日期
					dto.setUpdate_id(bean.getUpdate_id());//修改者ID
					dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
					dto.setDel_flag(bean.getDel_flag());//del_flag
					dto.setInfo_source(bean.getInfo_source());//消息来源0,自动回复,1,信息接收,2,客服回复
					dto.setDefault_flag(bean.getDefault_flag());
					dto.setSubscribe_flag(bean.getSubscribe_flag());
					dto.setSort_num(bean.getSort_num());
					dto.setConsult_flag(bean.getConsult_flag());//
					dto.setNote(bean.getNote());
	    	   }
				beans=tcWxInfoDao.findDataIsList(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
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
	public TcWxInfoBean findDataById(TcWxInfoBean bean){
       TcWxInfoBean bean1=null;
       try {
    	   if(bean!=null){
    		   TcWxInfo dto=new TcWxInfo();
				dto.setId(bean.getId());//id
				bean1=tcWxInfoDao.selectByPrimaryKey(dto);
    	   }
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * <p>修改多媒体id。</p>
	 * <ol>[功能概要] 
	 * <div>更新。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public void updateMediaId(TcWxInfoBean bean){
       try {
    	   TcWxInfo dto=new TcWxInfo();
    	   if(bean!=null){
			dto.setId(bean.getId());//id
			dto.setMediaid(bean.getMediaid());//多媒体id
			dto.setThumbmediaid(bean.getThumbmediaid());//缩略图id
    	   }
			tcWxInfoDao.updateMediaIdByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("修改多媒体id失败,数据库处理错误!", e);
		}
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcWxInfoDao getTcWxInfoDao() {
	    return tcWxInfoDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcWxInfoDao 信息DAO 接口类
	 */
	public void setTcWxInfoDao(ITcWxInfoDao tcWxInfoDao) {
	    this.tcWxInfoDao = tcWxInfoDao;
	}
	/**
	 * 删除或修改_首次关注和默认回复
	 * @param bean
	 */
	private void setMsgCacheSubscribeXDefault(TcWxInfoBean bean){
			try{
				String keyword=(String) jedisHelper.get("key_flag_uuid_"+bean.getId());
				if(Validator.notEmpty(keyword)){
					String[] keys=keyword.split(" ");
					for(String s:keys){
						if(Validator.notEmpty(s)){
							//删除关键字对象的数据id
							jedisHelper.del("key_flag_"+s);
						}
					}
				}
			} catch (Exception e) {
			}
			try {
				//首次关注回复信息
				String u1=(String) jedisHelper.get("key_flag_subscribe");
				if(bean.getId().equals(u1)
						&&!"1".equals(bean.getSubscribe_flag())){
					jedisHelper.del("key_flag_subscribe");
				}
			} catch (Exception e) {
			}
			try {
				//默认回复信息
				String u2=(String) jedisHelper.get("key_flag_default");
				if(bean.getId().equals(u2)
						&&!"1".equals(bean.getDefault_flag())){
					jedisHelper.del("key_flag_default");
				}
			} catch (Exception e) {
			}
	}
	/**
	 * 设置索引
	 * @param bean
	 */
	private void addLuceneIndex(TcWxInfoBean bean){
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.fff");
//			//delte
//			deleteLuceneIndex(bean);
//			//create
//			LuceneUtil.creatIndex(Resources.getData("lucene.wechat_index_path"),
//					new String[]{"id","keyword","last_updated"},
//					new String[]{bean.getId(),bean.getKeyword(),Validator.isEmpty(bean.getLast_updated())?sdf.format(new Date()):bean.getLast_updated()},
//					null, new Store[]{org.apache.lucene.document.Field.Store.YES,org.apache.lucene.document.Field.Store.YES,org.apache.lucene.document.Field.Store.YES},
//					new Index[]{org.apache.lucene.document.Field.Index.NOT_ANALYZED,org.apache.lucene.document.Field.Index.ANALYZED,org.apache.lucene.document.Field.Index.NOT_ANALYZED});
//		} catch (Exception e) {
//			log.error("关键字放入索引失败!", e);
//		}
	}
	/**
	 * 删除索引
	 * @param bean
	 */
	private void deleteLuceneIndex(TcWxInfoBean bean){
//		try {
//			LuceneUtil.delete(Resources.getData("lucene.wechat_index_path"),"id", bean.getId());
//		} catch (Exception e) {
//			log.error("关键字放入缓存失败!", e);
//		}
	}
	/**
	 * 信息放入 缓存
	 * @param beans
	 */
	private void setMsgCache(List<TcWxInfoBean> beans){
		//公共账号信息
		TcWxPublicUserBean publicUser=(TcWxPublicUserBean) jedisHelper.get(CommonConstant.SESSION_WECHAT_BEAN);
		if(publicUser==null){
			tcWxPublicUserManager.initCache();
			publicUser=(TcWxPublicUserBean) jedisHelper.get(CommonConstant.SESSION_WECHAT_BEAN);
		}
		String appid=publicUser.getAppid();
		String secret=publicUser.getAppsecret();
		
		String msgType=null;
		TcWxInfoBean bean=null;
		if(beans!=null && beans.size()>0){
			 bean=beans.get(0);
			if(bean!=null){
				msgType=bean.getMsgtype();
			}
		}
		if(msgType!=null){
			setMsgCacheSubscribeXDefault(bean);
			if(Validator.notEmpty(bean.getKeyword())){
				addLuceneIndex(bean);//TODO=========将关键字放入索引============
				//TODO 存放原始 关键字信息 用于更新关键字时清理旧缓存信息
				jedisHelper.set("key_flag_uuid_"+bean.getId(),bean.getKeyword());
				//TODO 
				String[] keys=bean.getKeyword().split(" ");
				for(String s:keys){
					if(Validator.notEmpty(s)){
						//保存关键字对象的数据id
						if(!"news".equals(msgType)){
							jedisHelper.set("key_flag_"+s,bean.getId());
							log.debug("key_flag_"+s+"========"+bean.getId());
						}else{
							jedisHelper.set("key_flag_"+s,bean.getArticles_id());
							log.debug("key_flag_"+s+"========"+bean.getArticles_id());
						}
						
					}
				}
				
			}
			//图文消息
			if("news".equals(msgType)){
				log.debug("=======自动回复信息msgType="+msgType+",Keyword="+bean.getKeyword()+",id="+bean.getArticles_id());
				//对象的存取id
				String uuid=bean.getArticles_id();
				WxReplyNewsMsg newsMsg = new WxReplyNewsMsg();
				if(beans!=null){
					for(TcWxInfoBean bean1:beans){
						HttpServletRequest request = ServletActionContext.getRequest(); // 获取客户端发过来的HTTP请求
						 String path = request.getContextPath();
					     String basePath = null;
				        if (request.getServerPort() != 80) {
				    		basePath = request.getScheme() + "://" + request.getServerName() 
									+ ":" + request.getServerPort() + path;	
				        } else {
				        	basePath = request.getScheme() + "://" + request.getServerName() + path;
				        }
						//TODO 需要判断图片是否为本地图片 需要加上项目http详细地址
						newsMsg.addItem(bean1.getTitle(),bean1.getDescription(),Validator.isUrl(bean1.getPicurl())?bean1.getPicurl():basePath+bean1.getPicurl(), bean1.getUrl());
					}
				}
				//自动回复对象
				WxReplyMsg replyMsg=newsMsg;
				//首次关注回复信息
				if("1".equals(bean.getSubscribe_flag())){
					jedisHelper.set("key_flag_subscribe", uuid);
				}
				//默认回复信息
				if("1".equals(bean.getDefault_flag())){
					jedisHelper.set("key_flag_default", uuid);
				}
				//缓存信息
				jedisHelper.set(uuid, replyMsg);
				
			}else{
				//非图文消息
				if(beans!=null){
					WxApiUtil wxApiUtil=new WxApiUtil();
					for(TcWxInfoBean bean1:beans){
						if(bean1!=null){
							msgType=bean1.getMsgtype();
							log.debug("=======自动回复信息msgType="+msgType+",Keyword="+bean1.getKeyword()+",id="+bean1.getId());
							//自动回复对象
							WxReplyMsg replyMsg=null;
							//对象的存取id
							String uuid=null;
							
							//文本消息
							if("text".equals(msgType)){
								uuid=bean1.getId();
								replyMsg=new WxReplyTextMsg(bean1.getContent());
							}else
							//图片消息
							if("image".equals(msgType)){
								uuid=bean1.getId();
								String MediaId=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(false, jedisHelper,appid, secret), msgType, bean1.getPicurl());
								if(wxApiUtil.isErrAccessToken(MediaId)){
									MediaId=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(true, jedisHelper,appid, secret), msgType, bean1.getPicurl());
								}
								replyMsg=new WxReplyPicMsg(MediaId);
							}else
							//语音消息
							if("voice".equals(msgType)){
								uuid=bean1.getId();
								String MediaId=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(false, jedisHelper,appid, secret), msgType, bean1.getUrl());
								if(wxApiUtil.isErrAccessToken(MediaId)){
									MediaId=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(true, jedisHelper,appid, secret), msgType, bean1.getUrl());
								}
								replyMsg=new WxReplyVoiceMsg(MediaId);
							}else
							//视频消息
							if("video".equals(msgType)){
								uuid=bean1.getId();
								String MediaId=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(false, jedisHelper,appid, secret), msgType, bean1.getUrl());
								if(wxApiUtil.isErrAccessToken(MediaId)){
									MediaId=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(true, jedisHelper,appid, secret), msgType, bean1.getUrl());
								}
								replyMsg=new WxReplyVideoMsg(MediaId,bean1.getTitle(),bean1.getDescription());
							}else
							//音乐消息
							if("music".equals(msgType)){
								uuid=bean1.getId();
//								//音乐图片
								String MediaId=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(false, jedisHelper,appid, secret), msgType, bean1.getPicurl());
								if(wxApiUtil.isErrAccessToken(MediaId)){
									MediaId=wxApiUtil.uploadMedia(wxApiUtil.getAccess_token(true, jedisHelper,appid, secret), msgType, bean1.getPicurl());
								}
								//TODO 需要判断音乐是否为本地音乐 需要加上项目http详细地址  
								if(!Validator.isUrl(bean1.getMusicurl())||!Validator.isUrl(bean1.getHqmusicurl())){
									HttpServletRequest request = ServletActionContext.getRequest(); // 获取客户端发过来的HTTP请求
								    String path = request.getContextPath();
								    String basePath = null;
//							       log.info("访问端口号："+ request.getServerPort());
								    //TODO  默认所以地址填写都为正确  未做各种[坑]的处理
							        if (request.getServerPort() != 80) {
							    		basePath = request.getScheme() + "://" + request.getServerName() 
												+ ":" + request.getServerPort() + path;	
							        } else {
							        	basePath = request.getScheme() + "://" + request.getServerName() + path;
							        }
							        if(!Validator.isUrl(bean1.getMusicurl())){
							        	bean1.setMusicurl(basePath+bean1.getMusicurl());
							        }
							        if(!Validator.isUrl(bean1.getHqmusicurl())){
							        	bean1.setHqmusicurl(basePath+bean1.getHqmusicurl());
							        }
								}
								replyMsg = new WxReplyMusicMsg(bean1.getTitle(),bean1.getDescription(),bean1.getMusicurl(),bean1.getHqmusicurl(),MediaId);
							}
							//首次关注回复信息
							if("1".equals(bean1.getSubscribe_flag())){
								jedisHelper.set("key_flag_subscribe", uuid);
							}
							//默认回复信息
							if("1".equals(bean1.getDefault_flag())){
								jedisHelper.set("key_flag_default", uuid);
							}
							//缓存信息
							jedisHelper.set(uuid, replyMsg);
						}
					}
				}
			}
		}
	}
	/***
	 * 将自动回复信息  放入缓存
	 */
	private void init(){
		if(!"1".equals(jedisHelper.get("tc_wechat_info_cache_flag"))){
			log.debug("=======将所有自动回复信息放入缓存,任务启动!");
			TcWxInfoBean bean1=new TcWxInfoBean();
			bean1.setInfo_source("0");
			bean1.setMsgtype("news");
			List<TcWxInfoBean> beans=findDataIsList(bean1);
			
			if (beans!=null && beans.size()>0){
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
				//--------
				for(Object o : map.keySet()){  
					//TODO--缓存
					setMsgCache(map.get(o));
				}
			}
			//-----------非图文对象-----
			bean1=new TcWxInfoBean();
			bean1.setInfo_source("0");
			bean1.setMsgtype(null);
			bean1.setNote("notNews");
			beans=findDataIsList(bean1);
			//TODO--缓存
			setMsgCache(beans);
			log.debug("=======将所有自动回复信息放入缓存,任务完成!");
			//已存入缓存标记  数据缓存3天
			jedisHelper.set("tc_wechat_info_cache_flag","1",3*23*60*60);
		}
	}
	/**删除当前信息缓存
	 * @param msgType
	 * @param bean
	 */
	private void deleteMsgCache(TcWxInfoBean bean){
		if(bean!=null){
			try {
				String keyword=null;
				String uuid=null;
				if("news".equals(bean.getMsgtype())){
					if(bean!=null){
						TcWxInfo dto=new TcWxInfo();
						dto.setArticles_id(bean.getArticles_id());
						TcWxInfoBean bean1=tcWxInfoDao.selectByAid(dto);
						if(bean1!=null){
							keyword=bean1.getKeyword();
							uuid=bean1.getArticles_id();
						}
					}
				}else{
					TcWxInfoBean bean1=findDataById(bean);
					if(bean1!=null){
						keyword=bean1.getKeyword();
						uuid=bean1.getId();
					}
				}
				//----删除---cahce---------
				if(keyword!=null){
					String[] keys=keyword.split(" ");
					if(keys!=null){
						for(String s:keys){
							if(Validator.notEmpty(s)){
								//保存关键字对象的数据id
									jedisHelper.del("key_flag_"+s);
									log.debug("===del=cache====key_flag_"+s);
							}
						}
					}
				}
				jedisHelper.del(uuid);
				log.debug("===del=cache====uuid="+uuid);
			} catch (Exception e) {
				log.error("删除缓存中-自动回复信息异常!", e);
			}
		}
	}
	/**重新将所有信息放入缓存*/
	public void updateAllMsgCache(){
		log.debug("执行重新将所有信息放入缓存,操作!");
		jedisHelper.set("tc_wechat_info_cache_flag","0");
		init();
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
	 * 微信服务_公共账号 service 接口类取得
	 * @return 微信服务_公共账号 service 接口类
	 */
	public TcWxPublicUserManager getTcWxPublicUserManager() {
	    return tcWxPublicUserManager;
	}
	/**
	 * 微信服务_公共账号 service 接口类设定
	 * @param tcWxPublicUserManager 微信服务_公共账号 service 接口类
	 */
	public void setTcWxPublicUserManager(TcWxPublicUserManager tcWxPublicUserManager) {
	    this.tcWxPublicUserManager = tcWxPublicUserManager;
	}
	/**
	 * 根据openID获取用户经纬度
	 * @throws Exception 
	 */
	public TcWxInfoBean queryLocation(TcWxInfoBean bean) throws Exception {
		return tcWxInfoDao.queryLocation(bean);
	}
}
