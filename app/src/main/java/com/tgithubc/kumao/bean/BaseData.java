package com.tgithubc.kumao.bean;


/**
 * Created by tc :)
 */
public class BaseData<E> {

    // 推荐页
    public static final int TYPE_BANNER = 0;// 轮播图
    public static final int TYPE_BILLBOARD = 1;// 榜单
    public static final int TYPE_TITLE_MORE = 2;// title,更多

    // 搜索结果页
    public static final int TYPE_SEARCH_RESULT_ARTIST = 2;// 搜索结果的歌手
    public static final int TYPE_SEARCH_RESULT_ALBUM = 3;// 搜索结果的专辑
    public static final int TYPE_SEARCH_RESULT_SONG = 4;// 搜索结果的单曲

    private int type;
    private E data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseData{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
