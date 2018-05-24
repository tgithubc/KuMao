package com.tgithubc.kumao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tc :)
 */
@Entity
public class KeyWord {

    private String keyWord;
    private long searchTime;

    @Generated(hash = 2093017384)
    public KeyWord(String keyWord, long searchTime) {
        this.keyWord = keyWord;
        this.searchTime = searchTime;
    }

    @Generated(hash = 617591908)
    public KeyWord() {
    }

    public long getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(long searchTime) {
        this.searchTime = searchTime;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}