package com.tgithubc.kumao.widget.dragLayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by tc:)
 */
public class StickyHeaderLayout extends LinearLayout {

    public interface IGetTargetViewListener {
        View getTargetView();

        int getDockedBarHeight();
    }

    public interface IHeaderHiddenListener {
        void onIsHidden(boolean hidden);

        void onHeaderScroll(float percent, int scrollDirection);
    }

    public static final int SCROLL_TO_NONE = -1;
    public static final int SCROLL_TO_UP = 0;
    public static final int SCROLL_TO_DOWN = 1;

    private IHeaderHiddenListener mHeaderHiddenListener;
    private IGetTargetViewListener mGetTargetViewListener;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private View mHeaderView, mContentView;

    private float mLastY;
    private int mHeaderHeight;
    // 这个从外部带过来的是停靠的高度
    private int mDockedBarHeight;
    private int mTouchSlop;
    private int mMaximumVelocity;
    private int mScrollDirection;
    //头部原始高度
    private int mHeaderInitialHeight;
    //回弹范围
    private int mPullDownDistance;
    private boolean isSticky;
    private boolean isExpand;
    private boolean isDispatch;
    private boolean isDragging;
    private boolean isControlled;
    private boolean isPullDownEnable;
    private boolean isSmoothScrollTop;
    private boolean isScrollEnable = true;

    public StickyHeaderLayout(Context context) {
        this(context, null);
    }

