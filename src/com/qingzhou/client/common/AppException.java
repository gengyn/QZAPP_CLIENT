package com.qingzhou.client.common;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class AppException extends RuntimeException {
	
	static Map<String,String> exception = new HashMap<String,String>();
	String code;
	String message;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public AppException(String code)
	{
		super(getMessage(code));
		this.code = code;
		this.message = exception.get(code);
	}
	
	public AppException(String code, String message)
	{
		this.code = code;
		this.message = message;
	}
	
	private AppException(Exception ex) {
		super(ex);
	}
	
	private static String getMessage(String code)
	{
		exception.put("0", "调用成功");
		exception.put("10", "系统内部错误");
		exception.put("1001", "只支持 HTTP Post 方法，不支持 Get 方法");
		exception.put("1002", "缺少了必须的参数");
		exception.put("1003", "参数值不合法");
		exception.put("1004", "verification_code 验证失败");
		exception.put("1005", "消息体太大");
		exception.put("1007", "receiver_value 参数 非法");
		exception.put("1008", "appkey参数非法");
		exception.put("1010", "msg_content 不合法");
		exception.put("1011", "没有满足条件的推送目标");
		exception.put("1012", "iOS 不支持推送自定义消息。只有 Android 支持推送自定义消息。");
		exception.put("1013", "content-type 只支持 application/x-www-form-urlencoded");
		
		return exception.get(code);
	}
}
