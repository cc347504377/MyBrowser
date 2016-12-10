package com.luoye.demo.mybrowser.web;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
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
import com.luoye.demo.mybrowser.web.base.WebBaseActivity;
import com.luoye.demo.mybrowser.web.module.WebManager;
import com.luoye.demo.mybrowser.web.view.Mywebview;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Webviewactivity extends WebBaseActivity {

    // TODO: 2016/11/15 浏览历史的保存
    private String Path;
    @BindView(R.id.webview)
    Mywebview webview;
    private GestureDetector detector;//监听器

    @Override
    protected void afterCreate() {
        //初始化手势监听器
        detector = new GestureDetector(this, new MyGestureListener());
        //初始化webview
        iniwebview();
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



    @OnClick({R.id.bookmarks, R.id.download, R.id.collect, R.id.refresh, R.id.menu, R.id.exit})
    public void onClickmewu(View view) {
        switch (view.getId()) {
            case R.id.bookmarks:
                startActivity(new Intent(Webviewactivity.this, BookActivity.class));
                break;
            case R.id.download:
                break;
            case R.id.collect:
                Myapplication.sqlmodel.insertbook(webview.getTitle(), webview.getUrl());
                break;
            case R.id.refresh:
                webview.reload();
                break;
            case R.id.menu:
                break;
            case R.id.exit:
                System.exit(0);
                break;
        }
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

}
