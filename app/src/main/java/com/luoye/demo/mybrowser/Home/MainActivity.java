package com.luoye.demo.mybrowser.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.luoye.demo.mybrowser.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            i++;
            if (i >= 2) {
                finish();
            } else {
                Toast.makeText(this, "再此点击退出应用", Toast.LENGTH_SHORT).show();
            }
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    i = 0;
                }
            }, 1500);
        }
        return false;
    }

    @OnClick({R.id.back, R.id.forward, R.id.more, R.id.home, R.id.multiwindow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                break;
            case R.id.forward:
                break;
            case R.id.more:
                break;
            case R.id.home:
                sendBroadcast(new Intent("can"));
                break;
            case R.id.multiwindow:
                break;
        }
    }
}
