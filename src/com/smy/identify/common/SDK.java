package com.smy.identify.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.R.string;
import android.os.Message;

public class SDK {
	public static final String PROTOCOL = "http://"; //协议类型
	public static final String SERVER = "bj-out.ccic2.com"; //服务器域名
	public static final String PORT = "8080"; //服务器端口号
	public static final String URL_VOICE_VERTIFY = "/interface/entrance/OpenInterfaceEntrance"; //语音验证码的接口url地址
	
	private String url; //服务请求url地址
	private String interfaceType; //端口类型
	private String enterpriseId; //企业ID
	private String userName; //用户名
	
	/**
	 * 无参构造函数，是用默认的参数
	 */
	public SDK(){
		this.url = PROTOCOL+SERVER+URL_VOICE_VERTIFY;
		this.interfaceType = "webCall";
		this.enterpriseId = "3001429";
		this.userName = "admin";
	}
	
	/**
	 * 带参数的构造函数，可以对Sdk中的变量进行设置
	 * @param url 请求服务的url地址
	 * @param interfaceType 端口类型
	 * @param enterpriseId 企业id
	 * @param userName 用户名
	 */
	public SDK(String url, String interfaceType, String enterpriseId,
			String userName) {
		this.url = url;
		this.interfaceType = interfaceType;
		this.enterpriseId = enterpriseId;
		this.userName = userName;
	}

	/**
	 * 带完整请求参数的语音验证码请求函数
	 * @param pwd 密码
	 * @param seed 种子
	 * @param ivrId 企业分配的id
	 * @param subtel 分机号码
	 * @param remoteIp 发起呼叫的IP
	 * @param sync 是否是同步的
	 * @param customerNumeber 接受语音验证码的电话
	 * @param paramNames 
	 * @param paramTypes
	 * @param vertifyCode 验证码（现在是随机生成的）
	 * @param userField 
	 * @param timeout 超时时间
	 * @return 服务器返回来的数据
	 */
	public String doVoiceIdentify(String pwd,String seed,String ivrId,String subtel,String remoteIp,String sync,String customerNumeber,
			String paramNames,String paramTypes,String vertifyCode,String userField,String timeout){
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("interfaceType", this.interfaceType);
		requestParams.put("enterpriseId", this.enterpriseId);
		requestParams.put("userName", this.userName);
		requestParams.put("pwd", secretePwd(pwd,seed));
		requestParams.put("seed", seed);
		requestParams.put("ivrId", ivrId);
		requestParams.put("subtel", subtel);
		requestParams.put("remoteIp", remoteIp);
		requestParams.put("sync", sync);
		requestParams.put("customerNumber", customerNumeber);
		requestParams.put("paramNames", paramNames);
		requestParams.put("paramTypes", paramTypes);
		requestParams.put("code", vertifyCode);
		requestParams.put("userField", userField);
		requestParams.put("timeout", timeout);
		return sendJson(this.url, requestParams);
	}
	
	
	

	/**
	 * 进行语音验证码的验证
	 * @param vertifyCode 验证码
	 * @param customerNumeber 接受验证码的电话
	 * @return 服务器返回来的数据
	 */
	public String doVoiceIdentify(String pwd,String vertifyCode,String customerNumeber){
		return doVoiceIdentify(pwd, "aaa", "517", "",
				"","1", customerNumeber, "code", "1", vertifyCode, "abc","30");
	}
	
