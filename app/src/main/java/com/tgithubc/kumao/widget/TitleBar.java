package com.tgithubc.kumao.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tgithubc.kumao.R;

/**
 * Created by tc :)
 */
public class TitleBar extends RelativeLayout {

    private View mRootView;

    private View mLeftPanel;
    private ImageView mLeftIcon;
    private TextView mLeftText;

    private LinearLayout mTitlePanel;
    private TextView mMainTitle;
    private TextView mSubTitle;

    private View mRightPanel;
    private ImageView mRightIcon;
    private View mRightIconTip;
    private TextView mRightText;
    private CheckBox mCheckBox;

    private View mExtendPanel;
    private ImageView mExtendIcon;
    private View mExtendIconTip;
    private TextView mExtendText;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttr(context, attrs);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.titlebar, this, true);
        mRootView = getRootView();
        // title
        mTitlePanel = findViewById(R.id.title_panel);
        mMainTitle = findViewById(R.id.main_title);
        mSubTitle = findViewById(R.id.sub_title);
        // left
        mLeftPanel = findViewById(R.id.left_panel);
        mLeftIcon = findViewById(R.id.left_icon);
        mLeftText = findViewById(R.id.left_text);
        //right
        mRightPanel = findViewById(R.id.right_panel);
        mRightIcon = findViewById(R.id.right_icon);
        mRightText = findViewById(R.id.right_text);
        mCheckBox = findViewById(R.id.check_all);
        mRightIconTip = findViewById(R.id.right_icon_tip);
        //extend
        mExtendPanel = findViewById(R.id.extend_panel);
        mExtendIcon = findViewById(R.id.extend_icon);
        mExtendIconTip = findViewById(R.id.extend_icon_tip);
        mExtendText = findViewById(R.id.extend_text);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.KMTitleBar);
        try {
            int bkg = array.getResourceId(R.styleable.KMTitleBar_bgColor, -1);
            int mainStr = array.getResourceId(R.styleable.KMTitleBar_main_title_str, -1);
            int subStr = array.getResourceId(R.styleable.KMTitleBar_sub_title_str, -1);
            int leftIconRes = array.getResourceId(R.styleable.KMTitleBar_leftIconId, -1);
            int leftStr = array.getResourceId(R.styleable.KMTitleBar_leftStr, -1);
            int rightIconRes = array.getResourceId(R.styleable.KMTitleBar_rightIconId, -1);
            int rightStr = array.getResourceId(R.styleable.KMTitleBar_rightStr, -1);
            int checkBoxBkgId = array.getResourceId(R.styleable.KMTitleBar_checkBoxBkg, -1);
            boolean showCheckBox = array.getBoolean(R.styleable.KMTitleBar_showCheckBox, false);
            int extendIconRes = array.getResourceId(R.styleable.KMTitleBar_extentIconId, -1);
            int extendStr = array.getResourceId(R.styleable.KMTitleBar_extendStr, -1);

            if (extendStr != -1) {
                setRightText(extendStr);
            }
            if (extendIconRes != -1) {
                setExtendIcon(extendIconRes);
            }
            if (rightStr != -1) {
                setRightText(rightStr);
            }
            if (rightIconRes != -1) {
                setRightIcon(rightIconRes);
            }
            if (leftStr != -1) {
                setLeftText(leftStr);
            }
            if (leftIconRes != -1) {
                setLeftIcon(leftIconRes);
            }
            if (bkg != -1) {
                setTitleBackground(bkg);
            }
            if (mainStr != -1) {
                setMainTitle(mainStr);
            }
            if (subStr != -1) {
                setSubTitle(subStr);
            }
            if (showCheckBox) {
                showCheckBox();
                if (checkBoxBkgId != -1) {
                    mCheckBox.setBackgroundResource(checkBoxBkgId);
                } else {
                    mCheckBox.setBackgroundResource(R.drawable.checkbox_style);
                }
            }
        } finally {
            array.recycle();
        }
    }

    private TitleBar setLeftText(int leftStr) {
        mLeftPanel.setVisibility(View.VISIBLE);
        mLeftText.setVisibility(View.VISIBLE);
        mLeftText.setText(leftStr);
        mLeftIcon.setVisibility(View.GONE);
        return this;
    }

    private TitleBar setLeftText(CharSequence str) {
        mLeftPanel.setVisibility(View.VISIBLE);
        mLeftText.setVisibility(View.VISIBLE);
        mLeftText.setText(str);
        mLeftIcon.setVisibility(View.GONE);
        return this;
    }

    public TitleBar showCheckBox() {
        mRightPanel.setVisibility(View.VISIBLE);
        mCheckBox.setVisibility(View.VISIBLE);
        mRightIcon.setVisibility(View.GONE);
        mRightText.setVisibility(View.GONE);
        return this;
    }

    public TitleBar setTitleColor(int color) {
        mMainTitle.setTextColor(color);
        mSubTitle.setTextColor(color);
        return this;
    }

    public TitleBar setTitleBackground(int bkg) {
        mRootView.setBackgroundResource(bkg);
        return this;
    }

    public TitleBar setMainTitle(int resId) {
        mMainTitle.setText(resId);
        return this;
    }

    public TitleBar setMainTitle(CharSequence str) {
        mMainTitle.setText(str);
        return this;
    }

    public TitleBar setMainTitleColor(int color) {
        mMainTitle.setTextColor(color);
        return this;
    }

    public TitleBar setSubTitle(int resId) {
        mSubTitle.setText(resId);
        mSubTitle.setVisibility(VISIBLE);
        return this;
    }

    public TitleBar setSubTitleColor(int color) {
        mSubTitle.setTextColor(color);
        return this;
    }

    public TitleBar setSubTitle(CharSequence str) {
        mSubTitle.setText(str);
        mSubTitle.setVisibility(VISIBLE);
        return this;
    }

    public TitleBar setLeftIcon(int leftIconId) {
        mLeftPanel.setVisibility(View.VISIBLE);
        mLeftIcon.setBackgroundResource(leftIconId);
        mLeftIcon.setVisibility(View.VISIBLE);
        return this;
    }

    public TitleBar setRightIcon(int rightIconId) {
        mRightPanel.setVisibility(View.VISIBLE);
        mRightIcon.setBackgroundResource(rightIconId);
        mRightIcon.setVisibility(View.VISIBLE);
        mRightText.setVisibility(View.GONE);
        mCheckBox.setVisibility(View.GONE);
        return this;
    }

    public TitleBar setRightText(int rightStr) {
        mRightPanel.setVisibility(View.VISIBLE);
        mRightText.setText(rightStr);
        mRightText.setVisibility(View.VISIBLE);
        mRightIcon.setVisibility(View.GONE);
        mCheckBox.setVisibility(View.GONE);
        return this;
    }

    public TitleBar setRightText(CharSequence rightStr) {
        mRightPanel.setVisibility(View.VISIBLE);
        mRightText.setText(rightStr);
        mRightText.setVisibility(View.VISIBLE);
        mRightIcon.setVisibility(View.GONE);
        mCheckBox.setVisibility(View.GONE);
        return this;
    }

    public TitleBar setExtendIcon(int extendIconId) {
        mExtendPanel.setVisibility(View.VISIBLE);
        mExtendIcon.setBackgroundResource(extendIconId);
        mExtendIcon.setVisibility(View.VISIBLE);
        mExtendText.setVisibility(View.GONE);
        return this;
    }

    public TitleBar setExtendText(int rightStr) {
        mExtendPanel.setVisibility(View.VISIBLE);
        mExtendText.setText(rightStr);
        mExtendText.setVisibility(View.VISIBLE);
        mExtendIcon.setVisibility(View.GONE);
        return this;
    }

    public TitleBar setExtendText(CharSequence rightStr) {
        mExtendPanel.setVisibility(View.VISIBLE);
        mExtendText.setText(rightStr);
        mExtendText.setVisibility(View.VISIBLE);
        mExtendIcon.setVisibility(View.GONE);
        return this;
    }

    public TitleBar showSettingsTip(boolean show) {
        mRightIconTip.setVisibility(show ? View.VISIBLE : View.GONE);
        return this;
    }

    public TitleBar showExtendIconTip(boolean show) {
        mExtendIconTip.setVisibility(show ? View.VISIBLE : View.GONE);
        return this;
    }

    public TitleBar setTitleLeftAlign() {
        mTitlePanel.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        return this;
    }

    public TitleBar setLeftClickListener(final View.OnClickListener listener) {
        if (listener != null) {
            mLeftPanel.setVisibility(View.VISIBLE);
            mLeftPanel.setOnClickListener(listener);
            mLeftPanel.setFocusable(true);
        }
        return this;
    }

    public TitleBar setRightClickListener(final View.OnClickListener listener) {
        if (listener != null) {
            mRightPanel.setVisibility(View.VISIBLE);
            mRightPanel.setOnClickListener(listener);
            mRightPanel.setFocusable(true);
        }
        return this;
    }

    public TitleBar setExtendClickListener(final View.OnClickListener listener) {
        if (listener != null) {
            mExtendPanel.setVisibility(View.VISIBLE);
            mExtendPanel.setOnClickListener(listener);
            mExtendPanel.setFocusable(true);
        }
        return this;
    }

    public TitleBar setCheckboxClickListener(final CompoundButton.OnCheckedChangeListener listener) {
        if (listener != null) {
            showCheckBox();
            mCheckBox.setOnCheckedChangeListener(listener);
        }
        return this;
    }

    public LinearLayout getTitlePanel() {
        return mTitlePanel;
    }
}
