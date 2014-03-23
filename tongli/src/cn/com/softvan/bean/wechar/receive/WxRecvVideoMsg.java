package cn.com.softvan.bean.wechar.receive;



public class WxRecvVideoMsg extends WxRecvMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6244148466695438814L;
	/**图片消息媒体id，可以调用多媒体文件下载接口拉取数据。*/
	private String MediaId;
	/**视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据*/
	private String ThumbMediaId;
	
	public WxRecvVideoMsg(WxRecvMsg msg,String MediaId,String ThumbMediaId) {
		super(msg);
		this.ThumbMediaId = ThumbMediaId;
		this.MediaId=MediaId;
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

	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据取得
	 * @return 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	 */
	public String getThumbMediaId() {
	    return ThumbMediaId;
	}

	/**
	 * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据设定
	 * @param ThumbMediaId 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据
	 */
	public void setThumbMediaId(String ThumbMediaId) {
	    this.ThumbMediaId = ThumbMediaId;
	}
	
}
