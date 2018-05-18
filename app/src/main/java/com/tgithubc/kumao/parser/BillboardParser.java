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
                song.setHot(object.optString("hot"));
                song.setRateArrary(object.optString("all_rate"));
                song.setDuration(object.optInt("file_duration"));
                song.setFreeBitrate(object.optString("bitrate_fee"));
                song.setCompany(object.optString("si_proxycompany'"));
                songList.add(song);
            }
            billboard.setSongList(songList);
        }
        return billboard;
    }
}
