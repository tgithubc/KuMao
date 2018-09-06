package com.tgithubc.kumao.viewProvider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.service.PlayManager;

/**
 * Created by tc :)
 */
public class SearchResultSongProvider extends BaseItemProvider<Song, BaseViewHolder> {

    @Override
    public int viewType() {
        return BaseData.TYPE_SEARCH_RESULT_SONG;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_search_result_song;
    }

    @Override
    public void convert(BaseViewHolder helper, Song song, int position) {
        helper.setText(R.id.search_result_song_name, replaceIllegal(song.getSongName()));
        helper.setText(R.id.search_result_song_artist_album,
                replaceIllegal(song.getAuthorName() + "-" + song.getAlbumName()));
    }

    @Override
    public void onClick(BaseViewHolder helper, Song song, int position) {
        PlayManager.getInstance().play(song);
    }

    private String replaceIllegal(String name) {
        return name.replaceAll("<em>", "").replaceAll("</em>", "").trim();
    }
}
