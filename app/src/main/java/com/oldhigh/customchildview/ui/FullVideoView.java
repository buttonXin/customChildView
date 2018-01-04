package com.oldhigh.customchildview.ui;

import android.content.Context;
import android.widget.VideoView;

/**
 * Created by oldhigh on 2018/1/4.
 */

public class FullVideoView extends VideoView {

    public FullVideoView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize( 0, widthMeasureSpec);
        int height = getDefaultSize( 0, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }
}
