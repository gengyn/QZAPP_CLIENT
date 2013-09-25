package com.qingzhou.app.utils;

import android.util.Log;
import com.qingzhou.client.BuildConfig;

/**
 * 日志工具类
 * @author hihi
 *
 */
public class Logger {
	private static final String COMMON_TAG = "QINGZHOU";
	
    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
        	Log.v(COMMON_TAG, "[" + tag + "] " + msg);
        }
    }
    
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
        	Log.d(COMMON_TAG, "[" + tag + "] " + msg);
        }
    }
    
    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
        	Log.i(COMMON_TAG, "[" + tag + "] " + msg);
        }
    }
    
    public static void w(String tag, String msg) {
        Log.w(COMMON_TAG, "[" + tag + "] " + msg);
    }
    
    public static void e(String tag, String msg) {
        Log.e(COMMON_TAG, "[" + tag + "] " + msg);
    }
    
    public static void v(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
        	Log.v(COMMON_TAG, "[" + tag + "] " + msg, tr);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
        	Log.d(COMMON_TAG, "[" + tag + "] " + msg, tr);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
        	Log.i(COMMON_TAG, "[" + tag + "] " + msg, tr);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        Log.w(COMMON_TAG, "[" + tag + "] " + msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        Log.e(COMMON_TAG, "[" + tag + "] " + msg, tr);
    }
    
        
}
