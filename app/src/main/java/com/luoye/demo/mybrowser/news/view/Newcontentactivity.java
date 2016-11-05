package com.luoye.demo.mybrowser.news.view;

import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.view.base.Baseactivity;


public class Newcontentactivity extends Baseactivity {
    private WebView webView;
    private ProgressBar progressBar;
    private GestureDetector detector;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (msg.arg1 == 100)
                        progressBar.setVisibility(View.GONE);
                    else
                    progressBar.setProgress(msg.arg1);
            }
        }
    };
    protected void setContentLayout() {
        setContentView(R.layout.activity_newcontent);
        setbacktoolbar("");
        detector = new GestureDetector(this, new MyGestureListener());
    }

    protected void initview() {
        progressBar = (ProgressBar) findViewById(R.id.news_progressbar);
        webView = (WebView) findViewById(R.id.news_web);
        websetting();
    }

    protected void afterViewlogic() {

    }

    protected void initdatas() {

    }

    protected void initadapter() {

    }

    protected void Myclick(View view) {

    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private class myweb extends WebViewClient{
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public void onLongPress(MotionEvent e) {
            // TODO Auto-generated method stub
            // getlocate(e.getX(), e.getY());
            // getPopupWindow();
            // 这里是位置显示方式,在屏幕的左侧
            // popupWindow.showAsDropDown(location);

            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            // TODO Auto-generated method stub
            if(e2.getX() - e1.getX() > 300 && Math.abs(velocityX) > 200&&Math.abs(e2.getY()-e1.getY())<250 ){
                if (webView.canGoBack())
                webView.goBack();
                else finish();

            }
            else if(e1.getX() - e2.getX() > 300 && Math.abs(velocityX) > 200&&Math.abs(e2.getY()-e1.getY())<250 ){

                webView.goForward();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }



    }


    private void websetting() {
        String url = getIntent().getExtras().getString("url");
        webView.setWebViewClient(new myweb());
        WebSettings webSettings = webView.getSettings();
        // 设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true); //自适应屏幕
        //自适应屏幕
        //webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDisplayZoomControls(false); //隐藏webview缩放按钮
        //缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setDomStorageEnabled(true);//支持DomStor缓存
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setUseWideViewPort(true);//将图片调整到适合webview的大小
        //设置WebView属性，能够执行Javascript脚本

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Message message = Message.obtain();
                message.what = 1;
                message.arg1 = newProgress;
                handler.sendMessage(message);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
//               view.loadUrl(url);
//               return true;
                //消除重定向
                return false;// doc上的注释为: True if the host application wants to handle the key event itself, otherwise return false(如果程序需要处理,那就返回true,如果不处理,那就返回false)
                // 我们这个地方返回false, 并不处理它,把它交给webView自己处理.
            }

        });
        webView.loadUrl(url);
    }
}
