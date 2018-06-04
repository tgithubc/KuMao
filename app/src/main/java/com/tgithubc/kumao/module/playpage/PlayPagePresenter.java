package com.tgithubc.kumao.module.playpage;


import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.message.MessageBus;
import com.tgithubc.kumao.observer.IKuMaoObserver;

/**
 * Created by tc :)
 */
public class PlayPagePresenter extends BasePresenter<IPlayPageContract.V> implements IPlayPageContract.P {


    @Override
    public void onCreate() {
        MessageBus.instance().getDefault(IKuMaoObserver.IChangeStatusBarObserver.class).onChangeStatusBar(false);
    }

    @Override
    public void onDestory() {
        MessageBus.instance().getDefault(IKuMaoObserver.IChangeStatusBarObserver.class).onChangeStatusBar(true);
    }
}
