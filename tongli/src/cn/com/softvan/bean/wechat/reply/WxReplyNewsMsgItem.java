package cn.com.softvan.bean.wechat.reply;

import cn.com.softvan.bean.BaseBean;


public class WxReplyNewsMsgItem extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = -55217010421239040L;
	private String title;
	private String description;
	private String picUrl;
	private String url;
	public WxReplyNewsMsgItem(String title, String description, String picUrl, String url) {
		this.title = title;
		this.description = description;
		this.picUrl = picUrl;
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
