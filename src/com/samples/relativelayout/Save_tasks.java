package com.samples.relativelayout;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.content.ContentProvider;
import android.content.ContentUris;


import android.content.UriMatcher;

import android.database.SQLException;

import android.net.Uri;
import android.text.TextUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
public class Save_tasks extends SQLiteOpenHelper implements BaseColumns{
	private Cursor mycursor;
	//public Save_tasks(Context context, String name, CursorFactory factory,
			//int version) {
		//super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	//}
	public Save_tasks(Context context){
		super(context, null, null, 0);
	}
	public static final String TABLE_NAME="tasks";
	public static final String MY_TASK="name of task";
	public static final String MY_PLACE="name of place";
	
	@Override
	public void onCreate(SQLiteDatabase db){
		db.execSQL("CREATE TABLE"+TABLE_NAME+" (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
	    MY_TASK+"TEXT,"+MY_PLACE+"TEXT);");
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
		onCreate(db);
	}
	
	
}

