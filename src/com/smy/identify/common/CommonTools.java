package com.smy.identify.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.widget.Toast;


public class CommonTools {


	/**
	 * 判断是不是合法的手机号
	*/
	public static boolean isMobileNO(String mobiles){
		Pattern pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
		Matcher matcher = pattern.matcher(mobiles);  
		return matcher.matches();
	}
	
	/**
	 * 弹出提示信息
	 * @param context 上下文对象
	 * @param msg 提示信息
	 */
	public static void showMessage(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	
}
