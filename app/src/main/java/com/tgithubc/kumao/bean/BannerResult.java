package com.tgithubc.kumao.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerResult {

    @SerializedName("pic")
    private List<Banner> bannerList;

    public void setBannerList(List<Banner> pic) {
        this.bannerList = pic;
    }

    public List<Banner> getBannerList() {
        return this.bannerList;
    }

    @Override
    public String toString() {
        return "BannerResult{" +
                "bannerList=" + bannerList +
                '}';
    }
}