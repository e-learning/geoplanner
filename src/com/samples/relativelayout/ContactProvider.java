package com.samples.relativelayout;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

public class ContactProvider extends ContentProvider {
	
    public static final String DB_CONTACTS = "contacts.db";
    
    public static final Uri CONTENT_URI = Uri.parse(
			"content://com.samples.dbcontacts.contactprovider/contact");
    public static final int URI_CODE = 1;
    public static final int URI_CODE_ID = 2;

    private static final UriMatcher mUriMatcher;
    private static HashMap<String, String> mContactMap;
    
    private SQLiteDatabase db;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI("com.samples.dbcontacts.contactprovider", 
                Save_tasks.TABLE_NAME, URI_CODE);
        mUriMatcher.addURI("com.samples.dbcontacts.contactprovider", 
                Save_tasks.TABLE_NAME + "/#", URI_CODE_ID);

        mContactMap = new HashMap<String, String>();
        mContactMap.put(Save_tasks._ID, Save_tasks._ID);
        mContactMap.put(Save_tasks.MY_TASK, Save_tasks.MY_TASK);
        mContactMap.put(Save_tasks.MY_PLACE, Save_tasks.MY_PLACE);
    }

    public String getDbName() {
        return(DB_CONTACTS);
    }
    
    @Override
    public boolean onCreate() {

        db = (new Save_tasks(getContext())).getWritableDatabase();
        return (db == null) ? false : true;
    }
   
    @Override
    public Cursor query(Uri url, String[] projection, 
            String selection, String[] selectionArgs, String sort) {
      
        String orderBy;       
        if (TextUtils.isEmpty(sort)) {
            orderBy = Save_tasks.MY_TASK;
        } 
        else {
            orderBy = sort;
        }

        Cursor c = db.query(Save_tasks.TABLE_NAME, projection, selection, selectionArgs, 
                null, null, orderBy);
        c.setNotificationUri(getContext().getContentResolver(), url);
        return c;
    }

    @Override
    public Uri insert(Uri url, ContentValues inValues) {

        ContentValues values = new ContentValues(inValues);

        long rowId = db.insert(Save_tasks.TABLE_NAME, Save_tasks.MY_TASK, values);
        if (rowId > 0) {
            Uri uri = ContentUris.withAppendedId(CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(uri, null);
            return uri;
        }
        else {
        	throw new SQLException("Failed to insert row into " + url);
        }
    }

    @Override
    public int delete(Uri url, String where, String[] whereArgs) {
        int retVal = db.delete(Save_tasks.TABLE_NAME, where, whereArgs);

        getContext().getContentResolver().notifyChange(url, null);
        return retVal;
    }

    @Override
    public int update(Uri url, ContentValues values, 
            String where, String[] whereArgs) {
        int retVal = db.update(Save_tasks.TABLE_NAME, values, where, whereArgs);
    
        getContext().getContentResolver().notifyChange(url, null);
        return retVal;
    }

    @Override
    public String getType(Uri uri) {       
        return null;
    }  
}