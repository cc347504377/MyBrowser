package com.luoye.demo.mybrowser.news.UtilClass;

import android.util.Log;

/**
 * Created by Luoye on 2016/8/31.
 */
public class UtilLog {
    public static boolean IsLog = false;
    public static void setlog(String log) {
        if (IsLog)
        Log.i("xnews", log);
    }
}
