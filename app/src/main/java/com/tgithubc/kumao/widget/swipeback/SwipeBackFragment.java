
package com.tgithubc.kumao.widget.swipeback;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;

import com.tgithubc.kumao.R;

public class SwipeBackFragment extends Fragment implements SwipeBackFragmentBase {

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        if (isNeedSwipeBack()) {
            mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(getActivity()).inflate(
                    R.layout.swipeback_layout, null);
            mSwipeBackLayout.attachToFragment(this);
        }
        setSwipeBackEnable(isNeedSwipeBack());
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mSwipeBackLayout;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        if (mSwipeBackLayout != null) {
            mSwipeBackLayout.setEnableGesture(enable);
        }
    }

    @Override
    public void swipeToCloseFragment() {
    }

    /**
     * 是否需要+上左滑退出布局层级，除了4个主tab和内部子页面绝大数页面应该都要
     * 需要动态改变左滑是否可用的话，再加了布局层级（return true）的基础上去调用setSwipeBackEnable（true/false）
     *
     * @return true是需要，false是不需要
     */
    public boolean isNeedSwipeBack() {
        return false;
    }
}
