package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.Song;
import com.tgithubc.kumao.bean.SongList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
public class SongListParser implements IParser<SongList> {

    @Override
    public SongList parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject object = new JSONObject(data);
        SongList songList = new SongList();
        songList.setCollectNum(object.optString("collectnum"));
        songList.setListenNum(object.optString("listenum"));
        songList.setPic(object.optString("pic"));
        songList.setPic(object.optString("pic_300"));
        songList.setSongListId(object.optString("listid"));
        String tag = object.optString("tag");
        if (!TextUtils.isEmpty(tag)) {
            String[] tags = tag.split(",");
            songList.setTags(tags);
        }
        songList.setName(object.optString("title"));
        JSONArray songArrary = object.optJSONArray("content");
        if (songArrary != null && songArrary.length() > 0) {
            List<Song> list = new ArrayList<>();
            for (int i = 0, len = songArrary.length(); i < len; i++) {
                JSONObject songObj = songArrary.optJSONObject(i);
                if (songObj == null) {
                    continue;
                }
                SongParser songParser = new SongParser();
                Song song = songParser.parse(songObj.toString());
                list.add(song);
            }
            songList.setSongList(list);
        }
        return songList;
    }
}
