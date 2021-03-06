package com.tgithubc.kumao.parser;


import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
class HotWordParser implements IParser<List<String>> {

    @Override
    public List<String> parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        List<String> result = null;
        JSONObject json = new JSONObject(data);
        JSONArray hotArrary = json.optJSONArray("result");
        if (hotArrary != null && hotArrary.length() > 0) {
            result = new ArrayList<>();
            for (int i = 0, len = hotArrary.length(); i < len; i++) {
                JSONObject object = hotArrary.optJSONObject(i);
                if (object == null) {
                    continue;
                }
                result.add(object.optString("word"));
            }
        }
        return result;
    }
}
