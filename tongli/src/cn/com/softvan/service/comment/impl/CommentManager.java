/*
 * 评论 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.06.01  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.comment.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.comment.TcCommentBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.comment.ITcCommentDao;
import cn.com.softvan.dao.entity.comment.TcComment;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.comment.ICommentManager;
/**
 *<p>评论 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class CommentManager extends BaseManager implements ICommentManager {
	/** 日志 */
	private static final transient Logger log = Logger
			.getLogger(CommentManager.class);
	/**评论  数据库处理 DAO*/
	private ITcCommentDao tcCommentDao;
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>新增信息。</div>
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	public String saveOrUpdateData(TcCommentBean bean){
		String msg="1";
		try {
			TcComment dto=new TcComment();
			
			dto.setId(bean.getId());//id
			dto.setMember_type(bean.getMember_type());//会员类型
			dto.setMember_id(bean.getMember_id());//会员id
			dto.setInfo_id(bean.getInfo_id());//被评论信息id
			dto.setDetail_info(bean.getDetail_info());//评论内容
			dto.setNote(bean.getNote());//备注
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者id
			dto.setCreate_ip(bean.getCreate_ip());//建立者ip
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者id
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
			dto.setDel_flag(bean.getDel_flag());//是否删除
			dto.setVersion(bean.getVersion());//version
			dto.setIs_show(bean.getIs_show());//是否显示
			//判断数据是否存在
			if(tcCommentDao.isDataYN(dto)!=0){
				
				//数据存在
				tcCommentDao.updateByPrimaryKeySelective(dto);
			}else{
				if(Validator.isEmpty(dto.getId())){
					dto.setId(IdUtils.createUUID(32));
				}
				//新增
				tcCommentDao.insert(dto);
			}
		} catch (Exception e) {
			log.error("信息保存,数据库错误!",e);
		}
		return msg;
	}
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>修改信息。</div>
	 * </ol>
	 * @return 处理结果
	 * @throws Exception 
	 */
	public String updateData(TcCommentBean bean){
		String msg="1";
		try {
			TcComment dto=new TcComment();
			
			dto.setId(bean.getId());//id
			dto.setMember_type(bean.getMember_type());//会员类型
			dto.setMember_id(bean.getMember_id());//会员id
			dto.setInfo_id(bean.getInfo_id());//被评论信息id
			dto.setDetail_info(bean.getDetail_info());//评论内容
			dto.setNote(bean.getNote());//备注
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者id
			dto.setCreate_ip(bean.getCreate_ip());//建立者ip
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者id
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
			dto.setDel_flag(bean.getDel_flag());//是否删除
			dto.setVersion(bean.getVersion());//version
			dto.setIs_show(bean.getIs_show());//是否显示
			//数据存在
			tcCommentDao.updateByPrimaryKeySelective(dto);
		} catch (Exception e) {
			msg="信息保存,数据库错误!";
			log.error(msg,e);
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
	public List<TcCommentBean> findDataIsList(TcCommentBean bean){
		List<TcCommentBean> beans=null;
		try {
			TcComment dto=new TcComment();
			
			dto.setId(bean.getId());//id
			dto.setMember_type(bean.getMember_type());//会员类型
			dto.setMember_id(bean.getMember_id());//会员id
			dto.setInfo_id(bean.getInfo_id());//被评论信息id
			dto.setDetail_info(bean.getDetail_info());//评论内容
			dto.setNote(bean.getNote());//备注
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者id
			dto.setCreate_ip(bean.getCreate_ip());//建立者ip
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者id
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
			dto.setDel_flag(bean.getDel_flag());//是否删除
			dto.setVersion(bean.getVersion());//version
			dto.setIs_show(bean.getIs_show());//是否显示
			dto.setLimit_s(bean.getLimit_s());
			dto.setLimit_e(bean.getLimit_e());
			beans=(List<TcCommentBean>) tcCommentDao.findDataIsList(dto);
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
	public List<TcCommentBean> findDataIsPage(TcCommentBean bean){
		List<TcCommentBean> beans=null;
		try {
			TcComment dto=new TcComment();
			
			dto.setId(bean.getId());//id
			dto.setMember_type(bean.getMember_type());//会员类型
			dto.setMember_id(bean.getMember_id());//会员id
			dto.setInfo_id(bean.getInfo_id());//被评论信息id
			dto.setDetail_info(bean.getDetail_info());//评论内容
			dto.setNote(bean.getNote());//备注
			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者id
			dto.setCreate_ip(bean.getCreate_ip());//建立者ip
			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者id
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
			dto.setDel_flag(bean.getDel_flag());//是否删除
			dto.setVersion(bean.getVersion());//version
			dto.setIs_show(bean.getIs_show());//是否显示
			dto.setPageInfo(bean.getPageInfo());
			beans=(List<TcCommentBean>) tcCommentDao.findDataIsPage(dto);
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
	public TcCommentBean findDataById(TcCommentBean bean){
		TcCommentBean bean1=null;
		try {
			TcComment dto=new TcComment();
			if(bean!=null){
				
				dto.setId(bean.getId());//id
				dto.setMember_type(bean.getMember_type());//会员类型
				dto.setMember_id(bean.getMember_id());//会员id
				dto.setInfo_id(bean.getInfo_id());//被评论信息id
				dto.setDetail_info(bean.getDetail_info());//评论内容
				dto.setNote(bean.getNote());//备注
				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者id
				dto.setCreate_ip(bean.getCreate_ip());//建立者ip
				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//version
				dto.setIs_show(bean.getIs_show());//是否显示
			}
			bean1=(TcCommentBean) tcCommentDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * <p>信息编辑。</p>
	 * <ol>[功能概要] 
	 * <div>物理删除。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String deleteData(TcCommentBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcComment dto=new TcComment();
				dto.setId(bean.getId());//id
				tcCommentDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcCommentBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcComment dto=new TcComment();
				dto.setId(bean.getId());//ID
				tcCommentDao.deleteById(dto);
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
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcCommentBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcComment dto=new TcComment();
				dto.setId(bean.getId());//ID
				tcCommentDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息恢复失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * 评论  数据库处理 DAO取得
	 * @return 评论  数据库处理 DAO
	 */
	public ITcCommentDao getTcCommentDao() {
	    return tcCommentDao;
	}
	/**
	 * 评论  数据库处理 DAO设定
	 * @param tcCommentDao 评论  数据库处理 DAO
	 */
	public void setTcCommentDao(ITcCommentDao tcCommentDao) {
	    this.tcCommentDao = tcCommentDao;
	}
}