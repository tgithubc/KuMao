package com.tgithubc.kumao.bean;


/**
 * Created by tc :)
 */
public class FeaturedData<E> {

    public static final int TYPE_BANNER = 0;// 轮播图
    public static final int TYPE_BILLBOARD = 1;// 榜单

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
}
