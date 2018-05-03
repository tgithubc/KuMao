package com.tgithubc.kumao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nukc.stateview.StateView;
import com.tgithubc.kumao.fragment.FragmentOperation;
import com.tgithubc.kumao.fragment.FragmentType;
import com.tgithubc.kumao.widget.swipeback.SwipeBackFragment;


/**
 * Created by tc :)
 */
public abstract class BaseFragment extends SwipeBackFragment {

    private StateView mStateLayout;
    private int mFragmentType = FragmentType.TYPE_NONE;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), container, false);
        if (isShowLCEE()) {
            mStateLayout = StateView.inject(root);
        }
        init(root, inflater, savedInstanceState);
        return root;
    }

    /**
     * 是否需要多状态视图管理
     */
    public boolean isShowLCEE() {
        return false;
    }

    /**
     * 显示空页面
     */
    protected void baseShowEmpty() {
        if (mStateLayout != null) {
            mStateLayout.showEmpty();
        }
    }

    /**
     * 显示错误重试
     */
    protected void baseShowError() {
        if (mStateLayout != null) {
            mStateLayout.showRetry();
            mStateLayout.setOnRetryClickListener(this::onRetry);
        }
    }

    /**
     * 显示加载中
     */
    protected void baseShowLoading() {
        if (mStateLayout != null) {
            mStateLayout.showLoading();
        }
    }

    /**
     * 显示内容
     */
    protected void baseShowContent() {
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

    public abstract int getLayoutId();

    public abstract void init(View view, LayoutInflater inflater, Bundle savedInstanceState);
}
