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
package cn.com.softvan.service.wechar.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.wechar.TcWxInfoBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.dao.daointer.wechar.ITcWxInfoDao;
import cn.com.softvan.dao.entity.wechar.TcWxInfo;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.wechar.ITcWxInfoManager;
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
				if(bean!=null){
					try {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						TcWxInfo dto=new TcWxInfo();
						dto.setId(bean.getId());//id
						dto.setKeyword(bean.getKeyword());//关键字
						dto.setMsgtype(bean.getMsgtype());//消息类型
						dto.setTousername(bean.getTousername());//接收方帐号（收到的OpenID）
						dto.setFromusername(bean.getFromusername());//开发者微信号
						dto.setCreatetime(""+System.currentTimeMillis());//消息创建时间 （整型）
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
//						dto.setDate_created(sdf.parse(bean.getDate_created()));//数据输入日期
						dto.setCreate_id(bean.getCreate_id());//建立者ID
						dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//						dto.setLast_updated(sdf.parse(bean.getLast_updated()));//资料更新日期
						dto.setUpdate_id(bean.getUpdate_id());//修改者ID
						dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
						dto.setDel_flag(bean.getDel_flag());//del_flag
						dto.setInfo_source(bean.getInfo_source());//消息来源0,自动回复,1,信息接收,2,客服回复
						dto.setDefault_flag(bean.getDefault_flag());
						dto.setSubscribe_flag(bean.getSubscribe_flag());
						dto.setSort_num(bean.getSort_num());
						//判断数据是否存在
						if(tcWxInfoDao.isDataYN(dto)!=0){
							//数据存在
							tcWxInfoDao.updateByPrimaryKeySelective(dto);
						}else{
							//新增
							//dto.setId(IdUtils.createUUID(32));
							tcWxInfoDao.insert(dto);
						}
					} catch (Exception e) {
						msg="信息保存失败,数据库处理错误!";
						log.error(msg, e);
						throw new Exception(msg);
					}
				}
			}
		}
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
			} catch (Exception e) {
				msg="信息保存失败,数据库处理错误!";
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
    	   }
			beans=tcWxInfoDao.findDataIsPage(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
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
	    	   }
				beans=tcWxInfoDao.findDataIsList(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
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
    	   TcWxInfo dto=new TcWxInfo();
    	   if(bean!=null){
			dto.setId(bean.getId());//id
    	   }
			bean1=tcWxInfoDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
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
}
