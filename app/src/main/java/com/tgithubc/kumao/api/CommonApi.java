package com.tgithubc.kumao.api;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by tc :)
 */
public interface CommonApi {

    @GET()
    Observable<String> get(@Url() String url);

    @GET()
    Observable<String> get(@Url() String url, @QueryMap Map<String, String> maps);

    @POST()
    Observable<String> post(@Url() String url, @QueryMap Map<String, String> maps);
}
