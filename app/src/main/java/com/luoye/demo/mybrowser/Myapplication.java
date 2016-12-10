package com.luoye.demo.mybrowser;

import android.app.Application;
import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.luoye.demo.mybrowser.Home.Customerview.MyScrollview;
import com.luoye.demo.mybrowser.database.SqlModel;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.news.UtilClass.UtilSharep;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by Luoye on 2016/9/13.
 */
public class Myapplication extends Application {
    public static RequestQueue requestQueue=null ;
    public static Gson gson = null;
    public static UtilSharep sp = null;
    public static Context context = null;
    public static MyScrollview.Canscroll canscroll;
    public static SqlModel sqlmodel = null;
    @Override
    public void onCreate() {
        //初始化Log工具类
        initUtils();
        //初始化Image-loader
        initimageloader();
        super.onCreate();
    }

    private void initUtils() {
        //log工具类
        UtilLog.IsLog = true;
        UtilLog.setlog("Myapplication oncreate "+"log"+UtilLog.IsLog);
        //Gson
        gson = new Gson();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //Shareprefence工具类
        sp = new UtilSharep(getApplicationContext());
        context = getApplicationContext();
        //数据库
        sqlmodel = new SqlModel(getApplicationContext());
    }

    private void initimageloader() {
        Runtime runtime = Runtime.getRuntime();
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.NONE)
                .showImageOnFail(getResources().getDrawable(R.drawable.loadingfail))
                .showImageOnLoading(getResources().getDrawable(R.drawable.img_news_loding))
                .build();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(options)
                .memoryCacheSize(new Long(runtime.maxMemory()/2).intValue())
                .diskCacheSize(1024*1024*30)
                .threadPoolSize(3)
                .writeDebugLogs()
                .build();
        imageLoader.init(configuration);
    }
}
