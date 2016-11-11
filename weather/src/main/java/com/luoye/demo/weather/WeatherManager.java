package com.luoye.demo.weather;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Luoye on 2016/11/7.
 */

public class WeatherManager implements WeatherApi{
    private OkHttpClient okHttpClient;
    private String path = "http://apis.baidu.com/apistore/weatherservice/cityname/";
    private static WeatherManager netmanager ;
    private WeatherApi weatherApi;

    private WeatherManager() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        initretrofit();
    }

    public static WeatherManager getinstance() {
        if (netmanager==null){
            netmanager = new WeatherManager();
        }
        return netmanager;
    }

    private void initretrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        weatherApi = retrofit.create(WeatherApi.class);
    }

    @Override
    public Call<WeatherInfo> getData(String name) {
        return weatherApi.getData(name);
    }
}
