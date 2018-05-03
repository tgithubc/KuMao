package com.tgithubc.kumao.api;

import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.bean.Billboard;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tc :)
 */

public interface MusicApi {

    /**
     * banner url
     */
    @Headers("url_name:music")
    @GET("ting?method=baidu.ting.plaza.getFocusPic")
    Observable<BannerResult> getBanner(@Query("num") int numb);

    /**
     * billboard url
     */
    @Headers("url_name:music")
    @GET("ting?method=baidu.ting.billboard.billList")
    Observable<Billboard> getBillboard(@Query("type") int type,
                                       @Query("offset") int offset,
                                       @Query("size") int size);
}
