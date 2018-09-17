package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.RecommendSongArray;
import com.tgithubc.kumao.bean.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
class RecommendSongArrayParser implements IParser<RecommendSongArray> {

    @Override
    public RecommendSongArray parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject json = new JSONObject(data);
        JSONArray contentArray = json.optJSONArray("content");
        if (contentArray == null || contentArray.length() <= 0) {
            return null;
        }
        JSONObject contentObject = contentArray.optJSONObject(0);
        RecommendSongArray arrary = new RecommendSongArray();
        JSONArray songListArrary = contentObject.optJSONArray("song_list");
        if (songListArrary != null && songListArrary.length() > 0) {
            List<Song> songs = new ArrayList<>();
            for (int i = 0, len = songListArrary.length(); i < len; i++) {
                JSONObject object = songListArrary.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                SongParser songParser = new SongParser();
                Song song = songParser.parse(object.toString());
                songs.add(song);
            }
            arrary.setSongs(songs);
        }
        return arrary;
    }
}
