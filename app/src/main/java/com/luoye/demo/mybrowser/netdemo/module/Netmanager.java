package com.luoye.demo.mybrowser.netdemo.module;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Luoye on 2016/10/27.
 */

public class Netmanager implements Netapi{
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private String path = "http://apis.baidu.com/txapi/mvtp/meinv/";
    private Netapi netapi;
    private static Netmanager netmanager ;
    private Netmanager() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        initretrofit();
    }

    public static Netmanager getinstance() {
        if (netmanager==null){
            netmanager = new Netmanager();
        }
        return netmanager;
    }

    private void initretrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        netapi = retrofit.create(Netapi.class);
    }

    @Override
    public Call<Datainfo> getData() {
        return netapi.getData();
    }
}
