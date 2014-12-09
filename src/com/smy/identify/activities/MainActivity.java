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

	private ListView lvFunc; //显示功能的列表
	private String[] funcs; //所有的功能
	
	//要展示的功能界面列表，有额外功能加上的话也要在这里加上
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

	//初始化view
	private void initViews() {
		lvFunc = (ListView) findViewById(R.id.lv_funcs);
		funcs = getResources().getStringArray(R.array.funcs); //加载数据源，在values/strings中
		ArrayAdapter<String> funcAdapter = new ArrayAdapter<String>(getApplicationContext(), 
				R.layout.tv_item,funcs);
		lvFunc.setAdapter(funcAdapter); //设置数据源
		lvFunc.setOnItemClickListener(this);  //设置监听器
	}
	
	//响应列表的点击事件，跳转到对应的功能实例界面中去
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if(arg2<activiies.length){
			startActivity(new Intent(MainActivity.this,activiies[arg2]));
		}else{
			CommonTools.showMessage(MainActivity.this.getApplicationContext(), "该功能还未开放");
		}
	}

}
