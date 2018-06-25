package com.tgithubc.kumao.module.playpage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.enrique.stackblur.NativeBlurProcess;
import com.tgithubc.fresco_wapper.ImageLoaderWrapper;
import com.tgithubc.fresco_wapper.listener.SimpleDownloaderListener;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.base.BaseFragment;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.service.PlayManager;
import com.tgithubc.kumao.service.PlayState;
import com.tgithubc.kumao.widget.JellyfishView;

/**
 * Created by tc :)
 */
public class PlayFragment extends BaseFragment implements IPlayPageContract.V, View.OnClickListener {

    private Song mCurrentSong;
    private PlayPagePresenter mPresenter;
    private ImageView mBlurIV;
    private JellyfishView mJellyfishView;
    private TextView mSongName;
    private TextView mAuthorName;
    private ImageView mPlayIV;
    private ImageView mPlayModeIV;
    private Bitmap mDefaultBitmap;

    public static PlayFragment newInstance() {
        return new PlayFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_play;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentSong = PlayManager.getInstance().getCurrentSong();
        mPresenter = new PlayPagePresenter();
        mPresenter.attachView(this);
        mPresenter.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter.onDestory();
    }

    @Override
    public void init(View view, LayoutInflater inflater, Bundle savedInstanceState) {
        mDefaultBitmap  = BitmapFactory.decodeResource(getResources(), R.drawable.play_page_cover);
        mSongName = view.findViewById(R.id.play_page_song_name);
        mAuthorName = view.findViewById(R.id.play_page_author_name);
        mBlurIV = view.findViewById(R.id.play_page_blur_bkg);
        mJellyfishView= view.findViewById(R.id.jellyfishView);
        mPlayIV = view.findViewById(R.id.play_page_play);
        mPlayModeIV = view.findViewById(R.id.play_page_play_mode);
        view.findViewById(R.id.play_page_play_pre).setOnClickListener(this);
        view.findViewById(R.id.play_page_play_next).setOnClickListener(this);
        mPlayIV.setOnClickListener(this);
        mPlayModeIV.setOnClickListener(this);
        refreshInfoView(mCurrentSong);
        refreshPlayStateView(PlayManager.getInstance().getPlayState() == PlayState.STATE_PLAYING);
    }

    @Override
    public void refreshInfoView(Song song) {
        boolean isNull = song == null;
        String songName = isNull ? "听音乐" : song.getSongName();
        String authorName = isNull ? "用酷猫" : song.getAuthorName();
        mSongName.setText(songName);
        mAuthorName.setText(authorName);
        setBlurBackground(song);
    }

    @Override
    public void refreshPlayStateView(boolean isPlaying) {
        mJellyfishView.start(isPlaying);
        mPlayIV.setImageResource(isPlaying ? R.drawable.play_page_pause_selector : R.drawable.play_page_play_selector);
    }

    @Override
    public void refreshPlayMode(int mode) {
        switch (mode) {
            case PlayManager.MODE_LOOP:
                mPlayModeIV.setImageResource(R.drawable.play_page_play_mode_loop);
                break;
            case PlayManager.MODE_SINGLE:
                mPlayModeIV.setImageResource(R.drawable.play_page_play_mode_single);
                break;
            case PlayManager.MODE_RANDOM:
                mPlayModeIV.setImageResource(R.drawable.play_page_play_mode_random);
                break;
        }
    }

    @Override
    public void refreshJellyfishView(byte[] waveform) {
        mJellyfishView.updateWave(waveform);
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, FrameLayout titleContainer) {
        return null;
    }

    private void setBlurBackground(Song song) {
        if (song == null || TextUtils.isEmpty(song.getBigPic())) {
            setDefaultBackground();
            return;
        }
        ImageLoaderWrapper.getInstance().load(song.getBigPic(), new SimpleDownloaderListener() {
            @Override
            public void onSuccess(Bitmap result) {
                super.onSuccess(result);
                Bitmap blur = new NativeBlurProcess().blur(result, 80);
                mBlurIV.setImageBitmap(blur);
                mJellyfishView.setBitmap(result);
            }

            @Override
            public void onFailure(Throwable throwable) {
                super.onFailure(throwable);
                setDefaultBackground();
            }
        });
    }

    private void setDefaultBackground() {
        mBlurIV.setImageBitmap(null);
        mJellyfishView.setBitmap(mDefaultBitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_page_play:
                mPresenter.doPlay();
                break;
            case R.id.play_page_play_next:
                mPresenter.doPlayNext();
                break;
            case R.id.play_page_play_pre:
                mPresenter.doPlayPrev();
                break;
            case R.id.play_page_play_mode:
                mPresenter.switchPlayMode();
                break;
        }
    }

}
