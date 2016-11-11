package com.luoye.demo.netdemo;

import android.app.Application;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Created by Luoye on 2016/10/26.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initimageloader();
    }

    private void initimageloader() {
        Runtime runtime = Runtime.getRuntime();
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .imageScaleType(ImageScaleType.NONE)
                .showImageOnFail(getResources().getDrawable(R.drawable.ic_clear_black_24dp))
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
