/*
 * 基础Entity类  车主管家模块附件表
 *
 * VERSION  DATE        BY              REASON
 * -------- ----------- --------------- ------------------------------------------
 * 1.00     2014.02.26  wangzi           程序发布
 * -------- ----------- --------------- ------------------------------------------
 * Copyright 2014 车主管家  System. - All Rights Reserved.
 *
 */
package cn.com.softvan.dao.entity;


/**
 * <p>
 * 车主管家模块附件表
 * <p>
 * 
 * @author wangzi
 * 
 */
public class TcFile extends BaseEntity {
	/** 附件ID */
	private String fileId;
	/** 1:图片 2:视频 3:网页 4:文件 */
	private String fileType;
	/** 附件大小 */
	private String fileSize;
	/** 附件名称 */
	private String fileName;
	/** 附件路径 */
	private String fileUrl;
	/** 文件后缀 */
	private String extension;
	/** 附件描述 */
	private String fileNote;
	/**
	 * 附件ID取得。
	 * @return 附件ID
	 */
	public String getFileId() {
	    return fileId;
	}
	/**
	 * 附件ID设定。
	 * @param fileId 附件ID
	 */
	public void setFileId(String fileId) {
	    this.fileId = fileId;
	}
	/**
	 * 1:图片 2:视频 3:网页 4:文件取得。
	 * @return 1:图片 2:视频 3:网页 4:文件
	 */
	public String getFileType() {
	    return fileType;
	}
	/**
	 * 1:图片 2:视频 3:网页 4:文件设定。
	 * @param fileType 1:图片 2:视频 3:网页 4:文件
	 */
	public void setFileType(String fileType) {
	    this.fileType = fileType;
	}
	/**
	 * 附件大小取得。
	 * @return 附件大小
	 */
	public String getFileSize() {
	    return fileSize;
	}
	/**
	 * 附件大小设定。
	 * @param fileSize 附件大小
	 */
	public void setFileSize(String fileSize) {
	    this.fileSize = fileSize;
	}
	/**
	 * 附件名称取得。
	 * @return 附件名称
	 */
	public String getFileName() {
	    return fileName;
	}
	/**
	 * 附件名称设定。
	 * @param fileName 附件名称
	 */
	public void setFileName(String fileName) {
	    this.fileName = fileName;
	}
	/**
	 * 附件路径取得。
	 * @return 附件路径
	 */
	public String getFileUrl() {
	    return fileUrl;
	}
	/**
	 * 附件路径设定。
	 * @param fileUrl 附件路径
	 */
	public void setFileUrl(String fileUrl) {
	    this.fileUrl = fileUrl;
	}
	/**
	 * 文件后缀取得。
	 * @return 文件后缀
	 */
	public String getExtension() {
	    return extension;
	}
	/**
	 * 文件后缀设定。
	 * @param extension 文件后缀
	 */
	public void setExtension(String extension) {
	    this.extension = extension;
	}
	/**
	 * 附件描述取得。
	 * @return 附件描述
	 */
	public String getFileNote() {
	    return fileNote;
	}
	/**
	 * 附件描述设定。
	 * @param fileNote 附件描述
	 */
	public void setFileNote(String fileNote) {
	    this.fileNote = fileNote;
	}
	
	 
}