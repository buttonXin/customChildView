package com.oldhigh.customchildlayout.ui;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.oldhigh.customchildlayout.R;
import com.oldhigh.customchildlayout.bean.CollectionViewState;
import com.oldhigh.customchildlayout.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class CustomChildLayout extends RelativeLayout implements View.OnDragListener {

    //布局参数
    private LayoutParams mParams;
    //数据类
    private CollectionViewState mBean;

    //初始化的宽高
    private int mWidthInit = 300 ;
    private int mHeightInit = 200 ;

    //view的集合
    private List<CollectionViewState> mListPool;
    //创建的view 以及当前操作的view
    private  View mView;
    //当前拖拽的view的位置
    private int mRealViewPosition = 0 ;
    //侧滑菜单
    private DrawerLayout mDrawerLayout;
    //默认最小值的宽高
    private final int minDp = 5;

    /**
     * 直接设置view的宽高是 一半  还是 match
     */
    public static final int MATCH_PARENT = 1000_000_001;
    public static final int HALF_PARENT = 1000_000_000;

    /**
     *声明 view 的 类型
     */
    public static final int IMAGE_VIEW = 1000_000_002 ;
    public static final int VIDEO_VIEW = 1000_000_003 ;
    public static final int TEXT_VIEW = 1000_000_004 ;
    public static final int TIME_VIEW = 1000_000_005 ;
    private int mX = -1 ;
    private int mY = -1;
    private Paint mPaint;


    public CustomChildLayout(Context context) {
        this(context , null);
    }

    public CustomChildLayout(Context context, AttributeSet attrs) {
        this(context, attrs , 0 );
    }

    public CustomChildLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) init(context, attrs);
    }

    /**
     *初始化一些数据
     */
    private void init(Context context, AttributeSet attrs) {

        //这里是主要的监听， 因为创建的子view，在拖拽的时候都是在自己原有的宽高内， 所以拖拽总是不成功，
        //监听父布局的拖拽，可以确保子view是一直在父布局的view中的。
        this.setOnDragListener(this);

        mParams = new LayoutParams(mWidthInit, mHeightInit);

       // mBean = new CollectionViewState();

        mListPool = new ArrayList<>();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(3);

        if (getBackground() == null){
            this.setBackground(new ColorDrawable(Color.WHITE));
        }else {
            this.setBackground(getBackground());
        }
    }

    public void setDrawerLayout(DrawerLayout drawerLayout){
        mDrawerLayout = drawerLayout;
    }
    /**
     * 根据类型创建不同的view
     * @param type {@link #}
     */
    public void createView(int type){
        checkDrawerLayout();

        mView = new View(getContext());


        if (type == IMAGE_VIEW){
            mView.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.vr_image));
        }
        if (type == VIDEO_VIEW){
            mView.setBackground(ContextCompat.getDrawable(getContext() , R.drawable.video_image));
        }

        mParams = new LayoutParams(mWidthInit, mHeightInit);
        mParams.leftMargin = getWindowWidth() / 2;
        mParams.topMargin = 0 ;
        addView(mView, mParams);

        mBean = new CollectionViewState(mView, type , getWindowWidth() , getWindowHeight());
        mListPool.add(mBean);

        onOnLong(mView);
    }

    /**
     * 检测DrawerLayout 是否为空， 否则就关闭侧滑布局
     */
    private void checkDrawerLayout() {
        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawers();

    }

    /**
     * 创建长按事件 , 并添加拖拽操作
     */
    private void onOnLong(final View imageView) {

   /*   imageView.setOnTouchListener(new OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
              if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xxxx
                  return true;
              }
              else{
                  return false;
              }
          }
      });*/

        imageView.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new DragShadowBuilder(imageView);

                v.startDrag(data, shadowBuilder, imageView, 0);
                v.setVisibility(View.INVISIBLE);

                //这里将进行操作的对象取出来放到对尾，到删除的时候就是删除最近操作的view
                for (int i = mListPool.size() -1 ; i >= 0; i--) {
                    if (v == mListPool.get(i).getView()){
                        L.e("image onTouch == " + i );
                        mListPool.add( mListPool.remove(i) );
                        mRealViewPosition = mListPool.size() - 1 ;
                        break;
                    }
                }

                return true;
            }
        });
    }

    /**
     * 监听拖拽后的view的位置
     */
    @Override
    public boolean onDrag(View v, DragEvent event) {
        if (checkList() ) return false;

        mView = mListPool.get(mRealViewPosition).getView();

        int width = mView.getWidth();
        int height = mView.getHeight();

        //说明已经不再父布局内了
        if (event.getAction() == DragEvent.ACTION_DRAG_EXITED){

            mView.setVisibility(View.VISIBLE);
        }
        //这里就是手指抬起来的操作了， 可以布局view
        if (event.getAction() == DragEvent.ACTION_DROP){

            L.e("drop  parent x = " + event.getX() + " y = " + event.getY());
            L.e("view x = "+ width + " y = "+height);
            int x_cord = (int) event.getX() - width/2;
            int  y_cord = (int) event.getY()  - height/2;

            L.e("cord x = "+ x_cord + " y = "+y_cord);

            L.e("screen x = "+ getWindowWidth() + " y = "+getWindowHeight());


            if (x_cord <= 0 ) x_cord = 0;
            if (y_cord <= 0 ) y_cord = 0;

            if (x_cord >= (getWindowWidth() - width ) ) x_cord = (getWindowWidth() - width );
            if (y_cord >= (getWindowHeight() - height )) y_cord = (getWindowHeight() - height );


            L.e("over x= " +x_cord + " y = " +y_cord);
            LayoutParams params = (LayoutParams) mView.getLayoutParams();
            params.leftMargin = x_cord ;
            params.topMargin = y_cord;
            mView.setLayoutParams(params);
            bringChildToFront(mView);
            mView.setVisibility(View.VISIBLE);

            mX = -1 ;
            mY = -1 ;
            widthCanvas = -1 ;
            postInvalidate();
        }
        //这是一直在移动的过程， 准备划线
        if (event.getAction() == DragEvent.ACTION_DRAG_LOCATION){

            mX = (int) (event.getX() - width /2);
            mY = (int) (event.getY() - height / 2);
            widthCanvas = width ;
            L.e( " mx = "+mX + "  my = "+ mY  + "       " + event.getX() + "  " + event.getY());
            postInvalidate();
        }

        return true;
    }
    private int widthCanvas = -1 ;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画左上角的竖线
        canvas.drawLine(mX, 0 , mX , getWindowHeight() , mPaint);
        //画左上角的横线
        canvas.drawLine(0 , mY , getWindowWidth() , mY , mPaint);
        //画右上角的竖线
        canvas.drawLine(mX + widthCanvas , 0 , mX + widthCanvas , getWindowHeight() , mPaint );

        mPaint.setTextSize(spTopx(15));
        //画左上角的坐标
        canvas.drawText("( " + mX + " , " + mY +" )" , mX  , mY - dpToPx() , mPaint);
        //画右上角的坐标
        canvas.drawText("( " +( mX + widthCanvas) + " , " + mY +" )" , mX + widthCanvas   , mY - dpToPx() , mPaint);

    }

    /**
     * 获取当前的view总是
     */
    public int getViewCount(){
        return mListPool.size();
    }

    /**
     * 增加view的宽高
     * @param width  {@link #MATCH_PARENT} , {@link #HALF_PARENT} , other
     */
    public void addWidth(int width) {
        if (checkList() ) return;

        View imageView = mListPool.get(mRealViewPosition).getView();
        LayoutParams params = (LayoutParams) imageView.getLayoutParams();
        if (width == MATCH_PARENT){
            params.width = LayoutParams.MATCH_PARENT;
            params.topMargin = 0 ;
            params.leftMargin = 0 ;
        }else if (width == HALF_PARENT){
            params.width = getWindowWidth() / 2 ;
            params.topMargin = 0 ;
            params.leftMargin  = 0 ;
        }else {
            params.width  = imageView.getWidth() + dpToPx(width);
        }
        imageView.setLayoutParams(params);
    }

    /**
     * 检测list的集合
     */
    private boolean checkList() {
        if (mListPool.size() == 0) return true ;
        return false;
    }

    public void addHeight( int heightInit ) {
        if (checkList() ) return;

        View imageView = mListPool.get(mRealViewPosition).getView();
        LayoutParams params = (LayoutParams) imageView.getLayoutParams();
        if (heightInit == MATCH_PARENT){
            params.height = LayoutParams.MATCH_PARENT;
            params.topMargin = 0 ;
            params.leftMargin = 0 ;
        }else if (heightInit == HALF_PARENT){
            params.height = getWindowHeight() / 2 ;
            params.topMargin = 0 ;
            params.leftMargin = 0 ;
        }else {
            params.height  = imageView.getHeight() + dpToPx(heightInit);
        }
        imageView.setLayoutParams(params);

    }

    /**
     * 减去MINUS view的宽高
     * @param width #{@link #MATCH_PARENT  = 1 dp } , {@link #HALF_PARENT  <= 0 : 1 dp} , other
     */
    public void deleteWidth(int width) {
        if (checkList() ) return;

        View imageView = mListPool.get(mRealViewPosition).getView();
        LayoutParams params = (LayoutParams) imageView.getLayoutParams();
        if (width == MATCH_PARENT){
            params.width = dpToPx();
        }else if (width == HALF_PARENT){
            int result = imageView.getWidth() - getWindowWidth() / 2;
            params.width = result <= 0 ? dpToPx() : result;
        }else {
            params.width  = (imageView.getWidth() - width) <= 0 ? dpToPx() :  (imageView.getWidth() - width);
        }
        imageView.setLayoutParams(params);
    }

    public void deleteHeight(int height) {
        if (checkList() ) return;

        View imageView = mListPool.get(mRealViewPosition).getView();
        LayoutParams params = (LayoutParams) imageView.getLayoutParams();
        if (height == MATCH_PARENT){
            params.width = dpToPx();
        }else if (height == HALF_PARENT){
            int result = imageView.getWidth() - getWindowHeight() / 2;
            params.height = result <= 0 ? dpToPx() : result;
        }else {
            params.height  = (imageView.getHeight() - height) <= 0 ? dpToPx() :  (imageView.getHeight() - height);
        }
        imageView.setLayoutParams(params);
    }


    /**
     * 获取屏幕的宽
     */
    private int getWindowWidth() {
        final WindowManager windowManager = ((WindowManager) getContext().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.x;
    }
    /**
     * 获取屏幕的高 包括状态栏
     */
    private int getWindowHeight() {
        final WindowManager windowManager = ((WindowManager) getContext().getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        return point.y;
    }

    /**
     * 删除单个View
     */
    public void deleteView(){
        checkDrawerLayout();
        if (mListPool.size() <= 0  ) return;
        View remove = mListPool.remove(mRealViewPosition).getView();
        removeView(remove);
        mRealViewPosition = mListPool.size() -1;
    }
    /**
     * 删除整个view
     */
    public void deleteAllView(){
        checkDrawerLayout();
        mListPool.clear();
        removeAllViews();
    }


    //转换dp为px
    private   int dpToPx(int dip) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    //转换dp为px  默认最小宽高
    private   int dpToPx() {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (minDp * scale + 0.5f * (minDp >= 0 ? 1 : -1));
    }

    //转换sp为px
    public  int spTopx( float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * 初始化 新建view的 宽高
     */
    public void setWidthHeightInit(int widthInit , int heightInit){
        mWidthInit = widthInit;
        mHeightInit = heightInit;
    }

    /**
     * 保存所有的 view 已经状态
     */
    public List<CollectionViewState> getListView(){

        for (int i = 0; i < mListPool.size(); i++) {
            mListPool.get(i).update();
        }
        return mListPool;
    }
}
