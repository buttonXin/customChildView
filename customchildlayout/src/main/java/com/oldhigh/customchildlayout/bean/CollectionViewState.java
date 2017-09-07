package com.oldhigh.customchildlayout.bean;

import android.view.View;

import com.google.gson.annotations.Expose;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class CollectionViewState {
    //不加@Expose 表示不进行序列化
    private View view;

    private double windowWidth ;

    private double windowHeight;

    @Expose private int viewType;

    //下面4个参数是在屏幕的百分比
    @Expose private String left ;

    @Expose private String top ;

    @Expose private String width ;

    @Expose private String height ;

    public CollectionViewState(View view,  int viewType , double windowWidth, double windowHeight) {
        this.view = view;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.viewType = viewType;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    /**
     * 调用更新来获取 得到view 参数的百分比
     */
    public void update(){
        if (view == null) return;
        //保留到小数点3位
        DecimalFormat decimalFormat = new DecimalFormat("0.000");

        left    =   decimalFormat.format( view.getX() / windowWidth  );
        top     =   decimalFormat.format( view.getY() / windowHeight);
        width   =   decimalFormat.format( view.getWidth() / windowWidth);
        height  =   decimalFormat.format( view.getHeight() / windowHeight);

    }

    @Override
    public String toString() {
        return "CollectionViewState{" +
                "viewType=" + viewType +
                ", left=" + left +
                ", top=" + top +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
