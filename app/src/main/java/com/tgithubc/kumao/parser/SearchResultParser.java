package com.tgithubc.kumao.parser;


import com.tgithubc.kumao.bean.Album;
import com.tgithubc.kumao.bean.Artist;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.bean.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
public class SearchResultParser implements IParser<SearchResult> {

    @Override
    public SearchResult parse(String data) throws JSONException {
        SearchResult result = new SearchResult();
        JSONObject json = new JSONObject(data);

        boolean isArtist = json.optInt("is_artist") == 1;
        result.setIsArtist(isArtist);
        if (isArtist) {
            JSONObject artistJson = json.optJSONObject("artist");
            if (artistJson != null) {
                Artist artist = new Artist();
                artist.setName(artistJson.optString("name"));
                artist.setId(artistJson.optString("ting_uid"));
                artist.setSongCount(artistJson.optString("songs_total"));
                artist.setAlbumCount(artistJson.optString("albums_total"));
                JSONObject picJson = artistJson.optJSONObject("avatar");
                artist.setPic(picJson.optString("big"));
                result.setArtist(artist);
            }
        }

        boolean isAlbum = json.optInt("is_album") == 1;
        result.setIsAlbum(isAlbum);
        if (isAlbum) {
            JSONObject albumJson = json.optJSONObject("album");
            if (albumJson != null) {
                Album album = new Album();
                album.setName(albumJson.optString("title"));
                album.setId(albumJson.optString("album_id"));
                album.setPic(albumJson.optString("pic_big"));
                album.setPublishTime(albumJson.optString("publishtime"));
                result.setAlbum(album);
            }
        }

        JSONArray songArrary = json.optJSONArray("song_list");
        if (songArrary != null && songArrary.length() > 0) {
            List<Song> songList = new ArrayList<>();
            for (int i = 0, len = songArrary.length(); i < len; i++) {
                JSONObject object = songArrary.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                SongParser songParser = new SongParser();
                Song song = songParser.parse(object.toString());
                songList.add(song);
            }
            result.setSongList(songList);
        }
        return result;
    }
}
