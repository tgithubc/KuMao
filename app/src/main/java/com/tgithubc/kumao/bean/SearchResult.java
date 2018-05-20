package com.tgithubc.kumao.bean;

import java.util.List;

/**
 * Created by tc :)
 */
public class SearchResult {

    private boolean isArtist;

    private boolean isAlbum;

    private List<Song> songList;

    private Artist artist;

    private Album album;

    public boolean isArtist() {
        return isArtist;
    }

    public void setIsArtist(boolean artist) {
        isArtist = artist;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public void setIsAlbum(boolean album) {
        isAlbum = album;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "isArtist=" + isArtist +
                ", isAlbum=" + isAlbum +
                ", songList=" + songList +
                ", artist=" + artist +
                ", album=" + album +
                '}';
    }
}
