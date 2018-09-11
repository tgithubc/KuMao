package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.HotSongListArrary;
import com.tgithubc.kumao.bean.SongList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
public class HotSongListArraryParser implements IParser<HotSongListArrary> {

    @Override
    public HotSongListArrary parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject json = new JSONObject(data);
        JSONObject contentJson = json.optJSONObject("content");
        if (contentJson == null) {
            return null;
        }
        JSONArray songListArrary = contentJson.optJSONArray("list");
        HotSongListArrary arrary = new HotSongListArrary();
        if (songListArrary != null && songListArrary.length() > 0) {
            List<SongList> songLists = new ArrayList<>();
            for (int i = 0, len = songListArrary.length(); i < len; i++) {
                JSONObject object = songListArrary.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                SongListParser songListParser = new SongListParser();
                SongList songList = songListParser.parse(object.toString());
                songLists.add(songList);
                arrary.setSongLists(songLists);
            }
        }
        return arrary;
    }
}
