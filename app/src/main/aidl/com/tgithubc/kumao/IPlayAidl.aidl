// IPlayMusicAidl.aidl
package com.tgithubc.kumao;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.IPlayCallbackAidl;
// Declare any non-default types here with import statements

interface IPlayAidl {

    void play(in Song song);

    void stop();

    void pause();

    void resume();

    void seekTo(int pos);

    int getPlayState();

    void addPlayCallback(IPlayCallbackAidl callback);

    void removePlayCallback(IPlayCallbackAidl callback);
}
