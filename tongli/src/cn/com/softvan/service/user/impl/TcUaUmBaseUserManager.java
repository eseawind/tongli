/**
 * 操作日志记录  service实现类
 * VERSION          DATE                 BY              REASON
 * -------- -------------------  ---------------------- ------------------------------------------
 * 1.00     2014 下午5:11:32             wangzi              程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 picc  System. - All Rights Reserved.
 */
package cn.com.softvan.service.user.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.softvan.bean.backuser.TcUaUmBaseUserBean;
import cn.com.softvan.common.CipherUtils;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.dao.daointer.user.ITcUaUmBaseUserDao;
import cn.com.softvan.dao.entity.user.TcUaUmBaseUser;
import cn.com.softvan.service.BaseManager;
import cn.com.softvan.service.user.ITcUaUmBaseUserManager;

/**
 * <p> 操作日志记录 service实现层 <p>
 * @author ll
 *
 */
public class TcUaUmBaseUserManager  extends BaseManager implements ITcUaUmBaseUserManager {

	/** 日志 */
	private static final transient Logger log = Logger.getLogger(TcUaUmBaseUserManager.class);
	
	/**信息DAO 接口类*/
	private ITcUaUmBaseUserDao tcUaUmBaseUserDao;
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
	public String saveOrUpdateData(TcUaUmBaseUserBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcUaUmBaseUser dto=new TcUaUmBaseUser();
				dto.setId(bean.getId());
				dto.setEmail(bean.getEmail());//邮箱
				dto.setUsername(bean.getUsername());//用户名
				if(bean.getPasswd()!=null&&!bean.getPasswd().equals("")){
					dto.setPasswd(CipherUtils.md5(bean.getPasswd()));//密码
				}
			    dto.setNickname(bean.getNickname());//真实姓名
				dto.setMobile(bean.getMobile());//手机号码
				dto.setMobile_phone(bean.getMobile_phone());//固定电话
				dto.setWork_phone(bean.getWork_phone());//工作电话
				dto.setOther_phone(bean.getOther_phone());//其他电话
				dto.setSex(bean.getSex());//性别
				dto.setEnabled(bean.getEnabled());//是否激活0是1否
				
				dto.setCreate_id(bean.getCreate_id());//建立者id
				dto.setCreate_ip(bean.getCreate_ip());//建立者ip
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				dto.setVersion(bean.getVersion());//version
				dto.setDel_flag(bean.getDel_flag());//del_flag

				
				//判断数据是否存在
				if(tcUaUmBaseUserDao.isDataYN(dto)!=0){
					//数据存在
					tcUaUmBaseUserDao.updateByPrimaryKeySelective(dto);
				}else{
					//新增
					if(Validator.isEmpty(dto.getId())){
						dto.setId(IdUtils.createUUID(32));
					}
					tcUaUmBaseUserDao.insertSelective(dto);
				}
				//==================保存用户和角色的关联关系============================================
				 
				if(bean.getRoleSetIds()!=null&&bean.getRoleSetIds().size()>0){
					tcUaUmBaseUserDao.deleteUserRoleByUId(dto.getId());
					tcUaUmBaseUserDao.saveUserRole(dto.getId(),dto.getCreate_id(),dto.getCreate_ip(),dto.getUpdate_id(),dto.getUpdate_ip(),bean.getRoleSetIds());
				}
				
				
				//==============================================================
				
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
	public String deleteData(TcUaUmBaseUserBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcUaUmBaseUser dto=new TcUaUmBaseUser();
				dto.setId(bean.getId());//id
				tcUaUmBaseUserDao.deleteByPrimaryKey(dto);
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
	public String deleteDataById(TcUaUmBaseUserBean bean) throws Exception{
		String msg="1";
		if(bean!=null){
			try {
				TcUaUmBaseUser dto=new TcUaUmBaseUser();
				dto.setId(bean.getId());//id
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				tcUaUmBaseUserDao.deleteById(dto);
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
	public List<TcUaUmBaseUserBean> findDataIsPage(TcUaUmBaseUserBean bean){
		List<TcUaUmBaseUserBean> beans=null;
		try {
			TcUaUmBaseUser dto=new TcUaUmBaseUser();
    	   if(bean!=null){
    		    dto.setId(bean.getId());
				dto.setEmail(bean.getEmail());//邮箱
				dto.setUsername(bean.getUsername());//用户名
				dto.setPasswd(bean.getPasswd());//密码
				dto.setNickname(bean.getNickname());//真实姓名
				dto.setMobile(bean.getMobile());//手机号码
				dto.setMobile_phone(bean.getMobile_phone());//固定电话
				dto.setWork_phone(bean.getWork_phone());//工作电话
				dto.setOther_phone(bean.getOther_phone());//其他电话
				dto.setSex(bean.getSex());//性别
				dto.setEnabled(bean.getEnabled());//是否激活0是1否
				
				dto.setCreate_id(bean.getCreate_id());//建立者id
				dto.setCreate_ip(bean.getCreate_ip());//建立者ip
				dto.setUpdate_id(bean.getUpdate_id());//修改者id
				dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
				dto.setVersion(bean.getVersion());//version
				dto.setDel_flag(bean.getDel_flag());//del_flag
			
			dto.setPageInfo(bean.getPageInfo());
    	   }
			beans=(List<TcUaUmBaseUserBean>) tcUaUmBaseUserDao.findUserIsPage(dto);
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
	public List<TcUaUmBaseUserBean> findDataIsList(TcUaUmBaseUserBean bean){
		List<TcUaUmBaseUserBean> beans=null;
		try {
			TcUaUmBaseUser dto=new TcUaUmBaseUser();
	    	   if(bean!=null){
	    		    dto.setId(bean.getId());
					dto.setEmail(bean.getEmail());//邮箱
					dto.setUsername(bean.getUsername());//用户名
					dto.setPasswd(bean.getPasswd());//密码
					dto.setNickname(bean.getNickname());//真实姓名
					dto.setMobile(bean.getMobile());//手机号码
					dto.setMobile_phone(bean.getMobile_phone());//固定电话
					dto.setWork_phone(bean.getWork_phone());//工作电话
					dto.setOther_phone(bean.getOther_phone());//其他电话
					dto.setSex(bean.getSex());//性别
					dto.setEnabled(bean.getEnabled());//是否激活0是1否
					
					dto.setCreate_id(bean.getCreate_id());//建立者id
					dto.setCreate_ip(bean.getCreate_ip());//建立者ip
					dto.setUpdate_id(bean.getUpdate_id());//修改者id
					dto.setUpdate_ip(bean.getUpdate_ip());//修改者ip
					dto.setVersion(bean.getVersion());//version
					dto.setDel_flag(bean.getDel_flag());//del_flag
	    	   }
				beans=(List<TcUaUmBaseUserBean>) tcUaUmBaseUserDao.findDataIsList(dto);
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
	public TcUaUmBaseUserBean findDataById(TcUaUmBaseUserBean bean){
       TcUaUmBaseUserBean bean1=null;
       try {
    	   TcUaUmBaseUser dto=new TcUaUmBaseUser();
    	   if(bean!=null){
    		    dto.setId(bean.getId());
    		    dto.setUsername(bean.getUsername());
    		    dto.setPasswd(bean.getPasswd());
			}
			bean1=(TcUaUmBaseUserBean) tcUaUmBaseUserDao.selectByPrimaryKey(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
 
	 
	
	public Boolean isUsernameExist(TcUaUmBaseUserBean bean){
		   int  count=0;
		   boolean flag=false;
		  try {
			  TcUaUmBaseUser dto=new TcUaUmBaseUser();
				if(bean!=null){
				    dto.setUsername(bean.getUsername());
				    count=tcUaUmBaseUserDao.isUsernameExist(dto);
				    if(count>0){
				    	flag=true;
				    }
				}
		  }catch (Exception e) {
			  log.error("信息详情查询失败,数据库错误!", e);
		  }
		 return flag;
	}
	
	
	/**
	 * <p>信息详情。</p>
	 * <ol>[功能概要] 
	 * <div>信息检索。</div>
	 * <div>详情。</div>
	 * </ol>
	 * @return 处理结果
	 */
	public TcUaUmBaseUserBean userLogin(TcUaUmBaseUserBean bean){
       TcUaUmBaseUserBean bean1=null;
       try {
    	   TcUaUmBaseUser dto=new TcUaUmBaseUser();
    	   if(bean!=null){
    		    dto.setUsername(bean.getUsername());
    		    dto.setPasswd(bean.getPasswd());
			}
			bean1=(TcUaUmBaseUserBean) tcUaUmBaseUserDao.userlogin(dto);
		} catch (Exception e) {
			log.error("信息详情查询失败,数据库错误!", e);
		}
		return bean1;
	}
	
	/**
	 * 信息DAO 接口类取得。
	 * @return 信息DAO 接口类
	 */
	public ITcUaUmBaseUserDao getTcUaUmBaseUserDao() {
	    return tcUaUmBaseUserDao;
	}
	/**
	 * 信息DAO 接口类设定。
	 * @param tcUaUmBaseUserDao 信息DAO 接口类
	 */
	public void setTcUaUmBaseUserDao(ITcUaUmBaseUserDao tcUaUmBaseUserDao) {
	    this.tcUaUmBaseUserDao = tcUaUmBaseUserDao;
	}

}
