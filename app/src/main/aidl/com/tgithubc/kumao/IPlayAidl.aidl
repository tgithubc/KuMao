// IPlayMusicAidl.aidl
package com.tgithubc.kumao;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.IPlayCallbackAidl;
// Declare any non-default types here with import statements

interface IPlayAidl {

    void play(in Song song);

    void stop();

    void pause();

    void seekTo(int pos);

    void addPlayCallback(IPlayCallbackAidl callback);

    void removePlayCallback(IPlayCallbackAidl callback);
}
