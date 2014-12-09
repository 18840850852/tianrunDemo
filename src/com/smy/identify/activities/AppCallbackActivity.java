package com.smy.identify.activities;

import org.json.JSONException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.smy.identify.R;

public class AppCallbackActivity extends DemoBaseActivity implements
		OnClickListener {

	private EditText editSendPhone; // ͨ�ŷ����˵ĵ绰����ı༭��
	private EditText editRecivePhone; // ͨ�Ž����˵ĵ绰����ı༭��

	private boolean canStartPhone; // �����Ƿ���Է���ͨ�ŵı��

	@Override
	public void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
		setContentView(R.layout.activity_app_callback);

		initViews();
	}

	private void initViews() {
		editSendPhone = (EditText) findViewById(R.id.edit_send_phone);
		editRecivePhone = (EditText) findViewById(R.id.edit_receive_phone);
		findViewById(R.id.bt_start).setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		final String customerNumber = editSendPhone.getText().toString().trim();
		final String transferNumber = editRecivePhone.getText().toString().trim();
		final String pwd = "test1234";
				
		canStartPhone = false;
		new AppCallbackTask().execute(pwd,customerNumber, transferNumber);
	}

	private class AppCallbackTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... params) {
			final String pwd = params[0];
			final String customerNumber = params[1];
			final String transferNumber = params[2];
			return sdk.doAppCallback(pwd,customerNumber, transferNumber);
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
		canStartPhone = true;
	}

}
