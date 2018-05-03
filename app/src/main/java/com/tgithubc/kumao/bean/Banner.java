package com.tgithubc.kumao.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tc :)
 */
public class Banner {

    @SerializedName("randpic")
    private String bannerPic;

    @SerializedName("code")
    private String url;

    public String getBannerPic() {
        return bannerPic;
    }

    public void setBannerPic(String bannerPic) {
        this.bannerPic = bannerPic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "bannerPic='" + bannerPic + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
