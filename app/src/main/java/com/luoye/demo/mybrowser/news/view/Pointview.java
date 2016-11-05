package com.luoye.demo.mybrowser.news.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Luoye on 2016/8/31.
 */
public class Pointview extends TextView {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Pointview(Context context) {
        super(context, null);
    }

    public Pointview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Pointview(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }
}
