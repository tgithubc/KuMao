package com.tgithubc.kumao.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.tgithubc.kumao.IPlayAidl;
import com.tgithubc.kumao.IPlayCallbackAidl;
import com.tgithubc.kumao.bean.Song;

/**
 * Created by tc :)
 */
public class PlayService extends Service implements
        MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnInfoListener,
        MediaPlayer.OnBufferingUpdateListener,
        Visualizer.OnDataCaptureListener {

    private Visualizer mVisualizer;
    private MediaPlayer mPlayer;
    private boolean mSeekFlag = false;
    private int mCurrentState = PlayState.STATE_IDLE;
    private RemoteCallbackList<IPlayCallbackAidl> mCallbackList = new RemoteCallbackList<>();

    private IBinder mBinder = new IPlayAidl.Stub() {

        @Override
        public void play(Song song) throws RemoteException {
            ensureMediaPlayer();
            try {
                mPlayer.reset();
                mPlayer.setDataSource(song.getFilelink());
                mPlayer.prepareAsync();
                mCurrentState = PlayState.STATE_PREPARE;
                notifyClient(PlayState.STATE_PREPARE);
            } catch (Exception e) {
                e.printStackTrace();
                Message message = Message.obtain();
                message.arg1 = -123;
                mCurrentState = PlayState.STATE_ERROR;
                notifyClient(PlayState.STATE_ERROR, message);
            }
        }

        @Override
        public void stop() throws RemoteException {
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
                mVisualizer.setEnabled(false);
                mCurrentState = PlayState.STATE_STOP;
                notifyClient(PlayState.STATE_STOP);
            }
        }

        @Override
        public void pause() throws RemoteException {
            if (mPlayer != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    mVisualizer.setEnabled(false);
                    mCurrentState = PlayState.STATE_PAUSE;
                    notifyClient(PlayState.STATE_PAUSE);
                }
            }
        }

        @Override
        public void resume() throws RemoteException {
            if (mPlayer != null) {
                if (PlayState.STATE_PAUSE == mCurrentState) {
                    mPlayer.start();
                    mVisualizer.setEnabled(true);
                    notifyClient(PlayState.STATE_CONTINUE);
                    mCurrentState = PlayState.STATE_PLAYING;
                }
            }
        }

        @Override
        public void seekTo(int pos) throws RemoteException {
            if (mPlayer != null && pos >= 0) {
                mPlayer.seekTo(pos);
                mSeekFlag = true;
            }
        }

        @Override
        public int getPlayState() throws RemoteException {
            return mCurrentState;
        }

        @Override
        public void addPlayCallback(IPlayCallbackAidl callback) throws RemoteException {
            if (callback != null) {
                mCallbackList.register(callback);
            }
        }

        @Override
        public void removePlayCallback(IPlayCallbackAidl callback) throws RemoteException {
            if (callback != null) {
                mCallbackList.unregister(callback);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        ensureMediaPlayer();
        initVisualizer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
                mCurrentState = PlayState.STATE_STOP;
                notifyClient(PlayState.STATE_STOP);
            }
            mPlayer.release();
            mPlayer = null;
            mVisualizer.setEnabled(false);
            mVisualizer.release();
            mVisualizer = null;
        }
    }

    private void ensureMediaPlayer() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnBufferingUpdateListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnSeekCompleteListener(this);
            mPlayer.setOnErrorListener(this);
            mPlayer.setOnInfoListener(this);
            mPlayer.setOnPreparedListener(this);
        }
    }

    private void initVisualizer() {
        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(this, Visualizer.getMaxCaptureRate() / 2, true, false);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        mSeekFlag = false;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Message message = Message.obtain();
        message.arg1 = what;
        mCurrentState = PlayState.STATE_ERROR;
        notifyClient(PlayState.STATE_ERROR, message);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        ensureMediaPlayer();
        mPlayer.start();
        mVisualizer.setEnabled(true);
        notifyClient(PlayState.STATE_REAL_PLAY);
        mCurrentState = PlayState.STATE_PLAYING;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mCurrentState = PlayState.STATE_PLAY_COMPLETE;
        notifyClient(PlayState.STATE_PLAY_COMPLETE);
        mVisualizer.setEnabled(false);
    }

    private void notifyClient(int state) {
        notifyClient(state, null);
    }

    private void notifyClient(int state, Message message) {
        int count = 0;
        try {
            count = mCallbackList.beginBroadcast();
            for (int i = 0; i < count; i++) {
                IPlayCallbackAidl callback = mCallbackList.getBroadcastItem(i);
                if (callback == null) {
                    continue;
                }
                switch (state) {
                    case PlayState.STATE_ERROR:
                        if (message != null) {
                            callback.onPlayError(message.arg1);
                        }
                        break;
                    case PlayState.STATE_PAUSE:
                        callback.onPause();
                        break;
                    case PlayState.STATE_PLAY_COMPLETE:
                        callback.onPlayCompleted();
                        break;
                    case PlayState.STATE_REAL_PLAY:
                        callback.onPlayRealStart();
                        break;
                    case PlayState.STATE_BUFFERING:
                        callback.onStartBuffering();
                        break;
                    case PlayState.STATE_BUFFERING_END:
                        callback.onEndBuffering();
                        break;
                    case PlayState.STATE_CONTINUE:
                        callback.onContinuePlay();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (count > 0) {
                mCallbackList.finishBroadcast();
            }
        }
    }

    private void notifyWaveFormData(byte[] waveform) {
        int count = 0;
        try {
            count = mCallbackList.beginBroadcast();
            for (int i = 0; i < count; i++) {
                IPlayCallbackAidl callback = mCallbackList.getBroadcastItem(i);
                if (callback == null) {
                    continue;
                }
                callback.onWaveFormDataCapture(waveform);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (count > 0) {
                mCallbackList.finishBroadcast();
            }
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (mCurrentState == PlayState.STATE_PLAYING) {
                    if (!mSeekFlag) {
                        mCurrentState = PlayState.STATE_BUFFERING;
                        notifyClient(PlayState.STATE_BUFFERING);
                    }
                }
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if (mCurrentState == PlayState.STATE_BUFFERING) {
                    if (!mSeekFlag) {
                        notifyClient(PlayState.STATE_BUFFERING_END);
                    }
                    mCurrentState = PlayState.STATE_PLAYING;
                }
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
        notifyWaveFormData(waveform);
    }

    @Override
    public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {

    }
}
