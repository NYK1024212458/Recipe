package com.ben.recipe.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MultiTableHelper extends SQLiteOpenHelper {

    private String[] tableNames;
    private String[] sqls;

    /**
     * 初始化构造函数
     *
     * @param context
     * @param name    数据库名
     * @param factory 游标工厂（基本不用）
     * @param version 版本号
     */
    public MultiTableHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MultiTableHelper(Context context, String name, int version, String[] tableNames, String[] sqls) {
        super(context, name, null, version);
        this.tableNames = tableNames;
        this.sqls = sqls;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(db!=null){
            for(int i = 0 ; i < tableNames.length ; i++){
                Log.d(TAG, tableNames[i]);
                Log.d(TAG, sqls[i]);
                db.execSQL("create table if not exists "+tableNames[i]+sqls[i]);
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "oldVersion=" + oldVersion);
        Log.d(TAG, "newVersion=" + newVersion);
        if (db != null) {
            // 如果表存在就删除
            db.execSQL("drop table if exists" + tableNames);
            // 重新初始化
            onCreate(db);
        }
    }
}
