package com.luoye.demo.mybrowser.news.UtilClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Luoye on 2016/9/11.
 */
public class UtilBitmap {
    private Bitmapcache bitmapcache;
    public UtilBitmap(String bitmapurl, ImageView imageView) {
        bitmapcache = Bitmapcache.getBitmapcache();
        new Asyncsetimg(imageView).execute(bitmapurl);
    }

    private class Asyncsetimg extends AsyncTask<String, Bitmap, Bitmap> {
        private ImageView imageView;

        public Asyncsetimg(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... params) {
            String bitmapurl = params[0];
            Bitmap bitmap = null;
            bitmap = getBitmapformurl(params[0]);
            bitmapcache.addBitmapcache(bitmapurl, bitmap);
            return bitmap;
        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }

    private Bitmap getBitmapformurl(String bitmapurl){
        HttpURLConnection connection = null;
        BufferedInputStream bfs = null;
        Bitmap bitmap = null;
        try {
            UtilLog.setlog("生成bitmap");
            URL url = new URL(bitmapurl);
            connection = (HttpURLConnection)url.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            bfs = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(bfs);
            bfs.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            UtilLog.setlog("网络连接异常");
            e.printStackTrace();
        } catch (IOException e) {
            UtilLog.setlog("IO异常");
            e.printStackTrace();
        }
        return bitmap;
    }

}
