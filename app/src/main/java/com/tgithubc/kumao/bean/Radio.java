package com.tgithubc.kumao.bean;

import java.util.List;

/**
 * Created by tc :)
 */
public class Radio {

    private String name;
    private String pic;
    private String radioKey;
    private String cateName;
    private List<Song> songList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getRadioKey() {
        return radioKey;
    }

    public void setRadioKey(String radioKey) {
        this.radioKey = radioKey;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public String toString() {
        return "Radio{" +
                "name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", radioKey='" + radioKey + '\'' +
                ", cateName='" + cateName + '\'' +
                ", songList=" + songList +
                '}';
    }
}
