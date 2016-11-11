package com.luoye.demo.netdemo;

import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.luoye.demo.netdemo.presenter.GetData;
import com.luoye.demo.netdemo.view.Viewapi;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Viewapi {
    private RecyclerView recycleview;
    private SwipeRefreshLayout refresh;
    private Toolbar toolbar;
    private RelativeLayout layout;
    private FloatingActionButton actionButton;
    private int num = 0;
    private int floatstat = 0;
    private boolean stat = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.);
        initview();
        loading();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Tag", "onresume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Tag", "onpause");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void loading() {
        recycleview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)){
                    if (newState == 1 && stat) {
                        stat = false;
                        animotionin();
                        initdata();
                    }
                }
            }
        });
    }

    private void initview() {
        actionButton = (FloatingActionButton) findViewById(R.id.floatactionbutton);
        layout = (RelativeLayout) findViewById(R.id.loadlayout);
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        refresh = (SwipeRefreshLayout) findViewById(R.id.refreshlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Runtime runtime = Runtime.getRuntime();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    runtime.exec("input keyevent "+KeyEvent.KEYCODE_BACK);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        toolbar.setTitleTextColor(getResources().getColor(R.color.textcolor));
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                floatstat++;
                switch (floatstat % 3) {
                    case 0:
                        recycleview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        break;
                    case 1:
                        recycleview.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                        break;
                    case 2:
                        recycleview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        break;
                }
            }
        });
        refresh.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.black),
                getResources().getColor(R.color.colorPrimaryDark));
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initdata();
            }
        });
        refresh.post(new Runnable() {
            @Override
            public void run() {
                refresh.setRefreshing(true);
                initdata();
            }
        });
    }

    private void initdata() {
        recycleview.setVisibility(View.INVISIBLE);
        GetData.getinstance(this, this).getdata(recycleview);
    }

    @Override
    public void onsuccess() {
        stat = true;
        refresh.setRefreshing(false);
        animotionout();
    }

    @Override
    public void onrefresh() {
        recycleview.setVisibility(View.VISIBLE);

    }

    @Override
    public void onfiled() {
        stat = true;
        Toast.makeText(this, "爆炸,请重新刷新", Toast.LENGTH_SHORT).show();
        refresh.setRefreshing(false);
        animotionout();
    }

    @Override
    public void onloader() {

    }

    @Override
    public void onimgeloader() {

    }

    public void animotionin() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.enter);
        layout.setVisibility(View.VISIBLE);
        layout.startAnimation(animation);
    }
    public void animotionout() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.out);
        layout.setVisibility(View.VISIBLE);
        layout.startAnimation(animation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Tag", "tuichu");
        GetData.getinstance(null, null).destroy();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        num++;
        if (num == 2) {
            finish();
        }
        if (keyCode == KeyEvent.KEYCODE_BACK&&num<2) {
            Toast.makeText(this, "再此点击关闭应用", Toast.LENGTH_SHORT).show();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    num = 0;
                }
            },2000);
        }
        return true;
    }
}
