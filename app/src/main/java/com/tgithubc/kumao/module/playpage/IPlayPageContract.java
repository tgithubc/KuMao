package com.tgithubc.kumao.module.playpage;


import com.tgithubc.kumao.base.IView;
import com.tgithubc.kumao.bean.Song;

/**
 * Created by tc :)
 */
public interface IPlayPageContract {

    interface V  extends IView{

        void refreshInfoView(Song song);

        void refreshPlayStateView(boolean isPlaying);

        void refreshPlayMode(int mode);

        void refreshJellyfishView(byte[] waveform);
    }

    interface P {

        void onCreate();

        void onDestory();

        void doPlay();

        void doPlayNext();

        void doPlayPrev();

        void switchPlayMode();
    }
}
