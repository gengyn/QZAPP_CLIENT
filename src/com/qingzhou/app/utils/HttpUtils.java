package com.qingzhou.app.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import com.qingzhou.app.utils.Logger;
import com.qingzhou.client.common.AppException;

/**
 * 网络通讯http协议工具类，主要使用了org.apache.http.client,统一返回字符串格式，服务器返回值的格式根据调用的服务不同而不同，不都是JSON
 * @author hihi
 *
 */
public class HttpUtils {
	
	private static final String TAG = "HttpUtils";
	/**
	 * 在网络上获取图片
	 * @param urlPath
	 * @return
	 */
	public static InputStream getImageView(String urlPath) {
		URL url;
		InputStream is = null;
		try {
			url = new URL(urlPath);
			Logger.e("url", urlPath);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.connect();
			int length = conn.getContentLength();
			is = conn.getInputStream();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return is;

	}
	/**
	 * 获取HttpClient对象，并且设置通信参数
	 * @return HttpClient
	 */
	public HttpClient getHttpClient()
	{
		HttpClient client = new DefaultHttpClient();// 新建http客户端
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 2000);// 设置连接超时范围
		HttpConnectionParams.setSoTimeout(httpParams, 4000);
		return client;
	}
	
	/**
	 * 将返回值解析为字符串
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String toJsonString(HttpResponse response) throws AppException
	{
		StringBuilder jsonSb = new StringBuilder();
		HttpEntity entity = response.getEntity();
		try{
			if (entity != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						entity.getContent(), "UTF-8"), 8192);
				String line = null;
				while ((line = reader.readLine()) != null) {
					jsonSb.append(line + "\n");// 按行读取放入StringBuilder中
				}
				reader.close();
				
				if (isErrorCode(jsonSb.toString())) throw new AppException(jsonSb.toString());
			}
		}catch(AppException ex)
		{
			Logger.e("toJsonString", ex.getCode());
			throw new AppException(ex.getCode());
		}
		catch(Exception ex)
		{
			Logger.e("toJsonString",ex.getMessage());
			throw new AppException(ex);
		}
		return jsonSb.toString();
	}
	/**
	 * 将返回值解析为字符串
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public String toJsonString(HttpEntity entity) throws AppException
	{
		String returnStr = "";
		try{
			if (entity != null)
				returnStr = EntityUtils.toString(entity,HTTP.UTF_8);
			
			if (isErrorCode(returnStr)) throw new AppException(returnStr);
		}catch(AppException ex)
		{
			Logger.e("toJsonString", ex.getCode());
			throw new AppException(ex.getCode());
		}
		catch(Exception ex)
		{
			Logger.e("toJsonString", ex.toString());
			throw new AppException(ex);
		}
		return returnStr;
	}
	
	/**
	 * GET方式访问服务，返回字符串
	 * @param url 服务地址
	 * @return 服务返回值
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public String httpGetExecute(String url) throws AppException
	{
		String returnStr = "";
		HttpClient client = this.getHttpClient();
		HttpResponse response = null;
		try{
			response = client.execute(new HttpGet(url));
			returnStr = this.toJsonString(response.getEntity());
		}catch(AppException ex)
		{
			Logger.e("HTTPGET{0}", ex.getCode());
			throw new AppException(ex.getCode());
		}
		catch(Exception ex)
		{
			Logger.e("HTTPGET{0}", ex.toString());
			throw new AppException(ex);
		}finally
		{
			client.getConnectionManager().shutdown();
		}
		
		return returnStr;
	}
	
	/**
	 * POST方式访问服务，传递json格式字符串，返回字符串
	 * @param url 服务地址
	 * @param inputJson jSON格式数据
	 * @return
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public String httpPostExecute(String url,String inputJson) throws AppException
	{
		String returnStr = "";
		HttpClient client = this.getHttpClient();
		HttpPost httpRequest = new HttpPost(url);
		HttpResponse response = null;
		try{
			StringEntity input = new StringEntity(inputJson,HTTP.UTF_8);
			input.setContentType("application/json");
			httpRequest.setEntity(input);
			response = client.execute(httpRequest);
			returnStr = this.toJsonString(response.getEntity());
			
		}catch(AppException ex)
		{
			Logger.e("HTTPPOST{0}", ex.getCode());
			throw new AppException(ex.getCode());
		}
		catch(Exception ex)
		{
			Logger.e("HTTPPOST{0}", ex.toString());
			throw new AppException(ex);
		}finally
		{
			client.getConnectionManager().shutdown();
		}
		
		return returnStr;
	}
	
	/**
	 * PUT方式访问服务，传递json格式字符串，返回字符串
	 * @param url 服务地址
	 * @param inputJson jSON格式数据
	 * @return
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public String httpPutExecute(String url,String inputJson) throws AppException
	{
		String returnStr = "";
		HttpClient client = this.getHttpClient();
		HttpPut httpRequest = new HttpPut(url);
		HttpResponse response = null;
		try{
			StringEntity input = new StringEntity(inputJson,HTTP.UTF_8);
			input.setContentType("application/json");
			httpRequest.setEntity(input);
			response = client.execute(httpRequest);
			returnStr = this.toJsonString(response.getEntity());
		}catch(AppException ex)
		{
			Logger.e("HTTPPUT{0}", ex.getCode());
			throw new AppException(ex.getCode());
		}
		catch(Exception ex)
		{
			Logger.e("HTTPPUT{0}", ex.toString());
			throw new AppException(ex);
		}finally
		{
			client.getConnectionManager().shutdown();
		}
		
		return returnStr;
	}
	
	/**
	 * DELETE方式访问服务
	 * @param url 服务地址
	 * @return 字符串
	 * @throws ClientProtocolException
	 * @throws Exception
	 */
	public String httpDelExecute(String url) throws AppException
	{
		String returnStr = "";
		HttpClient client = this.getHttpClient();
		HttpResponse response = null;
		try{
			response = client.execute(new HttpDelete(url));
			returnStr = this.toJsonString(response.getEntity());
		}catch(AppException ex)
		{
			Logger.e("HTTPDEL{0}", ex.getCode());
			throw new AppException(ex.getCode());
		}
		catch(Exception ex)
		{
			Logger.e("HTTPDEL{0}", ex.toString());
			throw new AppException(ex);
		}finally
		{
			client.getConnectionManager().shutdown();
		}
		
		return returnStr;
	}
	
	/**
	 * 是否错误码，等于空 或者 （长度等于4 并且 首位是9）
	 * @param src
	 * @return
	 */
	private Boolean isErrorCode(String src)
	{
		if ((src.length() == 4 && src.indexOf("9") == 1))
			return true;
		return false;
	}

}