package com.oldhigh.customchildview;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.gson.GsonBuilder;
import com.oldhigh.customchildlayout.bean.CollectionViewState;
import com.oldhigh.customchildlayout.ui.CustomChildLayout;
import com.oldhigh.customchildlayout.ui.DrawerChildLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    DrawerChildLayout mLayout ;
    private CustomChildLayout mCustomLayout;

    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.text_count)
    TextView mTextView;
    private int mProgress;

    @BindView(R.id.rbtn_half)
    RadioButton rbtn_half;
    @BindView(R.id.rbtn_full)
    RadioButton rbtn_full;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();

        setContentView(R.layout.activity_main);

        mLayout = (DrawerChildLayout) findViewById(R.id.drawer_view);
        mCustomLayout = mLayout.getCustomChildLayout();

        mLayout.addOperationView(R.layout.operation , Gravity.START);
        ButterKnife.bind(this , mLayout.getAddView());

        initView();
    }

    private void initView() {
        mTextView.setText(mSeekBar.getProgress() + "dp");
        mProgress = mSeekBar.getProgress();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextView.setText(progress + "dp");
                mProgress = progress;

                rbtn_full.setChecked(false);
                rbtn_half.setChecked(false);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @OnClick( {
            R.id.add_image_view ,
            R.id.add_video_view,
            R.id.btn_add_width ,
            R.id.btn_add_height ,
            R.id.btn_delete_width ,
            R.id.tbn_delete_height ,
            R.id.btn_delete_view,
            R.id.btn_delete_all_view,
            R.id.btn_save_all_view ,
            R.id.rbtn_half,
            R.id.rbtn_full
    })
    public void operationMethod(View view){
        switch (view.getId()) {
            case R.id.add_image_view :
                mCustomLayout.createView(CustomChildLayout.IMAGE_VIEW);
                break;
            case R.id.add_video_view :
                mCustomLayout.createView(CustomChildLayout.VIDEO_VIEW);

                break;
            case R.id.rbtn_half:
                rbtn_half.setChecked(true);
                mProgress = CustomChildLayout.HALF_PARENT;
                break;
            case R.id.rbtn_full:
                rbtn_full.setChecked(true);
                mProgress = CustomChildLayout.MATCH_PARENT;
                break;

            case R.id.btn_add_width  :
                mCustomLayout.addWidth(mProgress);

                break;
            case R.id.btn_add_height  :
                mCustomLayout.addHeight(mProgress);
                break;
            case R.id.btn_delete_width  :
                mCustomLayout.deleteWidth(mProgress);
                break;
            case R.id.tbn_delete_height  :
                mCustomLayout.deleteHeight(mProgress);
                break;
            case R.id.btn_delete_view :
                mCustomLayout.deleteView();
                break;
            case R.id.btn_delete_all_view :
                mCustomLayout.deleteAllView();
                break;
            case R.id.btn_save_all_view :
                saveView();
                break;
            default:
                break;
        }

    }

    private void saveView() {
        List<CollectionViewState> listView = mCustomLayout.getListView();

        Log.e("<---> ", new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create()
                .toJson(listView)
        );


    }


    private void setFullScreen() {
        //全屏显示，设置横屏
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);//使窗口支持透明度
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
