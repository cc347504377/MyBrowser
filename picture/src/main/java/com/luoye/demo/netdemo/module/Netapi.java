package com.luoye.demo.netdemo.module;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by Luoye on 2016/10/27.
 */

public interface Netapi {
    @Headers("apikey:91b479a879d0fb1fbcd43418e21c0348")
    @GET("?num=30")
    public Call<Datainfo> getData();
}
