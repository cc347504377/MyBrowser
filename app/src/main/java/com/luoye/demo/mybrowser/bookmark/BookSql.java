package com.luoye.demo.mybrowser.bookmark;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.luoye.demo.mybrowser.Myapplication.context;

/**
 * Created by Luoye on 2016/11/13.
 */

public class BookSql extends SQLiteOpenHelper {
    public static final String BOOKNAME = "bookmark";
    public static final String COLUMNNAME = "book";
    public BookSql(Context contexty) {
        super(context, "book.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists "+BOOKNAME+"(id integer primary key autoincrement,"
                +COLUMNNAME+" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
