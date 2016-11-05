package com.luoye.demo.mybrowser.Home.Customerview;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * Created by Luoye on 2016/11/4.
 */

public class MyScrollview extends ScrollView  {

    private int heightPixels;
    private boolean isfirst = true;
    private int otherviewheight;
    private Context context;
    private boolean canscroll = true;
    private Intent intent;
    private final String RECEIVERACTION = "scroll";
    private LinearLayout otherview;

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
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isfirst) {
            ViewGroup layout = (ViewGroup) getChildAt(0);
            layout.getChildAt(1).getLayoutParams().height = heightPixels-getStatusBarHeight();

            otherview = (LinearLayout) layout.getChildAt(0);
            otherviewheight = otherview.getMeasuredHeight();
            isfirst = false;
        }
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
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lasty = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (canscroll) {
                    otherview.setTranslationY(-(lasty-ev.getY()));
                    Log.i("TAG", ev.getY()-lasty + "");
                    lasty = ev.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;

        }


        if (ev.getAction() == MotionEvent.ACTION_UP) {


            if (getScrollY()>otherviewheight/2){//向上滑动
                smoothScrollTo(0,otherviewheight);


//                context.sendBroadcast(intent);
                canscroll = false;
            }
            return true;
        }
        if (canscroll) {
            return super.onTouchEvent(ev);
        }else {
            return true;
        }


    }
}
