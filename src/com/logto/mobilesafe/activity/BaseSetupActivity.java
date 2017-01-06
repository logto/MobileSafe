package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.logto.mobilesafe.R;

/*
 * 基类
 */
public abstract class BaseSetupActivity extends Activity {
	private GestureDetector detector;
	protected SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base_setup);
		initView();
	}

	private void initView() {
		//初始化手势识别器
		detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				//屏蔽斜滑造成的效果
				if(Math.abs(e1.getY()-e2.getY())>50){
					return true;
				}
				
				//屏蔽滑动慢：速度单位--像素/秒
				if(Math.abs(velocityX)<100){
					return true;
				}
				
				if(e2.getX()-e1.getX()>200){
					//显示上一个页面
					enterPrePage();
				}
				if(e1.getX()-e2.getX()>200){
					//显示下一个页面
					enterNextPage();
				}
				return true;
			}

		});
		
		//初始化sp
		sp = getSharedPreferences("config",	MODE_PRIVATE);
	}

	//使用手势识别器
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/*
	 * 显示下一个页面的抽象方法
	 */
	public abstract void enterNextPage();

	/*
	 * 进入上一个页面的抽象方法
	 */
	public abstract void enterPrePage();
	
}
