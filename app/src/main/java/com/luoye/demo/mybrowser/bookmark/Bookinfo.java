package com.luoye.demo.mybrowser.bookmark;

/**
 * Created by Luoye on 2016/11/13.
 */

public class Bookinfo {
    private String bookname;
    private String bookurl;

    public Bookinfo(String bookname, String bookurl) {
        this.bookname = bookname;
        this.bookurl = bookurl;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookurl() {
        return bookurl;
    }

    public void setBookurl(String bookurl) {
        this.bookurl = bookurl;
    }

    @Override
    public String toString() {
        return "Bookinfo{" +
                "bookname='" + bookname + '\'' +
                ", bookurl='" + bookurl + '\'' +
                '}';
    }
}
