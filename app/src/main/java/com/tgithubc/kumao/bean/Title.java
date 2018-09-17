package com.tgithubc.kumao.bean;

/**
 * Created by tc :)
 */

public class Title extends BaseData {

    private String title;
    private boolean addMore = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAddMore() {
        return addMore;
    }

    public void setAddMore(boolean addMore) {
        this.addMore = addMore;
    }

}
