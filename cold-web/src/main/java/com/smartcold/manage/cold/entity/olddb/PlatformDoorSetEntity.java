package com.smartcold.manage.cold.entity.olddb;

import java.util.Date;

public class PlatformDoorSetEntity {

	private int id;
	  
	private String name;
	  
	private int rdcid;
	  
	private float length;
	  
	private float width;
	  
	private String mapping;
	  
	private Date addTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRdcid() {
		return rdcid;
	}

	public void setRdcid(int rdcid) {
		this.rdcid = rdcid;
	}

	public float getLength() {
		return length;
	}

	public void setLength(float length) {
		this.length = length;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
