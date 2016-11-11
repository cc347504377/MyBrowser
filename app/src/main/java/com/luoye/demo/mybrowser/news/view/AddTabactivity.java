package com.luoye.demo.mybrowser.news.view;

import android.view.View;

import com.luoye.demo.mybrowser.Myapplication;
import com.luoye.demo.mybrowser.R;
import com.luoye.demo.mybrowser.news.UtilClass.UtilSharep;
import com.luoye.demo.mybrowser.news.view.base.Baseactivity;

import java.util.Set;

public class AddTabactivity extends Baseactivity implements Flowlayoutlistener {
    private FlowLayout flowLayout,flowLayoutall;
    private UtilSharep sp;
    private boolean isfirst = true;
    private Set<String> tabset,taball;
    private boolean ischanged = false;

    protected void setContentLayout() {
        setContentView(R.layout.activity_add_tab);
        sp = Myapplication.sp;
    }

    protected void initview() {
        setbacktoolbar("选择标签");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        flowLayoutall = (FlowLayout) findViewById(R.id.flowlayoutall);
        flowLayout = (FlowLayout) findViewById(R.id.flowlayout);
    }

    protected void afterViewlogic() {
        flowLayout.setflowclicklistener(this);
        flowLayoutall.setflowclicklistener(this);
    }

    protected void initdatas() {
        tabset = sp.getTabset(UtilSharep.TYPE_USE);
        taball = sp.getTabset(UtilSharep.TYPE_ALL);
        flowLayout.setDrawable(R.drawable.add_textview);
        flowLayout.getSetData(tabset);
        flowLayout.setFlowLayout(flowLayoutall);
        flowLayout.setOtherset(taball);
        flowLayout.setType(false);
        flowLayoutall.setType(true);
        flowLayoutall.setDrawable(R.drawable.add_textview2);
        flowLayoutall.getSetData(taball);
        flowLayoutall.setFlowLayout(flowLayout);
        flowLayoutall.setOtherset(tabset);
    }

    protected void initadapter() {
    }

    protected void Myclick(View view) {

    }

    public void run() {
        ischanged = true;
        if (ischanged&&isfirst){
            setResult(1);
        }
        isfirst = false;
    }


}
