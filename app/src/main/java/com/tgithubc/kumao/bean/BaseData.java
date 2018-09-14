package com.tgithubc.kumao.bean;


/**
 * Created by tc :)
 */
public class BaseData {

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
