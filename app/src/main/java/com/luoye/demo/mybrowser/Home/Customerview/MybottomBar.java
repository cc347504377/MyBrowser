package com.luoye.demo.mybrowser.Home.Customerview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.luoye.demo.mybrowser.news.UtilClass.UtilLog;

/**
 * Created by Luoye on 2016/11/11.
 */

public class MybottomBar extends LinearLayout implements ValueAnimator.AnimatorUpdateListener {
    private boolean isfrist = true;
    private boolean isshow = false;
    private LayoutParams params;
    private View menu;
    private ValueAnimator animator = null;

    public MybottomBar(Context context) {
        this(context,null);
    }

    public MybottomBar(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MybottomBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isfrist) {
            menu = getChildAt(0);
            params = (LayoutParams) menu.getLayoutParams();
            menu.measure(0, 0);
            params.topMargin = -menu.getMeasuredHeight();
            menu.setLayoutParams(params);
            isfrist = false;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //展开菜单
    public void showmenu() {
        if (isshow) {//当前展开状态
            startanimation(0, -menu.getMeasuredHeight());
        }else{//当前收起状态
            startanimation(-menu.getMeasuredHeight(), 0);
        }
        isshow = !isshow;
    }

    public void startanimation(int startvalue, int endvalue) {
        if (animator == null) {
            animator = new ValueAnimator();
        }
        animator.setIntValues(startvalue, endvalue);
        animator.setDuration(300);//动画时间
        animator.addUpdateListener(this);
        animator.start();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        params.topMargin = (int) animation.getAnimatedValue();
        menu.setLayoutParams(params);
        UtilLog.setlog(animation.getAnimatedValue()+"");
    }
}
