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
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetSongInfoTask;
import com.tgithubc.kumao.message.MessageBus;
import com.tgithubc.kumao.observer.IKuMaoObserver.IPlayObserver;
import com.tgithubc.kumao.util.RxMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by tc :)
 */
public class PlayManager {

    public final static int
            MODE_LOOP = 0,
            MODE_RANDOM = 1,
            MODE_SINGLE = 2,
            MODE_MAX = 3;

    private IPlayAidl mService;
    private ServiceBinder mBinder;
    private Song mCurrentSong;
    private List<Song> mCurrentSongList;
    private List<Integer> mRandomIndexList;
    private int mCurrentIndex;
    private int mRandomIndex;
    private int mCurrentPlayMode;
    private boolean isAutoPlayEnd;

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {
            if (mService != null) {
                mService.asBinder().unlinkToDeath(mDeathRecipient, 0);
                mService = null;
            }
            bindToService();
        }
    };

    private IPlayCallbackAidl mStateCallback = new IPlayCallbackAidl.Stub() {


        @Override
        public void onPlayError(int errorCode) throws RemoteException {

        }

        @Override
        public void onPause() throws RemoteException {
            MessageBus.instance().getDefault(IPlayObserver.class).onPause();
        }

        @Override
        public void onPlayCompleted() throws RemoteException {
            isAutoPlayEnd = true;
            if (mCurrentPlayMode == MODE_RANDOM) {
                mRandomIndex++;
                if (mRandomIndexList == null
                        || mRandomIndex < 0
                        || mRandomIndex > mRandomIndexList.size() - 1) {
                    shuffleList();
                }
            }
            playNext();
        }

        @Override
        public void onPlayRealStart() throws RemoteException {
            isAutoPlayEnd = false;
            MessageBus.instance().getDefault(IPlayObserver.class).onPlayRealStart();
        }

        @Override
        public void onStartBuffering() throws RemoteException {

        }

        @Override
        public void onEndBuffering() throws RemoteException {

        }

        @Override
        public void onContinuePlay() throws RemoteException {
            MessageBus.instance().getDefault(IPlayObserver.class).onContinuePlay();
        }

        @Override
        public void onWaveFormDataCapture(byte[] waveform) throws RemoteException {
            MessageBus.instance().getDefault(IPlayObserver.class).onWaveFormDataCapture(waveform);
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

    /**
     * 列表播放
     *
     * @param songList 歌曲列表
     * @param index    当前index
     */
    public void play(List<Song> songList, int index) {
        if (songList == null || songList.isEmpty()) {
            return;
        }
        mCurrentSongList = songList;
        playByIndex(index);
    }


    /**
     * 单曲插入播放
     *
     * @param song
     */
    public void play(Song song) {
        if (song == null) {
            return;
        }
        if (mCurrentSongList == null || mCurrentSongList.isEmpty()) {
            List<Song> list = new ArrayList<>();
            list.add(song);
            play(list, 0);
            return;
        }
        int index = mCurrentIndex + 1;
        mCurrentSongList.add(index, song);
        playByIndex(index);
    }

    /**
     * 暂停
     */
    public void pause() {
        if (mService == null) {
            return;
        }
        try {
            if (PlayState.STATE_PLAYING == mService.getPlayState()) {
                mService.pause();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void continuePlay() {
        if (mService == null) {
            return;
        }
        try {
            if (PlayState.STATE_PAUSE == mService.getPlayState()) {
                mService.resume();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放下一曲(自动或者手动)
     */
    public void playNext() {
        if (mCurrentSongList == null || mCurrentSongList.isEmpty()) {
            return;
        }
        int index = 0;
        switch (mCurrentPlayMode) {
            case MODE_SINGLE:
                if (isAutoPlayEnd) {
                    index = mCurrentIndex;
                } else {
                    index = ++mCurrentIndex;
                }
                break;
            case MODE_LOOP:
                index = ++mCurrentIndex;
                if (index > mCurrentSongList.size() - 1) {
                    index = 0;
                }
                break;
            case MODE_RANDOM:
                //如果播放下一曲的时候是随机播放，要用随机的index去取，还得记录随机index的列表拿到第几个了
                if (mRandomIndexList == null
                        || mRandomIndex < 0
                        || mRandomIndex > mRandomIndexList.size() - 1) {
                    shuffleList();
                }
                index = mRandomIndexList.get(mRandomIndex);
                break;
        }
        playByIndex(index);
    }

    /**
     * 播放上一曲
     */
    public void playPrev() {
        if (mCurrentSongList == null || mCurrentSongList.isEmpty()) {
            return;
        }
        int index = 0;
        switch (mCurrentPlayMode) {
            case MODE_SINGLE:
            case MODE_LOOP:
                index = --mCurrentIndex;
                if (index <= -1) {
                    index = mCurrentSongList.size() - 1;
                }
                break;
            case MODE_RANDOM:
                //先暂定上一曲也随机吧
                mRandomIndex = --mRandomIndex;
                if (mRandomIndexList == null
                        || mRandomIndex < 0
                        || mRandomIndex > mRandomIndexList.size() - 1) {
                    shuffleList();
                }
                index = mRandomIndexList.get(mRandomIndex);
                break;
        }
        playByIndex(index);
    }

    /**
     * 播放当前列表中index位歌曲
     *
     * @param index
     */
    private void playByIndex(int index) {
        if (mService == null) {
            return;
        }
        if (index < 0 || index >= mCurrentSongList.size()) {
            return;
        }
        Song requestSong = mCurrentSongList.get(index);
        new GetSongInfoTask()
                .execute(new Task.CommonRequestValue(
                        Constant.Api.URL_SONG_INFO,
                        new RxMap<String, String>().put("songid", requestSong.getSongId()).build()))
                .subscribe(responseValue -> {
                    Song song = responseValue.getResult();
                    try {
                        // 当前没播放的歌曲，或者当前播放的歌曲和要播放的歌曲不同，播放要播放的歌曲
                        if (mCurrentSong == null || !song.getFilelink().equals(mCurrentSong.getFilelink())) {
                            mService.play(song);
                        } else { // 还是同一首歌，seek 到 0
                            mService.seekTo(0);
                        }
                        mCurrentSong = song;
                        mCurrentIndex = index;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 设置播放模式
     *
     * @param mode
     */
    public void setPlayMode(int mode) {
        if (mCurrentPlayMode != mode) {
            mCurrentPlayMode = mode;
            if (mode == MODE_RANDOM) {
                // shuffle list
                shuffleList();
            }
        }
    }

    /**
     * 生成一个随机列表
     */
    private void shuffleList() {
        if (mCurrentSongList == null || mCurrentSongList.isEmpty()) {
            return;
        }
        int size = mCurrentSongList.size();
        mRandomIndexList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            mRandomIndexList.add(i);
        }
        Collections.shuffle(mRandomIndexList);
        mRandomIndex = 0;
    }

    /**
     * 获取当前播放歌曲
     *
     * @return
     */
    public Song getCurrentSong() {
        return mCurrentSong;
    }

    public int getPlayMode() {
        return mCurrentPlayMode;
    }

    /**
     * 获取当前播放状态
     *
     * @return
     */
    public int getPlayState() {
        int state = PlayState.STATE_IDLE;
        if (mService == null) {
            return state;
        }
        try {
            return mService.getPlayState();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return state;
    }

    public static PlayManager getInstance() {
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder {
        private static final PlayManager INSTANCE = new PlayManager();
    }

    private PlayManager() {
    }

    private class ServiceBinder implements ServiceConnection {

        @Override
        public void onServiceConnected(final ComponentName className, final IBinder service) {
            try {
                mService = IPlayAidl.Stub.asInterface(service);
                mService.addPlayCallback(mStateCallback);
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(final ComponentName className) {
            try {
                mService.removePlayCallback(mStateCallback);
                mService = null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            bindToService();
        }
    }
}
