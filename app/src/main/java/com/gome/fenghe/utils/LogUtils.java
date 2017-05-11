package com.gome.fenghe.utils;

import android.util.Log;

/**
 * Created by admin on 2016/3/14.
 */
public class LogUtils {
    public static final String COMMON_TAG = "sam---";
    private static final boolean DBG_I = true;
    private static final boolean DBG_D = true;

    public static void i(String tag, String msg) {
        if (DBG_I) {
            Log.i(getLogTAG(tag), msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DBG_D) {
            Log.d(getLogTAG(tag), msg);
        }
    }

    public static void e(String tag, String msg) {
        Log.e(getLogTAG(tag), msg);
    }

    private static String getLogTAG(String tag){
        return COMMON_TAG +tag;
    }
}
