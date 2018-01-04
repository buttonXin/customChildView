package com.oldhigh.customchildlayout.utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/9/7 0007.
 */

public class L {

    public static final boolean IS_DEBUG = true;

    public static void d(String text){
        if (IS_DEBUG) {
            Log.d("CustomChildLayout--> ", text);
        }
    }

    public static void e(String text){
        if (IS_DEBUG) {
            Log.e("CustomChildLayout--> ", text);
        }
    }
}
