/*
 * BEAN类  微信资源信息
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.03.10  wuxiaogang      程序.发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.bean.wechar;

import cn.com.softvan.bean.BaseBean;

/**
 * <p>
 * 微信资源信息 BEAN
 * <p>
 * 
 * @author wuxiaogang
 * 
 */
public class TcWxInfoBean extends BaseBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3373408318481043082L;
	/** id */
	private String id;
	/** 关键字 */
	private String keyword;
	/** 消息类型 */
	private String msgtype;
	/** 接收方帐号（收到的OpenID） */
	private String tousername;
	/** 开发者微信号 */
	private String fromusername;
	/** 消息创建时间 （整型） */
	private String createtime;
	/** 消息内容（换行：在content中能够换行，微信客户端就支持换行显示） */
	private String content;
	/** 通过上传多媒体文件，得到的id。 */
	private String mediaid;
	/** 消息的标题 */
	private String title;
	/** 消息的描述 */
	private String description;
	/** 音乐链接 */
	private String musicurl;
	/** 高质量音乐链接，WIFI环境优先使用该链接播放音乐 */
	private String hqmusicurl;
	/** 缩略图的媒体id，通过上传多媒体文件，得到的id */
	private String thumbmediaid;
	/** 语音格式，如amr，speex等 */
	private String format;
	/** 地理位置维度 */
	private String location_x;
	/** 地理位置经度 */
	private String location_y;
	/** 地图缩放大小 */
	private String scale;
	/** 地理位置信息 */
	private String label;
	/** 图文消息个数，限制为10条以内 */
	private String articlecount;
	/** 图文信息组ID */
	private String articles_id;
	/** 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200 */
	private String picurl;
	/** 点击图文消息跳转链接 */
	private String url;
	/** 消息来源0,自动回复,1,信息接收,2,客服回复 */
	private String info_source;
	/** 默认回复标记 */
	private String default_flag;
	/** 首次关注回复标记 */
	private String subscribe_flag;
	/** 序号 */
	private String sort_num;

	/**
	 * id取得
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * id设定
	 * @param id id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 关键字取得
	 * @return 关键字
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * 关键字设定
	 * @param keyword 关键字
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * 消息类型取得
	 * @return 消息类型
	 */
	public String getMsgtype() {
		return msgtype;
	}

	/**
	 * 消息类型设定
	 * @param msgtype 消息类型
	 */
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	/**
	 * 接收方帐号（收到的OpenID）取得
	 * @return 接收方帐号（收到的OpenID）
	 */
	public String getTousername() {
		return tousername;
	}

	/**
	 * 接收方帐号（收到的OpenID）设定
	 * @param tousername 接收方帐号（收到的OpenID）
	 */
	public void setTousername(String tousername) {
		this.tousername = tousername;
	}

	/**
	 * 开发者微信号取得
	 * @return 开发者微信号
	 */
	public String getFromusername() {
		return fromusername;
	}

	/**
	 * 开发者微信号设定
	 * @param fromusername 开发者微信号
	 */
	public void setFromusername(String fromusername) {
		this.fromusername = fromusername;
	}

	/**
	 * 消息创建时间 （整型）取得
	 * @return 消息创建时间 （整型）
	 */
	public String getCreatetime() {
		return createtime;
	}

	/**
	 * 消息创建时间 （整型）设定
	 * @param createtime 消息创建时间 （整型）
	 */
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	/**
	 * 消息内容（换行：在content中能够换行，微信客户端就支持换行显示）取得
	 * @return 消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 消息内容（换行：在content中能够换行，微信客户端就支持换行显示）设定
	 * @param content 消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 通过上传多媒体文件，得到的id。取得
	 * @return 通过上传多媒体文件，得到的id。
	 */
	public String getMediaid() {
		return mediaid;
	}

	/**
	 * 通过上传多媒体文件，得到的id。设定
	 * @param mediaid 通过上传多媒体文件，得到的id。
	 */
	public void setMediaid(String mediaid) {
		this.mediaid = mediaid;
	}

	/**
	 * 消息的标题取得
	 * @return 消息的标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 消息的标题设定
	 * @param title 消息的标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 消息的描述取得
	 * @return 消息的描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 消息的描述设定
	 * @param description 消息的描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 音乐链接取得
	 * @return 音乐链接
	 */
	public String getMusicurl() {
		return musicurl;
	}

	/**
	 * 音乐链接设定
	 * @param musicurl 音乐链接
	 */
	public void setMusicurl(String musicurl) {
		this.musicurl = musicurl;
	}

	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐取得
	 * @return 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 */
	public String getHqmusicurl() {
		return hqmusicurl;
	}

	/**
	 * 高质量音乐链接，WIFI环境优先使用该链接播放音乐设定
	 * @param hqmusicurl 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	 */
	public void setHqmusicurl(String hqmusicurl) {
		this.hqmusicurl = hqmusicurl;
	}

	/**
	 * 缩略图的媒体id，通过上传多媒体文件，得到的id取得
	 * @return 缩略图的媒体id，通过上传多媒体文件，得到的id
	 */
	public String getThumbmediaid() {
		return thumbmediaid;
	}

	/**
	 * 缩略图的媒体id，通过上传多媒体文件，得到的id设定
	 * @param thumbmediaid 缩略图的媒体id，通过上传多媒体文件，得到的id
	 */
	public void setThumbmediaid(String thumbmediaid) {
		this.thumbmediaid = thumbmediaid;
	}

	/**
	 * 语音格式，如amr，speex等取得
	 * @return 语音格式，如amr，speex等
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * 语音格式，如amr，speex等设定
	 * @param format 语音格式，如amr，speex等
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * 地理位置维度取得
	 * @return 地理位置维度
	 */
	public String getLocation_x() {
		return location_x;
	}

	/**
	 * 地理位置维度设定
	 * @param location_x 地理位置维度
	 */
	public void setLocation_x(String location_x) {
		this.location_x = location_x;
	}

	/**
	 * 地理位置经度取得
	 * @return 地理位置经度
	 */
	public String getLocation_y() {
		return location_y;
	}

	/**
	 * 地理位置经度设定
	 * @param location_y 地理位置经度
	 */
	public void setLocation_y(String location_y) {
		this.location_y = location_y;
	}

	/**
	 * 地图缩放大小取得
	 * @return 地图缩放大小
	 */
	public String getScale() {
		return scale;
	}

	/**
	 * 地图缩放大小设定
	 * @param scale 地图缩放大小
	 */
	public void setScale(String scale) {
		this.scale = scale;
	}

	/**
	 * 地理位置信息取得
	 * @return 地理位置信息
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 地理位置信息设定
	 * @param label 地理位置信息
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 图文消息个数，限制为10条以内取得
	 * @return 图文消息个数，限制为10条以内
	 */
	public String getArticlecount() {
		return articlecount;
	}

	/**
	 * 图文消息个数，限制为10条以内设定
	 * @param articlecount 图文消息个数，限制为10条以内
	 */
	public void setArticlecount(String articlecount) {
		this.articlecount = articlecount;
	}

	/**
	 * 图文信息组ID取得
	 * @return 图文信息组ID
	 */
	public String getArticles_id() {
		return articles_id;
	}

	/**
	 * 图文信息组ID设定
	 * @param articles_id 图文信息组ID
	 */
	public void setArticles_id(String articles_id) {
		this.articles_id = articles_id;
	}

	/**
	 * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200取得
	 * @return 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 */
	public String getPicurl() {
		return picurl;
	}

	/**
	 * 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200设定
	 * @param picurl 图片链接，支持JPG、PNG格式，较好的效果为大图360*200，小图200*200
	 */
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	/**
	 * 点击图文消息跳转链接取得
	 * @return 点击图文消息跳转链接
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 点击图文消息跳转链接设定
	 * @param url 点击图文消息跳转链接
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 消息来源0,自动回复,1,信息接收,2,客服回复取得
	 * @return 消息来源0,自动回复,1,信息接收,2,客服回复
	 */
	public String getInfo_source() {
		return info_source;
	}

	/**
	 * 消息来源0,自动回复,1,信息接收,2,客服回复设定
	 * @param info_source 消息来源0,自动回复,1,信息接收,2,客服回复
	 */
	public void setInfo_source(String info_source) {
		this.info_source = info_source;
	}

	/**
	 * 默认回复标记取得
	 * @return 默认回复标记
	 */
	public String getDefault_flag() {
		return default_flag;
	}

	/**
	 * 默认回复标记设定
	 * @param default_flag 默认回复标记
	 */
	public void setDefault_flag(String default_flag) {
		this.default_flag = default_flag;
	}

	/**
	 * 首次关注回复标记取得
	 * @return 首次关注回复标记
	 */
	public String getSubscribe_flag() {
		return subscribe_flag;
	}

	/**
	 * 首次关注回复标记设定
	 * @param subscribe_flag 首次关注回复标记
	 */
	public void setSubscribe_flag(String subscribe_flag) {
		this.subscribe_flag = subscribe_flag;
	}

	/**
	 * 序号取得
	 * @return 序号
	 */
	public String getSort_num() {
	    return sort_num;
	}

	/**
	 * 序号设定
	 * @param sort_num 序号
	 */
	public void setSort_num(String sort_num) {
	    this.sort_num = sort_num;
	}

}