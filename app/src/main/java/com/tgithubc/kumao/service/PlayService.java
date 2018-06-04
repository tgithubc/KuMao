package com.tgithubc.kumao.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.tgithubc.kumao.IPlayAidl;
import com.tgithubc.kumao.IPlayCallbackAidl;
import com.tgithubc.kumao.bean.Song;

import java.io.IOException;

/**
 * Created by tc :)
 */
public class PlayService extends Service implements
        MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener {

    private MediaPlayer mPlayer;
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
                notifyClient(PlayState.STATE_PREPARE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void stop() throws RemoteException {
            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
                notifyClient(PlayState.STATE_STOP);
            }
        }

        @Override
        public void pause() throws RemoteException {
            if (mPlayer != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    notifyClient(PlayState.STATE_PAUSE);
                } else {
                    if (PlayState.STATE_PAUSE == mCurrentState) {
                        mPlayer.start();
                        notifyClient(PlayState.STATE_CONTINUE);
                    }
                }
            }
        }

        @Override
        public void seekTo(int pos) throws RemoteException {
            if (mPlayer != null && pos >= 0) {
                mPlayer.seekTo(pos);
            }
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            }
            mPlayer.release();
            mPlayer = null;
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

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Message message = Message.obtain();
        message.arg1 = what;
        notifyClient(PlayState.STATE_ERROR, message);
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        ensureMediaPlayer();
        mPlayer.start();
        notifyClient(PlayState.STATE_REAL_PLAY);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        notifyClient(PlayState.STATE_PLAY_COMPLETE);
    }

    private void notifyClient(int state) {
        notifyClient(state, null);
    }

    private void notifyClient(int state, Message message) {
        mCurrentState = state;
        int count = mCallbackList.beginBroadcast();
        try {
            for (int i = 0; i < count; i++) {
                IPlayCallbackAidl callback = mCallbackList.getBroadcastItem(i);
                if (callback == null) {
                    continue;
                }
                switch (mCurrentState) {
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
                    case PlayState.STATE_CONTINUE:
                        callback.onContinue();
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mCallbackList.finishBroadcast();
        }
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                break;
        }
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }
}
