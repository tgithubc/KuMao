package com.tgithubc.kumao.module.detailpage.base.list;


import com.tgithubc.kumao.base.IStateView;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Song;

import java.util.List;

/**
 * Created by tc :)
 */
public interface IDetailListPageContract {

    interface V extends IStateView {

        void showSongList(List<Song> songList);
    }

    interface P {

        void getSongList(int type, Task.RequestValue requestValue);
    }
}
