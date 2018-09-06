package com.tgithubc.kumao.bean;


/**
 * Created by tc :)
 */
public class BaseData {

    // 推荐页
    public static final int TYPE_BANNER = 1000;// 轮播图
    public static final int TYPE_TITLE_MORE = 1001;// title,更多
    public static final int TYPE_HOT_SONG_LIST = 1002;// 热门歌单

    // 搜索结果页
    public static final int TYPE_SEARCH_RESULT_ARTIST = 2000;// 搜索结果的歌手
    public static final int TYPE_SEARCH_RESULT_ALBUM = 2001;// 搜索结果的专辑
    public static final int TYPE_SEARCH_RESULT_SONG = 2002;// 搜索结果的单曲

    // 排行榜页（新歌展示，普通）
    public static final int TYPE_RANK_NEW_BILLBOARD = 3000;// 新歌展示
    public static final int TYPE_RANK_BILLBOARD = 3001;// 普通

    protected int type;// 必须的

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "type=" + type +
                '}';
    }
}
