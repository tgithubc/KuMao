package com.tgithubc.kumao.bean;

import java.util.List;

/**
 * Created by tc :)
 */
public class RecommendSongArray extends BaseData{

    private List<Song> songList;

    public List<Song> getSongs() {
        return songList;
    }

    public void setSongs(List<Song> songList) {
        this.songList = songList;
    }
}
