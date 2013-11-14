package com.qingzhou.client.domain;

public class MessageLog {


	private int msg_id;
	private String sender_mobile;
	private String sender_name;
	private String receiver_mobile;
	private String msg_content;
	private String msg_time;
	private String img_url;
	private String voice_url;
	
	public String getSender_name() {
		return sender_name;
	}
	public void setSender_name(String sender_name) {
		this.sender_name = sender_name;
	}
	public String getImg_url() {
		return img_url;
	}
	public void setImg_url(String img_url) {
		this.img_url = img_url;
	}
	public String getVoice_url() {
		return voice_url;
	}
	public void setVoice_url(String voice_url) {
		this.voice_url = voice_url;
	}
	public int getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}
	public String getSender_mobile() {
		return sender_mobile;
	}
	public void setSender_mobile(String sender_mobile) {
		this.sender_mobile = sender_mobile;
	}
	public String getReceiver_mobile() {
		return receiver_mobile;
	}
	public void setReceiver_mobile(String receiver_mobile) {
		this.receiver_mobile = receiver_mobile;
	}
	public String getMsg_content() {
		return msg_content;
	}
	public void setMsg_content(String msg_content) {
		this.msg_content = msg_content;
	}
	public String getMsg_time() {
		return msg_time;
	}
	public void setMsg_time(String msg_time) {
		this.msg_time = msg_time;
	}
}
