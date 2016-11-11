package com.luoye.demo.mybrowser.web.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;
import com.luoye.demo.mybrowser.web.Webviewactivity;

import static android.R.attr.width;

/**
 * Created by Luoye on 2016/11/6.
 */

public class MyWebscrollview extends FrameLayout {

    private int heightPixels;
    private View webview;
    private int otherviewheight;
    private boolean isfirst = true;
    private GestureDetector detector;//监听器
    public MyWebscrollview(Context context) {
        this(context,null);

    }

    public MyWebscrollview(Context context, AttributeSet attrs) {
        this(context, attrs, -1);

    }

    public MyWebscrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(metrics);
        heightPixels = metrics.heightPixels;
        detector = new GestureDetector(context, new MyGestureListener());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isfirst) {
            ViewGroup view = (ViewGroup) getChildAt(0);
            otherviewheight = view.getMeasuredHeight();
            UtilLog.setlog(otherviewheight+"_____");
            webview = getChildAt(1);
            webview.getLayoutParams().height = heightPixels - getChildAt(2).getLayoutParams().height;
            isfirst = false;
            webview.scrollTo(0, -otherviewheight);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{
        private float offset;
        private int start = -otherviewheight;
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float offset =  e1.getY() - e2.getY();
            webview.scrollTo(0, (int) offset+start);

            if (offset > otherviewheight) {
                UtilLog.setlog("000000");
                webview.scrollTo(0, 0);
                start = 0;
            }
            if (offset < 0) {
                webview.scrollTo(0, -otherviewheight);
                start = -otherviewheight;
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (offset > otherviewheight / 2) {
                webview.scrollTo(0, 0);
            }else {
                webview.scrollTo(0, -otherviewheight);
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }


}
