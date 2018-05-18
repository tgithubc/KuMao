package com.tgithubc.kumao.parser;

import com.tgithubc.kumao.bean.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
public class BannerParser implements IParser<List<Banner>> {

    @Override
    public List<Banner> parse(String data) throws JSONException {
        List<Banner> result = null;
        JSONObject json = new JSONObject(data);
        JSONArray pic = json.optJSONArray("pic");
        if (pic != null && pic.length() > 0) {
            result = new ArrayList<>();
            for (int i = 0, len = pic.length(); i < len; i++) {
                JSONObject object = pic.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                Banner banner = new Banner();
                banner.setBannerPic(object.optString("randpic"));
                banner.setUrl(object.optString("code"));
                result.add(banner);
            }
        }
        return result;
    }
}
