package com.tgithubc.kumao.bean;

import java.util.List;

/**
 * Created by tc :)
 */
public class SongList {

    private String songListId;
    private String pic;
    private String listenNum;
    private String collectNum;
    private String[] tags;
    private String name;
    private List<Song> songList;

    public String getSongListId() {
        return songListId;
    }

    public void setSongListId(String songListId) {
        this.songListId = songListId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getListenNum() {
        return listenNum;
    }

    public void setListenNum(String listenNum) {
        this.listenNum = listenNum;
    }

    public String getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(String collectNum) {
        this.collectNum = collectNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "SongList{" +
                "songListId='" + songListId + '\'' +
                ", pic='" + pic + '\'' +
                ", listenNum='" + listenNum + '\'' +
                ", collectNum='" + collectNum + '\'' +
                ", tags='" + tags + '\'' +
                ", name='" + name + '\'' +
                ", songList=" + songList +
                '}';
    }
}
