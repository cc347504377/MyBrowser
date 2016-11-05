package com.luoye.demo.mybrowser.news.UtilClass;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by Luoye on 2016/9/14.
 */

public class UtilPublic {
    public static Bitmap getbitmapbysize(Bitmap bitmap,Context context,int size){
        int h = getheight(context);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,h/size/5*7, h/size);
        return bitmap;
    }
    public static Bitmap getheadbitmapbysize(Bitmap bitmap,Context context,int size){
        int h = getheight(context);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap,h/size/9*16, h/size);
        return bitmap;
    }
    private static int getheight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }
}
