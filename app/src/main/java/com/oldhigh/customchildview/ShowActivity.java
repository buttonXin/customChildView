package com.oldhigh.customchildview;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.percent.PercentFrameLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oldhigh.customchildlayout.LocalContant;
import com.oldhigh.customchildlayout.bean.CollectionViewState;
import com.oldhigh.customchildlayout.utils.L;
import com.oldhigh.customchildview.ui.FullVideoView;
import com.oldhigh.customchildview.util.ScreenUtil;

import java.util.List;

/**
 * Created by oldhigh on 2018/1/4.
 */

public class ShowActivity extends AppCompatActivity  {

    public static final String INFO = "json-info";

    public static final String URL = "https://cbc-stor.dopool.com/cp_name/adres/res/cctv/87671d7fe61f440c80b2a2a5f2d47195.mp4";

    private PercentFrameLayout mPfRoot;
    private List<CollectionViewState> mList;

    private FullVideoView mVideoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.setFullScreen(getWindow() , this);


        setContentView(R.layout.activity_show);

        mPfRoot = findViewById(R.id.pf_root);

        String stringJson  = getIntent().getStringExtra(INFO);

        L.e(stringJson);
        mList = new Gson().fromJson(stringJson,
                new TypeToken<List<CollectionViewState>>() {}.getType());


        fillView();
    }

    /**
     * 填充界面
     */
    private void fillView() {
        if (mList == null && mList.size() == 0) {
            Toast.makeText(this, "空的数据", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < mList.size(); i++) {

            CollectionViewState viewState = mList.get(i);
            switch (viewState.getViewType()) {

                case LocalContant.VIDEO_VIEW:

                    addVideoView(viewState);


                    break;
                case LocalContant.IMAGE_VIEW:
                    addImageView(viewState);


                    break;
                case LocalContant.TEXT_VIEW:
                    addTextView(viewState);

                    break;
                case LocalContant.TIME_VIEW:
                    //todo timeView
                    break;

                default:

                    break;
            }

        }

    }

    //添加textview
    private void addTextView(CollectionViewState viewState) {
        TextView textView = new TextView(this);
        mPfRoot.addView(textView);

        ScreenUtil.fitView(textView , viewState);
    }
    //添加图片布局
    private void addImageView(CollectionViewState viewState) {
        ImageView imageView = new ImageView(this);
        mPfRoot.addView(imageView);
        imageView.setImageResource(R.drawable.vr_image);
        ScreenUtil.fitView(imageView , viewState);
    }
    //添加视频布局
    private void addVideoView(CollectionViewState viewState) {
        mVideoView = new FullVideoView(this);
        mPfRoot.addView(mVideoView);

        mVideoView.setVideoURI(Uri.parse(URL));
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });

        ScreenUtil.fitView(mVideoView, viewState);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
        mVideoView = null;
    }
}
