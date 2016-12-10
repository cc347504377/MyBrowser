package com.luoye.demo.mybrowser.bookmark;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;

import java.util.List;

/**
 * Created by Luoye on 2016/11/26.
 */

public class TabViewPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> datas;
    private List<String> tabnames;
    public TabViewPagerAdapter(FragmentManager fm,List<Fragment> datas,List<String> tabnames) {
        super(fm);
        this.datas = datas;
        this.tabnames = tabnames;
    }

    @Override
    public Fragment getItem(int position) {
        return datas.get(position);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

}
