package com.oldhigh.customchildlayout.bean;

import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by oldhigh on 2018/1/4.
 */

public class ViewBean {

    private View view;

    private double windowWidth ;

    private double windowHeight;

    private int viewType;

    public ViewBean(View view, double windowWidth, double windowHeight, int viewType) {
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

    public double getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(double windowWidth) {
        this.windowWidth = windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(double windowHeight) {
        this.windowHeight = windowHeight;
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
    public void update(CollectionViewState state) {
        if (state == null) {
            return;
        }
        //保留到小数点3位
        DecimalFormat decimalFormat = new DecimalFormat("0.000");

        state.setLeft(decimalFormat.format(view.getX() / windowWidth));
        state.setTop(decimalFormat.format(view.getY() / windowHeight));
        state.setWidth(decimalFormat.format(view.getWidth() / windowWidth));
        state.setHeight(decimalFormat.format(view.getHeight() / windowHeight));

        state.setViewType(this.viewType);
    }

}
