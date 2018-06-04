package com.tgithubc.kumao.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.tgithubc.kumao.IPlayAidl;
import com.tgithubc.kumao.IPlayCallbackAidl;
import com.tgithubc.kumao.KuMao;
import com.tgithubc.kumao.bean.Song;

/**
 * 1，播放列表
 * 2，音频焦点
 * 3，通知栏
 * Created by tc :)
 */
public class PlayManager {

    private IPlayAidl mService = null;
    private ServiceBinder mBinder;
    private Song mCurrentSong;

    private IPlayCallbackAidl mStateCallback = new IPlayCallbackAidl.Stub() {


        @Override
        public void onPlayError(int errorCode) throws RemoteException {

        }

        @Override
        public void onPause() throws RemoteException {

        }

        @Override
        public void onPlayCompleted() throws RemoteException {

        }

        @Override
        public void onContinue() throws RemoteException {

        }
    };

    public void bindToService() {
        ContextWrapper contextWrapper = new ContextWrapper(KuMao.getContext());
        contextWrapper.startService(new Intent(contextWrapper, PlayService.class));
        if (mBinder == null) {
            mBinder = new ServiceBinder();
        }
        if (contextWrapper.bindService(
                new Intent().setClass(contextWrapper, PlayService.class),
                mBinder,
                Context.BIND_AUTO_CREATE)) {
            // binding
        }
    }

    public void unbindFromService() {
        if (mBinder == null) {
            return;
        }
        ContextWrapper contextWrapper = new ContextWrapper(KuMao.getContext());
        contextWrapper.unbindService(mBinder);
        contextWrapper.stopService(new Intent().setClass(contextWrapper, PlayService.class));
        mBinder = null;
        mService = null;
    }

    public static PlayManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder {
        private static final PlayManager INSTANCE = new PlayManager();
    }

    private PlayManager() {

    }

    public void play(Song song) {
        song = new Song();
        song.setFilelink("http://zhangmenshiting.qianqian.com/data2/music/d190bc80771bed15d5afd2e08672e194/64540063/" +
                "64540063.mp3?xcode=6738dc9b8b3a3bc184da5601d869449c");
        if (song == null || mService == null) {
            return;
        }
        try {
            // 当前没播放的歌曲，或者当前播放的歌曲和要播放的歌曲不同，播放要播放的歌曲
            if (mCurrentSong == null || !song.getFilelink().equals(mCurrentSong.getFilelink())) {
                mService.play(song);
                mCurrentSong = song;
            } else { // 还是同一首歌，seek 到 0
                mService.seekTo(0);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Song getCurrentSong() {
        return mCurrentSong;
    }

    private class ServiceBinder implements ServiceConnection {

        @Override
        public void onServiceConnected(final ComponentName className, final IBinder service) {
            mService = IPlayAidl.Stub.asInterface(service);
            try {
                mService.addPlayCallback(mStateCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(final ComponentName className) {
            try {
                mService.removePlayCallback(mStateCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mService = null;
        }
    }
}
