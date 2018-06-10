package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.Song;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tc :)
 */
public class SongParser implements IParser<Song> {

    @Override
    public Song parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject object = new JSONObject(data);
        Song song = new Song();
        song.setSongId(object.optString("song_id"));
        song.setSongName(object.optString("title"));
        song.setAuthorName(object.optString("author"));
        song.setArtistId(object.optString("artist_id"));
        song.setAlbumId(object.optString("album_id"));
        song.setAlbumName(object.optString("album_title"));
        song.setSmallPic(object.optString("pic_big"));
        song.setBigPic(object.optString("pic_s500"));
        song.setLrclink(object.optString("lrclink"));
        song.setIsNew(object.optString("is_new"));
        song.setContent(object.optString("content"));
        song.setHot(object.optString("hot"));
        song.setRateArrary(object.optString("all_rate"));
        song.setDuration(object.optInt("file_duration"));
        song.setFreeBitrate(object.optString("bitrate_fee"));
        song.setCompany(object.optString("si_proxycompany'"));
        return song;
    }
}
