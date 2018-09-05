package com.tgithubc.kumao.bean;


/**
 * Created by tc :)
 */
public class Banner {

    private String bannerPic;

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
