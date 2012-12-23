package com.sc.minitweater.data.entity;

import java.io.Serializable;
import java.util.Date;

public class Tweet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5182034146146500217L;
	private Integer id;
	private String text;
	private Date timestamp;
	private Integer userId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
