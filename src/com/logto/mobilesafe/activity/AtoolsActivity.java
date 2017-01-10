package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.logto.mobilesafe.R;

public class AtoolsActivity extends Activity {
	private TextView tv_query;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_atools);
		initView();
		setListeners();
	}
	
	private void setListeners() {
		//点击事件---进入号码归宿地查询
		tv_query.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AtoolsActivity.this,NumberAddressQueryActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * 初始化
	 */
	private void initView() {
		tv_query = (TextView) findViewById(R.id.tv_query);
	}

}
