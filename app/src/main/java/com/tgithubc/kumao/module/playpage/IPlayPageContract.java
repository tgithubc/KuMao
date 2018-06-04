package com.tgithubc.kumao.module.playpage;


import com.tgithubc.kumao.base.IView;
import com.tgithubc.kumao.bean.Song;

/**
 * Created by tc :)
 */
public interface IPlayPageContract {

    interface V  extends IView{

        void setBlurBackground(Song song);
    }

    interface P {

        void onCreate();

        void onDestory();

    }
}
