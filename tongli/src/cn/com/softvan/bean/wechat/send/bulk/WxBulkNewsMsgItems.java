/*
 * BEAN类  微信群发图文信息  明细
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.09.14  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 童励  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.wechat.send.bulk;

/**
 *  微信群发图文信息  明细
 * @author wuxiaogang 
 *
 */
public class WxBulkNewsMsgItems extends WxBulkMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9180438288300488808L;
	/**图片本地路径*/
	private String thumb_media_local_url;
	/**图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得*/
	private String thumb_media_id;
	/**图文消息的作者*/
	private String author;
	/**图文消息的标题*/
	private String title;
	/**在图文消息页面点击“阅读原文”后的页面*/
	private String content_source_url;
	/**图文消息页面的内容，支持HTML标签*/
	private String content;
	/**图文消息的描述*/
	private String digest;
	/**是否显示封面，1为显示，0为不显示*/
	private String show_cover_pic;
	/**
	 * 
	 * @param thumb_media_local_url 图片本地路径
	 * @param author 图文消息的作者
	 * @param title 图文消息的标题
	 * @param content_source_url 在图文消息页面点击“阅读原文”后的页面
	 * @param content 图文消息页面的内容，支持HTML标签
	 * @param digest 图文消息的描述
	 * @param show_cover_pic 是否显示封面，1为显示，0为不显示
	 */
	public WxBulkNewsMsgItems(String thumb_media_local_url, String author, String title, String content_source_url,String content,String digest,String show_cover_pic) {
		this.thumb_media_local_url = thumb_media_local_url;
		this.author = author;
		this.title = title;
		this.content_source_url = content_source_url;
		this.content = content;
		this.digest = digest;
		this.show_cover_pic = show_cover_pic;
	}
	/**
	 * 图片本地路径取得
	 * @return 图片本地路径
	 */
	public String getThumb_media_local_url() {
	    return thumb_media_local_url;
	}
	/**
	 * 图片本地路径设定
	 * @param thumb_media_local_url 图片本地路径
	 */
	public void setThumb_media_local_url(String thumb_media_local_url) {
	    this.thumb_media_local_url = thumb_media_local_url;
	}
	/**
	 * 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得取得
	 * @return 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
	 */
	public String getThumb_media_id() {
	    return thumb_media_id;
	}
	/**
	 * 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得设定
	 * @param thumb_media_id 图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
	 */
	public void setThumb_media_id(String thumb_media_id) {
	    this.thumb_media_id = thumb_media_id;
	}
	/**
	 * 图文消息的作者取得
	 * @return 图文消息的作者
	 */
	public String getAuthor() {
	    return author;
	}
	/**
	 * 图文消息的作者设定
	 * @param author 图文消息的作者
	 */
	public void setAuthor(String author) {
	    this.author = author;
	}
	/**
	 * 图文消息的标题取得
	 * @return 图文消息的标题
	 */
	public String getTitle() {
	    return title;
	}
	/**
	 * 图文消息的标题设定
	 * @param title 图文消息的标题
	 */
	public void setTitle(String title) {
	    this.title = title;
	}
	/**
	 * 在图文消息页面点击“阅读原文”后的页面取得
	 * @return 在图文消息页面点击“阅读原文”后的页面
	 */
	public String getContent_source_url() {
	    return content_source_url;
	}
	/**
	 * 在图文消息页面点击“阅读原文”后的页面设定
	 * @param content_source_url 在图文消息页面点击“阅读原文”后的页面
	 */
	public void setContent_source_url(String content_source_url) {
	    this.content_source_url = content_source_url;
	}
	/**
	 * 图文消息页面的内容，支持HTML标签取得
	 * @return 图文消息页面的内容，支持HTML标签
	 */
	public String getContent() {
	    return content;
	}
	/**
	 * 图文消息页面的内容，支持HTML标签设定
	 * @param content 图文消息页面的内容，支持HTML标签
	 */
	public void setContent(String content) {
	    this.content = content;
	}
	/**
	 * 图文消息的描述取得
	 * @return 图文消息的描述
	 */
	public String getDigest() {
	    return digest;
	}
	/**
	 * 图文消息的描述设定
	 * @param digest 图文消息的描述
	 */
	public void setDigest(String digest) {
	    this.digest = digest;
	}
	/**
	 * 是否显示封面，1为显示，0为不显示取得
	 * @return 是否显示封面，1为显示，0为不显示
	 */
	public String getShow_cover_pic() {
	    return show_cover_pic;
	}
	/**
	 * 是否显示封面，1为显示，0为不显示设定
	 * @param show_cover_pic 是否显示封面，1为显示，0为不显示
	 */
	public void setShow_cover_pic(String show_cover_pic) {
	    this.show_cover_pic = show_cover_pic;
	}
}
