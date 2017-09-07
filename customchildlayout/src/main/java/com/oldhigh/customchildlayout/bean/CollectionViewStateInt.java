package com.oldhigh.customchildlayout.bean;

import android.view.View;

import com.google.gson.annotations.Expose;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class CollectionViewStateInt {
    //不加@Expose 表示不进行序列化
    private View view;

    private int windowWidth ;

    private int windowHeight;

    @Expose private int viewType;

    @Expose private int left ;

    @Expose private int top ;

    @Expose private int width ;

    @Expose private int height ;

    public CollectionViewStateInt(View view, int viewType , int windowWidth, int windowHeight) {
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

    public int getLeft() {
        if (view != null) return (int) view.getX();
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        if (view != null) return (int) view.getY();
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getWidth() {
        if (view != null) return (int) view.getWidth();
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        if (view != null) return (int) view.getHeight();
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void update(){
        if (view == null) return;
        left = (int) view.getX() ;
        top = (int) view.getY();
        width = view.getWidth();
        height = view.getHeight();
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
