package com.logto.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.TextView;

public class FocusedTextView extends TextView {
	
	//������ʽ��ʱ�򣬻��õ�
	public FocusedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	//��android�У������ļ�ʹ��ĳ���ؼ���Ĭ�ϻ���ô������������Ĺ��췽��
	public FocusedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	//����ʵ������ʱ����õ�
	public FocusedTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *��ǰ����ؼ���һ����ý��㣬ֻ����ƭandroid,��androidϵͳ�Ի�ý���ķ�ʽ��������
	 */
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		return true;
	}
	
}
