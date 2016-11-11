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
    private int position = 1;
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
        animation = AnimationUtils.loadAnimation(this, R.anim.show);
        px = getResources().getDimension(R.dimen.pointmargin);
        ButterKnife.bind(this);
        datas = new ArrayList<>();
        final Drawable drawable0 = getResources().getDrawable(R.mipmap.ic_launcher);
        addview(getResources().getDrawable(R.drawable.lead_1));
        addview(getResources().getDrawable(R.drawable.lead_2));
        addview(getResources().getDrawable(R.drawable.lead_3));
        viewpager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return object == view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                Log.i("haha", "destroy" + position);
                container.removeView(datas.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                Log.i("haha", "instant" + position);
                container.addView(datas.get(position));
                return datas.get(position);
            }
        });
        viewpager.setCurrentItem(0);
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
