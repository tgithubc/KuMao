package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.Banner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.banner;

/**
 * Created by tc :)
 */
class BannerParser implements IParser<Banner> {

    @Override
    public Banner parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject json = new JSONObject(data);
        JSONArray pic = json.optJSONArray("pic");
        Banner banner = null;
        if (pic != null && pic.length() > 0) {
            banner = new Banner();
            List<String> picList = new ArrayList<>();
            List<String> urlList = new ArrayList<>();
            for (int i = 0, len = pic.length(); i < len; i++) {
                JSONObject object = pic.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                picList.add(object.optString("randpic"));
                urlList.add(object.optString("code"));
            }
            banner.setBannerPicList(picList);
            banner.setUrlList(urlList);
        }
        return banner;
    }
}
