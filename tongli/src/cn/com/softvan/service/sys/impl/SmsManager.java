/*
 * 短信管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.20  wuxiaogang      程序・发布
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

import cn.com.softvan.bean.sys.TcSysSmsBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.sys.ITcSysSmsDao;
import cn.com.softvan.dao.entity.sys.TcSysSms;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.sys.ISmsManager;
/**
 *<p>短信管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class SmsManager extends BaseManager implements ISmsManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(SmsManager.class);
	
	/**信息DAO 接口类*/
	private ITcSysSmsDao tcSysSmsDao;
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
	public String saveOrUpdateData(TcSysSmsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysSms dto=new TcSysSms();
				dto.setSms_id(bean.getSms_id());//短信编号
				dto.setSms_src_id(bean.getSms_src_id());//发信人
				dto.setSms_dst_id(bean.getSms_dst_id());//收信人
				dto.setSms_content(bean.getSms_content());//短信内容
				dto.setSms_send_time(bean.getSms_send_time());//预定发送时间
				dto.setSms_sended_time(bean.getSms_sended_time());//实际发送时间
				dto.setSms_send_count(bean.getSms_send_count());//实际发送次数
				dto.setSms_status(bean.getSms_status());//短信状态

				//判断数据是否存在
				if(tcSysSmsDao.isDataYN(dto)!=0){
					//数据存在---手机号码存在---且格式正确-----
					if(Validator.notEmpty(dto.getSms_dst_id())&&Validator.isMobile(dto.getSms_dst_id())){
						tcSysSmsDao.updateByPrimaryKeySelective(dto);
					}else{
						msg="手机号未填写!";
					}
				}else{
					//新增
					if(Validator.isEmpty(dto.getSms_id())){
						dto.setSms_id(IdUtils.createUUID(32));
					}
					tcSysSmsDao.insert(dto);
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
	public String deleteData(TcSysSmsBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcSysSms dto=new TcSysSms();
				dto.setSms_id(bean.getSms_id());//短信编号

				tcSysSmsDao.deleteByPrimaryKey(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
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
	public List<TcSysSmsBean> findDataIsPage(TcSysSmsBean bean){
		List<TcSysSmsBean> beans=null;
		try {
    	   TcSysSms dto=new TcSysSms();
    	   if(bean!=null){
    		   dto.setSms_id(bean.getSms_id());//短信编号
    		   dto.setSms_src_id(bean.getSms_src_id());//发信人
    		   dto.setSms_dst_id(bean.getSms_dst_id());//收信人
    		   dto.setSms_content(bean.getSms_content());//短信内容
    		   dto.setSms_send_time(bean.getSms_send_time());//预定发送时间
    		   dto.setSms_sended_time(bean.getSms_sended_time());//实际发送时间
    		   dto.setSms_send_count(bean.getSms_send_count());//实际发送次数
    		   dto.setSms_status(bean.getSms_status());//短信状态

			dto.setPageInfo(bean.getPageInfo());//分页对象
    	   }
			beans=tcSysSmsDao.findDataIsPage(dto);
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
	public List<TcSysSmsBean> findDataIsList(TcSysSmsBean bean){
		List<TcSysSmsBean> beans=null;
		try {
	    	   TcSysSms dto=new TcSysSms();
	    	   if(bean!=null){
	    		   dto.setSms_id(bean.getSms_id());//短信编号
	    		   dto.setSms_src_id(bean.getSms_src_id());//发信人
	    		   dto.setSms_dst_id(bean.getSms_dst_id());//收信人
	    		   dto.setSms_content(bean.getSms_content());//短信内容
	    		   dto.setSms_send_time(bean.getSms_send_time());//预定发送时间
	    		   dto.setSms_sended_time(bean.getSms_sended_time());//实际发送时间
	    		   dto.setSms_send_count(bean.getSms_send_count());//实际发送次数
	    		   dto.setSms_status(bean.getSms_status());//短信状态


		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=tcSysSmsDao.findDataIsList(dto);
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
	public TcSysSmsBean findDataById(TcSysSmsBean bean){
       TcSysSmsBean bean1=null;
       try {
    	   TcSysSms dto=new TcSysSms();
    	   if(bean!=null){
    		   dto.setSms_id(bean.getSms_id());//短信编号
    		   dto.setSms_src_id(bean.getSms_src_id());//发信人
    		   dto.setSms_dst_id(bean.getSms_dst_id());//收信人
    		   dto.setSms_content(bean.getSms_content());//短信内容
    		   dto.setSms_send_time(bean.getSms_send_time());//预定发送时间
    		   dto.setSms_sended_time(bean.getSms_sended_time());//实际发送时间
    		   dto.setSms_send_count(bean.getSms_send_count());//实际发送次数
    		   dto.setSms_status(bean.getSms_status());//短信状态
    	   }
			bean1=tcSysSmsDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	/**
	 * 信息DAO 接口类取得
	 * @return 信息DAO 接口类
	 */
	public ITcSysSmsDao getTcSysSmsDao() {
	    return tcSysSmsDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcSysSmsDao 信息DAO 接口类
	 */
	public void setTcSysSmsDao(ITcSysSmsDao tcSysSmsDao) {
	    this.tcSysSmsDao = tcSysSmsDao;
	}
}
