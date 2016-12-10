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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luoye.demo.mybrowser.Home.Customerview.MyScrollview;
import com.luoye.demo.mybrowser.Home.Customerview.MybottomBar;
import com.luoye.demo.mybrowser.Home.base.HomeBaseActivity;
import com.luoye.demo.mybrowser.Myapplication;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.bookmark.BookActivity;
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

public class MainActivity extends HomeBaseActivity {


    private Unbinder bind;
    @Override
    protected void afterCreate() {
        initlocation();
        initeditorlistener();
        bind = ButterKnife.bind(this);
        collect.setEnabled(false);
        refresh.setEnabled(false);
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
                bottomBar.showmenu();
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

    //监听扫描二维码
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



    @OnClick({R.id.bookmarks, R.id.download, R.id.collect, R.id.refresh, R.id.menu, R.id.exit})
    public void onClickmenu(View view) {
        switch (view.getId()) {
            case R.id.bookmarks:
                startActivity(new Intent(MainActivity.this, BookActivity.class));
                break;
            case R.id.download:
                // TODO: 2016/11/13 下载管理
                break;
            case R.id.collect:
                // TODO: 2016/11/13 添加到收藏夹
                break;
            case R.id.refresh:
                break;
            case R.id.menu:
                //// TODO: 2016/11/13 高级菜单
                break;
            case R.id.exit:
                finish();
                break;
        }
    }


}
