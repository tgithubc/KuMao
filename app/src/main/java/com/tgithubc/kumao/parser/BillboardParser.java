package com.tgithubc.kumao.parser;

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
public class BillboardParser implements IParser<Billboard> {

    @Override
    public Billboard parse(String data) throws JSONException {
        JSONObject json = new JSONObject(data);

        Billboard billboard = new Billboard();
        JSONObject infoJson = json.optJSONObject("billboard");
        if (infoJson != null) {
            Billboard.Info info = new Billboard.Info();
            info.setBillboardType(infoJson.optString("billboard_type"));
            info.setUpdateDate(infoJson.optString("update_date"));
            info.setHavemore(infoJson.optInt("havemore"));
            info.setComment(infoJson.optString("comment"));
            info.setName(infoJson.optString("name"));
            info.setPic_s192(infoJson.optString("pic_s192"));
            info.setPic_s210(infoJson.optString("pic_s210"));
            info.setPic_s260(infoJson.optString("pic_s260"));
            info.setPic_s444(infoJson.optString("pic_s444"));
            info.setPic_s640(infoJson.optString("pic_s640"));
            billboard.setBillboardInfo(info);
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
            billboard.setSongList(songList);
        }
        return billboard;
    }
}
