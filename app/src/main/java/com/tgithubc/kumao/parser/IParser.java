package com.tgithubc.kumao.parser;

import org.json.JSONException;

/**
 * Created by tc :)
 */

public interface IParser<T> {

    T parse(String data) throws JSONException;
}
