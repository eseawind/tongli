package cn.com.softvan.bean.wechat.reply;

import org.jdom.Document;
import org.jdom.Element;

public class WxReplyMusicMsg extends WxReplyMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7203031929531371666L;
	private String musicUrl;
	private String hqMusicUrl;
	private String description;
	private String title;
	private String ThumbMediaId;
	public WxReplyMusicMsg(String title,String description,String musicUrl,String hqMusicUrl) {
		setMsgType("music");
		this.title = title;
		this.description = description;
		this.musicUrl = musicUrl;
		this.hqMusicUrl = hqMusicUrl;
	}
	public WxReplyMusicMsg(WxReplyMsg msg,String title,String description,String musicUrl,String hqMusicUrl) {
		super(msg);
		setMsgType("music");
		this.title = title;
		this.description = description;
		this.musicUrl = musicUrl;
		this.hqMusicUrl = hqMusicUrl;
	}
	
	/**
	 * musicUrl取得
	 * @return musicUrl
	 */
	public String getMusicUrl() {
		return musicUrl;
	}
	/**
	 * musicUrl设定
	 * @param musicUrl musicUrl
	 */
	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}
	/**
	 * hqMusicUrl取得
	 * @return hqMusicUrl
	 */
	public String getHqMusicUrl() {
		return hqMusicUrl;
	}
	/**
	 * hqMusicUrl设定
	 * @param hqMusicUrl hqMusicUrl
	 */
	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}
	/**
	 * description取得
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * description设定
	 * @param description description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		Element music = createElement(doc.getRootElement(), "Music", "");
		createElement(music, "Description", getDescription());
		createElement(music, "Title", getTitle());
		createElement(music, "MusicUrl", getMusicUrl());
		createElement(music, "HQMusicUrl", getHqMusicUrl());
		return doc;
	}
	/**
	 * title取得
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * title设定
	 * @param title title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * ThumbMediaId取得
	 * @return ThumbMediaId
	 */
	public String getThumbMediaId() {
	    return ThumbMediaId;
	}
	/**
	 * ThumbMediaId设定
	 * @param ThumbMediaId ThumbMediaId
	 */
	public void setThumbMediaId(String ThumbMediaId) {
	    this.ThumbMediaId = ThumbMediaId;
	}
}
