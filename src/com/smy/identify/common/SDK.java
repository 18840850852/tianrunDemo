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
	public static final String PROTOCOL = "http://"; //Э������
	public static final String SERVER = "bj-out.ccic2.com"; //����������
	public static final String PORT = "8080"; //�������˿ں�
	public static final String URL_VOICE_VERTIFY = "/interface/entrance/OpenInterfaceEntrance"; //������֤��Ľӿ�url��ַ
	
	private String url; //��������url��ַ
	private String interfaceType; //�˿�����
	private String enterpriseId; //��ҵID
	private String userName; //�û���
	
	/**
	 * �޲ι��캯��������Ĭ�ϵĲ���
	 */
	public SDK(){
		this.url = PROTOCOL+SERVER+URL_VOICE_VERTIFY;
		this.interfaceType = "webCall";
		this.enterpriseId = "3001429";
		this.userName = "admin";
	}
	
	/**
	 * �������Ĺ��캯�������Զ�Sdk�еı�����������
	 * @param url ��������url��ַ
	 * @param interfaceType �˿�����
	 * @param enterpriseId ��ҵid
	 * @param userName �û���
	 */
	public SDK(String url, String interfaceType, String enterpriseId,
			String userName) {
		this.url = url;
		this.interfaceType = interfaceType;
		this.enterpriseId = enterpriseId;
		this.userName = userName;
	}

	/**
	 * ���������������������֤��������
	 * @param pwd ����
	 * @param seed ����
	 * @param ivrId ��ҵ�����id
	 * @param subtel �ֻ�����
	 * @param remoteIp ������е�IP
	 * @param sync �Ƿ���ͬ����
	 * @param customerNumeber ����������֤��ĵ绰
	 * @param paramNames 
	 * @param paramTypes
	 * @param vertifyCode ��֤�루������������ɵģ�
	 * @param userField 
	 * @param timeout ��ʱʱ��
	 * @return ������������������
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
	 * ����������֤�����֤
	 * @param vertifyCode ��֤��
	 * @param customerNumeber ������֤��ĵ绰
	 * @return ������������������
	 */
	public String doVoiceIdentify(String pwd,String vertifyCode,String customerNumeber){
		return doVoiceIdentify(pwd, "aaa", "517", "",
				"","1", customerNumeber, "code", "1", vertifyCode, "abc","30");
	}
	
	/**
	 * ����App�غ�
	 * @param pwd ����
	 * @param ivrId ��ҵ�����id
	 * @param remoteIp �����˵�ip
	 * @param cookie cookie
	 * @param sync �Ƿ�ͬ������0��ͬ������1���첽
	 * @param customerNumeber �����˵ĵ绰
	 * @param subtel �ֻ�����
	 * @param userField �Զ����ֶ�
	 * @param timeout ��ʱʱ�䣨0-60֮�䣬��λ�룬Ĭ��30��
	 * @param paramNames ��̬��������
	 * @param paramTypes �������������
	 * @param transferNumber ת�Ӻ���
	 * @param seed ����
	 * @return �������ķ�����Ϣ
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
	 * App�غ��ӿ�
	 * @param customerNumeber ͨ�������˵ĵ绰
	 * @param tranferNumber ͨ�������˵ĵ绰
	 * @return ���������ص���Ϣ
	 */
	public String doAppCallback(String pwd,String customerNumeber,String tranferNumber){
		return doAppCallback(pwd, "531", "", "", "1", customerNumeber, "", "abc",
				"30", "transferNumber", "1", tranferNumber,"aaa");
	}
	
	/**
	 * �������ŷ���
	 * @param pwd ����
	 * @param ivrId ��ҵ�����id
	 * @param remoteIp �����˵�ip
	 * @param cookie cookie
	 * @param sync �Ƿ�ͬ������0��ͬ������1���첽
	 * @param customerNumeber �����˵ĵ绰
	 * @param subtel �ֻ�����
	 * @param userField �Զ����ֶ�
	 * @param timeout ��ʱʱ�䣨0-60֮�䣬��λ�룬Ĭ��30��
	 * @param paramNames ��̬��������
	 * @param paramTypes �������������
	 * @param message ����������Ϣ
	 * @return �������ķ�����Ϣ
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
	 * �������ŷ���
	 * @param pwd ����
	 * @param customerNumber ���ն��ŵ��˵ĵ绰����
	 * @param msg ���͵���Ϣ
	 * @return ����������Ӧ��Ϣ
	 */
	public String doVoiceMessage(String pwd,String customerNumber,String msg){
		return doVoiceMessage(pwd, "530", "", "", "1", customerNumber, "", "abc",
				"30", "message", "2", msg,"aaa");
	}
	
	/**
	 * ��������ж��μ���
	 * @param pwd ԭʼ����
	 * @param seed ���ӣ�
	 * @return ���ܺ������
	 */
	private String secretePwd(final String pwd,final String seed){
		String _pwd = SecreteUtil.parseStrToMd5L32(pwd)+seed;
		return SecreteUtil.parseStrToMd5L32(_pwd);
	}
	
	
	/**
	 *  ����json���ݵĹ��߷���
	 * @param url Ŀ��url��ַ
	 * @param params ���ݵĲ���
	 * @return ���ص��ַ���
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
