package com.luoye.demo.mybrowser.web.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.luoye.demo.mybrowser.Home.Customerview.MybottomBar;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.web.module.WebManager;
import com.luoye.demo.mybrowser.web.view.Mywebview;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

// TODO: 2016/11/15 Webactivity基类 

public abstract class WebBaseActivity extends AppCompatActivity {
    @BindView(R.id.pb)
    protected ProgressBar pb;
    @BindView(R.id.edit_query)
    protected EditText editQuery;
    @BindView(R.id.bottom_bar)
    protected MybottomBar bottomBar;
    @BindView(R.id.webview)
    Mywebview webview;
    private Unbinder bind;
    public boolean isover;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        bind = ButterKnife.bind(this);
        afterCreate();
    }

    protected abstract void afterCreate();

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }

    protected void iniwebview() {
        WebManager.getinstance().webviewconfig(webview, pb, this);
        String url = getIntent().getStringExtra("url");
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

    //搜索判断
    protected void input(String url) {
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

    protected void gotohome() {
        sendBroadcast(new Intent("can"));
        finish();
    }
}
