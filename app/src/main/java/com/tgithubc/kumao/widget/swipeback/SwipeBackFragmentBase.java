package com.tgithubc.kumao.widget.swipeback;

/**
 * @author Yrom
 */
public interface SwipeBackFragmentBase {

    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    void swipeToCloseFragment();
}
