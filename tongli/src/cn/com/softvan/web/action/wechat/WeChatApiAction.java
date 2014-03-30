/*
 * 微信接口实现 servlet ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.08.16  wuxiaogang           程序・发布
 * 1.01     2014.03.19  wuxiaogang           程序・更新
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.action.wechat;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;
import org.springframework.core.task.TaskExecutor;

import cn.com.softvan.bean.wechat.TcWxInfoBean;
import cn.com.softvan.bean.wechat.TcWxPublicUserBean;
import cn.com.softvan.bean.wechat.TcWxUserBean;
import cn.com.softvan.bean.wechat.receive.WxRecvEventMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvGeoMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvLinkMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvPicMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvTextMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvVideoMsg;
import cn.com.softvan.bean.wechat.receive.WxRecvVoiceMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyNewsMsg;
import cn.com.softvan.bean.wechat.reply.WxReplyTextMsg;
import cn.com.softvan.common.CommonConstant;
import cn.com.softvan.common.IpUtils;
import cn.com.softvan.common.JedisHelper;
import cn.com.softvan.common.Resources;
import cn.com.softvan.common.StrUtil;
import cn.com.softvan.common.WebUtils;
import cn.com.softvan.common.wechat.WeChatUtil;
import cn.com.softvan.common.wechat.WxApiUtil;
import cn.com.softvan.service.wechat.ITcWxInfoManager;
import cn.com.softvan.service.wechat.ITcWxPublicUserManager;
import cn.com.softvan.service.wechat.ITcWxUserManager;
import cn.com.softvan.web.action.BaseAction;
public class WeChatApiAction extends BaseAction {
	private static final transient Logger log = Logger.getLogger(WeChatApiAction.class);
	private static final long serialVersionUID = -4267408236898837036L;
	/**线程池 */
	private TaskExecutor taskExecutor;
	/**微信服务_资源信息管理 业务处理*/
	private ITcWxInfoManager tcWxInfoManager;
	/**微信服务_公共账号 service */
	private ITcWxPublicUserManager tcWxPublicUserManager;
	/**微信服务_粉丝账号 service */
    private ITcWxUserManager tcWxUserManager;
	/**redis缓存工具类*/
	protected JedisHelper jedisHelper;
	/**微信api工具类*/
	WxApiUtil api=new WxApiUtil();
	/**收到的xml信息 */
	private String xml=null;
	/**微信 公共号 信息*/
	private TcWxPublicUserBean getPublicUserBean(){
		TcWxPublicUserBean publicUserBean=(TcWxPublicUserBean) jedisHelper.get(CommonConstant.SESSION_WECHAT_BEAN);
		if((publicUserBean==null) || (publicUserBean.getId()==null)){
			publicUserBean=tcWxPublicUserManager.findDataById(null);
			jedisHelper.set(CommonConstant.SESSION_WECHAT_BEAN,publicUserBean);
		}
		return publicUserBean;
	}
	private String  getToken(){
		if(getPublicUserBean()==null||getPublicUserBean().getId()==null){
			log.error("===公共号信息为空===");
			return "";//"c11e93a511b946d8a1427c02868ef854";
		}else{
			return getPublicUserBean().getId();
		}
	}
	/**
	 * 接口验证
	 */
	public void init() throws IOException {
		String method = request.getMethod();
		if("POST".equals(method)){
			//消息接收
			infoRecv();
		}else{
			//接口验证
			String signature = request.getParameter("signature");
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			
			PrintWriter out = response.getWriter();
			if(checkSignature(signature,timestamp,nonce)){
				out.print(request.getParameter("echostr"));
			}
			out.close();
			out = null;
		}
	}
	/**
	 * 信息接收与回复
	 */
	private void infoRecv() throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
        try {
        	xml=StrUtil.convertStreamToString(request.getInputStream());
        	log.error("==收到信息=="+xml);
        	//接收信息
			WxRecvMsg msg = WeChatUtil.recv(new ByteArrayInputStream(xml.getBytes()));
			//回复信息
			WxReplyMsg replyMsg = WeChatUtil.builderSendByRecv(msg);
			//--微信粉丝openid==
			request.getSession().setAttribute(CommonConstant.SESSION_KEY_USER_WECHAT_OPENID, msg.getToUser());
			//TODO -------处理接收信息类型------------------
			msgManager(msg, replyMsg);
		} catch (JDOMException e) {
			log.error("微信api!信息接收与回复异常!", e);
		}
	}
	
	/**
	 * 处理 接收信息类型
	 */
	private void msgManager(WxRecvMsg msg,WxReplyMsg replyMsg){
		TcWxInfoBean bean=null;
		try {
			//多媒体上传标记
			boolean media_flag=true;
			//多媒体接收成功返回信息
			String media_reply_text=null;
			//
			String keyword="default";
			//
			bean=getTcWxInfoBean(msg,request);
			//TODO ----事件---
			if(msg instanceof WxRecvEventMsg) {
				log.error("==收到一个事件==");
				WxRecvEventMsg m = (WxRecvEventMsg) msg;
				String event = m.getEvent();
				//TODO 首次关注
				if("subscribe".equals(event)) {
					//自动回复关键字
					keyword="subscribe";
					try {
						TcWxUserBean userBean=api.getUser(getAccess_token(false), m.getFromUser());
						//40001获取access_token时AppSecret错误，或者access_token无效
						//40014不合法的access_token
						//42001access_token超时
						if(api.isErrAccessToken(userBean.getErrcode())){
							//重新获取access_token 并重新调用接口
							userBean=api.getUser(getAccess_token(true), m.getFromUser());
						}
						tcWxUserManager.saveOrUpdateData(userBean);
					} catch (Exception e) {
						log.error("新粉丝 信息拉取并入库异常!", e);
					}
				}else {
					//自动回复关键字
					keyword="key_flag_"+m.getEvent();
	//				//取消订阅
					if("unsubscribe".equals(event)){
	//					//
						try {
							TcWxUserBean userBean=new TcWxUserBean();
							userBean.setOpenid(msg.getFromUser());
							userBean.setSubscribe("0");//取消关注
							tcWxUserManager.saveOrUpdateData(userBean);
						} catch (Exception e) {
							log.error("粉丝取消关注,事件记录 异常!", e);
						}
					}//else
	//				// 用户已关注时的事件推送
	//				if("SCAN".equals(event)){
	//					//
	//				}else	
					//点击菜单拉取消息时的事件推送 事件类型，CLICK
					if("CLICK".equals(event)){
						//
						keyword=m.getEventKey();
					}
//					else	
	//				//点击菜单跳转链接时的事件推送 事件类型，VIEW
	//				if("VIEW".equals(event)){
	//					//
	//				}else	
					//上报地理位置事件 事件类型，LOCATION
					if("LOCATION".equals(event)){
						log.error("====地理位置===");
						//
						msg = WeChatUtil.recv(new ByteArrayInputStream(xml.getBytes()));
						m = (WxRecvEventMsg) msg;
						m.setEvent("LOCATION");
					}
				}
				//-----------封装信息--------------
				bean.setEvent(m.getEvent());
				bean.setEventkey(m.getEventKey());
				bean.setLocation_x(m.getLatitude());
				bean.setLocation_y(m.getLongitude());
				bean.setLocation_precision(m.getPrecision());
				bean.setTicket(m.getTicket());
			}else
			//TODO ----- 文本信息
			if(msg instanceof WxRecvTextMsg) {
				WxRecvTextMsg m = (WxRecvTextMsg) msg;
				//自动回复关键字
				keyword=m.getContent();
				//-----------封装信息--------------
				bean.setContent(m.getContent());
			}else 
			//TODO ----- 图片信息
			if(msg instanceof WxRecvPicMsg){
				WxRecvPicMsg m=(WxRecvPicMsg)msg;
				//自动回复关键字
				keyword="key_flag_pic";
				media_flag=false;
				media_reply_text=Resources.getData("wx.media_reply_text_pic");
//				-------------下载图片-----------
				String picurl=null;
				if(getPublicUserBean()==null){
					picurl=downImg(m.getPicUrl());
				}else{
					picurl=api.downMedia(getAccess_token(false), "image", m.getMediaId());
					if(api.isErrAccessToken(picurl)){
						picurl=api.downMedia(getAccess_token(true), "image", m.getMediaId());
					}
				}
				//-----------封装信息--------------
				bean.setUrl(m.getPicUrl());//远程链接
				bean.setPicurl(picurl);//本地链接
				bean.setMediaid(m.getMediaId());
			}else
			//TODO ----- 链接消息
			if(msg instanceof WxRecvLinkMsg){
				WxRecvLinkMsg m=(WxRecvLinkMsg) msg;
				//-----------封装信息--------------
				bean.setTitle(m.getTitle());
				bean.setDescription(m.getDescription());
				bean.setUrl(m.getUrl());
			}else
			//TODO ----- 地理位置消息
			if(msg instanceof WxRecvGeoMsg){
				WxRecvGeoMsg m=(WxRecvGeoMsg)msg;
				//-----------封装信息--------------
				bean.setLocation_x(""+m.getLatitude());
				bean.setLocation_y(""+m.getLongitude());
				bean.setScale(""+m.getScale());
				bean.setLabel(m.getLabel());
			}else
			//TODO ----- 语音信息
			if(msg instanceof WxRecvVoiceMsg){
				WxRecvVoiceMsg m=(WxRecvVoiceMsg)msg;
				//自动回复关键字
				keyword="key_flag_voice";
				media_flag=false;
				media_reply_text=Resources.getData("wx.media_reply_text_voice");
//				-------------下载语音-----------
				String url=null;
				if(getPublicUserBean()!=null){
					url=api.downMedia(getAccess_token(false), "voice", m.getMediaId());
					if(api.isErrAccessToken(url)){
						url=api.downMedia(getAccess_token(true), "voice", m.getMediaId());
					}
				}
				//-----------封装信息--------------
				bean.setUrl(url);
				bean.setMediaid(m.getMediaId());
				bean.setFormat(m.getFormat());
			}else
			//TODO ----- 视频信息
			if(msg instanceof WxRecvVideoMsg){
				WxRecvVideoMsg m=(WxRecvVideoMsg)msg;
				//自动回复关键字
				keyword="key_flag_video";
				media_flag=false;
				media_reply_text=Resources.getData("wx.media_reply_text_video");
//					-------------下载视频-----------
				String url=null;
				if(getPublicUserBean()!=null){
					url=api.downMedia(getAccess_token(false), "video", m.getMediaId());
					if(api.isErrAccessToken(url)){
						url=api.downMedia(getAccess_token(true), "video", m.getMediaId());
					}
				}
				//-----------封装信息--------------
				bean.setUrl(url);
				//-----------封装信息--------------
				bean.setMediaid(m.getMediaId());
				bean.setThumbmediaid(m.getThumbMediaId());
			}else{
				log.error("接收到未知类型消息!"+xml);
				bean.setDescription("接收到未知类型消息!");
			}
			//TODO--自动回复--
			if(media_flag){
				reply(keyword, response, replyMsg);
			}else{
				WxReplyMsg replyMsg2 = new WxReplyTextMsg(replyMsg, media_reply_text);
				WeChatUtil.send(replyMsg2, response.getOutputStream());
			}
			//TODO--保存接收到的信息--
			tcWxInfoManager.saveOrUpdateData(bean);
		} catch (Exception e) {
			log.error("微信消息接收,处理异常!", e);
		}
	}
	/***/
	private String  getAccess_token(Boolean flag){
		if(getPublicUserBean()==null){
			log.error("===公共号信息为空===");
		}
		return api.getAccess_token(flag, jedisHelper, getPublicUserBean().getAppid(), getPublicUserBean().getAppsecret());
	}
	/**
	 * 信息自动回复
	 * @param text
	 * @param response
	 * @param replyMsg
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void reply(String text, HttpServletResponse response,WxReplyMsg replyMsg) throws JDOMException, IOException{
		if(null != text){
			text = text.trim();
		}
		//--------------判断数据是否缓存--------------
		if(!"1".equals(jedisHelper.get("tc_wechat_info_cache_flag"))){
			tcWxInfoManager.updateAllMsgCache();
		}
		WxReplyMsg replyMsg2=null;
		log.debug("==0==uuid_flag_key="+"key_flag_"+text);
		//根据关键字获取 回复对象id
		String uuid=(String) jedisHelper.get("key_flag_"+text);
		log.debug("==1==uuid="+uuid);
		//根据对象id 获取回复对象
		try {
			replyMsg2=(WxReplyMsg) jedisHelper.get(uuid);
		} catch (Exception e) {
			log.error("缓存读取自动回复信息异常1,", e);
		}
		log.debug("==2==replyMsg2="+replyMsg2);
		if(replyMsg2==null){
			uuid=(String) jedisHelper.get("key_flag_default");
		}
		if(uuid!=null){
			try {
				if(replyMsg2==null){
					replyMsg2=(WxReplyMsg) jedisHelper.get(uuid);
					if(replyMsg2 instanceof WxReplyNewsMsg){
//						replyMsg2.get
					}
				}
				log.debug("==3==replyMsg2="+replyMsg2);
				replyMsg2.setToUser(replyMsg.getToUser());
				replyMsg2.setFromUser(replyMsg.getFromUser());
				replyMsg2.setCreateDt(replyMsg.getCreateDt());
				WeChatUtil.send(replyMsg2, response.getOutputStream());
			} catch (Exception e) {
				log.error("缓存读取自动回复信息异常2,", e);
			}
		}
	}
	/**
	 * 获取接收信息bean对象
	 * @param msg
	 * @return
	 */
	private TcWxInfoBean getTcWxInfoBean(WxRecvMsg msg,HttpServletRequest request){
		TcWxInfoBean bean=new TcWxInfoBean();
		bean.setId(msg.getMsgId());//消息id
		bean.setMsgtype(msg.getMsgType());//消息类型
		bean.setInfo_source("1");//信息接收
		bean.setFromusername(msg.getFromUser());//普通粉丝的opentid
		bean.setTousername(msg.getToUser());//开发者微信号
		bean.setCreate_ip(IpUtils.getIpAddr(request));
		bean.setCreate_id("system");
		return bean;
	}
	/**
	 * 微信认证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	private boolean checkSignature(String signature,String timestamp,String nonce){
		String[] arr = new String[]{ getToken(),timestamp,nonce};
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for(int i=0;i<arr.length;i++){
			content.append(arr[i]);
		} 
		MessageDigest md = null;
		String tmpStr = null;
		
        try {
			md = MessageDigest.getInstance("SHA-1");
	        byte[] digest = md.digest(content.toString().getBytes());
	        tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			log.error("微信认证错误!", e);
		}
        
		content = null;
		return tmpStr!=null?tmpStr.equals(signature.toUpperCase()):false;
	}
	
    /**
     * 将字节转换为十六进制字符串
     * @param ib
     * @return
     */
    private static String byteToHexStr(byte ib) {
        char[] Digit = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
                'D', 'E', 'F'
            };
        char[] ob = new char[2];
        ob[0] = Digit[(ib >>> 4) & 0X0F];
        ob[1] = Digit[ib & 0X0F];

        String s = new String(ob);
        return s;
    }
	
    /**
     * 将字节数组转换为十六进制字符串
     * @param bytearray
     * @return
     */
    private static String byteToStr(byte[] bytearray) {
        String strDigest = "";
        for (int i = 0; i < bytearray.length; i++) {
            strDigest += byteToHexStr(bytearray[i]);
        }
        return strDigest;
    }
    /**
     * 下载微信图片
     * @param remote_url 图片远程路径
     * @return
     */
	private String downImg(String remote_url){
		String local_path=null;
		//
		SimpleDateFormat df = new SimpleDateFormat("/yyyyMM/");
			try {
				//相对路径
				String relative_path="/image/weixin"+df.format(new Date());
//				log.error(path);
				// 图片未保存 下载保存
				URL url = new URL(remote_url);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				if (conn.getResponseCode() == 200) {
					InputStream inputStream = conn.getInputStream();
//					// 文件大小
//					Long fileSize = conn.getContentLengthLong();
					//文件夹 根目录+相对路径
					String savePath = Resources.getData("UPLOAD_ROOT_FOLDER")+relative_path;
					// 文件名
					String fileName = WebUtils.getTime("yyyyMMddHHmmss") + WebUtils.getRandomString(5) + ".jpg";
					// 创建文件夹
					File saveDirFile = new File(savePath);
					if (!saveDirFile.exists()) {
						saveDirFile.mkdirs();
					}
					// 检查目录写权限
					if (!saveDirFile.canWrite()) {
						log.error("目录没有写权限，写入文件失败");
						throw new Exception();
					}
					// 文件保存目录路径
					File file = new File(saveDirFile, fileName);
					FileOutputStream outStream = new FileOutputStream(file);
					int len = -1;
					byte[] b = new byte[1024];
					while ((len = inputStream.read(b)) != -1) {
						outStream.write(b, 0, len);
					}
					outStream.flush();
					outStream.close();
					inputStream.close();
					//服务器访问路径
					local_path=Resources.getData("UPLOAD_ROOT_FOLDER_URL")+relative_path+fileName;
				}
			} catch (Exception e) {
				log.error("微信上传图片保存失败!",e);
			}
			
		return local_path;
	}
	/**
	 * 线程池取得
	 * @return 线程池
	 */
	public TaskExecutor getTaskExecutor() {
	    return taskExecutor;
	}
	/**
	 * 线程池设定
	 * @param taskExecutor 线程池
	 */
	public void setTaskExecutor(TaskExecutor taskExecutor) {
	    this.taskExecutor = taskExecutor;
	}
	/**
	 * 微信服务_资源信息管理 业务处理取得
	 * @return 微信服务_资源信息管理 业务处理
	 */
	public ITcWxInfoManager getTcWxInfoManager() {
	    return tcWxInfoManager;
	}
	/**
	 * 微信服务_资源信息管理 业务处理设定
	 * @param tcWxInfoManager 微信服务_资源信息管理 业务处理
	 */
	public void setTcWxInfoManager(ITcWxInfoManager tcWxInfoManager) {
	    this.tcWxInfoManager = tcWxInfoManager;
	}
	/**
	 * 微信服务_公共账号 service取得
	 * @return 微信服务_公共账号 service
	 */
	public ITcWxPublicUserManager getTcWxPublicUserManager() {
	    return tcWxPublicUserManager;
	}
	/**
	 * 微信服务_公共账号 service设定
	 * @param tcWxPublicUserManager 微信服务_公共账号 service
	 */
	public void setTcWxPublicUserManager(ITcWxPublicUserManager tcWxPublicUserManager) {
	    this.tcWxPublicUserManager = tcWxPublicUserManager;
	}
	/**
	 * 微信服务_粉丝账号 service取得
	 * @return 微信服务_粉丝账号 service
	 */
	public ITcWxUserManager getTcWxUserManager() {
	    return tcWxUserManager;
	}
	/**
	 * 微信服务_粉丝账号 service设定
	 * @param tcWxUserManager 微信服务_粉丝账号 service
	 */
	public void setTcWxUserManager(ITcWxUserManager tcWxUserManager) {
	    this.tcWxUserManager = tcWxUserManager;
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
	 * 微信api工具类取得
	 * @return 微信api工具类
	 */
	public WxApiUtil getApi() {
	    return api;
	}
	/**
	 * 微信api工具类设定
	 * @param api 微信api工具类
	 */
	public void setApi(WxApiUtil api) {
	    this.api = api;
	}
	/**
	 * 收到的xml信息取得
	 * @return 收到的xml信息
	 */
	public String getXml() {
	    return xml;
	}
	/**
	 * 收到的xml信息设定
	 * @param xml 收到的xml信息
	 */
	public void setXml(String xml) {
	    this.xml = xml;
	}
}
