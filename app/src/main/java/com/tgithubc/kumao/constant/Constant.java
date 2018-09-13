package com.tgithubc.kumao.constant;

/**
 * Created by tc :)
 */
public class Constant {

    /**
     * URL Constant
     */
    public interface Api {
        String HOST = "http://tingapi.ting.baidu.com/v1/restserver/ting?";
        String URL_BANNER = HOST + "method=baidu.ting.plaza.getFocusPic";
        String URL_HOTWORD = HOST + "method=baidu.ting.search.hot";
        String URL_SEARCH = HOST + "method=baidu.ting.search.common";
        String URL_SONGINFO = HOST + "method=baidu.ting.song.play";
        String URL_BILLBOARD = HOST + "method=baidu.ting.billboard.billList";
        String URL_BILLBOARD_LIST = HOST + "method=baidu.ting.billboard.billCategory";
        String URL_SONG_LIST_ARRARY = HOST + "method=baidu.ting.diy.gedan";
        String URL_SONG_LIST = HOST + "method=baidu.ting.diy.gedanInfo";

        int BILLBOARD_TYPE_NEW = 1;
    }

    /**
     * ui type 对应在Task复用，但是优需要在解析得到数据的时候，设置不同的ui展示类型的情况
     */
    public interface UIType {

    }
}
