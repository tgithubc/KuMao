package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.SongList;

import org.json.JSONException;
import org.json.JSONObject;

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
        songList.setSongListId(object.optString("listid"));
        songList.setTag(object.optString("tag"));
        songList.setName(object.optString("title"));
        return songList;
    }
}
