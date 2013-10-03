package com.qingzhou.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Calendar;


public class StringUtils {

    /**
     * will trim the string
     * 
     * @param s
     * @return
     */
    public static boolean isEmpty(String s) {
        if (null == s) return true;
        if (s.length() == 0) return true;
        if (s.trim().length() == 0) return true;
        return false;
    }
    
    public static String emptyStringIfNull(String s) {
        return (null == s) ? "" : s;
    }

    public static String humanReadableByteCount(long bytes) {
        int unit = 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = "KMGTPE".charAt(exp-1) + "";
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
    
    public static String fixedLengthString(String s, int expectedLength) {
        int l = s.length();
        if (l >= expectedLength) {
            return s;
            //return s.substring(0, expectedLength);
        }
        for (int i = 0; i < expectedLength - l; i++) {
            s = s + " ";
        }
        return s;
    }
    
    public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
    
    public static String toMD5(String source) {
        if (null == source || "".equals(source)) return null;
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(source.getBytes());
            return toHex(digest.digest());
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String toHex(byte[] buf) {
        if (buf == null) return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }
    
    private final static String HEX = "0123456789ABCDEF";
    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }
    
    /**
     * 获取当前日期及时间 格式为YYYY-MM-DD HH:MM:ss
     * @return
     */
    public static String getCurDate() {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int mins = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        
        DecimalFormat decimalformat = new DecimalFormat("00");
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + decimalformat.format(month) + "-" 
        + decimalformat.format(day) + " " + decimalformat.format(hour) + ":" 
        + decimalformat.format(mins) + ":" + decimalformat.format(second)); 
						
        return sbBuffer.toString();
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
		if (StringUtils.isEmpty(strNumber))
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
    
    /**
     * 人员显示默认处理
     * @return
     */
    public static String defaultPerson(String src)
    {
    	if (StringUtils.isEmpty(src))
    		return "暂缺";
    	else return src;
    }

    
}
