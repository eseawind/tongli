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

import cn.com.softvan.bean.sys.TcSysParterBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.sys.ITcSysParterDao;
import cn.com.softvan.dao.entity.sys.TcSysParter;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.sys.IParterManager;
/**
 *<p>资讯信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class ParterManager extends BaseManager implements IParterManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(ParterManager.class);
	
	/**信息DAO 接口类*/
	private ITcSysParterDao tcSysParterDao;
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
	public String saveOrUpdateData(TcSysParterBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysParter dto=new TcSysParter();
				dto.setId(bean.getId());//ID
				dto.setVersion(bean.getVersion());//VERSION
				dto.setSort_num(bean.getSort_num()==null?"0":bean.getSort_num());//序号
				dto.setIs_ontop(!"1".equals(bean.getIs_ontop())?"0":"1");//推荐置顶
				dto.setName(bean.getName());//名称
				dto.setUrl(bean.getUrl());//链接地址
				dto.setPic_url(bean.getPic_url());//图片链接
				dto.setType(bean.getType());//合作商类型0积分发行商1市场合作商
				dto.setIs_show(bean.getIs_show());//是否展示
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除

				//判断数据是否存在
				if(tcSysParterDao.isDataYN(dto)!=0){
					//数据存在
					tcSysParterDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcSysParterDao.insert(dto);
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
	public String deleteData(TcSysParterBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysParter dto=new TcSysParter();
				dto.setId(bean.getId());//id
				tcSysParterDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcSysParterBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysParter dto=new TcSysParter();
				dto.setId(bean.getId());//ID
				tcSysParterDao.deleteById(dto);
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
	public List<TcSysParterBean> findDataIsPage(TcSysParterBean bean){
		List<TcSysParterBean> beans=null;
		try {
    	   TcSysParter dto=new TcSysParter();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
				dto.setVersion(bean.getVersion());//VERSION
				dto.setSort_num(bean.getSort_num()==null?"0":bean.getSort_num());//序号
				dto.setIs_ontop(!"1".equals(bean.getIs_ontop())?"0":"1");//推荐置顶
				dto.setName(bean.getName());//名称
				dto.setUrl(bean.getUrl());//链接地址
				dto.setPic_url(bean.getPic_url());//图片链接
				dto.setType(bean.getType());//合作商类型0积分发行商1市场合作商
				dto.setIs_show(bean.getIs_show());//是否展示
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setPageInfo(bean.getPageInfo());//分页
    	   }
			beans=tcSysParterDao.findDataIsPage(dto);
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
	public List<TcSysParterBean> findDataIsList(TcSysParterBean bean){
		List<TcSysParterBean> beans=null;
		try {
	    	   TcSysParter dto=new TcSysParter();
	    	   if(bean!=null){
	    		   dto.setId(bean.getId());//ID
					dto.setVersion(bean.getVersion());//VERSION
					dto.setSort_num(bean.getSort_num()==null?"0":bean.getSort_num());//序号
					dto.setIs_ontop(!"1".equals(bean.getIs_ontop())?"0":"1");//推荐置顶
					dto.setName(bean.getName());//名称
					dto.setUrl(bean.getUrl());//链接地址
					dto.setPic_url(bean.getPic_url());//图片链接
					dto.setType(bean.getType());//合作商类型0积分发行商1市场合作商
					dto.setIs_show(bean.getIs_show());//是否展示
					dto.setNote(bean.getNote());//备注
//					dto.setDate_created(bean.getDate_created());//数据输入日期
					dto.setCreate_id(bean.getCreate_id());//建立者ID
					dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//					dto.setLast_updated(bean.getLast_updated());//资料更新日期
					dto.setUpdate_id(bean.getUpdate_id());//修改者ID
					dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
					dto.setDel_flag(bean.getDel_flag());//是否删除
	    	   }
				beans=tcSysParterDao.findDataIsList(dto);
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
	public TcSysParterBean findDataById(TcSysParterBean bean){
       TcSysParterBean bean1=null;
       try {
    	   TcSysParter dto=new TcSysParter();
    	   if(bean!=null){
    		   dto.setId(bean.getId());//ID
	   			dto.setIs_ontop(bean.getIs_ontop());//推荐置顶
	   			dto.setType(bean.getType());//分类
	   			dto.setDel_flag(bean.getDel_flag());//是否删除
    	   }
			bean1=tcSysParterDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcSysParterDao getTcSysParterDao() {
	    return tcSysParterDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcSysParterDao 信息DAO 接口类
	 */
	public void setTcSysParterDao(ITcSysParterDao tcSysParterDao) {
	    this.tcSysParterDao = tcSysParterDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcSysParterBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysParter dto=new TcSysParter();
				dto.setId(bean.getId());//ID
				tcSysParterDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>列表  TOP x 前几条。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcSysParterBean> findDataIsListTop(TcSysParterBean bean){
		List<TcSysParterBean> beans=null;
		try {
    	   TcSysParter dto=new TcSysParter();
    	   if(bean!=null){
				dto.setType(bean.getType());//合作商类型0积分发行商1市场合作商
    	   }
		   beans=tcSysParterDao.findDataIsListTop(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
}