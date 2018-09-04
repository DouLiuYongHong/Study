package com.ws.yonghong.doustudy.db.old.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "student";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,age INTEGER)";

    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //构造方法，系统会自动创建数据库文件
    public DbOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    //只有第一次打开数据库的时候调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    //数据库的版本发生变化的时候调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //删除原来的旧表，创建新表
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
