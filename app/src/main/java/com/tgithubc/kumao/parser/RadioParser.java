package com.tgithubc.kumao.parser;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.tgithubc.kumao.bean.Radio;
import com.tgithubc.kumao.bean.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
class RadioParser implements IParser<Radio> {

    @Override
    public Radio parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject object = new JSONObject(data);
        JSONObject resultObject = object.optJSONObject("result");
        if (resultObject == null) {
            return null;
        }
        return getRadio(resultObject);
    }

    @NonNull
    public Radio getRadio(JSONObject resultObject) throws JSONException {
        Radio radio = new Radio();
        radio.setName(resultObject.optString("name"));
        radio.setName(resultObject.optString("channel"));
        radio.setCateName(resultObject.optString("cate_sname"));
        radio.setRadioKey(resultObject.optString("ch_name"));
        radio.setPic(resultObject.optString("thumb"));
        JSONArray songArrary = resultObject.optJSONArray("songlist");
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
            radio.setSongList(list);
        }
        return radio;
    }
}
