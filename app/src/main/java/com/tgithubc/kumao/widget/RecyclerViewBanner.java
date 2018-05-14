package com.tgithubc.kumao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.util.DPPXUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 修改自
 * https://github.com/loonggg/RecyclerViewBanner
 * 里面还有个问题，内部的ImageView不应该写死啊，外面没法灵活改变图片加载方式了
 */
public class RecyclerViewBanner extends FrameLayout {

    private static final int DEFAULT_SELECTED_COLOR = 0xffffffff;
    private static final int DEFAULT_UNSELECTED_COLOR = 0x50ffffff;

    private Drawable mSelectedDrawable;
    private Drawable mUnselectedDrawable;
    private int mSize;
    private int mSpace;
    private int mInterval;
    private int mStartX, mStartY, mCurrentIndex;

    private RecyclerView mRecyclerView;
    private LinearLayout mLinearLayout;

    private RecyclerAdapter mAdapter;
    private OnItemClickListener mItemClickListener;
    private OnItemChangeListener mItemChangeListener;
    private List<Object> mData = new ArrayList<>();
    private Handler mHandler = new Handler();

    private boolean isShowIndicator;
    private boolean isTouched;
    private boolean isPlaying;
    private boolean isAutoPlaying = true;

    private Runnable mPlayTask = new Runnable() {

        @Override
        public void run() {
            mRecyclerView.smoothScrollToPosition(++mCurrentIndex);
            if (isShowIndicator) {
                switchIndicator();
            }
            mHandler.postDelayed(this, mInterval);
        }
    };

    public RecyclerViewBanner(Context context) {
        this(context, null);
    }

