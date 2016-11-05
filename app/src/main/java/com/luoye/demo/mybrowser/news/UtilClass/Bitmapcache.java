package com.luoye.demo.mybrowser.news.UtilClass;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Luoye on 2016/9/11.
 */
public class Bitmapcache {
    private LruCache<String, Bitmap> lruCache;
    private static Bitmapcache bitmapcache = null;
    private static Bitmapcache headbitmapcache = null;
    private Bitmapcache() {
        Runtime runtime = Runtime.getRuntime();
        int maxram = (int)(runtime.maxMemory() / 2);
        UtilLog.setlog("maxram"+runtime.maxMemory()+"totalram"+runtime.totalMemory()+"freeram"+runtime.freeMemory());
        lruCache = new LruCache<String,Bitmap>(maxram) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    public void addBitmapcache(String bitmapurl, Bitmap bitmap) {
        if (needBitmapcache(bitmapurl))
        lruCache.put(bitmapurl, bitmap);
    }

    public Bitmap getBitmapcache(String bitmapurl) {
        return lruCache.get(bitmapurl);
    }

    private boolean needBitmapcache(String bitmapurl) {
        if (lruCache.get(bitmapurl)==null)
            return true;//不存在缓存返回true
        else return false;
    }

    public static Bitmapcache getBitmapcache() {
        if (bitmapcache ==null)
            bitmapcache = new Bitmapcache();
        return bitmapcache;
    }

    public static Bitmapcache getheadBitmapcache() {
        if (headbitmapcache ==null)
            headbitmapcache = new Bitmapcache();
        return headbitmapcache;
    }
}
