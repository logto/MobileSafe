package com.logto.mobilesafe.view;

import com.logto.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class SettingItemView extends RelativeLayout{
	//��Ҫ������ʽʱʹ��
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	//�ڲ����ļ�ʵ����ʱʹ��
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
	
	//������ʵ����ʱʹ��
	public SettingItemView(Context context) {
		super(context);
		initView(context);
	}
	
	/**
	 * ��ʼ�������ļ�
	 */
	private void initView(Context context) {
		//infalte���������ã��Ѳ����ļ�-->view
		//root:���˭������˭����R.layout.setting_item_view�ĸ��ף�Ҳ����˵���Ѵ˲����ļ������ڴ�������������ؼ���
		View.inflate(context, R.layout.setting_item_view, SettingItemView.this);
		
	}
	

}
