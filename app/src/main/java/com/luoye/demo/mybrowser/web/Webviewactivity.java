package com.luoye.demo.mybrowser.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.luoye.demo.mybrowser.Home.Customerview.MybottomBar;
import com.luoye.demo.mybrowser.Myapplication;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.bookmark.BookActivity;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.web.module.WebManager;
import com.luoye.demo.mybrowser.web.view.Mywebview;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Webviewactivity extends AppCompatActivity {

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.edit_query)
    EditText editQuery;
    @BindView(R.id.QRcode)
    ImageButton QRcode;
    @BindView(R.id.action_bar)
    LinearLayout actionBar;
    @BindView(R.id.bookmarks)
    TextView bookmarks;
    @BindView(R.id.download)
    TextView download;
    @BindView(R.id.collect)
    TextView collect;
    @BindView(R.id.refresh)
    TextView refresh;
    @BindView(R.id.menu)
    TextView menu;
    @BindView(R.id.exit)
    TextView exit;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.forward)
    ImageButton forward;
    @BindView(R.id.more)
    ImageButton more;
    @BindView(R.id.home)
    ImageButton home;
    @BindView(R.id.multiwindow)
    ImageButton multiwindow;
    @BindView(R.id.bottom_bar)
    MybottomBar bottomBar;
    @BindView(R.id.activity_main)
    RelativeLayout activityMain;
    //// TODO: 2016/11/15 浏览历史的保存
    private String Path;
    @BindView(R.id.webview)
    Mywebview webview;
    public boolean isover;
    private GestureDetector detector;//监听器
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String url = getIntent().getStringExtra("url");
        bind = ButterKnife.bind(this);
        detector = new GestureDetector(this, new MyGestureListener());
        iniwebview();
        input(url);
        editQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                input(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void iniwebview() {
        WebManager.getinstance().webviewconfig(webview, pb, this);
    }

    @OnClick({R.id.back, R.id.forward, R.id.more, R.id.home, R.id.multiwindow, R.id.QRcode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                goback();
                break;
            case R.id.forward:
                goforward();
                break;
            case R.id.more:
                bottomBar.showmenu();
                break;
            case R.id.home:
                gotohome();
                break;
            case R.id.multiwindow:
                //多窗口
                Toast.makeText(this, "还没有实现，敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.QRcode:
                startActivityForResult(new Intent(Webviewactivity.this, CaptureActivity.class), 2);
                break;
        }
    }

    private void gotohome() {
        sendBroadcast(new Intent("can"));
        finish();
    }

    @OnClick({R.id.bookmarks, R.id.download, R.id.collect, R.id.refresh, R.id.menu, R.id.exit})
    public void onClickmewu(View view) {
        switch (view.getId()) {
            case R.id.bookmarks:
                startActivity(new Intent(Webviewactivity.this, BookActivity.class));
                break;
            case R.id.download:
                break;
            case R.id.collect:
                Myapplication.sqlmodel.insert(webview.getTitle(), webview.getUrl());
                break;
            case R.id.refresh:
                break;
            case R.id.menu:
                break;
            case R.id.exit:
                break;
        }
    }

    //搜索判断
    private void input(String url) {
        if (url != null && !" ".equals(url.trim()) && url.length() != 0) {
            if (url.startsWith("http")) {
                webview.loadUrl(url);
            } else if (url.startsWith("www") || url.endsWith("com") || url.endsWith("net") || url.endsWith("cn")) {
                webview.loadUrl("http://" + url);
            } else {
                webview.loadUrl("https://m.baidu.com/ssid=f917434357303035346100/s?word=" + url + "&ts=7955729&t_kt=0&ie=utf-8&rsv_iqid=17625304894037028313&rsv_t=7c81vcmfIMzZSWpokwcO5HZg5Xz%252BnAnREtj4mObSjsDRyhxIhNJ8&sa=ib&rsv_pq=17625304894037028313&rsv_sug4=7250&inputT=2431&ss=100");
            }
        }

        //function.inhistory(MainActivity.this, titile, lasturl);
    }

    /*
    *
    手势监听配置
    *
     */
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            if (e2.getX() - e1.getX() > 300 && Math.abs(velocityX) >
                    200 && Math.abs(e2.getY() - e1.getY()) < 250 && isover) {
                goback();
            } else if (e1.getX() - e2.getX() > 300 && Math.abs(velocityX) >
                    200 && Math.abs(e2.getY() - e1.getY()) < 250 && isover) {
                goforward();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }

    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void goback() {
        if (webview.canGoBack()) {
            webview.goBack(); // goBack()表示返回WebView的上一页面
        } else {
            gotohome();
        }
    }

    private void goforward() {
        if (webview.canGoForward()) {
            webview.goForward(); // goBack()表示返回WebView的上一页面
        } else {
            Toast.makeText(this, "没有更多", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goback();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //接收二维码返回值
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == -1) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                input(extras.getString("result"));

            }
        }
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }
}
