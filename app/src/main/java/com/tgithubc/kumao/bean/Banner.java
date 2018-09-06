package com.tgithubc.kumao.bean;


import java.util.List;

/**
 * Created by tc :)
 */
public class Banner extends BaseData{

    private List<String> bannerPicList;

    private List<String> urlList;

    public List<String> getBannerPicList() {
        return bannerPicList;
    }

    public void setBannerPicList(List<String> bannerPicList) {
        this.bannerPicList = bannerPicList;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "bannerPicList='" + bannerPicList + '\'' +
                ", urlList='" + urlList + '\'' +
                '}';
    }
}
