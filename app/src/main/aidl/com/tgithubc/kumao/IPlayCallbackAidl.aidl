// IPlayCallbackAidl.aidl
package com.tgithubc.kumao;
// Declare any non-default types here with import statements

interface IPlayCallbackAidl {

    void onPlayError(int errorCode);

    void onPause();

    void onPlayCompleted();

    void onPlayRealStart();

    void onStartBuffering();

    void onEndBuffering();

    void onContinuePlay();

    void onWaveFormDataCapture(in byte[] waveform);
}
