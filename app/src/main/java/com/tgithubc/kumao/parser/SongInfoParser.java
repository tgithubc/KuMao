package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.Song;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by tc :)
 */
public class SongInfoParser implements IParser<Song> {

    @Override
    public Song parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject object = new JSONObject(data);
        JSONObject infoObject = object.optJSONObject("songinfo");
        Song song = new Song();
        if (infoObject != null) {
            song.setSmallPic(infoObject.optString("pic_big"));
            song.setCompany(infoObject.optString("si_proxycompany'"));
            song.setArtistId(infoObject.optString("artist_id"));
            song.setAuthorName(infoObject.optString("author"));
            song.setSongName(infoObject.optString("title"));
            song.setSongId(infoObject.optString("song_id"));
            song.setBigPic(infoObject.optString("pic_premium"));
            song.setAlbumId(infoObject.optString("album_id"));
            song.setLrclink(infoObject.optString("lrclink"));
            song.setRateArrary(infoObject.optString("all_rate"));
            song.setFreeBitrate(infoObject.optString("bitrate_fee"));
            song.setAlbumName(infoObject.optString("album_title"));
        }
        JSONObject bitrateObject = object.optJSONObject("bitrate");
        if (bitrateObject != null) {
            song.setFilelink(bitrateObject.optString("file_link"));
            song.setDuration(bitrateObject.optInt("file_duration"));
            song.setRate(bitrateObject.optString("file_bitrate"));
            song.setFileSize(bitrateObject.optLong("file_size"));
        }
        return song;
    }
}
