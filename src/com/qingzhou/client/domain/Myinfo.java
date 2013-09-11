package com.qingzhou.client.domain;

import java.io.Serializable;
import java.util.List;

public class Myinfo implements Serializable{

	private String info_id; //资讯ID
	private String info_title;//资讯标题
	private String info_type;//资讯类型
	private String info_url;//资讯地址
	private String info_date;//资讯添加时间
	
	public String getInfo_id() {
		return info_id;
	}
	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}
	public String getInfo_title() {
		return info_title;
	}
	public void setInfo_title(String info_title) {
		this.info_title = info_title;
	}
	public String getInfo_type() {
		return info_type;
	}
	public void setInfo_type(String info_type) {
		this.info_type = info_type;
	}
	public String getInfo_url() {
		return info_url;
	}
	public void setInfo_url(String info_url) {
		this.info_url = info_url;
	}
	public String getInfo_date() {
		return info_date;
	}
	public void setInfo_date(String info_date) {
		this.info_date = info_date;
	}
	
}
