package com.luoye.demo.mybrowser.news.UtilClass;

import android.content.Context;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Luoye on 2016/9/8.
 */
public class Utiltoast {
    public static void toast(final Context context, int delay, final String text) {
        final Timer timer = new Timer();
        final Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        },0);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        },delay);

    }
}
