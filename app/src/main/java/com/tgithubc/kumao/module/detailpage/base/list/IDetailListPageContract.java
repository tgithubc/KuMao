package com.tgithubc.kumao.module.detailpage.base.list;


import com.tgithubc.kumao.base.IStateView;
import com.tgithubc.kumao.bean.Song;

import java.util.List;
import java.util.Map;

/**
 * Created by tc :)
 */
public interface IDetailListPageContract {

    interface V extends IStateView {

        void showSongList(List<Song> songList);
    }

    interface P {

        void getSongList(Map<String, String> requestValue);
    }
}
