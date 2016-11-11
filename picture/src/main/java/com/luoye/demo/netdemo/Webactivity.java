package com.luoye.demo.netdemo;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luoye.demo.netdemo.module.Datainfo;
import com.luoye.demo.netdemo.presenter.GetData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Webactivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textview;
    private ViewPager viewpager;
    private List<Datainfo.NewslistBean> list;
    private View view,index;
    private List<View> datas;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    initpager();
                    break;
            }
        }
    };
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webactivity);
        initview();
        new Thread(new Runnable() {
            @Override
            public void run() {
                getlist();
                handler.sendEmptyMessage(1);
            }
        }).start();

    }

    private void initpager() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setPageTransformer(true, new Scale());
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(datas.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(datas.get(position));
                return datas.get(position);
            }
        });

        if (position != -1) {
            viewpager.setCurrentItem(datas.indexOf(index));
        }else finish();
    }

    private void getlist() {
        list = GetData.getinstance(null, null).list;
        datas = new ArrayList<>();
        //设定观看的图片为当前点击图片的前后各十张
        for (int i=position-10;i<=position+10;i++) {
            Log.i("Tag", i+"");
            initview();
            if (i< 0) {
                continue;
            } else if (i >= list.size()) {
                break;
            }
            Datainfo.NewslistBean bean = list.get(i);
            textview.setText(bean.getTitle());
            Bitmap bitmap = ImageLoader.getInstance().loadImageSync(bean.getPicUrl());
            imageView.setImageBitmap(bitmap);
            if (i==position){
                index = view;//绑定当前view 用作点击时指向
            }
            datas.add(view);
        }
    }
    private void initview() {
        position = getIntent().getIntExtra("position", -1);
        view = getLayoutInflater().inflate(R.layout.imagelayout, null);
        textview = (TextView) view.findViewById(R.id.text);
        imageView = (ImageView) view.findViewById(R.id.image);
        ImageLoader.getInstance();
    }

}
class Scale implements ViewPager.PageTransformer{

    @Override
    public void transformPage(View page, float position) {
        int width = page.getWidth();
//        Log.i("haha","position: "+position + "view :" + page.toString());
        if (position < -1) {
            page.setAlpha(0);
        } else if (position <= 0) {
            Log.i("haha","position: "+position + "view :" + page.toString());
            page.setAlpha(1 - Math.abs(position));
//            page.setAlpha(1);
            //必须加下面的初始化设置 否则会出现直接跳转到页面白屏
            page.setTranslationX(0);
            page.setScaleX(1);
            page.setScaleY(1);
        } else if (position <= 1) {
            Log.i("haha","position: "+position + "view :" + page.toString());
            page.setAlpha(1 - position);
            page.setTranslationX(width * -position);//X位置不变，始终在屏幕中
            page.setScaleX(0.5f + ((1 - position) / 2));
            page.setScaleY(0.5f + ((1 - position) / 2));

        } else  {
            page.setAlpha(0);
        }

    }
}
