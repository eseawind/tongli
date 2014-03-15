/*
 * 微信接口实现 servlet ActionClass
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2013.08.16  wuxiaogang           程序・发布
 * 1.01     2014.03.14  wuxiaogang           程序・更新
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.web.servlet;
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
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jdom.JDOMException;

import cn.com.softvan.bean.wechar.TcWxInfoBean;
import cn.com.softvan.bean.wechar.receive.WxRecvEventMsg;
import cn.com.softvan.bean.wechar.receive.WxRecvGeoMsg;
import cn.com.softvan.bean.wechar.receive.WxRecvLinkMsg;
import cn.com.softvan.bean.wechar.receive.WxRecvMsg;
import cn.com.softvan.bean.wechar.receive.WxRecvPicMsg;
import cn.com.softvan.bean.wechar.receive.WxRecvTextMsg;
import cn.com.softvan.bean.wechar.reply.WxReplyMsg;
import cn.com.softvan.bean.wechar.reply.WxReplyMusicMsg;
import cn.com.softvan.bean.wechar.reply.WxReplyTextMsg;
import cn.com.softvan.common.JedisHelper;
import cn.com.softvan.common.Resources;
import cn.com.softvan.common.WebUtils;
import cn.com.softvan.common.wechar.WeCharUtil;
import cn.com.softvan.service.wechar.ITcWxInfoManager;

public class WeCharReplyServlet extends HttpServlet {
	private static final transient Logger log = Logger
			.getLogger(WeCharReplyServlet.class);
	private static final long serialVersionUID = -4267408236898837036L;
	
	/**微信服务_资源信息管理 业务处理*/
	private ITcWxInfoManager tcWxInfoManager;
	/**redis缓存工具类*/
	protected JedisHelper jedisHelper;
	
	//app token
	private static final String TOKEN = Resources.getData("wx.token");
	/**
	 * 接口验证
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
	/**
	 * 信息接收与回复
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
        try {
        	//接收信息
			WxRecvMsg msg = WeCharUtil.recv(request.getInputStream());
			//回复信息
			WxReplyMsg replyMsg = WeCharUtil.builderSendByRecv(msg);
			//TODO -------处理接收信息类型------------------
			msgManager(request, response, msg, replyMsg);
		} catch (JDOMException e) {
			log.error("", e);
		}
	}
	/**
	 * 处理接收信息类型
	 */
	private void msgManager(HttpServletRequest request, HttpServletResponse response,WxRecvMsg msg,WxReplyMsg replyMsg){
//		jedisHelper
		
		try {
//			System.out.println(msg);
//			String the_menu="感谢关注,1.啥\n2.和";
			//TODO ----事件---
			if(msg instanceof WxRecvEventMsg) {
				WxRecvEventMsg m = (WxRecvEventMsg) msg;
				String event = m.getEvent();
				//TODO 首次关注
				if("subscribe".equals(event)) {
//					replyMsg = new WxReplyTextMsg(replyMsg, the_menu);
//					WeCharUtil.send(replyMsg, response.getOutputStream());
					
					TcWxInfoBean bean=new TcWxInfoBean();
					
					bean.setInfo_source("1");//信息接收
					bean.setFromusername(msg.getFromUser());
					bean.setTousername(msg.getToUser());
					
					return ;
				}else 
					//取消订阅
					if("unsubscribe".equals(event)){
					//
				}
//				reply(m.getEventKey(),response,the_menu,replyMsg);
				return ;
			}else
			//TODO ----- 文本信息
			if(msg instanceof WxRecvTextMsg) {
//				WxRecvTextMsg m = (WxRecvTextMsg) msg;
//				reply(m.getContent(),response,the_menu,replyMsg);
				return;
			}else 
			//TODO ----- 图片信息
			if(msg instanceof WxRecvPicMsg){
//				WxRecvPicMsg m=(WxRecvPicMsg)msg;
//				if("1".equals(W001Action.map_auth.get(W001Action.map.get(replyMsg.getToUser())))){
//					//下载图片
//					Thread t=new Thread(new ImgThread(m.getPicUrl(),replyMsg.getToUser()+"_"+mapNum.get(replyMsg.getToUser())));
//					t.start();;
//				}else{
				replyMsg = new WxReplyTextMsg(replyMsg,"图片上传成功!");
//				}
				WeCharUtil.send(replyMsg, response.getOutputStream());
			}
			//TODO ----- 链接消息
			if(msg instanceof WxRecvLinkMsg){
				
			}else
			//TODO ----- 地理位置消息
			if(msg instanceof WxRecvGeoMsg){
				
			}else{
				replyMsg = new WxReplyTextMsg(replyMsg, (String)jedisHelper.get(""));
				WeCharUtil.send(replyMsg, response.getOutputStream());
				return;
			}
		} catch (Exception e) {
			log.error("微信自动回复错误!", e);
		}
		
		
	}
	
	
	
	/**
	 * 
	 * @param text
	 * @param response
	 * @param the_menu
	 * @param replyMsg
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void reply(String text, HttpServletResponse response,String the_menu,WxReplyMsg replyMsg) throws JDOMException, IOException{
		if(null != text){
			text = text.trim();
		}
		Set<String> keyworkds = new HashSet<String>();
		keyworkds.add("0");
		keyworkds.add("菜单");
		keyworkds.add("menu");
		if(keyworkds.contains(text)) {
			replyMsg = new WxReplyTextMsg(replyMsg, the_menu);
		} else 
		if("99".equals(text)) {
			replyMsg = new WxReplyMusicMsg(replyMsg, "最终信仰","尚雯婕",
					"http://zhangmenshiting.baidu.com/data2/music/35428663/152664111376877661128.mp3?xcode=f542fc57ed6395c9488136d2c4a12feb40883b4a8b1528c3", 
					"http://zhangmenshiting.baidu.com/data2/music/35428663/152664111376877661128.mp3?xcode=f542fc57ed6395c9488136d2c4a12feb40883b4a8b1528c3");
		}else 
		if("1".equals(text)||(text).contains("呵呵")) {
			replyMsg = new WxReplyTextMsg(replyMsg,"上");
		}else 
		if("2".equals(text)||(text).contains("哈哈")) {
			replyMsg = new WxReplyTextMsg(replyMsg,"去");
		}else{
//			replyMsg = new WxReplyNewsMsg(replyMsg)
//			.addItem("玩家的奥特莱斯", "玩家的奥特莱斯", "http://www.tehuiwan.cn/images/site/guide1.jpg", "http://211.152.32.9/index.php/mobile/specials/soldlist")
//			.addItem("玩品宣言", "玩品宣言", "http://www.tehuiwan.cn/images/site/guide1.jpg", "http://211.152.32.9/index.php/mobile/specials/soldlist")
//			.addItem("使用向导", "使用向导", "http://www.tehuiwan.cn/images/site/guide1.jpg", "http://211.152.32.9/index.php/mobile/specials/soldlist")
//			.addItem("特惠速查", "特惠速查", "http://www.tehuiwan.cn/images/site/guide1.jpg", "http://211.152.32.9/index.php/mobile/specials/soldlist")
//			.addItem("“过时不候”是什么", "“过时不候”是什么", "http://www.tehuiwan.cn/images/site/guide1.jpg", "http://211.152.32.9/index.php/mobile/specials/soldlist")
//			.addItem("身边特惠", "身边特惠", "http://www.tehuiwan.cn/images/site/guide1.jpg", "http://211.152.32.9/index.php/mobile/specials/soldlist")
//			.addItem("当季特惠精选", "当季特惠精选", "http://www.tehuiwan.cn/images/site/guide1.jpg", "http://211.152.32.9/index.php/mobile/specials/soldlist")
//			.addItem("戳！神奇的品哥", "戳！神奇的品哥", "http://www.tehuiwan.cn/images/site/guide1.jpg", "http://211.152.32.9/index.php/mobile/specials/soldlist");
			replyMsg = new WxReplyTextMsg(replyMsg, the_menu);
		}
		WeCharUtil.send(replyMsg, response.getOutputStream());
		return;
	}
	/**
	 * 微信认证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	private static boolean checkSignature(String signature,String timestamp,String nonce){
		String[] arr = new String[]{TOKEN,timestamp,nonce};
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
    

}
/**
 * 图片下载
 * @author wuxiaogang 
 *
 */
class ImgThread extends Thread{
	private static final transient Logger log = Logger
			.getLogger(ImgThread.class);
	private String path;
	private String num;
	public static SimpleDateFormat df = new SimpleDateFormat("/yyyyMM/");
	public static Date d=null;
	public ImgThread(String _path,String _num){
		this.path=_path;
		this.num=_num;
	}
	
	/**
	 * 执行线程
	 */
	public void run() {
		try {
//			log.error(path);
			// 图片未保存 下载保存
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				InputStream inputStream = conn.getInputStream();
//				// 文件大小
//				Long fileSize = conn.getContentLengthLong();
				//文件夹 根目录+相对路径
				String savePath = Resources.getData("UPLOAD_ROOT_FOLDER") 
						+ "/wx/image"
						+ df.format(d);
				// 文件名
				String fileName = WebUtils.getTime("yyyyMMddHHmmss")
						+ WebUtils.getRandomString(5) + ".jpg";
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
			}
		} catch (Exception e) {
			log.error("微信上传图片保存失败!",e);
		}
	}
}
