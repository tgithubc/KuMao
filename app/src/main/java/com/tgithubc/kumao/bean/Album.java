package com.tgithubc.kumao.bean;

/**
 * Created by tc :)
 */
public class Album {

    private String name;

    private String albumId;

    private String publishTime;

    private String pic;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return albumId;
    }

    public void setId(String id) {
        this.albumId = id;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    @Override
    public String toString() {
        return "Album{" +
                "name='" + name + '\'' +
                ", albumId='" + albumId + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", pic='" + pic + '\'' +
                '}';
    }
}
