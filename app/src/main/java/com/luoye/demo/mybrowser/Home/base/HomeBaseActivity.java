package com.luoye.demo.mybrowser.Home.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luoye.demo.mybrowser.Home.Customerview.MybottomBar;
import com.luoye.demo.mybrowser.Home.MainActivity;
import com.luoye.demo.mybrowser.Myapplication;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.web.Webviewactivity;
import com.luoye.demo.weather.WeatherInfo;
import com.luoye.demo.weather.WeatherPresenter;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
// TODO: 2016/11/15 HomeActivity基类

public abstract class HomeBaseActivity extends AppCompatActivity implements Callback<WeatherInfo> {
    @BindView(R.id.temp)
    protected TextView temp;
    @BindView(R.id.weather)
    protected TextView weather;
    @BindView(R.id.city)
    protected TextView city;
    @BindView(R.id.weather_img)
    protected ImageView weatherImg;
    @BindView(R.id.edit_query)
    protected EditText editQuery;
    @BindView(R.id.collect)
    protected TextView collect;
    @BindView(R.id.refresh)
    protected TextView refresh;
    @BindView(R.id.bottom_bar)
    protected MybottomBar bottomBar;
    private Unbinder bind;
    protected Intent intent;
    private int i = 0;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    WeatherPresenter.getinstance().getweather(HomeBaseActivity.this, (String) msg.obj);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        afterCreate();
    }

    protected abstract void afterCreate();

    @Override
    protected void onDestroy() {
        bind.unbind();
        unregisterReceiver(Myapplication.canscroll);
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            i++;
            if (i >= 2) {
                finish();
            } else {
                Toast.makeText(this, "再此点击退出应用", Toast.LENGTH_SHORT).show();
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    i = 0;
                }
            }, 1500);
        }
        return false;
    }

    protected void initeditorlistener() {
        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!"".equals(s.toString())) {
                    intent = new Intent(HomeBaseActivity.this, Webviewactivity.class);
                    intent.putExtra("url", s.toString());
                    editQuery.setText("");
                    startActivity(intent);
                    // TODO: 2016/11/15 独立检索页面
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    protected void initlocation() {
        // TODO: 2016/11/3 初始化高德组件
//                Getlocation getlocation = new Getlocation(MainActivity.this);
//                getlocation.getlocation(new AMapLocationListener() {
//                    @Override
//                    public void onLocationChanged(AMapLocation aMapLocation) {
//                        if (null != aMapLocation) {
//                            String log = Utils.getLocationStr(aMapLocation);
//                            UtilLog.setlog(log);
//                            if (aMapLocation.getErrorCode() == 0) {
//                                String city = aMapLocation.getCity();
//                                //请求天气
//                                Message message = Message.obtain();
//                                message.what = 1;
//                                message.obj = city;
//                                handler.sendMessage(message);
//                            } else UtilLog.setlog("定位失败");
//
//                        }
//                    }
//                });
        Message message = Message.obtain();
        message.what = 1;
        message.obj = "成都";
        handler.sendMessage(message);
    }


    protected void initweather(WeatherInfo.RetDataBean data) {
        city.setText(data.getCity());
        weather.setText(data.getWeather());
        temp.setText(data.getTemp());
        String isweather = isweather(data.getWeather());
        if ("晴".equals(isweather)) {
            weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.sun));

        } else if ("云".equals(isweather)) {
            weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.cloud));

        } else if ("雨".equals(isweather)) {
            weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.rain_128));

        } else if ("雪".equals(isweather)) {
            weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.snow));

        } else if ("雷".equals(isweather)) {
            weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.lightning));

        } else if ("风".equals(isweather)) {
            weatherImg.setImageDrawable(getResources().getDrawable(R.drawable.wind));

        }
    }

    private String isweather(String string) {
        for (int j = 0; j < string.length(); j++) {
            String c = String.valueOf(string.charAt(j));
            if ("晴".equals(c))
                return "晴";
            if ("云".equals(c))
                return "云";
            if ("雨".equals(c))
                return "雨";
            if ("雪".equals(c))
                return "雪";
            if ("雷".equals(c))
                return "雷";
        }
        return "风";
    }

    //接收提天气
    @Override
    public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
        if (response.body().getRetData() != null) {
            UtilLog.setlog(response.body().getRetData().toString());
            WeatherInfo.RetDataBean data = response.body().getRetData();
            initweather(data);//更新天气视图
        }

    }

    @Override
    public void onFailure(Call<WeatherInfo> call, Throwable t) {
        Toast.makeText(this, "获取天气失败", Toast.LENGTH_SHORT).show();
    }
}
