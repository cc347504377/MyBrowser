package com.luoye.demo.mybrowser.news.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.Bitmapcache;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.news.UtilClass.UtilPublic;
import com.luoye.demo.mybrowser.news.info.ContentlistBean;
import com.luoye.demo.mybrowser.news.volley.VolleyHttp;

import java.util.List;

/**
 * Created by Luoye on 2016/9/8.
 */
public class Fragment_news_adapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private List<ContentlistBean> datas;
    private LayoutInflater inflater;
    private Handler handler = new Handler();
    private NewsOnclicklistener listener;
    private VolleyHttp volleyHttp = VolleyHttp.getinstance();
    private Bitmapcache bitmapcache = Bitmapcache.getBitmapcache();
    public interface NewsOnclicklistener {
        void onclick(View v, int postion);
    }
    public Fragment_news_adapter(Context context, List<ContentlistBean> datas) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_itme_news, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.title = (TextView) view.findViewById(R.id.fragment_item_title);
        viewHolder.time = (TextView) view.findViewById(R.id.fragment_item_time);
        viewHolder.description = (TextView) view.findViewById(R.id.fragment_item_Description);
        viewHolder.img = (ImageView) view.findViewById(R.id.fragment_item_img);
        viewHolder.layout = (RelativeLayout) view.findViewById(R.id.fragment_item_layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ContentlistBean info = datas.get(position);
        final ViewHolder viewholder = (ViewHolder) holder;
        viewholder.description.setText(info.getChannelName());
        viewholder.title.setText(info.getTitle());
        viewholder.time.setText(info.getPubDate());
        viewholder.layout.setTag(position);
        viewholder.layout.setOnClickListener(this);
        String url = null;
        BitmapDrawable bd = (BitmapDrawable) context.getResources().getDrawable(R.drawable.load_small);
        Bitmap bit = UtilPublic.getbitmapbysize(bd.getBitmap(), context,7);
        if (info.getImageurls().size()>0){
            url = info.getImageurls().get(0).getUrl();
            if (viewholder.img.getTag()!=null&&viewholder.img.getTag().equals(url)){
                UtilLog.setlog("tag相同");
            }
            else{
                Bitmap bitmap = bitmapcache.getBitmapcache(url);
                if (bitmap!=null)
                    viewholder.img.setImageBitmap(bitmap);
                else{
                    viewholder.img.setImageBitmap(bit);
                    volleyHttp.getimage(url, viewholder.img, context);
                }
//            new UtilBitmap(info.getPicUrl(), viewholder.img);//异步加载图片

            }
        }
        else viewholder.img.setImageBitmap(bit);

        viewholder.img.setTag(url);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void onClick(View v) {
        listener.onclick(v,(int)v.getTag());
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title,time,description;
        public ImageView img;
        public RelativeLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setListener(NewsOnclicklistener listener) {
        this.listener = listener;
    }
}
