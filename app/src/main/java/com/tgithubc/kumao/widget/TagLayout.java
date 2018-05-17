package com.tgithubc.kumao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.tgithubc.kumao.R;
import com.tgithubc.kumao.util.DPPXUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc:)
 */
public class TagLayout extends ViewGroup {

    //默认值
    public static final float DEFAULT_BORDER_WIDTH = 0.5f;// 边框宽度
    public static final float DEFAULT_TEXT_SIZE = 11.0f;// 文字size
    public static final float DEFAULT_CORNER_RADIUS = 1.0f;// 圆角半径
    public static final float DEFAULT_LR_PADDING = 3.0f;// 左右padding
    public static final float DEFAULT_TB_PADDING = 1.0f;// 上下padding
    public static final float DEFAULT_HORIZONTAL_RANGE = 8.0f;// 标签之间的水平距离
    public static final float DEFAULT_VERTICAL_RANGE = 4.0f;// 标签之间的垂直距离

    public static final int DEFAULT_BORDER_COLOR = Color.WHITE;// 边框Color
    public static final int DEFAULT_TEXT_COLOR = Color.WHITE;// 文字color
    public static final int DEFAULT_STYLE = 1;// 默认样式，空心

    private float mTextSize;

    private int mTextColor;
    private int mBorderWidth;
    private int mBorderColor;
    private int mCornerRadius;
    private int mLRPadding;
    private int mTBPadding;
    private int mHorizontalRange;
    private int mVerticalRange;
    private int mStyle;

    private OnTagClickListener mOnTagClickListener;

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.TagLayoutStyle);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TagLayout);
        try {
            mTextSize = a.getDimension(R.styleable.TagLayout_tag_text_size, DEFAULT_TEXT_SIZE);
            mTextColor = a.getColor(R.styleable.TagLayout_tag_text_color, DEFAULT_TEXT_COLOR);
            mBorderColor = a.getColor(R.styleable.TagLayout_tag_border_color, DEFAULT_BORDER_COLOR);
            mBorderWidth = (int) a.getDimension(R.styleable.TagLayout_tag_border_width, DEFAULT_BORDER_WIDTH);
            mCornerRadius = (int) a.getDimension(R.styleable.TagLayout_tag_corner_radius, DEFAULT_CORNER_RADIUS);
            mLRPadding = (int) a.getDimension(R.styleable.TagLayout_tag_lr_padding, DEFAULT_LR_PADDING);
            mTBPadding = (int) a.getDimension(R.styleable.TagLayout_tag_tb_padding, DEFAULT_TB_PADDING);
            mHorizontalRange = (int) a.getDimension(R.styleable.TagLayout_tag_horizontal_range, DEFAULT_HORIZONTAL_RANGE);
            mVerticalRange = (int) a.getDimension(R.styleable.TagLayout_tag_vertical_range, DEFAULT_VERTICAL_RANGE);
            mStyle = a.getInt(R.styleable.TagLayout_tag_style, DEFAULT_STYLE);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int width = 0;
        int height = 0;

        int line = 0;
        int currentLineWidth = 0;
        int currentLineHeight = 0;
        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = getChildAt(i);
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            currentLineWidth += childWidth;
            if (currentLineWidth > widthSize) {
                currentLineWidth = childWidth;
                height += currentLineHeight + mVerticalRange;
                currentLineHeight = childHeight;
                line++;
            } else {
                currentLineHeight = Math.max(currentLineHeight, childHeight);
            }
            currentLineWidth += mHorizontalRange;
        }

        if (line == 0) {
            width = currentLineWidth;
            width += getPaddingLeft() + getPaddingRight();
        } else {
            width = widthSize;
        }

        height += currentLineHeight;
        height += getPaddingTop() + getPaddingBottom();

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width,
                heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int parentTop = getPaddingTop();
        int parentLeft = getPaddingLeft();
        int parentRight = r - l - getPaddingRight();
        int parentBottom = b - t - getPaddingBottom();

        int childLeft = parentLeft;
        int childTop = parentTop;

        int currentLineHeight = 0;

        for (int i = 0, count = getChildCount(); i < count; i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();

            if (childLeft + width > parentRight) {
                childLeft = parentLeft;
                childTop += currentLineHeight + mVerticalRange;
                currentLineHeight = height;
            } else {
                currentLineHeight = Math.max(currentLineHeight, height);
            }
            child.layout(childLeft, childTop, childLeft + width, childTop + height);

            childLeft += width + mHorizontalRange;
        }
    }

    public List<String> getTags() {
        List<String> list = new ArrayList<>();
        for (int i = 0, count = getChildCount(); i < count; i++) {
            TagView tagView = getTagAt(i);
            if (tagView != null) {
                list.add(tagView.getText().toString());
            }
        }
        return list;
    }

    public void setTags(List<String> tagList) {
        removeAllViews();
        for (String tag : tagList) {
            appendTag(tag);
        }
    }

    public TagView getTagAt(int index) {
        return (TagView) getChildAt(index);
    }

    private void appendTag(CharSequence tag) {
        TagView tagView = new TagView(getContext(), tag);
        tagView.setOnClickListener(v -> {
            final TagView tag1 = (TagView) v;
            if (mOnTagClickListener != null) {
                mOnTagClickListener.onTagClick(tag1.getText().toString());
            }
        });
        addView(tagView);
    }

    public void setOnTagClickListener(OnTagClickListener l) {
        this.mOnTagClickListener = l;
    }

    public interface OnTagClickListener {

        void onTagClick(String tag);
    }

    private class TagView extends AppCompatTextView {

        private Paint mPaint;
        private RectF mRectF;

        public TagView(Context context, CharSequence tag) {
            super(context);
            setText(tag);
            setTextColor(mTextColor);
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            setPadding(DPPXUtil.dp2px(mLRPadding),
                    DPPXUtil.dp2px(mTBPadding),
                    DPPXUtil.dp2px(mLRPadding),
                    DPPXUtil.dp2px(mTBPadding));
            initPaint();
        }

        private void initPaint() {
            mRectF = new RectF();
            mPaint = new Paint();
            if (mStyle == DEFAULT_STYLE) {
                mPaint.setStyle(Paint.Style.STROKE);
            } else {
                mPaint.setStyle(Paint.Style.FILL);
            }
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(mBorderWidth);
            mPaint.setColor(mBorderColor);
        }

        @Override
        protected void onDraw(@NonNull Canvas canvas) {
            super.onDraw(canvas);
            mRectF.left = mRectF.top = 0.5f * mBorderWidth;
            mRectF.right = getMeasuredWidth() - mBorderWidth;
            mRectF.bottom = getMeasuredHeight() - mBorderWidth;
            canvas.drawRoundRect(mRectF, mCornerRadius, mCornerRadius, mPaint);
        }
    }
}