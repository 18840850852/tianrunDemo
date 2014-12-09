package com.smy.identify.activities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.smy.identify.R;

public class VoiceMessageActivity extends DemoBaseActivity implements OnClickListener {

	private EditText editPhone; //������Ϣ�˵ĵ绰�ű༭��
	private EditText editMsg; //���ŵı༭��

	private boolean canSend; //�����Ƿ���Է��Ͷ���

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_voice_message);

		initViews();

	}

	//��ʼ��View
	private void initViews() {
		editPhone = (EditText) findViewById(R.id.edit_phone);
		editMsg = (EditText) findViewById(R.id.edit_msg);

		findViewById(R.id.bt_send).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		final String customerNumber = editPhone.getText().toString().trim();
		final String msg  = editMsg.getText().toString().trim();
		final String pwd = "test1234";
		canSend = false;
		new SendVoiceMessageTask().execute(pwd,customerNumber,msg); //ִ������������Ĳ���

	}

	private class SendVoiceMessageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			final String pwd = params[0];
			final String customerNumber = params[1];
			final String msg = params[2];
			return sdk.doVoiceMessage(pwd, customerNumber, msg);
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				parseResult(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	// �������������ص�����
	@Override
	protected void parseResult(String content) throws JSONException {
		super.parseResult(content);
		canSend = true;
	}

}
