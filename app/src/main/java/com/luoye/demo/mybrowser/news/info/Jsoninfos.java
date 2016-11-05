package com.luoye.demo.mybrowser.news.info;

import java.util.List;

/**
 * Created by Luoye on 2016/9/13.
 */
public class Jsoninfos {
    private int code;
    private String msg;
    private List<Jsoninfo> newslist;

    public int getCode() {
        return code;
    }

    public List<Jsoninfo> getNewslist() {
        return newslist;
    }

    public String getMsg() {
        return msg;
    }
}
