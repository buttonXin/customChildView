package com.oldhigh.customchildview.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.percent.PercentFrameLayout;
import android.support.percent.PercentLayoutHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.oldhigh.customchildlayout.bean.CollectionViewState;

/**
 *屏幕布局
 * @author oldhigh
 * @date 2018/1/4
 */

public class ScreenUtil {



    //计算控件布局位置
    public static View fitView(View mView, CollectionViewState state) {
        PercentFrameLayout.LayoutParams lp = new PercentFrameLayout.LayoutParams(mView.getLayoutParams());
        PercentLayoutHelper.PercentLayoutInfo layoutInfo = lp.getPercentLayoutInfo();
        try {
            layoutInfo.leftMarginPercent =  Float.parseFloat(state.getLeft());
            layoutInfo.topMarginPercent =  Float.parseFloat(state.getTop());
            layoutInfo.widthPercent = Float.parseFloat(state.getWidth());
            layoutInfo.heightPercent =  Float.parseFloat(state.getHeight());
            mView.setLayoutParams(lp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }


    public static void setFullScreen(Window window , AppCompatActivity context) {
        //全屏显示，设置横屏
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        window.setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        context.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
