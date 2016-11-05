package com.luoye.demo.mybrowser.news.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.luoye.demo.mybrowser.Myapplication;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.news.UtilClass.UtilSharep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luoye on 2016/9/1.
 */
public class Fragment_home extends Fragment {
    private ViewPager viewPager;
    private List<Fragment> datas;
    private TabLayout tabLayout;
    private FragmentStatePagerAdapter adapter;
    private ImageButton add;
    private List<String> titledatas;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_one,container,false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.fragmentviewpager);
        viewPager.setOffscreenPageLimit(1);
        initdatas();
    }

    private void initdatas() {
        titledatas = Myapplication.sp.getTablist(UtilSharep.TYPE_USE);
        datas = new ArrayList<>();
        for (int i = 0;i<titledatas.size();i++) {
            Fragment_news fragment_news = new Fragment_news();
//            fragment_news.settab(titledatas.get(i));
            Bundle bundle = new Bundle();
            bundle.putString("name",titledatas.get(i));
            fragment_news.setArguments(bundle);
            datas.add(fragment_news);
        }
        adapter = new MyFragmentadapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UtilLog.setlog(requestCode+" request "+resultCode+" result ");
        if (requestCode == 666 && resultCode == 1) {
            initdatas();
        }
    }

    class MyFragmentadapter extends FragmentStatePagerAdapter {

        public MyFragmentadapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titledatas.get(position);
        }
    }
}