	/**
	 * 进行App回呼
	 * @param pwd 密码
	 * @param ivrId 企业分配的id
	 * @param remoteIp 发起人的ip
	 * @param cookie cookie
	 * @param sync 是否同步，“0”同步，“1”异步
	 * @param customerNumeber 发起人的电话
	 * @param subtel 分机号码
	 * @param userField 自定义字段
	 * @param timeout 超时时间（0-60之间，单位秒，默认30）
	 * @param paramNames 动态附带参数
	 * @param paramTypes 上面参数的类型
	 * @param transferNumber 转接号码
	 * @param seed 种子
	 * @return 服务器的返回信息
	 */
	public String  doAppCallback(String pwd,String ivrId,String remoteIp,String cookie,
			String sync,String customerNumeber,String subtel,String userField,String timeout,
			String paramNames,String paramTypes,String transferNumber,String seed) {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("interfaceType", this.interfaceType);
		requestParams.put("enterpriseId", this.enterpriseId);
		requestParams.put("userName", this.userName);
		requestParams.put("pwd", secretePwd(pwd,seed));
		requestParams.put("ivrId", ivrId);
		requestParams.put("remoteIp", remoteIp);
		requestParams.put("cookie", "");
		requestParams.put("seed", seed);
		requestParams.put("sync", "1");
		requestParams.put("customerNumber", customerNumeber);
		requestParams.put("subtel", subtel);
		requestParams.put("userField", userField);
		requestParams.put("timeout", timeout);
		requestParams.put("paramNames", paramNames);
		requestParams.put("paramTypes", paramTypes);
		requestParams.put("transferNumber", transferNumber);
		return sendJson(this.url, requestParams);
	}
	
	
	/**
	 * App回呼接口
	 * @param customerNumeber 通话发起人的电话
	 * @param tranferNumber 通话接收人的电话
	 * @return 服务器返回的信息
	 */
	public String doAppCallback(String pwd,String customerNumeber,String tranferNumber){
		return doAppCallback(pwd, "531", "", "", "1", customerNumeber, "", "abc",
				"30", "transferNumber", "1", tranferNumber,"aaa");
	}
	
	/**
	 * 语音短信发送
	 * @param pwd 密码
	 * @param ivrId 企业分配的id
	 * @param remoteIp 发起人的ip
	 * @param cookie cookie
	 * @param sync 是否同步，“0”同步，“1”异步
	 * @param customerNumeber 发起人的电话
	 * @param subtel 分机号码
	 * @param userField 自定义字段
	 * @param timeout 超时时间（0-60之间，单位秒，默认30）
	 * @param paramNames 动态附带参数
	 * @param paramTypes 上面参数的类型
	 * @param message 语音短信信息
	 * @return 服务器的返回信息
	 */
	public String  doVoiceMessage(String pwd,String ivrId,String remoteIp,String cookie,
			String sync,String customerNumeber,String subtel,String userField,String timeout,
			String paramNames,String paramTypes,String message,String seed) {
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("interfaceType", this.interfaceType);
		requestParams.put("enterpriseId", this.enterpriseId);
		requestParams.put("userName", this.userName);
		requestParams.put("pwd", secretePwd(pwd,seed));
		requestParams.put("ivrId", ivrId);
		requestParams.put("remoteIp", remoteIp);
		requestParams.put("cookie", cookie);
		requestParams.put("seed", seed);
		requestParams.put("sync", sync);
		requestParams.put("customerNumber", customerNumeber);
		requestParams.put("subtel", subtel);
		requestParams.put("userField", userField);
		requestParams.put("timeout", timeout);
		requestParams.put("paramNames", paramNames);
		requestParams.put("paramTypes", paramTypes);
		requestParams.put("message", message);
		return sendJson(this.url, requestParams);
	}
	
	/**
	 * 语音短信发送
	 * @param pwd 密码
	 * @param customerNumber 接收短信的人的电话号码
	 * @param msg 发送的信息
	 * @return 服务器的响应消息
	 */
	public String doVoiceMessage(String pwd,String customerNumber,String msg){
		return doVoiceMessage(pwd, "530", "", "", "1", customerNumber, "", "abc",
				"30", "message", "2", msg,"aaa");
	}
	
	/**
	 * 对密码进行二次加密
	 * @param pwd 原始密码
	 * @param seed 种子，
	 * @return 加密后的密码
	 */
	private String secretePwd(final String pwd,final String seed){
		String _pwd = SecreteUtil.parseStrToMd5L32(pwd)+seed;
		return SecreteUtil.parseStrToMd5L32(_pwd);
	}
	
	
	/**
	 *  发送json数据的工具方法
	 * @param url 目标url地址
	 * @param params 传递的参数
	 * @return 返回的字符串
	 */
	public static String sendJson(String url, Map<String, String> params) {
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		List<NameValuePair> post = new ArrayList<NameValuePair>();
		try {
			for(String key:params.keySet()){
				post.add(new BasicNameValuePair(key, params.get(key)));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return "sendText error!";
		}
		UrlEncodedFormEntity form = null;
		HttpResponse response = null;
		try {
			form = new UrlEncodedFormEntity(post, "utf-8");
			request.setEntity(form);
			response = client.execute(request);
		}  catch (Exception e) {
			e.printStackTrace();
			return "sendText error!";
		}
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), "utf-8"));
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
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
