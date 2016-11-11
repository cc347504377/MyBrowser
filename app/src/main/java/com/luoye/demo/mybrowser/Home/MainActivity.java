package com.luoye.demo.mybrowser.Home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.luoye.demo.map.Getlocation;
import com.luoye.demo.map.Utils;
import com.luoye.demo.mybrowser.Myapplication;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.netdemo.view.PictureActivity;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.web.Webviewactivity;
import com.luoye.demo.weather.WeatherInfo;
import com.luoye.demo.weather.WeatherPresenter;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<WeatherInfo> {
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.weather)
    TextView weather;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.weather_img)
    ImageView weatherImg;
    @BindView(R.id.edit_query)
    EditText editQuery;
    private int i = 0;
    private Intent intent;
    private Unbinder bind;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    WeatherPresenter.getinstance().getweather(MainActivity.this, (String) msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
        Message message = Message.obtain();
        message.what = 1;
        message.obj = "成都";
        handler.sendMessage(message);
        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!"".equals(s.toString())) {
                    intent = new Intent(MainActivity.this, Webviewactivity.class);
                    intent.putExtra("url", s.toString());
                    editQuery.setText("");
                    startActivity(intent);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        initlocation();
    }

    private void initlocation() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Getlocation getlocation = new Getlocation(MainActivity.this);
                getlocation.getlocation(new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(AMapLocation aMapLocation) {
                        if (null != aMapLocation) {
                            String log = Utils.getLocationStr(aMapLocation);
                            UtilLog.setlog(log);
                            if (aMapLocation.getErrorCode() == 0) {
                                String city = aMapLocation.getCity();
                                //请求天气
                                Message message = Message.obtain();
                                message.what = 1;
                                message.obj = city;
                                handler.sendMessage(message);
                            } else UtilLog.setlog("定位失败");

                        }
                    }
                });
            }
        }).start();
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

    @OnClick({R.id.back, R.id.forward, R.id.more, R.id.home, R.id.multiwindow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Toast.makeText(this, "没有啦", Toast.LENGTH_SHORT).show();
                break;
            case R.id.forward:
                break;
            case R.id.more:
                break;
            case R.id.home:
                sendBroadcast(new Intent("can"));
                break;
            case R.id.multiwindow:
                break;
        }
    }

    @OnClick({R.id.baidu, R.id.taobao, R.id.zhongguanchun, R.id.meinv, R.id.xingzuo, R.id.QRcode})
    public void onClickaction(View view) {

        switch (view.getId()) {
            case R.id.baidu:
                intent = new Intent(MainActivity.this, Webviewactivity.class);
                intent.putExtra("url", "www.baidu.com");
                startActivity(intent);
                break;
            case R.id.taobao:
                intent = new Intent(MainActivity.this, Webviewactivity.class);
                intent.putExtra("url", "www.taobao.com");
                startActivity(intent);
                break;
            case R.id.zhongguanchun:
                intent = new Intent(MainActivity.this, Webviewactivity.class);
                intent.putExtra("url", "www.zol.com.cn");
                startActivity(intent);
                break;
            case R.id.meinv:
                startActivity(new Intent(MainActivity.this, PictureActivity.class));
                break;
            case R.id.xingzuo:
                intent = new Intent(MainActivity.this, Webviewactivity.class);
                intent.putExtra("url", "http://astro.sina.cn/fortune/starent");
                startActivity(intent);
                break;
            case R.id.QRcode:
                intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UtilLog.setlog(resultCode + "" + requestCode);
        if (requestCode == 1 && resultCode == -1) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                intent = new Intent(MainActivity.this, Webviewactivity.class);
                intent.putExtra("url", bundle.getString("result"));
            }

            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        //解绑
        bind.unbind();
        unregisterReceiver(Myapplication.canscroll);
        super.onDestroy();
    }

    //接受天气成功
    @Override
    public void onResponse(Call<WeatherInfo> call, Response<WeatherInfo> response) {
        if (response.body().getRetData() != null) {
            UtilLog.setlog(response.body().getRetData().toString());
            WeatherInfo.RetDataBean data = response.body().getRetData();
            initweather(data);
        }

    }

    private void initweather(WeatherInfo.RetDataBean data) {
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

    @Override
    public void onFailure(Call<WeatherInfo> call, Throwable t) {
        Toast.makeText(this, "获取天气失败", Toast.LENGTH_SHORT).show();
    }

}
