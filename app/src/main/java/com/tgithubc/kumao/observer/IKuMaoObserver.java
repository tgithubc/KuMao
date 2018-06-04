package com.tgithubc.kumao.observer;

import com.tgithubc.kumao.message.IObserver;

/**
 * 聚合观察者
 * Created by tc :)
 */
public interface IKuMaoObserver extends IObserver {

    interface IFragmentSwipeBackObserver extends IKuMaoObserver {
        void onMainViewPagerVisible(boolean isVisible);
    }

    interface IChangeStatusBarObserver extends IKuMaoObserver {
        void onChangeStatusBar(boolean isDark);
    }
}
