package com.tgithubc.kumao.bean;

/**
 * Created by tc :)
 */
public class SongList {

    private String songListId;
    private String pic;
    private String listenNum;
    private String collectNum;
    private String tag;
    private String name;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SongList{" +
                "songListId='" + songListId + '\'' +
                ", pic='" + pic + '\'' +
                ", listenNum='" + listenNum + '\'' +
                ", collectNum='" + collectNum + '\'' +
                ", tag='" + tag + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
