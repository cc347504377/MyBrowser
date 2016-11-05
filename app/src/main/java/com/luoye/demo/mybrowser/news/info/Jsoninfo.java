package com.luoye.demo.mybrowser.news.info;

/**
 * Created by Luoye on 2016/9/8.
 */
public class Jsoninfo {
    private String ctime,title,description,picUrl, url;

    public Jsoninfo(String ctime, String title, String description, String picUrl, String url) {
        this.ctime = ctime;
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCtime() {
        return ctime;
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

    public String getPicUrl() {
        return picUrl;
    }

    @Override
    public String toString() {
        return "Jsoninfo{" +
                "ctime='" + ctime + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
