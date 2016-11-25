package com.luoye.demo.mybrowser.welcome;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.luoye.demo.mybrowser.Home.MainActivity;
import com.luoye.demo.mybrowser.R;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Pageractivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.point)
    TextView pointx;
    @BindView(R.id.wel_button)
    Button welButton;
    private float px;
    private ImageView imageView;
    private List<View> datas;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        initanimation();
        initdata();
        initviewpager();
    }

    private void initviewpager() {
        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewpager.setAdapter(new WelAdapter(datas));
        viewpager.setCurrentItem(0);
        //指示器滑动动画
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.i("TAG", "position: " + position + " positionoffset: " + positionOffset + " positionOffsetPixels: " + positionOffsetPixels);
                //px为小球之间间隔 positionoffset为滑动比例 positionoffsetpx 为滑动像素/
                pointx.setTranslationX(px * position + px * positionOffset);

            }

            @Override
            public void onPageSelected(int position) {
                if (position == datas.size()-1) {
                    //跳转Button动画
                    welButton.setVisibility(View.VISIBLE);
                    welButton.startAnimation(animation);
                }else {
                    welButton.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initdata() {
        //ViewPager指示器间隔
        px = getResources().getDimension(R.dimen.pointmargin);
        //绑定Butterknife
        ButterKnife.bind(this);
        //初始化ViewPager数据
        datas = new ArrayList<>();
        addview(getResources().getDrawable(R.drawable.lead_1));
        addview(getResources().getDrawable(R.drawable.lead_2));
        addview(getResources().getDrawable(R.drawable.lead_3));
    }


    private void initanimation() {
        animation = AnimationUtils.loadAnimation(this, R.anim.show);
    }

    private void addview(Drawable drawable) {
        imageView = new ImageView(this);
        imageView.setBackground(drawable);
        datas.add(imageView);
    }

    @OnClick(R.id.wel_button)
    public void onClick() {
        startActivity(new Intent(this, MainActivity.class));
        finish();

    }
}
