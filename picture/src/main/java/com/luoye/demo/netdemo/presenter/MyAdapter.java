package com.luoye.demo.netdemo.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.luoye.demo.netdemo.R;
import com.luoye.demo.netdemo.Webactivity;
import com.luoye.demo.netdemo.module.Datainfo.NewslistBean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.List;

/**
 * Created by Luoye on 2016/10/27.
 */

public class MyAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private Context context;
    private List<NewslistBean> datas;
    public MyAdapter(Context context,List<NewslistBean> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.imageitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder viewholder = (ViewHolder) holder;
        ImageView imageView = viewholder.imageView;
        imageView.setTag(viewholder);
        imageView.setOnClickListener(this);
        viewholder.textView.setText(datas.get(position).getTitle());
        ImageLoader.getInstance().displayImage(datas.get(position).getPicUrl(),
                viewholder.imageView,new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        super.onLoadingStarted(imageUri, view);
                        viewholder.progressbar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        super.onLoadingFailed(imageUri, view, failReason);
                        viewholder.progressbar.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        super.onLoadingComplete(imageUri, view, loadedImage);
                        viewholder.progressbar.setVisibility(View.INVISIBLE);
                    }
                });

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, Webactivity.class);
        intent.putExtra("position",((ViewHolder)v.getTag()).getLayoutPosition());
        context.startActivity(intent);
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView;
        private ProgressBar progressbar;
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            textView = (TextView) itemView.findViewById(R.id.text);
            progressbar = (ProgressBar) itemView.findViewById(R.id.progress);
        }

    }
}
