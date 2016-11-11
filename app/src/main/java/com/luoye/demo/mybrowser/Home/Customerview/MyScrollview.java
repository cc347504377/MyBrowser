package com.luoye.demo.mybrowser.Home.Customerview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

import com.luoye.demo.mybrowser.Myapplication;
import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;

/**
 * Created by Luoye on 2016/11/4.
 */

public class MyScrollview extends FrameLayout {

    private int heightPixels;
    private boolean isfirst = true;
    private int otherviewheight;
    private Context context;
    private boolean canscroll = true;
    private Intent intent;
    private final String RECEIVERACTION = "scroll";
    private LinearLayout otherview;
    private View content;
    private int contentheight;


    public MyScrollview(Context context) {
        this(context,null);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        heightPixels = metrics.heightPixels;
        this.context = context;
        intent = new Intent(RECEIVERACTION);
        mScroller = new Scroller(getContext());
        mtoughstop = ViewConfiguration.get(context).getScaledTouchSlop();
        //注册广播
        Canscroll canscroll = new Canscroll();
        Myapplication.canscroll = canscroll;//将广播对象赋给Application方便管理
        context.registerReceiver(canscroll, new IntentFilter("can"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isfirst) {
            getChildAt(1).getLayoutParams().height = heightPixels-getStatusBarHeight()-getChildAt(2).getMeasuredHeight();
            contentheight = getChildAt(1).getLayoutParams().height;
            otherview = (LinearLayout) getChildAt(0);


            otherviewheight = otherview.getMeasuredHeight();
            isfirst = false;
            content = getChildAt(1);
            content.scrollTo(0, -otherviewheight);

            start = -otherviewheight;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private float lasty;
    private int offsety;
    private int start ;
    private int mtoughstop;
    private double myfristtough;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lasty = ev.getY();
                if (!mScroller.isFinished()) {
                    mScroller.forceFinished(true);
                }
                myfristtough = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(myfristtough - ev.getY()) > mtoughstop) {
                    if (canscroll){
                        return true;
                    }
                    else return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;

            case MotionEvent.ACTION_MOVE:
                offsety =  (int) (lasty - event.getY());
                content.scrollTo(0, offsety + start);
                otherview.scrollTo(0,offsety/(contentheight/otherviewheight));
//                Log.i("TAG", "start: " + start);
//                Log.i("TAG", "offsety:" + offsety);
                if (offsety > otherviewheight) {
                    content.scrollTo(0, 0);
                }
                if (offsety < 0) {
                    otherview.scrollTo(0, 0);
                    content.scrollTo(0, -otherviewheight);
                }
//                lasty = event.getY();
                break;
            case MotionEvent.ACTION_UP:
//                Log.i("TAG", "___________________________");
                if (offsety < otherviewheight / 2) {
//                    content.scrollTo(0, -otherviewheight);
                    smoothScrollTo(0,-otherviewheight);
                    start = -otherviewheight;
                }else{
//                    content.scrollTo(0, 0);
                    smoothScrollTo(0, 0);
                    start = 0;
                    canscroll = false;
                }
                break;

        }
        return true;
    }

    private Scroller mScroller;
    private void smoothScrollTo(int scrollX,int scrollY){
        int startX = content.getScrollX();
        int startY = content.getScrollY();
        int dx = scrollX - startX;
        int dy = scrollY - startY;
        mScroller.startScroll(startX, startY, dx, dy, 1000);
        invalidate();//为了触发computeScroll方法
    }
    /**
     * Scroller
     * 起点：startX
     * 滚动多少：dx
     * 时间：duration
     *
     * currX: 随着时间在发生变化
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if(!mScroller.isFinished()){
            if(mScroller.computeScrollOffset()){
                content.scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//                Log.i("TAG", (mScroller.getCurrY()-start) / (contentheight / otherviewheight) + "curry");

                if (start == 0) {
                    otherview.scrollTo(0, (otherviewheight+mScroller.getCurrY())/ (contentheight / otherviewheight));
//                    Log.i("TAG", (otherviewheight+mScroller.getCurrY())/ (contentheight / otherviewheight)+"-------");
                }else
                otherview.scrollTo(0, (mScroller.getCurrY() - start)/ (contentheight / otherviewheight));
                invalidate();
            }
        }
    }

    public class Canscroll extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            canscroll = true;
            smoothScrollTo(0,-otherviewheight);
            start = -otherviewheight;
        }
    }
}
