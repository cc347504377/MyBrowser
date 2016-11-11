package com.luoye.demo.netdemo.module;

import java.util.List;

/**
 * Created by Luoye on 2016/10/27.
 */

public class Datainfo {


    /**
     * code : 200
     * msg : success
     * newslist : [{"ctime":"2016-03-06 14:11","title":"刘雨欣美女桌面","description":"美女图片","picUrl":"http://m.xxxiao.com/wp-content/uploads/sites/3/2015/06/m.xxxiao.com_2411c2dfab27e4411a27c16f4f87dd22-760x500.jpg","url":"http://m.xxxiao.com/1811"}]
     */

    private int code;
    private String msg;
    /**
     * ctime : 2016-03-06 14:11
     * title : 刘雨欣美女桌面
     * description : 美女图片
     * picUrl : http://m.xxxiao.com/wp-content/uploads/sites/3/2015/06/m.xxxiao.com_2411c2dfab27e4411a27c16f4f87dd22-760x500.jpg
     * url : http://m.xxxiao.com/1811
     */

    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
