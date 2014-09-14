/*
 * BEAN类  微信群发图文信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.09.14  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.wechat.send.bulk;

import java.util.LinkedList;
import java.util.List;
/**
 *  微信群发图文信息
 * @author wuxiaogang 
 *
 */
public class WxBulkNewsMsg extends WxBulkMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9180438288300488808L;
	private List<WxBulkNewsMsgItems> items = new LinkedList<WxBulkNewsMsgItems>();
	public WxBulkNewsMsg() {
		setMsgType("news");
	}
	public WxBulkNewsMsg(WxBulkMsg msg) {
		super(msg);
		setMsgType("news");
	}
	public void setItems(List<WxBulkNewsMsgItems> items) {
		this.items = items;
	}
	/**
	 * 添加图文明细
	 * @param thumb_media_local_url 图片本地路径
	 * @param author 图文消息的作者
	 * @param title 图文消息的标题
	 * @param content_source_url 在图文消息页面点击“阅读原文”后的页面
	 * @param content 图文消息页面的内容，支持HTML标签
	 * @param digest 图文消息的描述
	 * @param show_cover_pic 是否显示封面，1为显示，0为不显示
	 */
	public WxBulkNewsMsg addItem(String thumb_media_local_url, String author, String title, String content_source_url,String content,String digest,String show_cover_pic) {
		if(items.size() >= 10) {
			throw new IllegalArgumentException("只能接受最多10个item...");
		}
		items.add(new WxBulkNewsMsgItems(thumb_media_local_url, author, title, content_source_url,content,digest,show_cover_pic));
		return this;
	}
	
	public String toJson() {
		StringBuffer sb=new StringBuffer("{\"articles\":[");
		for(int i=0;i<items.size();i++) {
			WxBulkNewsMsgItems item = items.get(i);
			sb.append("{");
			sb.append("\"thumb_media_id\":\""+item.getThumb_media_id()+"\",");
			sb.append("\"author\":\""+item.getAuthor()+"\",");
			sb.append("\"title\":\""+item.getTitle()+"\",");
			sb.append("\"content_source_url\":\""+item.getContent_source_url()+"\",");
			sb.append("\"content\":\""+item.getContent()+"\",");
			sb.append("\"digest\":\""+item.getDigest()+"\",");
			sb.append("\"show_cover_pic\":\""+item.getShow_cover_pic()+"\"");
			sb.append("}");
			if(i<items.size()-1){
				sb.append(",");
			}
		}
		sb.append("]}");
		return sb.toString();
	}
	
}
