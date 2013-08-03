package com.qingzhou.client.util;

import org.apache.commons.codec.binary.Base64;

import java.util.UUID;

/**
 * 对客户操作的相关工具
 * @author hihi
 *
 */
public class CustomerUtil {
	
	/**
	 * 生成客户密码
	 * @param str 加密源串
	 * @return 返回6位密码,9999为不合法的字符串
	 */
	public static String createPasswd(String str)
	{
		String passwd = "9999";
		if (!StringUtil.isNull(str) && str.length() > 11)
		{
			//passwd = Base64.encodeBase64String(str.getBytes());
			passwd = new String(Base64.encodeBase64(str.getBytes()));
			passwd = passwd.substring(2, 5) + passwd.substring(passwd.length()-1-3,passwd.length()-1);
		}
		return passwd.toUpperCase().replaceAll("/", "A");
	}
	
	/**
	 * 校验密码是否正确
	 * @param src
	 * @param passwd
	 * @return  成功默认返回true，失败默认返回false
	 */
	public static boolean checkPasswd(String src, String passwd)
	{
		boolean returnFlg = false; 
		if (!StringUtil.isNull(src) && !StringUtil.isNull(passwd))
		{
			String cuPasswd = CustomerUtil.createPasswd(src);
			if (!cuPasswd.equals("9999") && cuPasswd.equals(passwd.toUpperCase()))
				returnFlg = true;				
		}
		
		return returnFlg;
	}
	
	/**
	 * 生成用户令牌
	 * @return
	 */
	public static String createToken()
	{
		return UUID.randomUUID().toString();
	}
	
	 public static void main(String[] args)
	 {
		 //System.out.println(CustomerUtil.createPasswd("15011320143焗油"));
		 //System.out.println(CustomerUtil.checkPasswd("15011320143耿延宁", "UWMROe"));
		 System.out.println(CustomerUtil.createToken());
	 }	

}
