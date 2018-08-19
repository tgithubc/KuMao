package com.tgithubc.kumao.module.listpage.billboard;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.Song;

import java.util.List;

/**
 * Created by tc :)
 */
public class SongAdapter extends BaseQuickAdapter<Song, BaseViewHolder> {

    public SongAdapter(@Nullable List<Song> data) {
        super(R.layout.rv_item_song, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Song item) {
        helper.setText(R.id.song_name, item.getSongName());
        helper.setText(R.id.song_artist, item.getAuthorName());
    }
}