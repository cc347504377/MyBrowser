package com.luoye.demo.mybrowser.web;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.luoye.demo.mybrowser.Home.MainActivity;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.web.view.Mywebview;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Webviewactivity extends AppCompatActivity {

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.edit_query)
    EditText editQuery;
    private String Path;
    @BindView(R.id.webview)
    Mywebview webview;
    private boolean isover;
    private GestureDetector detector;//监听器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String url = getIntent().getStringExtra("url");
        ButterKnife.bind(this);
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
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//               view.loadUrl(url);
//               return true;
                //消除重定向
                return false;// doc上的注释为: True if the host application wants to handle the key event itself, otherwise return false(如果程序需要处理,那就返回true,如果不处理,那就返回false)
                // 我们这个地方返回false, 并不处理它,把它交给webView自己处理.
            }

        });
        webview.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                        long contentLength) {
                // TODO Auto-generated method stub
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // Activity和Webview根据加载程度决定进度条的进度大小
                // 当加载到100%的时候 进度条自动消失
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                } else {
                    if (View.GONE == pb.getVisibility()) {
                        pb.setVisibility(View.VISIBLE);
                    }
                    pb.setProgress(newProgress);
                }
                pb.setProgress(newProgress);
                super.onProgressChanged(view, newProgress);
            }

        });

        webview.setOnoverscolledListener(new Mywebview.OnoverscolledListener() {
            @Override
            public void onochanged(boolean isover) {
                Webviewactivity.this.isover = isover;
            }
        });
        // 设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setUseWideViewPort(true); //自适应屏幕
        //自适应屏幕
//        webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        //缓存模式
        webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webview.getSettings().setDomStorageEnabled(true);//支持DomStor缓存
        webview.getSettings().setDatabaseEnabled(true);
        webview.getSettings().setUseWideViewPort(true);//将图片调整到适合webview的大小
    }

    @OnClick({R.id.back, R.id.forward, R.id.more, R.id.home, R.id.multiwindow, R.id.QRcode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if (webview.canGoBack()) {
                    webview.goBack(); // goBack()表示返回WebView的上一页面
                } else {
                    gotohome();
                }
                break;
            case R.id.forward:
                if (webview.canGoForward()) {
                    webview.goForward(); // goBack()表示返回WebView的上一页面
                }
                break;
            case R.id.more:
                break;
            case R.id.home:
                gotohome();
                break;
            case R.id.multiwindow:
                break;
            case R.id.QRcode:
                startActivityForResult(new Intent(Webviewactivity.this, CaptureActivity.class)
                        , 2);
                break;
        }
    }

    private void gotohome() {
        sendBroadcast(new Intent("can"));
        finish();
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {

            if (e2.getX() - e1.getX() > 300 && Math.abs(velocityX) >
                    200 && Math.abs(e2.getY() - e1.getY()) < 250 && isover) {
                webview.goBack();

            } else if (e1.getX() - e2.getX() > 300 && Math.abs(velocityX) >
                    200 && Math.abs(e2.getY() - e1.getY()) < 250 && isover) {
                webview.goForward();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }

    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }


    //监听
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (webview.canGoBack()) {
                webview.goBack(); // goBack()表示返回WebView的上一页面
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void input(String url) {
        if (url!=null&&!" ".equals(url.trim()) && url.length() != 0) {
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
}
