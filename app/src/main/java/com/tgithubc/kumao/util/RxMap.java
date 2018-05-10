package com.tgithubc.kumao.util;

import java.util.HashMap;
import java.util.Map;

public class RxMap<T,R>{

    private Map<T,R> map;

    public RxMap() {
        this.map = new HashMap<>();
    }

    public RxMap(Map<T,R> map) {
        this.map = map;
    }

    public RxMap<T,R> put(T t, R r){
        map.put(t,r);
        return this;
    }

    public Map<T,R> build(){
        return map;
    }
}
