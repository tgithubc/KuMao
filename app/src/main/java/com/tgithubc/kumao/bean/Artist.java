package com.tgithubc.kumao.bean;

/**
 * Created by tc :)
 */
public class Artist {

    private String name;

    private String artistId;

    private String pic;

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
