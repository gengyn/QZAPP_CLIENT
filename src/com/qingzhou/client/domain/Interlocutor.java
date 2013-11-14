package com.qingzhou.client.domain;

import java.io.Serializable;

public class Interlocutor  implements Serializable{
	
	private String my_mobile;
	private String i_mobile;
	private String i_name;
	private String last_message;
	private String last_time;
	private String isreaded;
	
	public String getI_name() {
		return i_name;
	}
	public void setI_name(String i_name) {
		this.i_name = i_name;
	}
	public String getMy_mobile() {
		return my_mobile;
	}
	public void setMy_mobile(String my_mobile) {
		this.my_mobile = my_mobile;
	}
	public String getI_mobile() {
		return i_mobile;
	}
	public void setI_mobile(String i_mobile) {
		this.i_mobile = i_mobile;
	}
	public String getLast_message() {
		return last_message;
	}
	public void setLast_message(String last_message) {
		this.last_message = last_message;
	}
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	public String getIsreaded() {
		return isreaded;
	}
	public void setIsreaded(String isreaded) {
		this.isreaded = isreaded;
	}

}
