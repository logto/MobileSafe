package com.logto.mobilesafe.activity;

import com.logto.mobilesafe.R;
import com.logto.mobilesafe.R.layout;
import com.logto.mobilesafe.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MobileSecurityGuideFourActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mobile_security_guide_four);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mobile_security_guide_four, menu);
		return true;
	}

}
