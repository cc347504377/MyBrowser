package com.luoye.demo.mybrowser.bookmark;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import java.util.List;


/**
 * Created by Luoye on 2016/11/16.
 */

public class BookAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Bookinfo> datas;

    public BookAdapter(Context context, List<Bookinfo> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_bookmark, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.name.setText(datas.get(position).getBookname());
        viewHolder.url.setText(datas.get(position).getBookurl());

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, url;
        ImageView img;
        ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.bookname);
            url = (TextView) itemView.findViewById(R.id.bookurl);
            img = (ImageView) itemView.findViewById(R.id.bookimg);
        }
    }
}
