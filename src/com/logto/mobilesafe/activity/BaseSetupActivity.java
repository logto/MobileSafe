package com.logto.mobilesafe.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.logto.mobilesafe.R;

/*
 * ����
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
		//��ʼ������ʶ����
		detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				//����б����ɵ�Ч��
				if(Math.abs(e1.getY()-e2.getY())>50){
					return true;
				}
				
				//���λ��������ٶȵ�λ--����/��
				if(Math.abs(velocityX)<100){
					return true;
				}
				
				if(e2.getX()-e1.getX()>200){
					//��ʾ��һ��ҳ��
					enterPrePage();
				}
				if(e1.getX()-e2.getX()>200){
					//��ʾ��һ��ҳ��
					enterNextPage();
				}
				return true;
			}

		});
		
		//��ʼ��sp
		sp = getSharedPreferences("config",	MODE_PRIVATE);
	}

	//ʹ������ʶ����
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/*
	 * ��ʾ��һ��ҳ��ĳ��󷽷�
	 */
	public abstract void enterNextPage();

	/*
	 * ������һ��ҳ��ĳ��󷽷�
	 */
	public abstract void enterPrePage();
	
}
