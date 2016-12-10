package com.luoye.demo.mybrowser.history;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Luoye on 2016/11/26.
 */

public class HisSql extends SQLiteOpenHelper {
    public static final String HISTORYNAME = "history";
    public static final String COLUMNNAME = "his";

    public HisSql(Context context) {
        super(context, "his.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if exists "+HISTORYNAME+"(id integer primary key autoincrement," +
                COLUMNNAME+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
