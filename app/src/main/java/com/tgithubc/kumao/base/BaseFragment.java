package com.tgithubc.kumao.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.nukc.stateview.StateView;
import com.tgithubc.kumao.widget.swipeback.SwipeBackFragment;


/**
 * Created by tc :)
 */
public abstract class BaseFragment extends SwipeBackFragment {

    private StateView mStateLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutId(), container, false);
        init(root, inflater, savedInstanceState);
        if (isShowLCEE()) {
            mStateLayout = StateView.inject(root);
        }
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
    protected void showEmpty() {
        if (mStateLayout != null) {
            mStateLayout.showEmpty();
        }
    }

    /**
     * 显示错误重试
     */
    protected void showError() {
        if (mStateLayout != null) {
            mStateLayout.showRetry();
            mStateLayout.setOnRetryClickListener(this::onRetry);
        }
    }

    /**
     * 显示加载中
     */
    protected void showLoading() {
        if (mStateLayout != null) {
            mStateLayout.showLoading();
        }
    }

    /**
     * 显示内容
     */
    protected void showContent() {
        if (mStateLayout != null) {
            mStateLayout.showContent();
        }
    }

    public void onRetry() {
    }

    public abstract int getLayoutId();

    public abstract void init(View view, LayoutInflater inflater, Bundle savedInstanceState);
}
