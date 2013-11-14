package com.qingzhou.client.domain;
import java.io.Serializable;

public class PushExtras implements Serializable{
	
	private int notice_type;
	private String notice_title;
	private String notice_content;
	private String notice_url;//通知连接的具体内容，非栏目性通知使用
	
	private int notice_id;
	private String msg_sender; //如果是我的消息，需要填写发送人是谁
	private String msg_sendername;//发送人人名
	
	public String getNotice_url() {
		return notice_url;
	}
	public void setNotice_url(String notice_url) {
		this.notice_url = notice_url;
	}
	public String getMsg_sendername() {
		return msg_sendername;
	}
	public void setMsg_sendername(String msg_sendername) {
		this.msg_sendername = msg_sendername;
	}
	public String getMsg_sender() {
		return msg_sender;
	}
	public void setMsg_sender(String msg_sender) {
		this.msg_sender = msg_sender;
	}
	public int getNotice_type() {
		return notice_type;
	}
	public void setNotice_type(int notice_type) {
		this.notice_type = notice_type;
	}
	public String getNotice_title() {
		return notice_title;
	}
	public void setNotice_title(String notice_title) {
		this.notice_title = notice_title;
	}
	public String getNotice_content() {
		return notice_content;
	}
	public void setNotice_content(String notice_content) {
		this.notice_content = notice_content;
	}
	public int getNotice_id() {
		return notice_id;
	}
	public void setNotice_id(int notice_id) {
		this.notice_id = notice_id;
	}
	
	
}
