package com.tgithubc.kumao.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.github.nukc.stateview.StateView;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.fragment.FragmentOperation;
import com.tgithubc.kumao.fragment.FragmentType;
import com.tgithubc.kumao.widget.swipeback.SwipeBackFragment;


/**
 * Created by tc :)
 */
public abstract class BaseFragment extends SwipeBackFragment implements  IStateView{

    private StateView mStateLayout;
    private int mFragmentType = FragmentType.TYPE_NONE;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_base, container, false);
        FrameLayout titleContainer = root.findViewById(R.id.base_title_container);
        FrameLayout contentContainer = root.findViewById(R.id.base_content_container);
        View content = inflater.inflate(getLayoutId(), contentContainer, true);
        if (isShowTitle()) {
            View titleView = onCreateTitleView(inflater, titleContainer);
            if (titleView == null) {
                titleContainer.setVisibility(View.GONE);
            } else {
                titleContainer.setVisibility(View.VISIBLE);
                titleContainer.addView(titleView);
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) contentContainer.getLayoutParams();
                int widthSpec = View.MeasureSpec.makeMeasureSpec(66666, View.MeasureSpec.EXACTLY);
                int heightSpec = View.MeasureSpec.makeMeasureSpec(66666, View.MeasureSpec.AT_MOST);
                titleContainer.measure(widthSpec, heightSpec);
                lp.topMargin = titleContainer.getMeasuredHeight();
            }
        } else {
            titleContainer.setVisibility(View.GONE);
        }
        if (isShowLCEE()) {
            mStateLayout = StateView.inject(content);
        }
        init(content, inflater, savedInstanceState);
        return root;
    }

    /**
     * 加载自定义title
     */
    protected View onCreateTitleView(LayoutInflater inflater, FrameLayout titleContainer) {
        return null;
    }


    /**
     * 是否显示title
     */
    protected boolean isShowTitle() {
        return mFragmentType != FragmentType.TYPE_NONE;
    }

    /**
     * 是否需要多状态视图管理
     */
    protected boolean isShowLCEE() {
        return false;
    }

    /**
     * 显示空页面
     */
    @Override
    public void showEmpty() {
        if (mStateLayout != null) {
            mStateLayout.showEmpty();
        }
    }

    /**
     * 显示错误重试
     */
    @Override
    public void showError() {
        if (mStateLayout != null) {
            mStateLayout.showRetry();
            mStateLayout.setOnRetryClickListener(this::onRetry);
        }
    }

    /**
     * 显示加载中
     */
    @Override
    public void showLoading() {
        if (mStateLayout != null) {
            mStateLayout.showLoading();
        }
    }

    /**
     * 显示内容
     */
    @Override
    public void showContent() {
        if (mStateLayout != null) {
            mStateLayout.showContent();
        }
    }

    @Override
    public void swipeToCloseFragment() {
        super.swipeToCloseFragment();
        Fragment top = FragmentOperation.getInstance().getTopFragment();
        if (this == top) {
            FragmentOperation.getInstance().pop();
        }
    }

    @Override
    public boolean isNeedSwipeBack() {
        // 默认viewpager里面的子tab都不添加左滑层级，
        // 同时所有sub的和full的默认可以左滑，
        // 不需要的话自己复写返回false
        return mFragmentType != FragmentType.TYPE_NONE;
    }

    public void setFragmentType(@FragmentType int type) {
        this.mFragmentType = type;
    }

    @FragmentType
    public int getFragmentType() {
        return mFragmentType;
    }

    public void onRetry() {
    }

    public void onNewIntent(Bundle bundle) {
    }

    /**
     * 内容布局，不包括title
     * @return
     */
    public abstract int getLayoutId();

    public abstract void init(View view, LayoutInflater inflater, Bundle savedInstanceState);
}
