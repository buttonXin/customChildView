package com.oldhigh.customchildlayout.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class DrawerChildLayout extends DrawerLayout {

    private CustomChildLayout mCustomChildLayout;
    private View mView;

    public DrawerChildLayout(Context context) {
        this(context, null);
    }

    public DrawerChildLayout(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public DrawerChildLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mCustomChildLayout = new CustomChildLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        mCustomChildLayout.setBackgroundColor(Color.WHITE);
        mCustomChildLayout.setLayoutParams(params);

        addView(mCustomChildLayout);

        mCustomChildLayout.setDrawerLayout(this);

    }

    public CustomChildLayout getCustomChildLayout(){
        return mCustomChildLayout;
    }

    /**
       添加侧滑的布局
     */
    public void addOperationView(View view , int gravity){
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = gravity;
        mView = view ;
        addView(view , params);
    }

    public void addOperationView(@LayoutRes int layout , int gravity){
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = gravity;

        mView = LayoutInflater.from(getContext()).inflate(layout, this, false);

        addView(mView, params);
    }

    /**
     * 获取添加在侧滑的布局
     */
    public View getAddView(){
        return mView ;
    }


}
