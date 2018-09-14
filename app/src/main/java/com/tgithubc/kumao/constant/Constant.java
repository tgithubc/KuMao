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
     * ui type
     */
    public interface UIType {

        // 推荐页
        int TYPE_BANNER = 1000;// 轮播图
        int TYPE_TITLE_MORE = 1001;// title,更多
        int TYPE_SONG_LIST_3S = 1002;// 歌单3列

        // 搜索结果页
        int TYPE_SEARCH_RESULT_ARTIST = 2000;// 搜索结果的歌手
        int TYPE_SEARCH_RESULT_ALBUM = 2001;// 搜索结果的专辑
        int TYPE_SEARCH_RESULT_SONG = 2002;// 搜索结果的单曲

        // 排行榜页（新歌展示，普通）
        int TYPE_RANK_NEW_BILLBOARD = 3000;// 新歌展示
        int TYPE_RANK_BILLBOARD = 3001;// 普通
    }
}