    public StickyHeaderLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyHeaderLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
        mMaximumVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mTouchSlop = (ViewConfiguration.get(context).getScaledTouchSlop());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() < 2) {
            throw new RuntimeException("StickyHeaderLayout must contains two child views!");
        }
        mHeaderView = getChildAt(0);
        mContentView = getChildAt(1);
        if (mHeaderHiddenListener != null) {
            mHeaderHiddenListener.onHeaderScroll(0, SCROLL_TO_NONE);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                StickyHeaderLayout.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //不属于这个layout的外部控件，谁先谁后初始化不能保证，高度外部传递
                mDockedBarHeight = mGetTargetViewListener == null ? 0 :
                        mGetTargetViewListener.getDockedBarHeight();
                mHeaderInitialHeight = mHeaderView.getMeasuredHeight();
                if (mPullDownDistance == 0) {
                    mPullDownDistance = mHeaderInitialHeight / 2;
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeaderHeight = mHeaderView.getMeasuredHeight() - mDockedBarHeight;
        ViewGroup.LayoutParams params = mContentView.getLayoutParams();
        params.height = getMeasuredHeight() - mDockedBarHeight;
        mContentView.setLayoutParams(params);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!isDispatch) {
                    mLastY = y;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastY - y;
                if (dy < 0
                        && Math.abs(dy) > mTouchSlop
                        && isControlled
                        && FirstItemUtils.isFirstItem(getInnerScrollView())) {
                    mLastY = y;
                    isControlled = false;
                    isDispatch = true;
                    ev.setAction(MotionEvent.ACTION_CANCEL);
                } else {
                    isDispatch = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityY = (int) mVelocityTracker.getYVelocity();
                mScrollDirection = velocityY < 0 ? SCROLL_TO_UP : SCROLL_TO_DOWN;
                recycleVelocityTracker();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void scrollTo(int x, int y) {
        if (!isScrollEnable) {
            return;
        }
        if (y < 0) {
            y = 0;
        }
        if (y > mHeaderHeight) {
            y = mHeaderHeight;
        }
        isSticky = y == mHeaderHeight;
        isExpand = y == 0;
        float v = (float) getScrollY() / (mHeaderHeight);
        if (mHeaderHiddenListener != null) {
            mHeaderHiddenListener.onHeaderScroll(v, mScrollDirection);
            mHeaderHiddenListener.onIsHidden(isSticky);
        }
        super.scrollTo(x, y);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastY - y;
                // 拦截触发点，分开写好维护
                if (!isSticky
                        && dy > 0
                        && Math.abs(dy) > mTouchSlop
                        && !FirstItemUtils.isFirstItem(getInnerScrollView())

                        || !isSticky
                        && Math.abs(dy) > mTouchSlop
                        && FirstItemUtils.isFirstItem(getInnerScrollView())

                        || isSticky
                        && dy < 0
                        && FirstItemUtils.isFirstItem(getInnerScrollView())) {
                    mLastY = y;
                    return true;
                } else if (Math.abs(dy) > mTouchSlop) {
                    isControlled = true;
                    isDragging = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isSticky && isDragging) {
                    isDragging = false;
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float y = ev.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastY - y;
                mScrollDirection = dy > 0 ? SCROLL_TO_UP : SCROLL_TO_DOWN;
                isControlled = false;
                if (!isDragging && Math.abs(dy) > mTouchSlop) {
                    isDragging = true;
                }
                if (isDragging) {
                    scrollBy(0, (int) dy);
                    if (isSticky) {
                        ev.setAction(MotionEvent.ACTION_DOWN);
                        dispatchTouchEvent(ev);
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                    } else {
                        if (isPullDownEnable && getScrollY() <= 0 && dy < 0) {
                            updateHeaderState(-(int) dy);
                        }
                    }
                    mLastY = y;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                recycleVelocityTracker();
                isDragging = false;
                break;
            case MotionEvent.ACTION_UP:
                isDragging = false;
                if (isPullDownEnable && getScrollY() >= 0) {
                    springBack();
                }
                if (mScrollDirection == SCROLL_TO_UP && !isSticky) {
                    if (getScrollY() >= mHeaderHeight * 0.15f) {
                        mScroller.startScroll(0, getScrollY(), 0, mHeaderHeight - getScrollY(), 600);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 600);
                    }
                    invalidate();
                } else if (mScrollDirection == SCROLL_TO_DOWN) {
                    mScroller.startScroll(0, getScrollY(), 0, -getScrollY(), 800);
                    invalidate();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        if (!mScroller.computeScrollOffset()) {
            return;
        }
        int currY = mScroller.getCurrY();
        // 处理特殊需求平滑滚动回到展开位置
        if (isSmoothScrollTop) {
            // 得置为下滚
            mScrollDirection = SCROLL_TO_DOWN;
            scrollTo(0, currY);
            View inner = getInnerScrollView();
            if (inner instanceof RecyclerView) {
                ((RecyclerView) inner).scrollToPosition(0);
            }
            if (getScrollY() == 0 && !mScroller.isFinished()) {
                mScroller.forceFinished(true);
                isSmoothScrollTop = false;
            }
            invalidate();
            return;
        }
        // 处理内部fling和自身平滑滚动
        if (mScrollDirection == SCROLL_TO_UP) {
            if (isSticky) {
                isControlled = true;
                View inner = getInnerScrollView();
                if (inner instanceof RecyclerView) {
                    ((RecyclerView) inner).fling(0, (int) mScroller.getCurrVelocity() / 4);
                }
                mScroller.forceFinished(true);
                return;
            } else {
                scrollTo(0, currY);
            }
        } else {
            if (FirstItemUtils.isFirstItem(getInnerScrollView())) {
                scrollTo(0, currY);
            }
        }
        invalidate();
    }

    private View getInnerScrollView() {
        if (mGetTargetViewListener != null) {
            return mGetTargetViewListener.getTargetView();
        }
        return null;
    }

    public void setHeaderHiddenListener(IHeaderHiddenListener l) {
        this.mHeaderHiddenListener = l;
    }

    public void setGetTargetViewListener(IGetTargetViewListener l) {
        this.mGetTargetViewListener = l;
    }

    public void setScrollEnable(boolean scrollEnable) {
        this.isScrollEnable = scrollEnable;
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    //用于悬停后平滑展开页面
    public void smoothScrollTop() {
        isSmoothScrollTop = true;
        mScroller.startScroll(getScrollX(), getScrollY(), 0, -getScrollY(), 500);
        postInvalidate();
    }

    // 用于下拉反弹--start
    public void setPullDownDistance(int pullDownDistance) {
        this.mPullDownDistance = pullDownDistance;
    }

    public void setPullDownEnable(boolean pullDownEnable) {
        this.isPullDownEnable = pullDownEnable;
    }

    private void springBack() {
        ValueAnimator springBack = ValueAnimator.ofInt(mHeaderView.getMeasuredHeight(), mHeaderInitialHeight);
        springBack.addUpdateListener(animation -> setHeaderViewParams((Integer) animation.getAnimatedValue()));
        springBack.setInterpolator(new DecelerateInterpolator());
        springBack.start();
    }

    private void updateHeaderState(int dy) {
        int height;
        int maxHeight = mHeaderInitialHeight + mPullDownDistance;
        if (mHeaderView.getMeasuredHeight() >= maxHeight) {
            height = maxHeight;
        } else {
            height = mHeaderView.getMeasuredHeight() + dy;
        }
        setHeaderViewParams(height);
    }

    private void setHeaderViewParams(int height) {
        ViewGroup.LayoutParams params = mHeaderView.getLayoutParams();
        params.height = height;
        mHeaderView.setLayoutParams(params);
    }
    // 用于下拉反弹---end
}
