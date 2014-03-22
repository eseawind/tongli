package cn.com.softvan.bean.wechar.receive;



public class WxRecvPicMsg extends WxRecvMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6244148466695438814L;
	/**图片链接*/
	private String picUrl;
	/**图片消息媒体id，可以调用多媒体文件下载接口拉取数据。*/
	private String MediaId;
	public WxRecvPicMsg(WxRecvMsg msg,String picUrl,String MediaId) {
		super(msg);
		this.picUrl = picUrl;
		this.MediaId=MediaId;
	}
	
	/**
	 * 图片链接取得
	 * @return 图片链接
	 */
	public String getPicUrl() {
		return picUrl;
	}

	/**
	 * 图片链接设定
	 * @param picUrl 图片链接
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	/**
	 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。取得
	 * @return 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	public String getMediaId() {
	    return MediaId;
	}

	/**
	 * 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。设定
	 * @param MediaId 图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	 */
	public void setMediaId(String MediaId) {
	    this.MediaId = MediaId;
	}
}
