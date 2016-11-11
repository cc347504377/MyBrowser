package com.luoye.demo.mybrowser.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.luoye.demo.mybrowser.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Welcomeactivity extends AppCompatActivity {


    @BindView(R.id.weilogo)
    ImageView weilogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(Welcomeactivity.this, Pageractivity.class));
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        weilogo.setAnimation(animation);

    }
}
