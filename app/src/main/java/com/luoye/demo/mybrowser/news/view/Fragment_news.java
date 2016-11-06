package com.luoye.demo.mybrowser.news.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.luoye.demo.mybrowser.Myapplication;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.HttpManager;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.news.info.ContentlistBean;
import com.luoye.demo.mybrowser.news.view.adapter.Fragment_news_adapter;
import com.luoye.demo.mybrowser.news.view.adapter.News_adapter;
import com.luoye.demo.mybrowser.news.volley.VolleyHttp;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Fragment_news extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutmanager;
    private News_adapter adapter;
    private ProgressBar progressBar;
    private ImageView imageView;
    private SwipeRefreshLayout refreshLayout;
    private String urlname = null, urltitle = null;
    private LinearLayout loading;
    private boolean isup = true ;
    private VolleyHttp volleyHttp;
    private TextView textView;
    private List<ContentlistBean> alldatas;
    private static int page = 1;
    private int FLAG_ADD = 0;
    private String urltag;

    private Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    loading.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    loading.setVisibility(View.GONE);
                    FLAG_ADD = 0;//初始FLAG
                    break;
                case 2:
                    textView.setText("拼命加载ing");
                    break;
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        volleyHttp = VolleyHttp.getinstance();
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_recycleview);
        progressBar = (ProgressBar) view.findViewById(R.id.fragment_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        imageView = (ImageView) view.findViewById(R.id.fragment_imgview);
        imageView.setVisibility(View.INVISIBLE);
        loading = (LinearLayout) view.findViewById(R.id.news_loading);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_swiperefreshlayout);
        textView = (TextView) view.findViewById(R.id.fragment_layout_text);
        initfreshlayout();
        initloadlistener();
        initrecycleview();
    }


    private void initloadlistener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                UtilLog.setlog("dx" + dx + "dy" + dy);
                if (dy>0)
                    isup = true;
                else if (dy<0)
                    isup = false;
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                UtilLog.setlog("newstate" + newState);
                if (FLAG_ADD == 0) {
                    if (!ViewCompat.canScrollVertically(recyclerView, 0) && newState == 1) {
//                        UtilLog.setlog("OK");
                        new Timer().schedule(new TimerTask() {
                            public void run() {
                                if (isup||alldatas.size()<4){
                                    FLAG_ADD = 1;
                                    loaddata();
                                }
                            }
                        },100);
                    }
                }
            }
        });
    }

    private void loaddata() {
        handle.sendEmptyMessage(2);
        handle.sendEmptyMessage(0);//显示加载

        volleyHttp.SetJsonCallback(new VolleyHttp.JsonCallback() {
            @Override
            public void onResponse(final List<ContentlistBean> datas) {
//                adapter = new Fragment_news_adapter(getActivity(), datas);
                deletenull(datas);
                handle.sendEmptyMessage(1);
                alldatas.addAll(datas);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                page--;
                textView.setText("加载失败");
                handle.sendEmptyMessageDelayed(1, 1000);
//                progressBar.setVisibility(View.GONE);
//                Utiltoast.toast(getActivity(),1000,"网络连接错误");
            }
        });
        volleyHttp.getJson(HttpManager.getHttpUrl(++page,urlname,urltitle));
    }

    private void initfreshlayout() {
        refreshLayout.setColorSchemeResources(R.color.skyblue,R.color.orange,R.color.black);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initrecycleview();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                initrecycleview();
            }
        });
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        urlname = stringtourlcode(args.getString("name"));
        urltitle = stringtourlcode(args.getString("title"));
    }

    private void initrecycleview() {
        layoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutmanager);
        volleyHttp.SetJsonCallback(new VolleyHttp.JsonCallback() {
            @Override
            public void onResponse(final List<ContentlistBean> datas) {
//                adapter = new Fragment_news_adapter(getActivity(), datas);
                deletenull(datas);
                alldatas = datas;
                adapter = new News_adapter(getContext(), alldatas, recyclerView);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                adapter.setListener(new Fragment_news_adapter.NewsOnclicklistener() {
                                public void onclick(View v, int postion) {
                                    Intent intent = new Intent(getActivity(), Newcontentactivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("url", datas.get(postion).getLink());
                                    bundle.putString("title", datas.get(postion).getTitle());
                                    String jsonstring = Myapplication.gson.toJson(datas.get(postion));
                                    bundle.putString("json", jsonstring);
//                                    UtilLog.setlog(jsonstring);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                }
                            });
            }

            @Override
            public void onError() {
                recyclerView.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
//                Utiltoast.toast(getActivity(),1000,"网络连接错误");
            }
        });
        urltag = HttpManager.getHttpUrl(1, urlname,urltitle);
        volleyHttp.getJson(urltag);
//        volleyHttp.getJson(HttpManager.httpUrl);
        refreshLayout.setRefreshing(false);
    }
    private void deletenull(List<ContentlistBean> datas) {
        Iterator it = datas.iterator();
        while (it.hasNext()) {
            ContentlistBean bean = (ContentlistBean)it.next();
            if (bean.getImageurls().size() == 0 || bean.getImageurls().get(0).getUrl() == null) {
                it.remove();
//                UtilLog.setlog(bean.getTitle()+"删除空值");
            }
        }
    }

    private String stringtourlcode(String s) {
        String urlcode = null;
        try {
            if (s!=null)
            urlcode = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            UtilLog.setlog("urlcode转换错误");
            e.printStackTrace();
        }
        return urlcode;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Myapplication.requestQueue.cancelAll(urltag);
    }

}
