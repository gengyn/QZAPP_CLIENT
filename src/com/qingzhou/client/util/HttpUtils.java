package com.qingzhou.client.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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

/**
 * 网络通讯http协议工具类，主要使用了org.apache.http.client,统一返回字符串格式，服务器返回值的格式根据调用的服务不同而不同，不都是JSON
 * @author hihi
 *
 */
public class HttpUtils {
	
	/**
	 * 获取HttpClient对象，并且设置通信参数
	 * @return HttpClient
	 */
	public HttpClient getHttpClient()
	{
		HttpClient client = new DefaultHttpClient();// 新建http客户端
		HttpParams httpParams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 1000);// 设置连接超时范围
		HttpConnectionParams.setSoTimeout(httpParams, 2000);
		return client;
	}
	
	/**
	 * 将返回值解析为字符串
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String toJsonString(HttpResponse response) throws Exception
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
			}
		}catch(Exception ex)
		{
			System.err.printf("toJsonString", ex.toString());
			throw ex;
		}
		return jsonSb.toString();
	}
	/**
	 * 将返回值解析为字符串
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public String toJsonString(HttpEntity entity) throws Exception
	{
		String returnStr = "";
		try{
			if (entity != null)
				returnStr = EntityUtils.toString(entity,HTTP.UTF_8);
		}catch(Exception ex)
		{
			System.err.printf("toJsonString", ex.toString());
			throw ex;
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
	public String httpGetExecute(String url) throws ClientProtocolException, Exception
	{
		String returnStr = "";
		HttpClient client = this.getHttpClient();
		HttpResponse response = null;
		try{
			response = client.execute(new HttpGet(url));
			returnStr = this.toJsonString(response.getEntity());
		}catch(Exception ex)
		{
			System.err.printf("HTTPGET{0}", ex.toString());
			throw ex;
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
	public String httpPostExecute(String url,String inputJson) throws ClientProtocolException, Exception
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
			
		}catch(Exception ex)
		{
			System.err.printf("HTTPPOST{0}", ex.toString());
			throw ex;
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
	public String httpPutExecute(String url,String inputJson) throws ClientProtocolException, Exception
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
		}catch(Exception ex)
		{
			System.err.printf("HTTPPUT{0}", ex.toString());
			throw ex;
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
	public String httpDelExecute(String url) throws ClientProtocolException, Exception
	{
		String returnStr = "";
		HttpClient client = this.getHttpClient();
		HttpResponse response = null;
		try{
			response = client.execute(new HttpDelete(url));
			returnStr = this.toJsonString(response.getEntity());
		}catch(Exception ex)
		{
			System.err.printf("HTTPDEL{0}", ex.toString());
			throw ex;
		}finally
		{
			client.getConnectionManager().shutdown();
		}
		
		return returnStr;
	}

}