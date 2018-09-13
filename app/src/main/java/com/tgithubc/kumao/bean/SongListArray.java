package com.tgithubc.kumao.bean;

import java.util.List;

/**
 * Created by tc :)
 */

public class SongListArray extends BaseData {

    private List<SongList> songLists;

    public List<SongList> getSongLists() {
        return songLists;
    }

    public void setSongLists(List<SongList> songLists) {
        this.songLists = songLists;
    }

    @Override
    public String toString() {
        return "SongListArray{" +
                "songLists=" + songLists +
                '}';
    }
}
