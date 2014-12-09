package com.smy.identify.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.provider.SyncStateContract.Constants;
import android.util.Log;

/**
 * 
 * @author smy
 *	http请求数据的工具类
 */
public class HttpUtil {

	/**
	 *  发送json数据的工具方法
	 * @param url 目标url地址
	 * @param jsonKey json的键数组
	 * @param jsonValue json的值数组
	 * @param encoding 编码
	 * @return 返回的字符串
	 */
	public static String sendJson(String url, String[] jsonKey,
			String[] jsonValue, String encoding) {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		List<NameValuePair> post = new ArrayList<NameValuePair>();
		try {
			for (int i = 0; i < jsonValue.length; i++) {
				post.add(new BasicNameValuePair(jsonKey[i], jsonValue[i]));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "sendText error!";
		}
		UrlEncodedFormEntity form = null;
		HttpResponse response = null;
		try {
			form = new UrlEncodedFormEntity(post, "utf-8");
			request.setEntity(form);
			response = client.execute(request);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "sendText error!";
		} catch (Exception e) {
			e.printStackTrace();
			return "sendText error!";
		}
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), encoding));
			String retData = null;
			StringBuffer responseData = new StringBuffer();
			while ((retData = in.readLine()) != null) {
				responseData.append(retData);
			}
			in.close();
			return responseData.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "sendText error!";
		} 
	}

	/**
	 *  发送json数据
	 * @param url 目标url地址
	 * @param jsonKey json的键数组
	 * @param jsonValue json的值数组
	 * @return 返回的字符串
	 */
	public static String sendJson(String url, String[] jsonKey,
			String[] jsonValue) {
		return sendJson(url, jsonKey, jsonValue, "utf-8");
	}


	/**
	 * 显示返回的消息
	 * @param response HttpResponse对象
	 */
	private static String showResponseResult(HttpResponse response) {
		if (null == response) {
			return null;
		}
		HttpEntity httpEntity = response.getEntity();
		try {
			InputStream inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			String result = "";
			String line = "";
			while (null != (line = reader.readLine())) {
				result += line;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
