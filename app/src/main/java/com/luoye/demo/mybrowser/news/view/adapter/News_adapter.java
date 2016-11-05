package com.luoye.demo.mybrowser.news.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.news.UtilClass.UtilPublic;
import com.luoye.demo.mybrowser.news.info.ContentlistBean;
import com.luoye.demo.mybrowser.news.view.Pointview;
import com.luoye.demo.mybrowser.news.view.adapter.baseadapter.BaseViewHolderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luoye on 2016/9/18.
 */
public class News_adapter extends BaseViewHolderAdapter {
    public News_adapter(Context context, List<ContentlistBean> datas, RecyclerView recyclerView) {
        super(context, datas, recyclerView);
    }

    @Override
    protected BaseViewHolderAdapter.HeadViewHolder onCreateHeadViewHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.adapter_item_news_head,parent,false);
        HeadViewHolder holder = new HeadViewHolder(view);
        return holder;
    }

    @Override
    protected void onBindHeadViewHolder(HeadViewHolder holder) {
        final ViewPager viewPager = holder.viewPager;
        final List<View> datas = new ArrayList<>();
        int tag = 0;
        if (viewPager.getTag()!=null)
        tag = (int) viewPager.getTag();
        datas.add(getlayout(0));
        datas.add(getlayout(1));
        datas.add(getlayout(2));
        final List<Pointview> pointviews = holder.pointviews;
        holder.pointview1.setSelected(true);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return datas.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(datas.get(position));
                return datas.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(datas.get(position));
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setTag(position);
                for (Pointview view : pointviews) {
                    view.setSelected(false);
                    if (position==view.getPosition())
                        view.setSelected(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(tag);
    }

    @Override
    protected ThreeViewHolder onCreateThreeViewHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.adapter_item_news_three, parent, false);
        ThreeViewHolder viewHolder = new ThreeViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void onBindThreeViewHolder(ThreeViewHolder holder,int position) {
        final ContentlistBean info = datas.get(position+2);
        holder.description.setText(info.getChannelName());
        holder.title.setText(info.getTitle());
        holder.time.setText(info.getPubDate());
        holder.layout.setTag(position+2);
        holder.layout.setOnClickListener(this);
        BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.load_small);
        Bitmap bit = UtilPublic.getbitmapbysize(bd.getBitmap(), context,7);
        ImageView[] views = new ImageView[]{holder.img1, holder.img2, holder.img3};
        for(int i = 0;i<3;i++) {
            String url = info.getImageurls().get(i).getUrl();
            initthreeimage(views[i], url, bit);
        }
    }

    private void initthreeimage(ImageView view,String url,Bitmap cache) {
        if (view.getTag()!=null&&view.getTag().equals(url)){
            UtilLog.setlog("tag相同");
        }
        else{
            view.setTag(url);
            Bitmap bitmap = bitmapcache.getBitmapcache(url);
            if (bitmap!=null)
                view.setImageBitmap(bitmap);
            else{
                view.setImageBitmap(cache);
                volleyHttp.getimage(url, view, context);
            }
        }
    }

    //得到viewpager子视图
    private View getlayout(int position) {
        View layout = inflater.inflate(R.layout.adapter_item_news_head_viewpagerlayout, null);
        ImageView imageView = (ImageView) layout.findViewById(R.id.fragment_item_head_layout_imag);
        TextView textView = (TextView) layout.findViewById(R.id.fragment_item_head_layout_tv);
        //        textView.setText();

        RelativeLayout relayout = (RelativeLayout) layout.findViewById(R.id.fragment_item_head_layout);
        relayout.setTag(position);
        relayout.setOnClickListener(this);
        String imageurl = datas.get(position).getImageurls().get(0).getUrl();
        imageView.setBackground(context.getResources().getDrawable(R.drawable.load_small));
        volleyHttp.getheadimage(imageurl,imageView,context);
        textView.setText(datas.get(position).getTitle());
        return layout;
    }

}
