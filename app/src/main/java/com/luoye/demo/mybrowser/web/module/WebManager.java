package com.luoye.demo.mybrowser.web.module;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.web.Webviewactivity;
import com.luoye.demo.mybrowser.web.base.WebBaseActivity;
import com.luoye.demo.mybrowser.web.view.Mywebview;

/**
 * Created by Luoye on 2016/11/25.
 */

public class WebManager {
    private static WebManager webManager;
    public static WebManager getinstance() {
        if (webManager==null)
            webManager = new WebManager();
        return webManager;
    }
    public void webviewconfig(Mywebview webview, final ProgressBar pb, final WebBaseActivity activity) {
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
                activity.startActivity(intent);

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
                activity.isover = isover;
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
}
