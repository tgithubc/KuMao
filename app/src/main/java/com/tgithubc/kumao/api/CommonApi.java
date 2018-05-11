package com.tgithubc.kumao.api;


import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by tc :)
 */
public interface CommonApi {

    @GET()
    Observable<String> get(@Url() String url, @QueryMap Map<String, String> maps);

    @POST()
    Observable<String> post(@Url() String url, @QueryMap Map<String, String> maps);
}
