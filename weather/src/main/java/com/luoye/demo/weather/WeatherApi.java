package com.luoye.demo.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Luoye on 2016/11/7.
 */
 interface WeatherApi {
    @GET("http://apis.baidu.com/apistore/weatherservice/cityname/")
    @Headers("apikey:91b479a879d0fb1fbcd43418e21c0348")
    public Call<WeatherInfo> getData(@Query("cityname") String cityname);

}
