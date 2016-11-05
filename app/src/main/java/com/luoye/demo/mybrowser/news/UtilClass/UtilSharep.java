package com.luoye.demo.mybrowser.news.UtilClass;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Luoye on 2016/9/22.
 */
public class UtilSharep {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    public static int TYPE_USE = 1, TYPE_ALL = 2;

    public UtilSharep(Context context) {
        sp = context.getSharedPreferences("news_sp", Context.MODE_PRIVATE);
        editor = sp.edit();
        initTabtile();
        initTaball();
    }

    public boolean isfrst() {
        if (!sp.getBoolean("count", false)){
            editor.putBoolean("count", true);
            editor.commit();
            return true;
        } else return false;
    }

    private void initTabtile() {
        Set<String> tabtitleset = sp.getStringSet("tab",null);
        if (tabtitleset==null||tabtitleset.size()==0){
            tabtitleset = new TreeSet<>();
            tabtitleset.add("电脑");
            tabtitleset.add("科技");
            tabtitleset.add("娱乐");
            tabtitleset.add("军事");
            tabtitleset.add("互联网");
            tabtitleset.add("电影");
            tabtitleset.add("数码");
            editor.putStringSet("tab", tabtitleset);
            editor.commit();
        }
    }
    private void initTaball() {
        Set<String> tabtitleset = sp.getStringSet("taball",null);
        if (tabtitleset==null||tabtitleset.size()==0){
            tabtitleset = new TreeSet<>();
            tabtitleset.add("国内");
            tabtitleset.add("国际");
            tabtitleset.add("财经");
            tabtitleset.add("房产");
            tabtitleset.add("汽车");
            tabtitleset.add("体育");
            tabtitleset.add("游戏");
            tabtitleset.add("教育");
            tabtitleset.add("女人");
            tabtitleset.add("社会");
            tabtitleset.add("台湾");
            tabtitleset.add("港澳");
            tabtitleset.add("理财");
            tabtitleset.add("宏观经济");
            tabtitleset.add("美容护肤");
            tabtitleset.add("情感两性");
            tabtitleset.add("科普");
            editor.putStringSet("taball", tabtitleset);
            editor.commit();
        }
    }

    private List<String> fromsettolist(Set<String> set) {
        List<String> list = new ArrayList<>();
        for (String s : set) {
            list.add(s);
        }
        return list;
    }

    public List<String> getTablist(int type) {
        if (type == TYPE_USE){
            Set<String> tabtitleset = sp.getStringSet("tab",null);
            return fromsettolist(tabtitleset);
        } else if (type == TYPE_ALL) {
            Set<String> tabtitleset = sp.getStringSet("taball",null);
            return fromsettolist(tabtitleset);
        }
        return null;
    }

    public Set<String> getTabset(int type) {
        if (type == TYPE_USE){
            Set<String> tabtitleset = sp.getStringSet("tab",null);
            return tabtitleset;
        } else if (type == TYPE_ALL) {
            Set<String> tabtitleset = sp.getStringSet("taball",null);
            return tabtitleset;
        }
        return null;
    }


    public void deleteTab(int type,String title) {
        if (type == TYPE_USE){
            Set<String> tabtitleset = sp.getStringSet("tab",null);
            tabtitleset.remove(title);
            editor.putStringSet("tab", tabtitleset);
            editor.commit();
        } else if (type == TYPE_ALL) {
            Set<String> tabtitleset = sp.getStringSet("taball",null);
            tabtitleset.remove(title);
            editor.putStringSet("taball", tabtitleset);
            editor.commit();
        }
    }

    public void addtab(int type, String title) {
        if (type == TYPE_USE){
            Set<String> tabtitleset = sp.getStringSet("tab",null);
            tabtitleset.add(title);
            editor.putStringSet("tab", tabtitleset);
            editor.commit();
        } else if (type == TYPE_ALL) {
            Set<String> tabtitleset = sp.getStringSet("taball",null);
            tabtitleset.add(title);
            editor.putStringSet("taball", tabtitleset);
            editor.commit();
        }
    }

}
