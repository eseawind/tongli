/*
 * 短信 ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.05.20  wuxiaogang      程序・发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.sys;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import cn.com.softvan.bean.member.TcMemberBean;
import cn.com.softvan.bean.sys.TcSysSmsBean;
import cn.com.softvan.bean.sys.TcSysTelBookBean;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IdUtils;
import cn.com.softvan.common.Validator;
import cn.com.softvan.service.member.IMemberManager;
import cn.com.softvan.service.sys.ISmsManager;
import cn.com.softvan.service.sys.ITelBookManager;
import cn.com.softvan.web.action.BaseAction;
import cn.com.softvan.web.tag.PageInfo;

/**
 * 短信 ActionClass
 * 
 * @author wuxiaogang
 * 
 */
public class S005Action extends BaseAction {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -3061791975484213551L;
	private static final transient Logger log = Logger.getLogger(S005Action.class);
	
	/**BEAN类  短信信息*/
	private TcSysSmsBean bean;
	/**BEAN类  短信信息 集合*/
	private List<TcSysSmsBean> beans;
	/**短信 信息管理 业务处理*/
	private ISmsManager smsManager;
	/**通讯录 信息管理 业务处理*/
	private ITelBookManager telBookManager;
	/**会员 信息管理 业务处理*/
	private IMemberManager memberManager;
	//
	public S005Action() {
		log.info("默认构造器......S005Action");
	}

	/**
	 * <p>
	 * 初始化处理。
	 * </p>
	 * <ol>
	 * [功能概要] <div>初始化处理。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String init() {
		log.info("S005Action init.........");
		return "init";
	}
	/**
	 * <p>
	 * 信息列表。
	 * </p>
	 * <ol>
	 * [功能概要] <div>列表。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String list1() {
		log.info("S005Action list1.........");
		int offset = 0;
		// 分页偏移量
		if (!Validator.isNullEmpty(request.getParameter("offset"))
				&& Validator.isNum(request.getParameter("offset"))) {
			offset = Integer.parseInt(request.getParameter("offset"));
		}
		PageInfo page = new PageInfo(); 
		//当前页
		page.setCurrOffset(offset);
		//每页显示条数
		page.setPageRowCount(15);
		if(bean==null){
			bean=new TcSysSmsBean();
		}
		bean.setPageInfo(page);
		//栏目资讯列表
		List<TcSysSmsBean> beans=smsManager.findDataIsPage(bean);
		request.setAttribute("beans",beans);
		request.setAttribute(CommonConstant.PAGEROW_OBJECT_KEY,page);
		return "list1";
	}
	/**
	 * <p>
	 * 编辑。
	 * </p>
	 * <ol>
	 * [功能概要] <div>编辑。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String edit() {
		log.info("S005Action edit.........");
		String id=request.getParameter("id");
		TcSysSmsBean bean1=new TcSysSmsBean();
		bean1.setSms_id(id);
		bean=smsManager.findDataById(bean1);
		if(bean==null){
			bean=new TcSysSmsBean();
			bean.setSms_id(IdUtils.createUUID(32));
		}
		return "edit";
	}
	/**
	 * <p>
	 * 删除。
	 * </p>
	 * <ol>
	 * [功能概要] <div>物理删除。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String delxx() {
		log.info("S005Action delxx.........");
		String id=request.getParameter("id");
		TcSysSmsBean bean1=new TcSysSmsBean();
		bean1.setSms_id(id);
		String msg="1";
		try {
			msg=smsManager.deleteData(bean1);
		} catch (Exception e) {
			msg=e.getMessage();
		}
		request.setAttribute("msg",msg);
		
		return SUCCESS;
	}
	/**
	 * <p>
	 * 信息保存
	 * </p>
	 * <ol>
	 * [功能概要] <div>新增。</div>
	 * <div>修改。</div>
	 * </ol>
	 * @return 转发字符串
	 * @throws IOException 
	 */
	public String save() throws IOException {
		log.info("S005Action edit.........");
		String token=request.getParameter("token");
		String token2=(String) request.getSession().getAttribute("token");
		if(token!=null && token.equals(token2)){
			request.setAttribute("msg", "请不要重复提交!");
			return SUCCESS;
		}else{
			if(token!=null){
				request.getSession().setAttribute("token",token);
			}
		}
		if(bean!=null){
			String msg="1";
			String type=request.getParameter("type");
			try {
				if("0".equals(type)){
					
					if(Validator.notEmpty(bean.getSms_content())){
						//TODO 群发短信 
						Set<String> telSet=new HashSet<String>();
						//----1---获取通讯录---
						List<TcSysTelBookBean> telBookBeans=telBookManager.findDataIsList(null);
						if(telBookBeans!=null){
							for(TcSysTelBookBean telBookBean:telBookBeans){
								if(Validator.notEmpty(telBookBean.getTel())){
									telSet.add(telBookBean.getTel());
								}
								
							}
						}
						//----2---获取所有会员----
						TcMemberBean memberBean=new TcMemberBean();
						memberBean.setUser_type("1");//家长
						List<TcMemberBean> memberBeans=memberManager.findDataIsList(memberBean);
							if(memberBeans!=null){
								for(TcMemberBean memberBean2:memberBeans){
									if(Validator.notEmpty(memberBean2.getTel())){
										telSet.add(memberBean2.getTel());
									}
									if(Validator.notEmpty(memberBean2.getBind_mobile())){
										telSet.add(memberBean2.getBind_mobile());
									}
								}
							}
						//----4---短信入库----
						if(telSet!=null){
							for(String tel:telSet){
								if(tel!=null && Validator.isMobile(tel)){
									try {
										TcSysSmsBean smsBean=new TcSysSmsBean();
										smsBean.setSms_dst_id(tel);
										smsBean.setSms_content(bean.getSms_content());
										msg=smsManager.saveOrUpdateData(smsBean);
									} catch (Exception e) {
										log.error("短信入库失败!号码["+tel+"],内容["+bean.getSms_content()+"]",e);
									}
								}
							}
						}
					}else{
						msg="处理失败!发送内容不能为空!";
					}
				}else{
					if(Validator.isEmpty(bean.getSms_dst_id())||!Validator.isMobile(bean.getSms_dst_id())){
						msg="处理失败!手机号格式问题!";
					}else
						if(Validator.isEmpty(bean.getSms_content())){
							msg="处理失败!发送内容不能为空!";
						}else{
							msg=smsManager.saveOrUpdateData(bean);
						}
				}
			} catch (Exception e) {
				msg=e.getMessage();
			}
			request.setAttribute("msg",msg);
		}else{
			request.setAttribute("msg", "信息保存失败!");
		}
		return SUCCESS;
	}
	/**
	 * <p>
	 * 预览。
	 * </p>
	 * <ol>
	 * [功能概要] <div>预览。</div>
	 * </ol>
	 * @return 转发字符串
	 */
	public String view() {
		log.info("S005Action view.........");
		String id=request.getParameter("id");
		if(id!=null){
			TcSysSmsBean bean1=new TcSysSmsBean();
			bean1.setSms_id(id);
			bean=smsManager.findDataById(bean1);
		}
		return "view";
	}
	/**
	 * BEAN类  短信信息取得
	 * @return BEAN类  短信信息
	 */
	public TcSysSmsBean getBean() {
	    return bean;
	}

