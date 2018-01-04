package com.oldhigh.customchildview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.oldhigh.customchildlayout.bean.CollectionViewState;
import com.oldhigh.customchildlayout.ui.CustomChildLayout;
import com.oldhigh.customchildlayout.ui.DrawerChildLayout;
import com.oldhigh.customchildview.util.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.oldhigh.customchildlayout.LocalContant.HALF_PARENT;
import static com.oldhigh.customchildlayout.LocalContant.IMAGE_VIEW;
import static com.oldhigh.customchildlayout.LocalContant.MATCH_PARENT;
import static com.oldhigh.customchildlayout.LocalContant.VIDEO_VIEW;

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
    @BindView(R.id.rg_)
    RadioGroup mGroup ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtil.setFullScreen(getWindow() , this);

        setContentView(R.layout.activity_main);

        mLayout = (DrawerChildLayout) findViewById(R.id.drawer_view);
        mCustomLayout = mLayout.getCustomChildLayout();

        //在Drawer中添加添加 操作的布局
        mLayout.addOperationView(R.layout.operation , Gravity.START);

        ButterKnife.bind(this , mLayout.getAddView());

        initView();


        instruction();

    }

    /**
     * Drawer 的一些操作
     */
    private void instruction() {
        final View textView = LayoutInflater.from(this).inflate(R.layout.instruction_text, mLayout, false);
        mLayout.addView(textView , new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));

        mLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mLayout.removeView(textView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //如果没有试图就提示左滑
                if (mCustomLayout.getChildCount() == 0){
                    mLayout.addView(textView , new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.MATCH_PARENT));
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private void initView() {
        mTextView.setText(mSeekBar.getProgress() + "dp");
        mProgress = mSeekBar.getProgress();

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mTextView.setText(progress + "dp");
                mProgress = progress;

                mGroup.clearCheck();

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
            R.id.rbtn_full,
            R.id.btn_complete
    })
    public void operationMethod(View view){
        switch (view.getId()) {
            case R.id.add_image_view :
                mCustomLayout.createView( IMAGE_VIEW);
                break;
            case R.id.add_video_view :
                mCustomLayout.createView( VIDEO_VIEW);

                break;
            case R.id.rbtn_half:
                mProgress =  HALF_PARENT;
                break;
            case R.id.rbtn_full:
                mProgress =  MATCH_PARENT;
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
                mCustomLayout.deleteRecentView();
                break;
            case R.id.btn_delete_all_view :
                mCustomLayout.deleteAllView();
                break;
            case R.id.btn_save_all_view :
                saveView();
                break;
                case R.id.btn_complete :
                    Intent intent = new Intent(this, ShowActivity.class);
                    intent.putExtra(ShowActivity.INFO , saveView());
                    startActivity(intent);
                break;
            default:
                break;
        }

    }

    private String saveView() {
        List<CollectionViewState> listView = mCustomLayout.getListView();

        String json = new Gson().toJson(listView);
        Log.e("<---> ", json);

        return json;

    }

}
