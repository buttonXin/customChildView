package com.oldhigh.customchildlayout.bean;

import android.view.View;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class CollectionViewState {


    private int viewType;

    //下面4个参数是在屏幕的百分比
    private String left;

    private String top;

    private String width;

    private String height;

    public CollectionViewState() {
    }


    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }


    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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
