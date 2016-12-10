package com.luoye.demo.mybrowser.bookmark;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BookActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.book_tab)
    TabLayout bookTab;
    @BindView(R.id.book_viewpager)
    ViewPager bookViewpager;
    private Unbinder bind;
    private List<Fragment> datas;
    private List<String> tabnames;
    private final String TAB_BOOK = "收藏夹", TAB_HISTORY = "历史记录";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        bind = ButterKnife.bind(this);
        inittoolbar();
        initfragment();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();

    }

    private void initfragment() {
        initlist();
        bookViewpager.setOffscreenPageLimit(1);
        bookViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return datas.get(position);
            }

            @Override
            public int getCount() {
                return datas.size();
            }
        });
//        bookViewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bookTab));
//        bookTab.setupWithViewPager(bookViewpager, true);
    }


    private void inittoolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initlist() {
        datas = new ArrayList<>();
        datas.add(new BookFragment());
        datas.add(new BookFragment());
        tabnames = new ArrayList<>();
        tabnames.add(TAB_BOOK);
        tabnames.add(TAB_HISTORY);
    }

    @Override
    protected void onDestroy() {
        bind.unbind();
        super.onDestroy();
    }
}
