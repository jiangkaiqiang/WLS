package com.smartcold.zigbee.manage.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author jiangkaiqiang
 * @date 2016-6-7 下午8:10:02  
 * @Description: UploadFileEntity,used to upload file
 */
public class UploadFileEntity {
	private String name;//file name-->FTP client
	private MultipartFile multipartFile;//multipartFile-->user upload file
	private String remoteDir; //file path-->FTP client
	private String remoteNewDir;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MultipartFile getMultipartFile() {
		return multipartFile;
	}
	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}
	public String getRemoteDir() {
		return remoteDir;
	}
	public void setRemoteDir(String remoteDir) {
		this.remoteDir = remoteDir;
	}
	public String getRemoteNewDir() {
		return remoteNewDir;
	}
	public void setRemoteNewDir(String remoteNewDir) {
		this.remoteNewDir = remoteNewDir;
	}
	public UploadFileEntity(String name, MultipartFile multipartFile,
			String remoteDir, String remoteNewDir) {
		super();
		this.name = name;
		this.multipartFile = multipartFile;
		this.remoteDir = remoteDir;
		this.remoteNewDir = remoteNewDir;
	}
	
	

}
