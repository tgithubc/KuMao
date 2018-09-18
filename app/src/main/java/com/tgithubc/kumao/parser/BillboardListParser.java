package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.bean.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
class BillboardListParser implements IParser<List<Billboard>> {

    @Override
    public List<Billboard> parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject json = new JSONObject(data);
        List<Billboard> billboardList = new ArrayList<>();
        JSONArray contentArray = json.optJSONArray("content");
        if (contentArray != null && contentArray.length() > 0) {
            for (int i = 0; i < contentArray.length(); i++) {
                JSONObject obj = contentArray.optJSONObject(i);
                Billboard billboard = new Billboard();
                Billboard.Info info = new Billboard.Info();
                info.setBillboardType(obj.optInt("type"));
                info.setComment(obj.optString("comment"));
                info.setName(obj.optString("name"));
                info.setPic_s192(obj.optString("pic_s192"));
                info.setPic_s210(obj.optString("pic_s210"));
                info.setPic_s260(obj.optString("pic_s260"));
                info.setPic_s444(obj.optString("pic_s444"));
                billboard.setBillboardInfo(info);

                JSONArray songArrary = obj.optJSONArray("content");
                if (songArrary != null && songArrary.length() > 0) {
                    List<Song> songList = new ArrayList<>();
                    for (int k = 0, len = songArrary.length(); k < len; k++) {
                        JSONObject object = songArrary.optJSONObject(k);
                        if (object == null) {
                            continue;
                        }
                        SongParser songParser = new SongParser();
                        Song song = songParser.parse(object.toString());
                        songList.add(song);
                    }
                    billboard.setSongList(songList);
                }
                billboardList.add(billboard);
            }
        }
        return billboardList;
    }
}
