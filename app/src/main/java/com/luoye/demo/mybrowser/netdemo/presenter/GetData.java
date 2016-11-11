package com.luoye.demo.mybrowser.netdemo.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import com.luoye.demo.mybrowser.netdemo.module.Datainfo;
import com.luoye.demo.mybrowser.netdemo.module.Netmanager;
import com.luoye.demo.mybrowser.netdemo.view.Viewapi;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Luoye on 2016/10/27.
 */

public class GetData {
    private Viewapi viewapi;
    private Netmanager netmanager;
    private Context context;
    private MyAdapter adapter;
    private static GetData getData = null;
    public List<Datainfo.NewslistBean> list;

    private GetData(Context context,Viewapi viewapi) {
        this.viewapi = viewapi;
        this.context = context;
    }

    public static GetData getinstance(Context context,Viewapi viewapi) {
        if (getData == null) {
            getData = new GetData(context,viewapi);
            Log.i("TAG", "new");
        }
        return getData;
    }
    public void getdata(final RecyclerView recycvlerview) {
        viewapi.onrefresh();
        netmanager = Netmanager.getinstance();
        netmanager.getData().enqueue(new Callback<Datainfo>() {
            @Override
            public void onResponse(Call<Datainfo> call, Response<Datainfo> response) {
                viewapi.onsuccess();
                if (response.body().getCode()==200){
                    if (adapter == null) {
                        list = response.body().getNewslist();
                        adapter = new MyAdapter(context, list);
                        recycvlerview.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                        recycvlerview.setAdapter(adapter);
                    }else {
                        list.addAll(response.body().getNewslist());
                        adapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onFailure(Call<Datainfo> call, Throwable t) {
                viewapi.onfiled();
            }
        });
    }

    public void destroy() {
        getData = null;
    }
}
