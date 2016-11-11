package com.luoye.demo.mybrowser.news.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.luoye.demo.mybrowser.Myapplication;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.Bitmapcache;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.news.UtilClass.UtilPublic;
import com.luoye.demo.mybrowser.news.info.Been;
import com.luoye.demo.mybrowser.news.info.ContentlistBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Luoye on 2016/9/13.
 */
public class VolleyHttp {
    private static VolleyHttp volleyHttp=null;
    private JsonCallback jsonCallback = null;
    private BitmapCallback bitmapCallback = null;
    private Context context;
    public interface JsonCallback{
        public void onResponse(List<ContentlistBean> datas);
        public void onError();
    }
    public interface BitmapCallback{
        public void onResponse(Bitmap bitmap);
        public void onError();
    }

    private VolleyHttp() {

    }

    public void SetJsonCallback(JsonCallback callback) {
        this.jsonCallback = callback;
    }

    public void SetBitmapCallback(BitmapCallback bitmapCallback) {
        this.bitmapCallback = bitmapCallback;
    }

    public static VolleyHttp getinstance() {
            volleyHttp = new VolleyHttp();
        return volleyHttp;
    }


    public void getJson(String url) {
        UtilLog.setlog("start get json");
        RequestQueue queue = Myapplication.requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                UtilLog.setlog(s);//打印Json
                Been been = null;
                try{
                    been = Myapplication.gson.fromJson(s, Been.class);
                }catch (RuntimeException e){
                    Toast.makeText(Myapplication.context, "Json解析失败", Toast.LENGTH_SHORT).show();
                }

//                List<Jsoninfo> datas = infos.getNewslist();
                List<ContentlistBean> datas = null;
                if (been==null){
                    return;
                }
                if (been.getShowapi_res_body().getPagebean().getContentlist()!=null){
                    datas = been.getShowapi_res_body().getPagebean().getContentlist();
                }
                jsonCallback.onResponse(datas);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                jsonCallback.onError();
                UtilLog.setlog(volleyError.toString());
                volleyError.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("apikey", "91b479a879d0fb1fbcd43418e21c0348");
                return map;
            }

        };
        stringRequest.setTag(url);
        queue.add(stringRequest);
    }

    public void getimage(final String imgurl, final ImageView imageView, final Context context) {
        this.context = context;
        final String tag = (String) imageView.getTag();
        RequestQueue queue = Myapplication.requestQueue;
        final ImageRequest imageRequest = new ImageRequest(imgurl, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {
                if (tag.equals((String)imageView.getTag())) {
                    bitmap = UtilPublic.getbitmapbysize(bitmap,context,7);
                    imageView.setImageBitmap(bitmap);
                    Bitmapcache.getBitmapcache().addBitmapcache(imgurl,bitmap);
                }
            }
        },0,0,Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.loadingfail);
                Bitmap bit = UtilPublic.getbitmapbysize(bd.getBitmap(), context, 7);
                Bitmapcache.getBitmapcache().addBitmapcache(imgurl, bit);
                imageView.setImageBitmap(bit);
                volleyError.printStackTrace();
//                bitmapCallback.onError();
            }
        });
        imageRequest.setTag(imgurl);
        queue.add(imageRequest);
    }
    public void getheadimage(final String imgurl, final ImageView imageView, final Context context) {
        this.context = context;
        RequestQueue queue = Myapplication.requestQueue;
        Bitmap bitmap = null;
        bitmap = Bitmapcache.getheadBitmapcache().getBitmapcache(imgurl);
        if (bitmap==null){
            final ImageRequest imageRequest = new ImageRequest(imgurl, new Response.Listener<Bitmap>() {
                public void onResponse(Bitmap bitmap) {
                    bitmap = UtilPublic.getheadbitmapbysize(bitmap, context,4);
                    Drawable db = new BitmapDrawable(bitmap);
                    imageView.setImageDrawable(db);
                    Bitmapcache.getheadBitmapcache().addBitmapcache(imgurl, bitmap);
                }
            }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError volleyError) {
                    BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.loadingfail);
                    Bitmap bit = UtilPublic.getheadbitmapbysize(bd.getBitmap(), context, 4);
                    Bitmapcache.getheadBitmapcache().addBitmapcache(imgurl, bit);
                    Drawable db = new BitmapDrawable(bit);
                    imageView.setBackground(db);
                    volleyError.printStackTrace();
                }
            });
            imageRequest.setTag(imgurl);
            queue.add(imageRequest);
        } else {
            Drawable db = new BitmapDrawable(bitmap);
            imageView.setImageDrawable(db);
        }

    }

}
