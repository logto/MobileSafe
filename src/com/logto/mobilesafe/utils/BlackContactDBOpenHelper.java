package com.logto.mobilesafe.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackContactDBOpenHelper extends SQLiteOpenHelper{
	
	/**���췽��--�����ݿ��Ѿ�����*/
	public BlackContactDBOpenHelper(Context context) {
		super(context, "blackcontact.db",null,1);
	}
	
	/**���ݿ��Ѿ�������ȥ�������ݿ��еı�*/
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table blackcontact(_id integer primary key autoincrement,number varchar(20),mode varchar(2))");
	}

	/**���ݿ�������ʱ�򣬻���ô˷���*/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
