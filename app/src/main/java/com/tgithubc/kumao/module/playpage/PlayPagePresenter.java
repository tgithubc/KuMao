package com.tgithubc.kumao.module.playpage;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.message.IObserver;
import com.tgithubc.kumao.message.MessageBus;
import com.tgithubc.kumao.observer.IKuMaoObserver;
import com.tgithubc.kumao.service.PlayManager;
import com.tgithubc.kumao.service.PlayState;

/**
 * Created by tc :)
 */
public class PlayPagePresenter extends BasePresenter<IPlayPageContract.V> implements IPlayPageContract.P {

    private IObserver mPlayObserver = new IKuMaoObserver.IPlayObserver() {
        @Override
        public void onPlayRealStart() {
            Song song = PlayManager.getInstance().getCurrentSong();
            getView().refreshInfoView(song);
            getView().refreshPlayStateView(true);
        }

        @Override
        public void onPause() {
            getView().refreshPlayStateView(false);
        }

        @Override
        public void onContinuePlay() {
            getView().refreshPlayStateView(true);
        }
    };

    @Override
    public void onCreate() {
        MessageBus.instance().register(mPlayObserver);
        MessageBus.instance().getDefault(IKuMaoObserver.IChangeStatusBarObserver.class).onChangeStatusBar(false);
    }

    @Override
    public void onDestory() {
        MessageBus.instance().unRegister(mPlayObserver);
        MessageBus.instance().getDefault(IKuMaoObserver.IChangeStatusBarObserver.class).onChangeStatusBar(true);
    }

    @Override
    public void doPlay() {
        int state = PlayManager.getInstance().getPlayState();
        if (PlayState.STATE_PLAYING == state) {
            PlayManager.getInstance().pause();
        } else {
            PlayManager.getInstance().continuePlay();
        }
    }

    @Override
    public void doPlayNext() {
        PlayManager.getInstance().playNext();
    }

    @Override
    public void doPlayPrev() {
        PlayManager.getInstance().playPrev();
    }

    @Override
    public void switchPlayMode() {
        int mode = PlayManager.getInstance().getPlayMode();
        mode++;
        if (mode >= PlayManager.MODE_MAX) {
            mode = 0;
        }
        PlayManager.getInstance().setPlayMode(mode);
        getView().refreshPlayMode(mode);
    }
}
