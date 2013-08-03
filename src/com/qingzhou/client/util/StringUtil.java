package com.qingzhou.client.util;

/**
 * 对字符串进行操作的工具类
 * @author hihi
 *
 */
public class StringUtil {
	
		/**
	    *功能：检查此字符串是否为空
	    *@return 如果为空，则返回true，否则则返回false
	    */
	    public static boolean isNull(String strSrouce)
	    {
	        if(strSrouce==null || strSrouce.trim().length()==0) return true;
	        else if(strSrouce.trim().toLowerCase().equals("null")) return true;
	        else return false;
	    }
	    
	    /**
	     * 将字符转换为整形
	     * @param str
	     * @return
	     */
	    public static int toInt(String str)
	    {
	    	return Integer.parseInt(str);
	    }

	    public static void main(String[] args)
	    {
	    	
	    }
}
