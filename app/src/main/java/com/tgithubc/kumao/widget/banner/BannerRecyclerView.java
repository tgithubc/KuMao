package com.tgithubc.kumao.widget.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Message;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.util.DPPXUtil;
import com.tgithubc.kumao.util.WeakWrapperHandler;

import java.util.List;

/**
 * Created by tc :)
 */
public class BannerRecyclerView extends FrameLayout implements WeakWrapperHandler.MessageHandler {

    private static final int MSG_WHAT_SCROLL = 0x01;
    private static final int SCROLL_SPEED = 3000;
    private static final int DEFAULT_SELECTED_COLOR = 0xffffffff;
    private static final int DEFAULT_UNSELECTED_COLOR = 0x50ffffff;
    private static final int DEFAULT_INDICATOR_SIZE = 4;
    private static final int DEFAULT_INDICATOR_LR_MARGIN = 5;
    private static final int DEFAULT_INDICATOR_TB_MARGIN = 10;

    private Drawable mSelectedIndicatorDrawable, mUnSelectedIndicatorDrawable;
    private RecyclerView mRecyclerView;
    private LinearLayout mLinearLayout;
    private BannerAdapter mAdapter;
    private WeakWrapperHandler mScrollHandler;
    private OnBannerScrollListener mScrollListener;
    private boolean isShowIndicator = true;
    private boolean isScrollEnable;
    private int mCurrentIndex;
    private int mIndicatorSize;
    private int mIndicatorLRMargin;
    private float mDownX;
    private float mDownY;

