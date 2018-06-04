package com.tgithubc.kumao.module.playpage;

import android.graphics.Bitmap;
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
import com.tgithubc.kumao.util.ImmersedStatusBarHelper;

/**
 * Created by tc :)
 */
public class PlayFragment extends BaseFragment implements IPlayPageContract.V {

    private Song mCurrentSong;
    private PlayPagePresenter mPresenter;
    private ImageView mBlurIV;
    private TextView mSongName;
    private TextView mAuthorName;

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
        mSongName = view.findViewById(R.id.play_page_song_name);
        mAuthorName = view.findViewById(R.id.play_page_author_name);
        mBlurIV = view.findViewById(R.id.play_page_blur_bkg);
        boolean isNull = mCurrentSong == null;
        String songName = isNull ? "听音乐" : mCurrentSong.getSongName();
        String authorName = isNull ? "用酷猫" : mCurrentSong.getAuthorName();
        mSongName.setText(songName);
        mAuthorName.setText(authorName);
        mCurrentSong = new Song();
        setBlurBackground(mCurrentSong);
    }

    @Override
    protected View onCreateTitleView(LayoutInflater inflater, FrameLayout titleContainer) {
        return null;
    }

    @Override
    public void setBlurBackground(Song song) {
        song.setBigPic("http://qukufile2.qianqian.com/data2/pic/88389085/88389085.jpg");
        if (song != null && !TextUtils.isEmpty(song.getBigPic())) {
            ImageLoaderWrapper.getInstance()
                    .load(song.getBigPic(), new SimpleDownloaderListener() {
                        @Override
                        public void onSuccess(Bitmap result) {
                            super.onSuccess(result);
                            Bitmap blur = new NativeBlurProcess().blur(result, 180);
                            mBlurIV.setImageBitmap(blur);
                        }
                    });
        }
    }
}
