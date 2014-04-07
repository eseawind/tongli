/*
 * 会员信息管理 service 接口实现类
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.04.06  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家 System. - All Rights Reserved.
 *
 */
package cn.com.softvan.service.member.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.member.TcMemberBean;
import cn.com.softvan.bean.student.TcStudentBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IOHelper;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.member.ITcMemberDao;
import cn.com.softvan.dao.daointer.member.ITcMemberDetailDao;
import cn.com.softvan.dao.daointer.member.ITcMemberVsStudentDao;
import cn.com.softvan.dao.entity.member.TcMember;
import cn.com.softvan.dao.entity.member.TcMemberDetail;
import cn.com.softvan.dao.entity.member.TcMemberVsStudent;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.member.IMemberManager;
/**
 *<p>会员信息管理 service类。</p>
 * <ol>[功能概要] 
 * <div>信息编辑。</div>
 * <div>信息检索。</div>
 * </ol>
 * @author wuxiaogang
 */
public class MemberManager extends BaseManager implements IMemberManager {
	/** 日志 */
	private static final transient Logger log = Logger.getLogger(MemberManager.class);
	
	/**信息DAO 接口类*/
	private ITcMemberDao tcMemberDao;
	/**信息详情 DAO*/
	private ITcMemberDetailDao tcMemberDetailDao;
	/**会员与学员关联表信息 Dao类 */
	private ITcMemberVsStudentDao tcMemberVsStudentDao;
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
	public String saveOrUpdateData(TcMemberBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcMember dto=new TcMember();
				dto.setId(bean.getId());//id
				dto.setUser_name(bean.getUser_name());//用户名
				dto.setPasswd(bean.getPasswd());//密码
				dto.setUser_type(bean.getUser_type());//会员类型
				dto.setNickname(bean.getNickname());//用��昵称
				dto.setLogin_count(bean.getLogin_count());//登录次数
				dto.setLast_login(bean.getLast_login());//最后登入时间
				dto.setBind_mobile(bean.getBind_mobile());//绑定手机
				dto.setBind_email(bean.getBind_email());//绑定邮箱
				dto.setSex(bean.getSex());//性别
				dto.setTel(bean.getTel());//电话
				dto.setPic_url(bean.getPic_url());//头像
				dto.setReal_name(bean.getReal_name());//真实姓名
				dto.setIs_enabled(bean.getIs_enabled());//是否可用
				dto.setLast_login_ip(bean.getLast_login_ip());//最后登录IP
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//VERSION
				//判断数据是否存在
				if(tcMemberDao.isDataYN(dto)!=0){
					//数据存在
					tcMemberDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcMemberDao.insert(dto);
				}
				//会员详情
				TcMemberDetail dto1=new TcMemberDetail();
				dto1.setUser_id(dto.getId());//用户id
				dto1.setBirthdate(bean.getBirthdate());//生日
				dto1.setBlood_type(bean.getBlood_type());//血型
				dto1.setEdu_level(bean.getEdu_level());//文化程度
				dto1.setTrade(bean.getTrade());//从事行业
				dto1.setJob(bean.getJob());//从事职业
				dto1.setIncome_level(bean.getIncome_level());//收入水平
				dto1.setProvince_id(bean.getProvince_id());//省
				dto1.setCity_id(bean.getCity_id());//市
				dto1.setCounty_id(bean.getCounty_id());//县
				dto1.setAddress(bean.getAddress());//详细地址
				dto1.setZipcode(bean.getZipcode());//邮政编码
				dto1.setCredential(bean.getCredential());//证件类型
				dto1.setCredential_code(bean.getCredential_code());//证件号码
				dto1.setQq(bean.getQq());//QQ
				dto1.setMsn(bean.getMsn());//MSN
				dto1.setHobby(bean.getHobby());//爱好
				if(Validator.notEmpty(bean.getDetail_info())){
					IOHelper.deleteFile(bean.getDetail_info());//TODO=删除文件
					dto1.setDetail_info(IOHelper.writeHtml("html",bean.getDetail_info()));//内容
				}
				//判断数据是否存在
				if(tcMemberDetailDao.isDataYN(dto1)!=0){
					//数据存在
					tcMemberDetailDao.updateByPrimaryKeySelective(dto1);
				}else{
					//新增
					if(Validator.isEmpty(dto1.getUser_id())){
						throw new Exception("会员详情信息保存失败!会员id获取失败!");
					}
					tcMemberDetailDao.insert(dto1);
				}
				//TODO ------保存会员与学员关系-------
				//会员类型为家长 user_type==1
				if("1".equals(bean.getUser_type())){
					//清空当前会员与学员的关联关系
					TcMemberVsStudent msDto=new TcMemberVsStudent();
					//会员id
					msDto.setUser_id(dto.getId());
					//清空
					tcMemberVsStudentDao.deleteByPrimaryKey(msDto);
					if(bean.getSids()!=null){
						for(String sid:bean.getSids()){
							msDto.setStudent_id(sid);
							msDto.setCreate_id(bean.getCreate_id());
							msDto.setCreate_ip(bean.getCreate_ip());
							//写入数据库
							tcMemberVsStudentDao.insert(msDto);
						}
					}
					//
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
	public String deleteData(TcMemberBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcMember dto=new TcMember();
				dto.setId(bean.getId());//id
				tcMemberDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcMemberBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcMember dto=new TcMember();
				dto.setId(bean.getId());//ID
				tcMemberDao.deleteById(dto);
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
	public List<TcMemberBean> findDataIsPage(TcMemberBean bean){
		List<TcMemberBean> beans=null;
		try {
    	   TcMember dto=new TcMember();
    	   if(bean!=null){
    		dto.setId(bean.getId());//id
			dto.setUser_name(bean.getUser_name());//用户名
			dto.setPasswd(bean.getPasswd());//密码
			dto.setUser_type(bean.getUser_type());//会员类型
			dto.setNickname(bean.getNickname());//用��昵称
			dto.setLogin_count(bean.getLogin_count());//登录次数
			dto.setLast_login(bean.getLast_login());//最后登入时间
			dto.setBind_mobile(bean.getBind_mobile());//绑定手机
			dto.setBind_email(bean.getBind_email());//绑定邮箱
			dto.setSex(bean.getSex());//性别
			dto.setTel(bean.getTel());//电话
			dto.setPic_url(bean.getPic_url());//头像
			dto.setReal_name(bean.getReal_name());//真实姓名
			dto.setIs_enabled(bean.getIs_enabled());//是否可用
			dto.setLast_login_ip(bean.getLast_login_ip());//最后登录IP
			dto.setNote(bean.getNote());//备注
//			dto.setDate_created(bean.getDate_created());//数据输入日期
			dto.setCreate_id(bean.getCreate_id());//建立者ID
			dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//			dto.setLast_updated(bean.getLast_updated());//资料更新日期
			dto.setUpdate_id(bean.getUpdate_id());//修改者ID
			dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
			dto.setDel_flag(bean.getDel_flag());//是否删除
			dto.setVersion(bean.getVersion());//VERSION
			dto.setPageInfo(bean.getPageInfo());//分页对象
    	   }
			beans=tcMemberDao.findDataIsPage(dto);
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
	public List<TcMemberBean> findDataIsList(TcMemberBean bean){
		List<TcMemberBean> beans=null;
		try {
	    	   TcMember dto=new TcMember();
	    	   if(bean!=null){
	    		    dto.setId(bean.getId());//id
					dto.setUser_name(bean.getUser_name());//用户名
					dto.setPasswd(bean.getPasswd());//密码
					dto.setUser_type(bean.getUser_type());//会员类型
					dto.setNickname(bean.getNickname());//用��昵称
					dto.setLogin_count(bean.getLogin_count());//登录次数
					dto.setLast_login(bean.getLast_login());//最后登入时间
					dto.setBind_mobile(bean.getBind_mobile());//绑定手机
					dto.setBind_email(bean.getBind_email());//绑定邮箱
					dto.setSex(bean.getSex());//性别
					dto.setTel(bean.getTel());//电话
					dto.setPic_url(bean.getPic_url());//头像
					dto.setReal_name(bean.getReal_name());//真实姓名
					dto.setIs_enabled(bean.getIs_enabled());//是否可用
					dto.setLast_login_ip(bean.getLast_login_ip());//最后登录IP
					dto.setNote(bean.getNote());//备注
//					dto.setDate_created(bean.getDate_created());//数据输入日期
					dto.setCreate_id(bean.getCreate_id());//建立者ID
					dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//					dto.setLast_updated(bean.getLast_updated());//资料更新日期
					dto.setUpdate_id(bean.getUpdate_id());//修改者ID
					dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
					dto.setDel_flag(bean.getDel_flag());//是否删除
					dto.setVersion(bean.getVersion());//VERSION
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=tcMemberDao.findDataIsList(dto);
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
	public TcMemberBean findDataById(TcMemberBean bean){
       TcMemberBean bean1=null;
       try {
    	   TcMember dto=new TcMember();
    	   if(bean!=null){
    		    dto.setId(bean.getId());//id
				dto.setUser_name(bean.getUser_name());//用户名
				dto.setPasswd(bean.getPasswd());//密码
				dto.setUser_type(bean.getUser_type());//会员类型
				dto.setNickname(bean.getNickname());//用��昵称
				dto.setLogin_count(bean.getLogin_count());//登录次数
				dto.setLast_login(bean.getLast_login());//最后登入时间
				dto.setBind_mobile(bean.getBind_mobile());//绑定手机
				dto.setBind_email(bean.getBind_email());//绑定邮箱
				dto.setSex(bean.getSex());//性别
				dto.setTel(bean.getTel());//电话
				dto.setPic_url(bean.getPic_url());//头像
				dto.setReal_name(bean.getReal_name());//真实姓名
				dto.setIs_enabled(bean.getIs_enabled());//是否可用
				dto.setLast_login_ip(bean.getLast_login_ip());//最后登录IP
				dto.setNote(bean.getNote());//备注
//				dto.setDate_created(bean.getDate_created());//数据输入日期
				dto.setCreate_id(bean.getCreate_id());//建立者ID
				dto.setCreate_ip(bean.getCreate_ip());//建立者IP
//				dto.setLast_updated(bean.getLast_updated());//资料更新日期
				dto.setUpdate_id(bean.getUpdate_id());//修改者ID
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者IP
				dto.setDel_flag(bean.getDel_flag());//是否删除
				dto.setVersion(bean.getVersion());//VERSION
    	   }
			bean1=tcMemberDao.selectByPrimaryKey(dto);
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
	public ITcMemberDao getTcMemberDao() {
	    return tcMemberDao;
	}
	/**
	 * 信息DAO 接口类设定
	 * @param tcMemberDao 信息DAO 接口类
	 */
	public void setTcMemberDao(ITcMemberDao tcMemberDao) {
	    this.tcMemberDao = tcMemberDao;
	}
	/**
	 * <p>信息 单条。</p>
	 * <ol>[功能概要] 
	 * <div>恢复逻辑删除的数据。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public String recoveryDataById(TcMemberBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcMember dto=new TcMember();
				dto.setId(bean.getId());//ID
				tcMemberDao.recoveryDataById(dto);
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
	public ITcMemberDetailDao getTcMemberDetailDao() {
	    return tcMemberDetailDao;
	}
	/**
	 * 信息详情 DAO设定
	 * @param tcMemberDetailDao 信息详情 DAO
	 */
	public void setTcMemberDetailDao(ITcMemberDetailDao tcMemberDetailDao) {
	    this.tcMemberDetailDao = tcMemberDetailDao;
	}
	/**
	 * 会员与学员关联表信息 Dao类取得
	 * @return 会员与学员关联表信息 Dao类
	 */
	public ITcMemberVsStudentDao getTcMemberVsStudentDao() {
	    return tcMemberVsStudentDao;
	}
	/**
	 * 会员与学员关联表信息 Dao类设定
	 * @param tcMemberVsStudentDao 会员与学员关联表信息 Dao类
	 */
	public void setTcMemberVsStudentDao(ITcMemberVsStudentDao tcMemberVsStudentDao) {
	    this.tcMemberVsStudentDao = tcMemberVsStudentDao;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前会员关联的学员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcStudentBean> findDataIsListStudent(TcMemberBean bean){
		List<TcStudentBean> beans=null;
		try {
			TcMemberVsStudent dto=new TcMemberVsStudent();
	    	   if(bean!=null){
	    		    dto.setUser_id(bean.getId());
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
				beans=tcMemberVsStudentDao.findDataIsListStudent(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
	/**
	 * <p>信息列表。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>当前学员关联的会员列表。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public List<TcMemberBean> findDataIsListMember(TcStudentBean bean){
		List<TcMemberBean> beans=null;
		try {
			TcMemberVsStudent dto=new TcMemberVsStudent();
	    	   if(bean!=null){
	    		   	dto.setStudent_id(bean.getId());
		   			dto.setLimit_s(bean.getLimit_s());
		   			dto.setLimit_e(bean.getLimit_e());
	    	   }
	    	   beans=tcMemberVsStudentDao.findDataIsListMember(dto);
		} catch (Exception e) {
			log.error("信息查询失败,数据库错误!", e);
		}
		return beans;
	}
}
