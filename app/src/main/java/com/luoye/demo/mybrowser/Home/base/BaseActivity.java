package com.luoye.demo.mybrowser.Home.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.luoye.demo.mybrowser.Home.Customerview.MybottomBar;
import com.luoye.demo.mybrowser.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
// TODO: 2016/11/15 HomeActivity基类

public class BaseActivity extends AppCompatActivity {
    @BindView(R.id.edit_query)
    protected EditText editQuery;
    @BindView(R.id.QRcode)
    protected ImageButton QRcode;
    @BindView(R.id.bookmarks)
    protected TextView bookmarks;
    @BindView(R.id.download)
    protected TextView download;
    @BindView(R.id.collect)
    protected TextView collect;
    @BindView(R.id.refresh)
    protected TextView refresh;
    @BindView(R.id.menu)
    protected TextView menu;
    @BindView(R.id.exit)
    protected TextView exit;
    @BindView(R.id.back)
    protected ImageButton back;
    @BindView(R.id.forward)
    protected ImageButton forward;
    @BindView(R.id.more)
    protected ImageButton more;
    @BindView(R.id.home)
    protected ImageButton home;
    @BindView(R.id.multiwindow)
    protected ImageButton multiwindow;
    @BindView(R.id.bottom_bar)
    protected MybottomBar bottomBar;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }
}
