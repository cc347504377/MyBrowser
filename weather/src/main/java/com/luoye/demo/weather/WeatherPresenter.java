package com.luoye.demo.weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luoye on 2016/11/7.
 */

public class WeatherPresenter {
    private static WeatherPresenter weatherPresenter;
    private WeatherPresenter(){

    }

    public static WeatherPresenter getinstance() {
        if (weatherPresenter == null) {
            weatherPresenter = new WeatherPresenter();
        }
        return weatherPresenter;
    }

    public void getweather(Callback<WeatherInfo> callback,String cityname) {
        Call<WeatherInfo> call = WeatherManager.getinstance().getData(cityname);
        call.enqueue(callback);
    }
}
