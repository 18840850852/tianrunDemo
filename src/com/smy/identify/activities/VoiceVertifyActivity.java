package com.smy.identify.activities;

import java.util.Random;

import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.smy.identify.R;
import com.smy.identify.common.CommonTools;
import com.smy.identify.common.SecreteUtil;

public class VoiceVertifyActivity extends DemoBaseActivity implements
		OnClickListener {

	public static final int FLAG_SUCCESS = 1; // 呼叫成功

	private EditText editCode;
	private EditText editPhone;
	private TextView tvMsg;

	private String identifyCode = null;// 生成的验证码
	private boolean canLogin = false; // 现在是不是可以登录

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice_vertify);
		initViews();
	}

	// 初始化界面的view
	private void initViews() {
		editCode = (EditText) findViewById(R.id.edit_identify);
		editPhone = (EditText) findViewById(R.id.edit_phone);
		tvMsg = (TextView) findViewById(R.id.tv_msg);
		findViewById(R.id.bt_login).setOnClickListener(this);
		findViewById(R.id.bt_identify).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.bt_identify:
			doGetVoiceIdentify();
			break;
		case R.id.bt_login:
			doLogin();
			break;
		}
	}

	// 发送请求，服务器发送语音验证码
	private void doGetVoiceIdentify() {
		String newCode = getRandomIdentifyCode();
		if (newCode != null) {
			identifyCode = newCode;
		}
		String phoneString = editPhone.getText().toString();
		if (!CommonTools.isMobileNO(phoneString.trim())) {
			CommonTools.showMessage(getApplicationContext(), "您输入的手机号不合法");
			return;
		}
		final String phone = this.editPhone.getText().toString().trim();
		final String pwd = "test1234";
		new GetVoiceTask().execute(pwd, newCode, phone);
	}

	// 模拟登录，主要是检查 输入的验证码是否正确
	private void doLogin() {
		String identifyString = editCode.getText().toString().trim();
		if (!canLogin) {
			CommonTools.showMessage(getApplicationContext(), "请先获取验证码");
			return;
		}
		if (identifyString.isEmpty()) {
			CommonTools.showMessage(getApplicationContext(), "请输入验证码");
			return;
		}
		System.out.println("code:" + identifyCode);
		if (identifyString.equals(identifyCode)) { // 验证码正确
			tvMsg.setText("登录成功");
		} else { // 验证码错误
			tvMsg.setText("验证码错误，请重试");
		}
	}

	/**
	 * 产生随机的验证码
	 * 
	 * @return 随机的验证码
	 */
	private String getRandomIdentifyCode() {
		StringBuffer code = new StringBuffer();
		int length = 4; //现在将验证码长度定为4
		System.out.println("length:" + length);
		for (int i = 0; i < length; ++i) {
			code.append(getRandomNumber());
		}
		System.out.println("code:" + code);
		return code.toString();
	}

	/**
	 * 产生一个0-9的随机数字
	 * 
	 * @return 0-9的随机数字
	 */
	private int getRandomNumber() {
		return new Random().nextInt(10);
	}

	private class GetVoiceTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			return sdk.doVoiceIdentify(params[0], params[1], params[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			canLogin = true;
			try {
				parseResult(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	// 解析服务器返回的数据
	@Override
	protected void parseResult(String content) throws JSONException {
		super.parseResult(content);
		canLogin = true;
	}
}
