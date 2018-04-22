package com.tgithubc.kumao.observer;

import com.tgithubc.kumao.message.IObserver;

/**
 * 聚合观察者
 * Created by tc :)
 */
public interface IKuMaoObserver extends IObserver {

    interface ISwipeBackObserver extends IKuMaoObserver {
        void onMainViewPagerVisible(boolean isVisible);
    }
}
