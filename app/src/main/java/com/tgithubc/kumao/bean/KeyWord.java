package com.tgithubc.kumao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by tc :)
 */
@Entity
public class KeyWord {

    @Id(autoincrement = true)
    private Long ID;
    private String keyWord;
    private long searchTime;

    @Generated(hash = 1687250301)
    public KeyWord(Long ID, String keyWord, long searchTime) {
        this.ID = ID;
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

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}