package com.ws.yonghong.doustudy.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ws.yonghong.doustudy.R;
import com.ws.yonghong.doustudy.View.RipplesView;
import com.ws.yonghong.doustudy.db.old.helper.DbOpenHelper;
import com.ws.yonghong.doustudy.task.AbsTask;
import com.ws.yonghong.doustudy.task.TaskManager;
import com.ws.yonghong.doustudy.utilcode.util.LogUtils;

public class DbManagerActivity extends AppCompatActivity {

    private TextView tv_show_log;
    private DbOpenHelper mDbOpenHelper;
    private SQLiteDatabase db;
    private Cursor mCursor;
    private static final String TABLE_NAME = "student";
    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
            "(_id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,age INTEGER)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);
        tv_show_log = findViewById(R.id.tv_show_log);
        mDbOpenHelper = new DbOpenHelper(this, "test.db", 1);
        db = mDbOpenHelper.getWritableDatabase();
        mCursor = db.rawQuery("select * from " + TABLE_NAME, null);
    }

    public void create2(View v) {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void insert2(View v) {


    }

    public void delete2(View v) {

    }

    public void update2(View v) {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void query2(View v) {

    }

    public void create(View v) {
        try {

        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void insert(View v) {
        ContentValues values = new ContentValues();
        values.put("name", "张三");
        values.put("age", 18);
        /**
         *插入数据
         * 参数一：要插入的表名
         * 参数二：要插入的空数据所在的行数，第三个参数部位空，则此参数为null
         * 参数三：要插入的数据
         */
        if (!db.isOpen()) {
            db = mDbOpenHelper.getWritableDatabase();
        }
        db.insert(TABLE_NAME, null, values);
        ContentValues values1 = new ContentValues();
        values1.put("name", "李四");
        values1.put("age", 21);
        db.insert(TABLE_NAME, null, values1);
        db.close();

    }

    public void delete(View v) {
        if (!db.isOpen()) {
            db = mDbOpenHelper.getWritableDatabase();
        }
//要删除的数据
        db.delete(TABLE_NAME, "name = ?", new String[]{"李四"});
        db.close();
    }

    public void update(View v) {
        ContentValues values = new ContentValues();
        values.put("name", "王五王五");
        values.put("age", 43);
        /**
         * 数据的更新
         * 参数一：要更新的数据所在的表名
         * 参数二：新的数据
         * 参数三：要更新数据的查找条件
         * 参数四：条件的参数
         */
        try {
            if (!db.isOpen()) {
                db = mDbOpenHelper.getWritableDatabase();
            }
            db.update(TABLE_NAME, values, "name = ? ", new String[]{"张三"});
            db.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void query(View v) {
        if (!db.isOpen()) {
            db = mDbOpenHelper.getWritableDatabase();
        }
        StringBuffer mStringBuffer = new StringBuffer();
        //查询部分数据
        Cursor cursor = db.rawQuery("select * from " + TABLE_NAME + " where name=? ", new String[]{"张三"});
        //查询全部数据
        Cursor cursor1 = db.rawQuery("select * from " + TABLE_NAME, null);
        //将游标移到第一行
        cursor1.moveToFirst();
        //循环读取数据
        while (!cursor1.isAfterLast()) {
            //获得当前行的标签
            int nameIndex = cursor1.getColumnIndex("name");
            //获得对应的数据
            String name = cursor1.getString(nameIndex);
            int ageIndex = cursor1.getColumnIndex("age");
            int age = cursor1.getInt(ageIndex);
            mStringBuffer.append(nameIndex);
            mStringBuffer.append(" || ");
            mStringBuffer.append(name);
            mStringBuffer.append(" || ");
            mStringBuffer.append(ageIndex);
            mStringBuffer.append(" || ");
            mStringBuffer.append(age);
            mStringBuffer.append(" \n ");
            //游标移到下一行
            cursor1.moveToNext();
        }
        tv_show_log.setText(mStringBuffer.toString());
        db.close();
    }

    /**
     * 判断数据库中的某个表是否存在
     *
     * @param tableName
     * @return
     */
    private boolean isTableIsExist(String tableName) {
        boolean result = false;
        if (TextUtils.isEmpty(tableName)) {
            return false;
        }
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from Sqlite_master  where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {

        }
        return result;
    }
}
