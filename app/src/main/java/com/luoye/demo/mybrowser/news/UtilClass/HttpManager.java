package com.luoye.demo.mybrowser.news.UtilClass;

 public class HttpManager {
    static String httpUrl = "http://apis.baidu.com/showapi_open_bus/channel_news/search_news";
    private String httpArg = "page=1";
    private StringBuffer sbf = new StringBuffer();
    public static String getHttpUrl(int page,String name,String title) {
        if (title == null)
            return httpUrl + "?channelName=" + name + "&page=" + page;
         else
            return httpUrl + "?page=" + page + "&title=" + title;

    }

}