	/**
	 * BEAN类  短信信息设定
	 * @param bean BEAN类  短信信息
	 */
	public void setBean(TcSysSmsBean bean) {
	    this.bean = bean;
	}

	/**
	 * BEAN类  短信信息 集合取得
	 * @return BEAN类  短信信息 集合
	 */
	public List<TcSysSmsBean> getBeans() {
	    return beans;
	}

	/**
	 * BEAN类  短信信息 集合设定
	 * @param beans BEAN类  短信信息 集合
	 */
	public void setBeans(List<TcSysSmsBean> beans) {
	    this.beans = beans;
	}

	/**
	 * 短信 信息管理 业务处理取得
	 * @return 短信 信息管理 业务处理
	 */
	public ISmsManager getSmsManager() {
	    return smsManager;
	}

	/**
	 * 短信 信息管理 业务处理设定
	 * @param smsManager 短信 信息管理 业务处理
	 */
	public void setSmsManager(ISmsManager smsManager) {
	    this.smsManager = smsManager;
	}

	/**
	 * 通讯录 信息管理 业务处理取得
	 * @return 通讯录 信息管理 业务处理
	 */
	public ITelBookManager getTelBookManager() {
	    return telBookManager;
	}

	/**
	 * 通讯录 信息管理 业务处理设定
	 * @param telBookManager 通讯录 信息管理 业务处理
	 */
	public void setTelBookManager(ITelBookManager telBookManager) {
	    this.telBookManager = telBookManager;
	}

	/**
	 * 会员 信息管理 业务处理取得
	 * @return 会员 信息管理 业务处理
	 */
	public IMemberManager getMemberManager() {
	    return memberManager;
	}

	/**
	 * 会员 信息管理 业务处理设定
	 * @param memberManager 会员 信息管理 业务处理
	 */
	public void setMemberManager(IMemberManager memberManager) {
	    this.memberManager = memberManager;
	}
}
