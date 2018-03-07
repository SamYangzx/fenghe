package com.lanmeng.fenghe.utils;

import android.util.Log;
import android.view.WindowManager;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by zhuoxun.yang on 2018/3/7.
 */

public class DisplayUtil {
    private static final String TAG = DisplayUtil.class.getSimpleName();

    public static void printDisplay(Context context) {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;//获取到的是px，像素，绝对像素，需要转化为dpi
        int height = metrics.heightPixels;
        Log.d(TAG, "px--width: " + width + ", height: " + height);
        Log.d(TAG, "dp--width: " + px2dip(context, width) + ", height: " + px2dip(context, height));
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
