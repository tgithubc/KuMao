package com.tgithubc.kumao.widget.bottomtab;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.tgithubc.kumao.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
public class BottomTabLayout extends ViewGroup {

    // 子tab集合，不包括中间控件
    private List<BottomTabItemView> mTabs;

    // 该父容器默认宽高
    private int mDefaultWidth;
    private int mDefaultHeight;

    // 文字大小
    private int mTextSize;
    // 文字默认颜色
    private int mTextNormalColor;
    // 文字选中颜色
    private int mTextSelectColor;
    // icon大小
    private int mIconSize;

    // 中间的控件
    private View mCenterView;
    // 中间控件的宽度
    private int mCenterViewWidth;
    // 中间的控件资源id
    private int mCenterViewRes;
    // 中间控件的左右padding
    private int mCenterViewPaddingLR;
    // 是否显示中间的控件
    private boolean isShowCenterView;

    // 红点的size
    private int mRedDotSize;
    // 消息相关
    private int mMessageBkgColor;
    private int mMessageTextSize;
    private int mMessageTextColor;

    private OnTabSelectedListener mListener;
    // 记录当前选中的tab index
    private int mCurrentIndex;
    // 如果需要的话绑定ViewPager
    private ViewPager mViewPager;

    public BottomTabLayout(Context context) {
        this(context, null);
    }

