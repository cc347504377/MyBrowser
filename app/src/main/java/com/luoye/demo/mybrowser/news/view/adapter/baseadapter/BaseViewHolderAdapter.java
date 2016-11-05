package com.luoye.demo.mybrowser.news.view.adapter.baseadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.Bitmapcache;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.news.UtilClass.UtilPublic;
import com.luoye.demo.mybrowser.news.info.ContentlistBean;
import com.luoye.demo.mybrowser.news.view.Pointview;
import com.luoye.demo.mybrowser.news.view.adapter.Fragment_news_adapter;
import com.luoye.demo.mybrowser.news.volley.VolleyHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luoye on 2016/9/18.
 */
public abstract class BaseViewHolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    protected List<ContentlistBean> datas;
    protected final int TYPE_HEAD=1,TYPE_NORMAL=2, TYPE_THREE=3;
    protected LayoutInflater inflater;
    protected RecyclerView recyclerView;
    protected Context context;
    protected abstract HeadViewHolder onCreateHeadViewHolder(ViewGroup parent);
    protected abstract void onBindHeadViewHolder(HeadViewHolder holder);
    protected abstract ThreeViewHolder onCreateThreeViewHolder(ViewGroup parent);
    protected abstract void onBindThreeViewHolder(ThreeViewHolder holder,int position);
    protected VolleyHttp volleyHttp = VolleyHttp.getinstance();
    protected Bitmapcache bitmapcache = Bitmapcache.getBitmapcache();
    protected Fragment_news_adapter.NewsOnclicklistener listener;

    public BaseViewHolderAdapter(Context context, List<ContentlistBean> datas, RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public int getItemViewType(int position) {
        int type = 0;
        if (position==0)
            type = TYPE_HEAD;
        else if (datas.get(position+2).getImageurls().size()>=3){
            for (ContentlistBean.ImageurlsBean bean :datas.get(position).getImageurls() ){
                if (bean.getUrl()==null){
                    UtilLog.setlog("null");
                    return TYPE_NORMAL;
                }
            }
            type = TYPE_THREE;
        }
        else type = TYPE_NORMAL;
        return type;
    }

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_HEAD:
                viewHolder = onCreateHeadViewHolder(parent);
                break;
            case TYPE_NORMAL:
                View itemview = inflater.inflate(R.layout.adapter_itme_news, parent, false);
                viewHolder = new NormalViewHolder(itemview);
                break;
            case TYPE_THREE:
                viewHolder = onCreateThreeViewHolder(parent);
                break;
        }
        return viewHolder;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_HEAD:
                onBindHeadViewHolder((HeadViewHolder)holder);
                break;
            case TYPE_NORMAL:
                onBindNormalViewHolder((NormalViewHolder)holder,position);
                break;
            case TYPE_THREE:
                onBindThreeViewHolder((ThreeViewHolder)holder,position);
                break;

        }
    }

    private void onBindNormalViewHolder(NormalViewHolder holder,int position) {
        final ContentlistBean info = datas.get(position+2);
        holder.description.setText(info.getChannelName());
        holder.title.setText(info.getTitle());
        holder.time.setText(info.getPubDate());
        holder.layout.setTag(position+2);
        holder.layout.setOnClickListener(this);
        String url = null;
        BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.load_small);
        Bitmap bit = UtilPublic.getbitmapbysize(bd.getBitmap(), context,7);
            url = info.getImageurls().get(0).getUrl();
            if (holder.img.getTag()!=null&&holder.img.getTag().equals(url)){
                UtilLog.setlog("tag相同");
            }
            else{
                holder.img.setTag(url);
                Bitmap bitmap = bitmapcache.getBitmapcache(url);
                if (bitmap!=null)
                    holder.img.setImageBitmap(bitmap);
                else{
                    holder.img.setImageBitmap(bit);
                    volleyHttp.getimage(url, holder.img, context);
                }
//            new UtilBitmap(info.getPicUrl(), viewholder.img);//异步加载图片
            }

    }
/*
因为头项占用了三条数据 -2
 */
    public int getItemCount() {
        return datas.size()-2;
    }

    @Override
    public void onClick(View v) {
        listener.onclick(v,(int)v.getTag());
    }

    public class HeadViewHolder extends RecyclerView.ViewHolder {
        public ViewPager viewPager;
        public Pointview pointview1,pointview2, pointview3;
        public List<Pointview> pointviews;
        public HeadViewHolder(View itemView) {
            super(itemView);
            pointviews = new ArrayList<>();
            viewPager = (ViewPager) itemView.findViewById(R.id.fragment_item_head_viewpager);
            pointview1 = (Pointview) itemView.findViewById(R.id.point1);
            pointview2 = (Pointview) itemView.findViewById(R.id.point2);
            pointview3 = (Pointview) itemView.findViewById(R.id.point3);
            pointview1.setPosition(0);
            pointview2.setPosition(1);
            pointview3.setPosition(2);
            pointviews.add(pointview1);
            pointviews.add(pointview2);
            pointviews.add(pointview3);
        }
    }

    public class NormalViewHolder extends RecyclerView.ViewHolder {
        public TextView title,time,description;
        public ImageView img;
        public RelativeLayout layout;
        public NormalViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.fragment_item_title);
            time = (TextView) itemView.findViewById(R.id.fragment_item_time);
            description = (TextView) itemView.findViewById(R.id.fragment_item_Description);
            img = (ImageView) itemView.findViewById(R.id.fragment_item_img);
            layout = (RelativeLayout) itemView.findViewById(R.id.fragment_item_layout);
        }
    }

    public class ThreeViewHolder extends RecyclerView.ViewHolder {
        public TextView title,time,description;
        public ImageView img1,img2,img3;
        public LinearLayout layout;
        public ThreeViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.fragment_item_three_title);
            time = (TextView) itemView.findViewById(R.id.fragment_item_three_time);
            description = (TextView) itemView.findViewById(R.id.fragment_item_three_descrption);
            img1 = (ImageView) itemView.findViewById(R.id.fragment_item_three_img1);
            img2 = (ImageView) itemView.findViewById(R.id.fragment_item_three_img2);
            img3 = (ImageView) itemView.findViewById(R.id.fragment_item_three_img3);
            layout = (LinearLayout) itemView.findViewById(R.id.fragment_item_three_layout);
        }
    }

    public void setListener(Fragment_news_adapter.NewsOnclicklistener listener) {
        this.listener = listener;
    }
}
