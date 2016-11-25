package com.luoye.demo.mybrowser.bookmark;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.luoye.demo.mybrowser.Myapplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luoye on 2016/11/13.
 */

public class SqlModel {
    private BookSql bookSql;
    private Context context;
    public SqlModel(Context context) {
        bookSql = new BookSql(context);
        this.context = context;
    }

    public void insert(String name,String url) {
        List<Bookinfo> list = getbook();
        for (Bookinfo info : list) {
            if (info.getBookname().equals(name) && info.getBookurl().equals(url)) {
                Toast.makeText(context, "已存在该书签", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        SQLiteDatabase writableDatabase = bookSql.getWritableDatabase();
        String bookstring = Myapplication.gson.toJson(new Bookinfo(name, url));
        writableDatabase.execSQL("insert into bookmark(book) values(?)",
                new String[]{bookstring});
        writableDatabase.close();
    }

    public List<Bookinfo> getbook() {
        SQLiteDatabase readableDatabase = bookSql.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("Select * from bookmark", null);
        List<Bookinfo> datas = null;
        if (cursor != null) {
            datas = new ArrayList<Bookinfo>();
            while (cursor.moveToNext()) {
                String bookstring = cursor.getString(cursor.getColumnIndex("book"));
                Bookinfo bookinfo = Myapplication.gson.fromJson(bookstring, Bookinfo.class);
                datas.add(bookinfo);
            }
            cursor.close();
        }
        readableDatabase.close();
        return datas;
    }

    public void delete(Bookinfo bookinfo) {
        SQLiteDatabase readableDatabase = bookSql.getReadableDatabase();
        String json = Myapplication.gson.toJson(bookinfo);
        Cursor cursor = readableDatabase.rawQuery("Delete from bookmark where book like ?", new String[]{json});
        cursor.close();
        readableDatabase.close();
    }
}