    public BottomTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDefaultWidth = wm.getDefaultDisplay().getWidth();
        mDefaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
                getResources().getDisplayMetrics());
        // 默认文字大小
        int defaultTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12,
                getResources().getDisplayMetrics());
        // 默认中间控件宽度
        int defaultCenterViewWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80,
                getResources().getDisplayMetrics());
        // 默认红点大小
        int defaultRedDotSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics());
        // 默认消息文字大小
        int defaultMessageTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics());

        // 获取自定义属性，设置给子类
        final TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BottomTabStyle, 0, 0);
        try {
            mTextSize = ta.getDimensionPixelOffset(R.styleable.BottomTabStyle_bottom_tab_item_text_size,
                    defaultTextSize);
            mTextNormalColor = ta.getColor(R.styleable.BottomTabStyle_bottom_tab_item_text_normal_color,
                    Color.parseColor("#707070"));
            mTextSelectColor = ta.getColor(R.styleable.BottomTabStyle_bottom_tab_item_text_selected_color,
                    Color.BLACK);
            mIconSize = ta.getDimensionPixelOffset(R.styleable.BottomTabStyle_bottom_tab_center_view_width, 0);
            mCenterViewRes = ta.getResourceId(R.styleable.BottomTabStyle_bottom_tab_center_view, 0);
            mCenterViewWidth = ta.getDimensionPixelOffset(R.styleable.BottomTabStyle_bottom_tab_center_view_width,
                    defaultCenterViewWidth);
            mCenterViewPaddingLR = ta.getDimensionPixelOffset(R.styleable.BottomTabStyle_bottom_tab_center_view_padding_lr,
                    0);
            mRedDotSize = ta.getDimensionPixelOffset(R.styleable.BottomTabStyle_bottom_tab_item_red_dot_size,
                    defaultRedDotSize);
            mMessageTextColor = ta.getColor(R.styleable.BottomTabStyle_bottom_tab_item_message_tip_text_color,
                    Color.WHITE);
            mMessageTextSize = ta.getDimensionPixelOffset(R.styleable.BottomTabStyle_bottom_tab_item_message_text_size,
                    defaultMessageTextSize);
            mMessageBkgColor = ta.getColor(R.styleable.BottomTabStyle_bottom_tab_item_message_bkg_color,
                    Color.RED);
            isShowCenterView = mCenterViewRes != 0;
        } finally {
            ta.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = handlerMeasure(widthMeasureSpec, mDefaultWidth);
        int height = handlerMeasure(heightMeasureSpec, mDefaultHeight);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        // tab可用宽高
        int availableWidth = width - paddingLeft - paddingRight;
        int availableHeight = height - paddingTop - paddingBottom;
        int count = getChildCount();

        int centerViewIndex = (count - 1) / 2;
        if (isShowCenterView) {
            // 测量中间view，总体tab的可用宽要减去该中间view的宽，有中间控件的padding还得去掉padding
            View view = getChildAt(centerViewIndex);
            LayoutParams params = view.getLayoutParams();
            view.measure(getCenterViewMeasureSpec(mCenterViewWidth, params.width),
                    getCenterViewMeasureSpec(availableHeight, params.height));
            availableWidth = availableWidth - view.getMeasuredWidth() - mCenterViewPaddingLR * 2;
        }
        int widthSpec;
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            // 中间view已经再上面处理完了，只测量tab，宽度就是剩下的可用空间以count等分
            if (isShowCenterView && i == centerViewIndex) {
                continue;
            }
            widthSpec = MeasureSpec.makeMeasureSpec(
                    availableWidth / (isShowCenterView ? count - 1 : count),
                    MeasureSpec.EXACTLY);
            view.measure(widthSpec, MeasureSpec.makeMeasureSpec(availableHeight, MeasureSpec.EXACTLY));
        }
        setMeasuredDimension(width, height);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int parentLeft = getPaddingLeft();
        final int parentTop = getPaddingTop();
        final int parentBottom = b - t - getPaddingBottom();
        // 锚点
        int point = parentLeft;
        int count = getChildCount();
        int top, bottom;
        // 模仿线性布局位移按宽布局就行
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            int width = view.getMeasuredWidth();
            if (isShowCenterView && i == ((count - 1) / 2)) {
                top = (parentBottom - parentTop - view.getMeasuredHeight()) / 2 + parentTop;
                bottom = view.getMeasuredHeight() + top;
            } else {
                top = parentTop;
                bottom = parentBottom;
            }
            view.layout(point, top, point + width, bottom);
            if (isShowCenterView && mCenterViewPaddingLR > 0
                    && (i == ((count - 1) / 2) - 1 || i == ((count - 1) / 2))) {
                point = point + width + mCenterViewPaddingLR;
            } else {
                point += width;
            }
        }
    }

    public int handlerMeasure(int measureSpec, int defaultSize) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = defaultSize;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(defaultSize, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     * 生成限定中间view的测量规格
     *
     * @param available
     * @param paramsWH
     * @return
     */
    private int getCenterViewMeasureSpec(int available, int paramsWH) {
        int widthSpec;
        switch (paramsWH) {
            case LayoutParams.WRAP_CONTENT:
                widthSpec = MeasureSpec.makeMeasureSpec(available, MeasureSpec.AT_MOST);
                break;
            case LayoutParams.MATCH_PARENT:
                widthSpec = MeasureSpec.makeMeasureSpec(available, MeasureSpec.EXACTLY);
                break;
            default:
                widthSpec = MeasureSpec.makeMeasureSpec(Math.min(paramsWH, available),
                        MeasureSpec.EXACTLY);
                break;
        }
        return widthSpec;
    }

    public void bindViewPage(ViewPager viewPager) {
        if (viewPager == null || mViewPager == viewPager) {
            return;
        }
        this.mViewPager = viewPager;
        mViewPager.clearOnPageChangeListeners();
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                setSelected(position);
            }
        });
    }


    public void addOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        this.mListener = onTabSelectedListener;
    }

    /**
     * 获得对应index的tab view
     *
     * @param index
     * @return
     */
    public BottomTabItemView getTabByIndex(int index) {
        if (index < 0 || index >= mTabs.size()) {
            return null;
        }
        return mTabs.get(index);
    }

    /**
     * 设置红点
     *
     * @param index 需要显示红点的tab对应的角标
     */
    public void setRedDot(int index) {
        BottomTabItemView view = getTabByIndex(index);
        if (view != null) {
            view.showRedDot(true);
        }
    }

    /**
     * 隐藏红点
     *
     * @param index 需要隐藏红点的tab对应的角标
     */
    public void hideRedDot(int index) {
        BottomTabItemView view = getTabByIndex(index);
        if (view != null && view.hasRedDot()) {
            view.showRedDot(false);
        }
    }

    /**
     * 隐藏所有红点
     */
    public void hideAllRedDot() {
        if (mTabs == null || mTabs.isEmpty()) {
            return;
        }
        for (int i = 0, size = mTabs.size(); i < size; i++) {
            hideRedDot(i);
        }
    }

    /**
     * 设置选中
     *
     * @param index 需要选中的tab对应的角标
     */
    public void setSelected(int index) {
        if (mViewPager != null) {
            mViewPager.setCurrentItem(index, false);
        }
        if (mListener != null) {
            mListener.onTabSelected(index, mCurrentIndex);
        }
        for (int i = 0, size = mTabs.size(); i < size; i++) {
            BottomTabItemView view = getTabByIndex(i);
            if (view != null) {
                view.selected(index == i);
            }
        }
        mCurrentIndex = index;
    }

    /**
     * 设置消息
     *
     * @param index   要显示消息的tab index
     * @param message 消息数量
     */
    public void setMessage(int index, int message) {
        BottomTabItemView view = getTabByIndex(index);
        if (view != null) {
            view.setMessage(message);
        }
    }

    /**
     * 隐藏消息
     *
     * @param index 要隐藏消息的tab index
     */
    public void hideMessage(int index) {
        BottomTabItemView view = getTabByIndex(index);
        if (view != null && view.getUnReadMessage() > 0) {
            view.setMessage(0);
        }
    }

    /**
     * 获取当前选中的tab index
     *
     * @return
     */
    public int getCurrentIndex() {
        return mCurrentIndex;
    }

    /**
     * 构造器
     *
     * @return
     */
    public BottomTabAdapter createAdapter() {
        return new BottomTabAdapter();
    }

    public class BottomTabAdapter {

        public BottomTabAdapter() {
            mTabs = new ArrayList<>();
        }

        public BottomTabAdapter addTab(BottomTabItemView tab) {
            mTabs.add(tab);
            return BottomTabAdapter.this;
        }

        public BottomTabLayout build() {
            if (mTabs.isEmpty()) {
                return null;
            }
            // 根据add接受的数据构造tab
            for (int i = 0, size = mTabs.size(); i < size; i++) {

                // 设置tab的字体，颜色等显示相关等个性化数据
                BottomTabItemView tab = mTabs.get(i);
                final int finalI = i;
                tab.setTextSize(mTextSize)
                        .setNormalTextColor(mTextNormalColor)
                        .setSelectTextColor(mTextSelectColor)
                        .setIconSize(mIconSize)
                        .setRedDotSize(mRedDotSize)
                        .setMessageBkgColor(mMessageBkgColor)
                        .setMessageTextSize(mMessageTextSize)
                        .setMessageTextColor(mMessageTextColor)
                        .setOnClickListener(view -> {
                            if (mCurrentIndex == finalI) {
                                if (mListener != null) {
                                    mListener.onTabReselected(finalI);
                                }
                            } else {// 临时记住上一次点击的
                                int preIndex = mCurrentIndex;
                                // 再把新位置赋给current
                                mCurrentIndex = finalI;
                                // 反选上次的，选中当前的
                                mTabs.get(mCurrentIndex).selected(true);
                                mTabs.get(preIndex).selected(false);
                                if (mListener != null) {
                                    mListener.onTabSelected(mCurrentIndex, preIndex);
                                    if (mViewPager != null) {
                                        mViewPager.setCurrentItem(mCurrentIndex, false);
                                    }
                                }
                            }
                            // 点击隐藏当前tab的红点
                            hideRedDot(mCurrentIndex);
                        });
                addView(tab);
            }

            // 构造完tab再把中间view塞进去
            if (isShowCenterView) {
                mCenterView = LayoutInflater.from(getContext()).inflate(mCenterViewRes,
                        (ViewGroup) getParent(), false);
                mCenterView.setOnClickListener(view -> {
                    if (mListener != null) {
                        mListener.onCenterViewClicked(mCenterView);
                    }
                });
                addView(mCenterView, mTabs.size() / 2);
            }
            return BottomTabLayout.this;
        }
    }
}