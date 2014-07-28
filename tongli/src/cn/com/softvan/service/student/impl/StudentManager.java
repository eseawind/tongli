/*
 * 学员信息管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.student.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.student.TcStudentBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IOHelper;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.student.ITcStudentDao;
import cn.com.softvan.dao.daointer.student.ITcStudentHobbyItemsDao;
import cn.com.softvan.dao.entity.student.TcStudent;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.student.IStudentManager;
/**
 *<p>学员信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class StudentManager extends BaseManager implements IStudentManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(StudentManager.class);
	
	/**信息DAO 接口类*/
	private ITcStudentDao tcStudentDao;
	/**信息详情 DAO*/
	private ITcStudentHobbyItemsDao tcStudentHobbyItemsDao;
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
	public String saveOrUpdateData(TcStudentBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcStudent dto=new TcStudent();
				dto.setKeyword(bean.getKeyword());//关键字
				dto.setId(bean.getId());//学员id
				dto.setName(bean.getName());//姓名
				dto.setAge(bean.getAge());//年龄
				dto.setSex(bean.getSex());//性别
				dto.setHobby(bean.getHobby());//爱好
				if(Validator.notEmpty(bean.getDetail_info())){
					IOHelper.deleteFile(bean.getDetail_info());//TODO=删除文件
					dto.setDetail_info(IOHelper.writeHtml("html",bean.getDetail_info()));//内容
				}
				dto.setPic_url(bean.getPic_url());//头像
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//VERSION
				dto.setBirthdate(bean.getBirthdate());//生日
				dto.setHeight(bean.getHeight());//身高
				dto.setWeight(bean.getWeight());//体重
				dto.setNationality(bean.getNationality());//国籍
				dto.setTutor(bean.getTutor());//监护人姓名
				dto.setTel(bean.getTel());//紧急联系电话
				dto.setAddres(bean.getAddres());//家庭住址
				dto.setSchool(bean.getSchool());//所在学校或幼儿园
				dto.setLike_sports(bean.getLike_sports());//喜欢的运动
				dto.setOnce_in_motion(bean.getOnce_in_motion());//曾经参加的运动
				dto.setInjury_history(bean.getInjury_history());//伤病史
				dto.setParents_expectations(bean.getParents_expectations());//家长的期许
				//判断数据是否存在
				if(tcStudentDao.isDataYN(dto)!=0){
					//数据存在
					tcStudentDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					dto.setId(IdUtils.createUUID(32));
					tcStudentDao.insert(dto);
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
	public String deleteData(TcStudentBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcStudent dto=new TcStudent();
				dto.setId(bean.getId());//id
				tcStudentDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcStudentBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcStudent dto=new TcStudent();
				dto.setId(bean.getId());//ID
				tcStudentDao.deleteById(dto);
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
	public List<TcStudentBean> findDataIsPage(TcStudentBean bean){
		List<TcStudentBean> beans=null;
		try {
    	   TcStudent dto=new TcStudent();
    	   if(bean!=null){
			dto.setKeyword(bean.getKeyword());//关键字
			dto.setId(bean.getId());//学员id
			dto.setName(bean.getName());//姓名
			dto.setAge(bean.getAge());//年龄
			dto.setSex(bean.getSex());//性别
			dto.setHobby(bean.getHobby());//爱好
			dto.setPageInfo(bean.getPageInfo());//分页对象
			dto.setDel_flag(bean.getDel_flag());//删除标记
    	   }
			beans=(List<TcStudentBean>) tcStudentDao.findDataIsPage(dto);
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
	public List<TcStudentBean> findDataIsList(TcStudentBean bean){
		List<TcStudentBean> beans=null;
		try {
	    	   TcStudent dto=new TcStudent();
	    	   if(bean!=null){
	    		   dto.setKeyword(bean.getKeyword());//关键字
					dto.setId(bean.getId());//学员id
					dto.setName(bean.getName());//姓名
					dto.setAge(bean.getAge());//年龄
					dto.setSex(bean.getSex());//性别
					dto.setHobby(bean.getHobby());//爱好
		   			dto.setDel_flag(bean.getDel_flag());//是否删除
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=(List<TcStudentBean>) tcStudentDao.findDataIsList(dto);
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
	public TcStudentBean findDataById(TcStudentBean bean){
       TcStudentBean bean1=null;
       try {
    	   TcStudent dto=new TcStudent();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//ID
	   			dto.setKeyword(bean.getKeyword());//关键字
	   			dto.setDel_flag(bean.getDel_flag());//是否删除
    	   }
			bean1=(TcStudentBean) tcStudentDao.selectByPrimaryKey(dto);
			if(bean1!=null && Validator.notEmpty(bean1.getDetail_info())){	
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
	public ITcStudentDao getTcStudentDao() {
	    return tcStudentDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcStudentDao 信息DAO 接口类
	 */
	public void setTcStudentDao(ITcStudentDao tcStudentDao) {
	    this.tcStudentDao = tcStudentDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcStudentBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcStudent dto=new TcStudent();
				dto.setId(bean.getId());//ID
				tcStudentDao.recoveryDataById(dto);
			} catch (Exception e) {
				msg="信息删除失败,数据库处理错误!";
				log.error(msg, e);
				throw new Exception(msg);
			}
		}
		return msg;
	}
	/**
	 * 信息详情 DAO取得
	 * @return 信息详情 DAO
	 */
	public ITcStudentHobbyItemsDao getTcStudentHobbyItemsDao() {
	    return tcStudentHobbyItemsDao;
	}
	/**
	 * 信息详情 DAO设定
	 * @param tcStudentHobbyItemsDao 信息详情 DAO
	 */
	public void setTcStudentHobbyItemsDao(ITcStudentHobbyItemsDao tcStudentHobbyItemsDao) {
	    this.tcStudentHobbyItemsDao = tcStudentHobbyItemsDao;
	}
}
