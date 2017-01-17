package com.logto.mobilesafe.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class BlackContactDBOpenHelper extends SQLiteOpenHelper{
	
	/**构造方法--把数据库已经创建*/
	public BlackContactDBOpenHelper(Context context) {
		super(context, "blackcontact.db",null,1);
	}
	
	/**数据库已经创建后，去创建数据库中的表*/
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table blackcontact(_id integer primary key autoincrement,number varchar(20),mode varchar(2))");
	}

	/**数据库升级的时候，会调用此方法*/
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
