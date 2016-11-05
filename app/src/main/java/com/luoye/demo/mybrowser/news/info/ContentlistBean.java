package com.luoye.demo.mybrowser.news.info;

import java.util.List;

/**
 * Created by Luoye on 2016/9/14.
 */
public class ContentlistBean {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String channelId;
    private String channelName;
    private String content;
    private String desc;
    private String html;
    private String link;
    private String nid;
    private String pubDate;
    private int sentiment_display;
    private String source;
    private String title;
    /**
     * height : 250
     * url : http://image20.it168.com/201604_800x800/2502/f4afc28a07a8a151.jpg
     * width : 546
     */

    private List<ImageurlsBean> imageurls;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public int getSentiment_display() {
        return sentiment_display;
    }

    public void setSentiment_display(int sentiment_display) {
        this.sentiment_display = sentiment_display;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImageurlsBean> getImageurls() {
        return imageurls;
    }

    public void setImageurls(List<ImageurlsBean> imageurls) {
        this.imageurls = imageurls;
    }

    public static class ImageurlsBean {
        private int height;
        private String url;
        private int width;

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}