package com.smy.identify.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.smy.identify.R;
import com.smy.identify.common.CommonTools;
import com.smy.identify.common.SecreteUtil;

public class MainActivity extends Activity implements OnItemClickListener {

	private ListView lvFunc; //��ʾ���ܵ��б�
	private String[] funcs; //���еĹ���
	
	//Ҫչʾ�Ĺ��ܽ����б��ж��⹦�ܼ��ϵĻ�ҲҪ���������
	@SuppressWarnings("unchecked")
	final Class<? extends DemoBaseActivity>[] activiies = (Class<? extends DemoBaseActivity>[])new Class[]{
			VoiceVertifyActivity.class,AppCallbackActivity.class,VoiceMessageActivity.class
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initViews();
	}

	//��ʼ��view
	private void initViews() {
		lvFunc = (ListView) findViewById(R.id.lv_funcs);
		funcs = getResources().getStringArray(R.array.funcs); //��������Դ����values/strings��
		ArrayAdapter<String> funcAdapter = new ArrayAdapter<String>(getApplicationContext(), 
				R.layout.tv_item,funcs);
		lvFunc.setAdapter(funcAdapter); //��������Դ
		lvFunc.setOnItemClickListener(this);  //���ü�����
	}
	
	//��Ӧ�б�ĵ���¼�����ת����Ӧ�Ĺ���ʵ��������ȥ
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg2<activiies.length){
			startActivity(new Intent(MainActivity.this,activiies[arg2]));
		}else{
			CommonTools.showMessage(MainActivity.this.getApplicationContext(), "�ù��ܻ�δ����");
		}
	}

}
