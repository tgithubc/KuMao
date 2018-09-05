package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.SongList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
public class HotSongListParser implements IParser<List<SongList>> {

    @Override
    public List<SongList> parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject json = new JSONObject(data);
        JSONObject contentJson = json.optJSONObject("content");
        if (contentJson == null) {
            return null;
        }
        JSONArray songListArrary = contentJson.optJSONArray("song_list");
        List<SongList> songLists = null;
        if (songListArrary != null && songListArrary.length() > 0) {
            songLists = new ArrayList<>();
            for (int i = 0, len = songListArrary.length(); i < len; i++) {
                JSONObject object = songListArrary.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                SongListParser songListParser = new SongListParser();
                SongList songList = songListParser.parse(object.toString());
                songLists.add(songList);
            }
        }
        return songLists;
    }
}
