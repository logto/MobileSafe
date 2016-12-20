package com.logto.mobilesafe.view;

import com.logto.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingItemView extends RelativeLayout{
	private CheckBox cb_status;
	private TextView tv_desc;
	//��Ҫ������ʽʱʹ��
	public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	//�ڲ����ļ�ʵ����ʱʹ��
	public SettingItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
		attrs.getAttributeValue("http://schemas.android.com/apk/res/com.logto.mobilesafe",
								"title");
		
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
		View settingView = View.inflate(context, R.layout.setting_item_view, this);
		cb_status = (CheckBox) findViewById(R.id.cb_status);
		tv_desc = (TextView) findViewById(R.id.tv_desc);
	}
	
	/**
	 * �õ���Ͽؼ��Ƿ񱻹�ѡ
	 * @return
	 */
	public boolean isChecked(){
		return  cb_status.isChecked();
 	}
	/**
	 * ���ÿؼ��Ĺ�ѡ״̬
	 */
	public void setChecked(boolean isChecked){
		cb_status.setChecked(isChecked);
	}
	
	/**
	 * ������Ͽؼ��ĵ�״̬��Ϣ
	 * @param description
	 */
	public void setDescription(String description){
		tv_desc.setText(description);
	}
	

	
}
