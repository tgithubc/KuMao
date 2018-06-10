package com.tgithubc.kumao.viewProvider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.tgithubc.kumao.KuMao;
import com.tgithubc.kumao.MainActivity;
import com.tgithubc.kumao.R;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.service.PlayManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
public class SearchResultSongProvider extends BaseItemProvider<BaseData, BaseViewHolder> {

    @Override
    public int viewType() {
        return BaseData.TYPE_SEARCH_RESULT_SONG;
    }

    @Override
    public int layout() {
        return R.layout.rv_item_search_result_song;
    }

    @Override
    public void convert(BaseViewHolder helper, BaseData data, int position) {
        Song song = (Song) data.getData();
        helper.setText(R.id.search_result_song_name, replaceIllegal(song.getSongName()));
        helper.setText(R.id.search_result_song_artist_album,
                replaceIllegal(song.getAuthorName() + "-" + song.getAlbumName()));
    }

    @Override
    public void onClick(BaseViewHolder helper, BaseData data, int position) {
        super.onClick(helper, data, position);
        List<Song> list = new ArrayList<>();

        Song song = new Song();
        song.setSongId("591579114");
        song.setSongName("肆无忌惮");
        list.add(song);

        Song song1 = new Song();
        song1.setSongId("591310911");
        song1.setSongName("哑巴");
        list.add(song1);

        Song song2 = new Song();
        song2.setSongId("593080765");
        song2.setSongName("忠于自我");
        list.add(song2);

        Song song3 = new Song();
        song3.setSongId("591846818");
        song3.setSongName("初恋的地方");
        list.add(song3);

        Song song4 = new Song();
        song4.setSongId("593097514");
        song4.setSongName("Fendiman");
        list.add(song4);

        Song song5 = new Song();
        song5.setSongId("591130598");
        song5.setSongName("空空如也");
        list.add(song5);

        Song song6 = new Song();
        song6.setSongId("591523237");
        song6.setSongName("起床歌");
        list.add(song6);

        Song song7 = new Song();
        song7.setSongId("592187060");
        song7.setSongName("让一切走吧");
        list.add(song7);

        Song song8 = new Song();
        song8.setSongId("593443305");
        song8.setSongName("小流星");
        list.add(song8);

        Song song9 = new Song();
        song9.setSongId("592187086");
        song9.setSongName("苦雨");
        list.add(song9);

        PlayManager.getInstance().play(list, 0);
    }

    private String replaceIllegal(String name) {
        return name.replaceAll("<em>", "").replaceAll("</em>", "").trim();
    }
}
