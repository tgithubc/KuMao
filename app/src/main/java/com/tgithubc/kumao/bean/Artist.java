package com.tgithubc.kumao.bean;

/**
 * Created by tc :)
 */
public class Artist extends BaseData{

    private String name;

    private String artistId;

    private String pic;

    private String songCount;

    private String albumCount;

    public String getSongCount() {
        return songCount;
    }

    public void setSongCount(String songCount) {
        this.songCount = songCount;
    }

    public String getAlbumCount() {
        return albumCount;
    }

    public void setAlbumCount(String albumCount) {
        this.albumCount = albumCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return artistId;
    }

    public void setId(String id) {
        this.artistId = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", artistId='" + artistId + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
