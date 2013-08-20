package com.qingzhou.client.util;

import java.text.DecimalFormat;

/**
 * 对字符串进行操作的工具类
 * @author hihi
 *
 */
public class StringUtils {
	
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
	    
	    /**
	     * 默认取两位小数，三位一个逗号
	     * @param strNumber
	     * @return
	     */
	    public static String formatDecimal(String strNumber)
	    {
	    	return formatDecimal(strNumber,",##0.00");
	    }
	    
	    /**
	     * 格式化数字，格式为自定义
	     * @param strNumber
	     * @param pattern
	     * @return
	     */
		public static String formatDecimal(String strNumber,String pattern)
		{
			if (StringUtils.isNull(strNumber))
				strNumber = "0.00";
			return new DecimalFormat(pattern).format(new Double(strNumber)).toString();
		}
		
		 /**
	     * 格式化折扣，输入值为0.1-0.9的字符
	     * @param src
	     * @return
	     */
	    public static String formatDiscount(String src)
	    {
	    	String str = "一二三四五六七八九";
	    	try
	    	{
	    		int d = (int) (new Double(src)*10);
		    	
		    	if (d >= 10 || d <= 0)
		    		str = "无折扣";
		    	else
		    		str = str.substring(d-1, d)+"折";
	    	}catch(Exception ex)
	    	{
	    		ex.printStackTrace();
	    		str = "无折扣";
	    	}
	    	return str;
	    }

	    
}
