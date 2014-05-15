/*
 * 资讯信息管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.25  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.sys.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.sys.TcSysNewsBean;
import cn.com.softvan.bean.sys.TcSysNewsTypeBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IOHelper;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.sys.ITcSysNewsDao;
import cn.com.softvan.dao.daointer.sys.ITcSysNewsVsTypeDao;
import cn.com.softvan.dao.entity.sys.TcSysNews;
import cn.com.softvan.dao.entity.sys.TcSysNewsType;
import cn.com.softvan.dao.entity.sys.TcSysNewsVsType;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.sys.INewsManager;
/**
 *<p>资讯信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class NewsManager extends BaseManager implements INewsManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(NewsManager.class);
	
	/**信息DAO 接口类*/
	private ITcSysNewsDao tcSysNewsDao;
	/**资讯信息 栏目 分类 Dao类 */
	private ITcSysNewsVsTypeDao tcSysNewsVsTypeDao;
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
	public String saveOrUpdateData(TcSysNewsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysNews dto=new TcSysNews();
				dto.setId(bean.getId());//ID
				dto.setVersion(bean.getVersion());//VERSION
				dto.setSort_num(bean.getSort_num()==null?"0":bean.getSort_num());//序号
				dto.setTitle(bean.getTitle());//标题
				dto.setSecond_title(bean.getSecond_title());//副标题
				dto.setKeyword(bean.getKeyword());//关键字
				dto.setBrief_info(bean.getBrief_info());//摘要
				if(Validator.notEmpty(bean.getDetail_info())){
				IOHelper.deleteFile(bean.getDetail_info());//TODO=删除文件
				dto.setDetail_info(IOHelper.writeHtml("html",bean.getDetail_info()));//内容
				}
				dto.setIs_ontop(!"1".equals(bean.getIs_ontop())?"0":"1");//推荐置顶
				dto.setClick_count(bean.getClick_count());//点击量
				dto.setMsgtype(bean.getMsgtype());//消息类型
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setPic_url(bean.getPic_url());//标题图url
				dto.setInfo_source(bean.getInfo_source());//0微信1网站
				//判断数据是否存在
				if(tcSysNewsDao.isDataYN(dto)!=0){
					//数据存在
					tcSysNewsDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcSysNewsDao.insert(dto);
				}
				//TODO ---清空资讯栏目关联关系----
				TcSysNewsVsType typeDto1=new TcSysNewsVsType();
				typeDto1.setNew_id(dto.getId());//资讯id
				tcSysNewsVsTypeDao.deleteByPrimaryKey(typeDto1);
				//增加资讯 栏目 关联关系
				if(bean.getNews_type()!=null){
					//==================================================
					for(String type:bean.getNews_type()){
						TcSysNewsVsType typeDto2=new TcSysNewsVsType();
						typeDto2.setNew_id(dto.getId());//资讯id
						typeDto2.setType_id(type);//资讯栏目id
						//TODO ---保存资讯栏目关联关系----
						tcSysNewsVsTypeDao.insert(typeDto2);
					}
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
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcSysNewsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysNews dto=new TcSysNews();
				dto.setId(bean.getId());//id
				tcSysNewsDao.deleteByPrimaryKey(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
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
	public String deleteDataById(TcSysNewsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysNews dto=new TcSysNews();
				dto.setId(bean.getId());//ID
				tcSysNewsDao.deleteById(dto);
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
	public List<TcSysNewsBean> findDataIsPage(TcSysNewsBean bean){
		List<TcSysNewsBean> beans=null;
		try {
    	   TcSysNews dto=new TcSysNews();
    	   if(bean!=null){
    		dto.setId(bean.getId());//ID
			dto.setTitle(bean.getTitle());//标题
			dto.setKeyword(bean.getKeyword());//关键字
			dto.setIs_ontop(bean.getIs_ontop());//推荐置顶
			dto.setMsgtype(bean.getMsgtype());//消息类型
			dto.setType_id(bean.getType_id());//
			dto.setPageInfo(bean.getPageInfo());//分页对象
			dto.setDel_flag(bean.getDel_flag());//删除标记
			dto.setInfo_source(bean.getInfo_source());//0微信1网站
    	   }
			beans=tcSysNewsDao.findDataIsPage(dto);
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
	public List<TcSysNewsBean> findDataIsList(TcSysNewsBean bean){
		List<TcSysNewsBean> beans=null;
		try {
	    	   TcSysNews dto=new TcSysNews();
	    	   if(bean!=null){
	    		    dto.setId(bean.getId());//ID
		   			dto.setTitle(bean.getTitle());//标题
		   			dto.setKeyword(bean.getKeyword());//关键字
		   			dto.setIs_ontop(bean.getIs_ontop());//推荐置顶
		   			dto.setMsgtype(bean.getMsgtype());//消息类型
		   			dto.setDel_flag(bean.getDel_flag());//是否删除
		   			dto.setType_id(bean.getType_id());//
		   			dto.setInfo_source(bean.getInfo_source());//0微信1网站
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=tcSysNewsDao.findDataIsList(dto);
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
	public TcSysNewsBean findDataById(TcSysNewsBean bean){
       TcSysNewsBean bean1=null;
       try {
    	   TcSysNews dto=new TcSysNews();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
	   			dto.setTitle(bean.getTitle());//标题
	   			dto.setKeyword(bean.getKeyword());//关键字
	   			dto.setIs_ontop(bean.getIs_ontop());//推荐置顶
	   			dto.setMsgtype(bean.getMsgtype());//消息类型
	   			dto.setDel_flag(bean.getDel_flag());//是否删除
	   			dto.setInfo_source(bean.getInfo_source());//0微信1网站
    	   }
			bean1=tcSysNewsDao.selectByPrimaryKey(dto);
			if(bean1!=null){
				bean1.setDetail_info(IOHelper.readHtml(bean1.getDetail_info()));
			}
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcSysNewsDao getTcSysNewsDao() {
	    return tcSysNewsDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcSysNewsDao 信息DAO 接口类
	 */
	public void setTcSysNewsDao(ITcSysNewsDao tcSysNewsDao) {
	    this.tcSysNewsDao = tcSysNewsDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcSysNewsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysNews dto=new TcSysNews();
				dto.setId(bean.getId());//ID
				tcSysNewsDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * 资讯信息 栏目 分类 Dao类取得
	 * @return 资讯信息 栏目 分类 Dao类
	 */
	public ITcSysNewsVsTypeDao getTcSysNewsVsTypeDao() {
	    return tcSysNewsVsTypeDao;
	}
	/**
	 * 资讯信息 栏目 分类 Dao类设定
	 * @param tcSysNewsVsTypeDao 资讯信息 栏目 分类 Dao类
	 */
	public void setTcSysNewsVsTypeDao(ITcSysNewsVsTypeDao tcSysNewsVsTypeDao) {
	    this.tcSysNewsVsTypeDao = tcSysNewsVsTypeDao;
	}
	/**
	 * <p>根据资讯id查询资讯的所属栏目集合。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>集合。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysNewsTypeBean> findTypeDataByIdIsList(TcSysNewsBean bean){
		List<TcSysNewsTypeBean> beans=null;
		try {
			TcSysNewsType dto=new TcSysNewsType();
	    	   if(bean!=null){
	    		    dto.setId(bean.getId());//资讯ID
	    	   }
				beans=tcSysNewsVsTypeDao.findTypeDataByIdIsList(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
}
