package com.smy.identify.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.widget.Toast;


public class CommonTools {


	/**
	 * �ж��ǲ��ǺϷ����ֻ���
	*/
	public static boolean isMobileNO(String mobiles){
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher matcher = pattern.matcher(mobiles);  
		return matcher.matches();
	}
	
	/**
	 * ������ʾ��Ϣ
	 * @param context �����Ķ���
	 * @param msg ��ʾ��Ϣ
	 */
	public static void showMessage(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	
}
