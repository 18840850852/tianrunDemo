package com.smy.identify.activities;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.smy.identify.common.CommonTools;
import com.smy.identify.common.SDK;

public class DemoBaseActivity extends Activity{

	protected SDK sdk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sdk = new SDK();
	}
	
	// 解析服务器返回的数据
	protected void parseResult(String content) throws JSONException {
		JSONObject jsonObject = new JSONObject(content);
		Log.i("tag", "result:" + content);
		String desc = jsonObject.getString("description");
		CommonTools.showMessage(getApplicationContext(), desc);
	}
	
	

}