    public RecyclerViewBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewBanner);
        mInterval = a.getInt(R.styleable.RecyclerViewBanner_rvb_interval, 3500);
        isShowIndicator = a.getBoolean(R.styleable.RecyclerViewBanner_rvb_showIndicator, true);
        isAutoPlaying = a.getBoolean(R.styleable.RecyclerViewBanner_rvb_autoPlaying, true);
        Drawable sd = a.getDrawable(R.styleable.RecyclerViewBanner_rvb_indicatorSelectedSrc);
        Drawable usd = a.getDrawable(R.styleable.RecyclerViewBanner_rvb_indicatorUnselectedSrc);
        if (sd == null) {
            mSelectedDrawable = generateDefaultDrawable(DEFAULT_SELECTED_COLOR);
        } else {
            if (sd instanceof ColorDrawable) {
                mSelectedDrawable = generateDefaultDrawable(((ColorDrawable) sd).getColor());
            } else {
                mSelectedDrawable = sd;
            }
        }
        if (usd == null) {
            mUnselectedDrawable = generateDefaultDrawable(DEFAULT_UNSELECTED_COLOR);
        } else {
            if (usd instanceof ColorDrawable) {
                mUnselectedDrawable = generateDefaultDrawable(((ColorDrawable) usd).getColor());
            } else {
                mUnselectedDrawable = usd;
            }
        }
        mSize = a.getDimensionPixelSize(R.styleable.RecyclerViewBanner_rvb_indicatorSize, 0);
        mSpace = a.getDimensionPixelSize(R.styleable.RecyclerViewBanner_rvb_indicatorSpace, DPPXUtil.dip2px(4));
        int margin = a.getDimensionPixelSize(R.styleable.RecyclerViewBanner_rvb_indicatorMargin, DPPXUtil.dip2px(8));
        int g = a.getInt(R.styleable.RecyclerViewBanner_rvb_indicatorGravity, 1);
        int gravity;
        if (g == 0) {
            gravity = GravityCompat.START;
        } else if (g == 2) {
            gravity = GravityCompat.END;
        } else {
            gravity = Gravity.CENTER;
        }
        a.recycle();

        mRecyclerView = new RecyclerView(context);
        mLinearLayout = new LinearLayout(context);

        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new ScrollerLinearLayoutManager(context));
        mAdapter = new RecyclerAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (first == last && mCurrentIndex != last) {
                        mCurrentIndex = last;
                        if (isShowIndicator && isTouched) {
                            isTouched = false;
                            switchIndicator();
                        }
                    }
                }
            }
        });
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLinearLayout.setGravity(Gravity.CENTER);

        LayoutParams vpLayoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutParams linearLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.gravity = Gravity.BOTTOM | gravity;
        linearLayoutParams.setMargins(margin, margin, margin, margin);
        addView(mRecyclerView, vpLayoutParams);
        addView(mLinearLayout, linearLayoutParams);
    }

    /**
     * 默认指示器是一系列直径为6dp的小圆点
     */
    private GradientDrawable generateDefaultDrawable(int color) {
        int size = DPPXUtil.dip2px(6);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(size, size);
        gradientDrawable.setCornerRadius(size);
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    /**
     * 设置是否显示指示器导航点
     *
     * @param show 显示
     */
    public void isShowIndicator(boolean show) {
        this.isShowIndicator = show;
    }

    /**
     * 设置轮播间隔时间
     *
     * @param millisecond 时间毫秒
     */
    public void setIndicatorInterval(int millisecond) {
        this.mInterval = millisecond;
    }

    /**
     * 设置是否自动播放
     *
     * @param playing 开始播放
     */
    private synchronized void setPlaying(boolean playing) {
        if (isAutoPlaying) {
            if (!isPlaying && playing && mAdapter != null && mAdapter.getItemCount() > 2) {
                mHandler.postDelayed(mPlayTask, mInterval);
                isPlaying = true;
            } else if (isPlaying && !playing) {
                mHandler.removeCallbacksAndMessages(null);
                isPlaying = false;
            }
        }
    }

    /**
     * 设置是否禁止滚动播放
     *
     * @param isAutoPlaying true  是自动滚动播放,false 是禁止自动滚动
     */
    public void setAutoPlaying(boolean isAutoPlaying) {
        this.isAutoPlaying = isAutoPlaying;
    }

    /**
     * 设置轮播数据集
     *
     * @param data Banner对象列表
     */
    public void setBannerData(List data) {
        setPlaying(false);
        mData.clear();
        if (data != null) {
            mData.addAll(data);
        }
        if (mData.size() > 1) {
            mCurrentIndex = mData.size();
            mAdapter.notifyDataSetChanged();
            mRecyclerView.scrollToPosition(mCurrentIndex);
            if (isShowIndicator) {
                createIndicators();
            }
            setPlaying(true);
        } else {
            mCurrentIndex = 0;
            mAdapter.notifyDataSetChanged();
        }
    }

    private void createIndicators() {
        mLinearLayout.removeAllViews();
        for (int i = 0; i < mData.size(); i++) {
            ImageView img = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = mSpace / 2;
            lp.rightMargin = mSpace / 2;
            if (mSize >= DPPXUtil.dip2px(4)) { // 设置了indicatorSize属性
                lp.width = lp.height = mSize;
            } else {
                // 如果设置的resource.xml没有明确的宽高，默认最小2dp，否则太小看不清
                img.setMinimumWidth(DPPXUtil.dip2px(2));
                img.setMinimumHeight(DPPXUtil.dip2px(2));
            }
            img.setImageDrawable(i == 0 ? mSelectedDrawable : mUnselectedDrawable);
            mLinearLayout.addView(img, lp);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //手动触摸的时候，停止自动播放，根据手势变换
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) ev.getX();
                mStartY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                int disX = moveX - mStartX;
                int disY = moveY - mStartY;
                boolean hasMoved = 2 * Math.abs(disX) > Math.abs(disY);
                getParent().requestDisallowInterceptTouchEvent(hasMoved);
                if (hasMoved) {
                    setPlaying(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!isPlaying) {
                    isTouched = true;
                    setPlaying(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setPlaying(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        setPlaying(visibility == VISIBLE);
        super.onWindowVisibilityChanged(visibility);
    }

    /**
     * RecyclerView适配器
     */
    private class RecyclerAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            SimpleDraweeView img = new SimpleDraweeView(parent.getContext());
            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            img.setLayoutParams(params);
            img.setId(R.id.rvb_banner_image_view_id);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setOnClickListener(v -> {
                if (mItemClickListener != null) {
                    mItemClickListener.onClick(mCurrentIndex % mData.size());
                }
            });
            return new RecyclerView.ViewHolder(img) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SimpleDraweeView img = holder.itemView.findViewById(R.id.rvb_banner_image_view_id);
            if (mItemChangeListener != null) {
                mItemChangeListener.switchBanner(position % mData.size(), img);
            }
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size() < 2 ? mData.size() : Integer.MAX_VALUE;
        }
    }

    private class PagerSnapHelper extends LinearSnapHelper {

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
            int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
            final View currentView = findSnapView(layoutManager);
            if (targetPos != RecyclerView.NO_POSITION && currentView != null) {
                int currentPos = layoutManager.getPosition(currentView);
                int first = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                int last = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                currentPos = targetPos < currentPos ? last : (targetPos > currentPos ? first : currentPos);
                targetPos = targetPos < currentPos ? currentPos - 1 : (targetPos > currentPos ? currentPos + 1 : currentPos);
            }
            return targetPos;
        }
    }

    private class ScrollerLinearLayoutManager extends LinearLayoutManager {
        private float MILLISECONDS_PER_INCH = 0.5f;  //修改可以改变数据,越大速度越慢
        private Context mContext;

        public ScrollerLinearLayoutManager(Context context) {
            super(context, LinearLayoutManager.HORIZONTAL, false);
            this.mContext = context;
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller linearSmoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        @Override
                        public PointF computeScrollVectorForPosition(int targetPosition) {
                            return ScrollerLinearLayoutManager.this.computeScrollVectorForPosition(targetPosition);
                        }

                        //This returns the milliseconds it takes to
                        //scroll one pixel.
                        //返回滑动一个pixel需要多少毫秒
                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            return MILLISECONDS_PER_INCH / displayMetrics.density;

                        }

                    };
            linearSmoothScroller.setTargetPosition(position);
            startSmoothScroll(linearSmoothScroller);
        }
    }

    /**
     * 改变导航的指示点
     */
    private void switchIndicator() {
        if (mLinearLayout != null && mLinearLayout.getChildCount() > 0) {
            for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
                ((ImageView) mLinearLayout.getChildAt(i)).setImageDrawable(
                        i == mCurrentIndex % mData.size() ? mSelectedDrawable : mUnselectedDrawable);
            }
        }
    }

    public interface OnItemChangeListener {
        void switchBanner(int position, ImageView bannerView);
    }

    public void setItemChangeListener(OnItemChangeListener listener) {
        this.mItemChangeListener = listener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}