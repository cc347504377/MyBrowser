package com.luoye.demo.mybrowser.news.view.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;

/**
 * Created by Luoye on 2016/8/31.
 */
public abstract class Baseactivity extends AppCompatActivity {
    protected Toolbar toolbar;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentLayout();
        initview();
        afterViewlogic();
        initdatas();
        initadapter();
    }

    protected abstract void setContentLayout();
    protected abstract void initview();
    protected abstract void afterViewlogic();
    protected abstract void initdatas();
    protected abstract void initadapter();
    protected abstract void Myclick(View view);
    protected void startactivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
    protected void setbacktoolbar(String title) {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(R.drawable.btn_return);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setBackgroundColor(getResources().getColor(R.color.skyblue));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
    }
    protected String getAppVersionName() {
        String varName = "";
        try {
            varName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            UtilLog.setlog("获得包名失败");
        }
        return varName;
    }

    protected String getAppName() {
        String appName = "";
        appName = getAppName();
        return appName;
    }

    protected void toast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

}
