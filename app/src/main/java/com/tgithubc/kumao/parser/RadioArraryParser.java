package com.tgithubc.kumao.parser;

import android.text.TextUtils;

import com.tgithubc.kumao.bean.Radio;
import com.tgithubc.kumao.bean.RadioArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tc :)
 */
class RadioArraryParser implements IParser<RadioArray> {

    @Override
    public RadioArray parse(String data) throws JSONException {
        if (TextUtils.isEmpty(data)) {
            return null;
        }
        JSONObject json = new JSONObject(data);
        JSONArray resultArrary = json.optJSONArray("result");
        RadioArray arrary = null;
        if (resultArrary != null && resultArrary.length() > 0) {
            JSONObject one = resultArrary.optJSONObject(0);
            JSONArray radioArrary = one.optJSONArray("channellist");
            if (radioArrary != null && radioArrary.length() > 0) {
                arrary = new RadioArray();
                List<Radio> radioLists = new ArrayList<>();
                for (int i = 0, len = radioArrary.length(); i < len; i++) {
                    JSONObject object = radioArrary.optJSONObject(i);
                    if (object == null) {
                        continue;
                    }
                    RadioParser radioParser = new RadioParser();
                    Radio radio = radioParser.getRadio(object);
                    radioLists.add(radio);
                }
                arrary.setRadioList(radioLists);
            }
        }
        return arrary;
    }
}
