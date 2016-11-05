package com.luoye.demo.mybrowser;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.news.UtilClass.UtilSharep;

/**
 * Created by Luoye on 2016/9/13.
 */
public class Myapplication extends Application {
    public static RequestQueue requestQueue=null ;
    public static Gson gson = null;
    public static UtilSharep sp = null;
    public static Context context = null;
    @Override
    public void onCreate() {
        UtilLog.IsLog = true;
        UtilLog.setlog("Myapplication oncreate "+"log"+UtilLog.IsLog);
        gson = new Gson();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sp = new UtilSharep(getApplicationContext());
        context = getApplicationContext();
        super.onCreate();
    }
}