    public BannerRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public BannerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrRes(context, attrs);
    }

    private void initAttrRes(Context context, AttributeSet attrs) {

        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BannerRecyclerView, 0, 0);
        int gravity;
        int indicatorTBMargin;
        try {
            Drawable selected = ta.getDrawable(R.styleable.BannerRecyclerView_banner_indicator_selected_icon);
            initSelectedIndicatorDrawable(selected);
            Drawable unSelected = ta.getDrawable(R.styleable.BannerRecyclerView_banner_indicator_unselected_icon);
            initUnSelectedIndicatorDrawable(unSelected);
            mIndicatorSize = ta.getDimensionPixelSize(R.styleable.BannerRecyclerView_banner_indicator_size,
                    DPPXUtil.dp2px(DEFAULT_INDICATOR_SIZE));
            mIndicatorLRMargin = ta.getDimensionPixelSize(R.styleable.BannerRecyclerView_banner_indicator_lr_margin,
                    DPPXUtil.dp2px(DEFAULT_INDICATOR_LR_MARGIN));
            gravity = ta.getInt(R.styleable.BannerRecyclerView_banner_indicator_layout_gravity, 0);
            indicatorTBMargin = ta.getDimensionPixelSize(R.styleable.BannerRecyclerView_banner_indicator_tb_margin,
                    DPPXUtil.dp2px(DEFAULT_INDICATOR_TB_MARGIN));
        } finally {
            ta.recycle();
        }
        mScrollHandler = new WeakWrapperHandler(this);

        mRecyclerView = new RecyclerView(context);
        mRecyclerView.setLayoutManager(new BannerLayoutManager(context));
        mRecyclerView.addOnScrollListener(new BannerRecyclerViewScrollListener());
        PagerSnapHelper snap = new PagerSnapHelper();
        snap.attachToRecyclerView(mRecyclerView);
        LayoutParams rvp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRecyclerView, rvp);

        mLinearLayout = new LinearLayout(context);
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams llp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        if (gravity == 0) {
            gravity = Gravity.CENTER;
        } else if (gravity == 1) {
            gravity = Gravity.LEFT;
        } else if (gravity == 2) {
            gravity = Gravity.RIGHT;
        }
        llp.gravity = Gravity.BOTTOM | gravity;
        // 竖向margin给父布局省事
        llp.setMargins(0, indicatorTBMargin, 0, indicatorTBMargin);
        addView(mLinearLayout, llp);
    }

    public void setBannerList(BannerHolderCreator creator, List bannerList) {
        if (bannerList == null || bannerList.isEmpty()) {
            return;
        }
        mAdapter = new BannerAdapter(creator, bannerList);
        mRecyclerView.setAdapter(mAdapter);
        int size = bannerList.size();
        boolean scroll = size > 1;
        if (scroll) {
            // 初始就往10倍了整，防止有用户喜欢反着滑着玩，滑到头滑不动了
            mCurrentIndex = bannerList.size() * 10;
            mRecyclerView.scrollToPosition(mCurrentIndex);
            initIndicator(size);
        } else {
            mCurrentIndex = 0;
        }
        setScroll(scroll);
    }

    public void setOnBannerItemClickListener(OnBannerItemClickListener listener) {
        if (mAdapter != null && listener != null) {
            mAdapter.setOnBannerItemClickListener(listener);
        }
    }

    public void setOnBannerScrollListener(OnBannerScrollListener listener) {
        mScrollListener = listener;
    }

    private void initUnSelectedIndicatorDrawable(Drawable unSelected) {
        if (unSelected == null) {
            mUnSelectedIndicatorDrawable = getDefaultIndicatorDrawable(DEFAULT_UNSELECTED_COLOR);
        } else {
            if (unSelected instanceof ColorDrawable) {
                mUnSelectedIndicatorDrawable = getDefaultIndicatorDrawable(((ColorDrawable) unSelected).getColor());
            } else {
                mUnSelectedIndicatorDrawable = unSelected;
            }
        }
    }

    private void initSelectedIndicatorDrawable(Drawable selected) {
        if (selected == null) {
            mSelectedIndicatorDrawable = getDefaultIndicatorDrawable(DEFAULT_SELECTED_COLOR);
        } else {
            if (selected instanceof ColorDrawable) {
                mSelectedIndicatorDrawable = getDefaultIndicatorDrawable(((ColorDrawable) selected).getColor());
            } else {
                mSelectedIndicatorDrawable = selected;
            }
        }
    }

    private Drawable getDefaultIndicatorDrawable(int color) {
        int size = DPPXUtil.dp2px(DEFAULT_INDICATOR_SIZE);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setSize(size, size);
        drawable.setCornerRadius(size);
        drawable.setColor(color);
        return drawable;
    }

    private void initIndicator(int size) {
        mLinearLayout.removeAllViews();
        for (int i = 0; i < size; i++) {
            ImageView indicator = new ImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.width = lp.height = mIndicatorSize;
            lp.leftMargin = lp.rightMargin = mIndicatorLRMargin / 2;
            indicator.setImageDrawable(i == 0 ? mSelectedIndicatorDrawable : mUnSelectedIndicatorDrawable);
            mLinearLayout.addView(indicator, lp);
        }
    }

    private void refreshIndicator() {
        if (!isShowIndicator) {
            return;
        }
        if (mLinearLayout == null || mLinearLayout.getChildCount() <= 0 || mAdapter.getData() == null) {
            return;
        }
        for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
            ((ImageView) mLinearLayout.getChildAt(i)).setImageDrawable(
                    i == mCurrentIndex % mAdapter.getData().size() ? mSelectedIndicatorDrawable : mUnSelectedIndicatorDrawable);
        }
    }

    public BannerAdapter getAdapter() {
        return mAdapter;
    }

    public void showIndicator(boolean show) {
        isShowIndicator = show;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = ev.getX();
                mDownY = ev.getY();
                setScroll(false);
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();
                if (Math.abs(moveX - mDownX) > Math.abs(moveY - mDownY)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setScroll(true);
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setScroll(boolean isScroll) {
        mScrollHandler.removeMessages(MSG_WHAT_SCROLL);
        if (isScroll) {
            mScrollHandler.sendEmptyMessageDelayed(MSG_WHAT_SCROLL, SCROLL_SPEED);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setScroll(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        // 在滑动不可见的时候，置一个不可见flag
        isScrollEnable = visibility == VISIBLE;
        setScroll(isScrollEnable);
    }

    @Override
    public void handleMessage(Message msg) {
        if (!isScrollEnable
                || this.getVisibility() != View.VISIBLE) {
            return;
        }
        if (msg.what == MSG_WHAT_SCROLL) {
            mRecyclerView.smoothScrollToPosition(++mCurrentIndex);
            refreshIndicator();
            mScrollHandler.sendEmptyMessageDelayed(MSG_WHAT_SCROLL, SCROLL_SPEED);
        }
    }

    private class BannerRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mAdapter.getData() == null || mAdapter.getData().size() < 2) {
                return;
            }
            if (mScrollListener != null) {
                mScrollListener.onScrolled(recyclerView, dx, dy);
            }
            //处理连续快速滑动时指示器更新
            int firstVisable = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            View viewFirst = recyclerView.getLayoutManager().findViewByPosition(firstVisable);
            float width = getWidth();
            if (width != 0 && viewFirst != null) {
                float right = viewFirst.getRight();
                float ratio = right / width;
                if (ratio >= 0.8f) {
                    if (mCurrentIndex != firstVisable) {
                        mCurrentIndex = firstVisable;
                        refreshIndicator();
                    }
                } else if (ratio <= 0.2f) {
                    if (mCurrentIndex != firstVisable + 1) {
                        mCurrentIndex = firstVisable + 1;
                        refreshIndicator();
                    }
                }
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            if (first == last && mCurrentIndex != last) {
                mCurrentIndex = last;
                refreshIndicator();
                if (mScrollListener != null) {
                    mScrollListener.onScrollStateChanged(recyclerView, newState, mCurrentIndex);
                }
            }
        }
    }
}
