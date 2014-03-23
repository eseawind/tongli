package cn.com.softvan.bean.wechar.reply;

import org.jdom.Document;
import org.jdom.Element;

public class WxReplyVideoMsg extends WxReplyMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**通过上传多媒体文件，得到的id。*/
	private String MediaId;
	/**消息的标题*/
	private String Title;
	/**消息的描述*/
	private String Description;
	
	public WxReplyVideoMsg(String MediaId,String Title,String Description) {
		setMsgType("video");
		this.MediaId = MediaId;
		this.Title=Title;
		this.Description=Description;
	}
	public WxReplyVideoMsg(WxReplyMsg msg,String MediaId,String Title,String Description) {
		super(msg);
		setMsgType("video");
		this.MediaId = MediaId;
		this.Title=Title;
		this.Description=Description;
	}
	
	@Override
	public Document toDocument() {
		Document doc = super.toDocument();
		Element el = createElement(doc.getRootElement(), "Video", "");
		createElement(el, "MediaId", getMediaId());
		createElement(el, "Title", getTitle());
		createElement(el, "Description", getDescription());
		return doc;
	}
	/**
	 * 通过上传多媒体文件，得到的id。取得
	 * @return 通过上传多媒体文件，得到的id。
	 */
	public String getMediaId() {
	    return MediaId;
	}
	/**
	 * 通过上传多媒体文件，得到的id。设定
	 * @param MediaId 通过上传多媒体文件，得到的id。
	 */
	public void setMediaId(String MediaId) {
	    this.MediaId = MediaId;
	}
	/**
	 * 消息的标题取得
	 * @return 消息的标题
	 */
	public String getTitle() {
	    return Title;
	}
	/**
	 * 消息的标题设定
	 * @param Title 消息的标题
	 */
	public void setTitle(String Title) {
	    this.Title = Title;
	}
	/**
	 * 消息的描述取得
	 * @return 消息的描述
	 */
	public String getDescription() {
	    return Description;
	}
	/**
	 * 消息的描述设定
	 * @param Description 消息的描述
	 */
	public void setDescription(String Description) {
	    this.Description = Description;
	}
}
