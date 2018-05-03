package com.tgithubc.kumao.bean;

import java.util.List;

/**
 * Created by tc :)
 */
public class FeaturedData {

    private List<Banner> bannerList;
    private List<Billboard> billboardList;

    public List<Banner> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Banner> bannerList) {
        this.bannerList = bannerList;
    }

    public List<Billboard> getBillboardList() {
        return billboardList;
    }

    public void setBillboardList(List<Billboard> billboardList) {
        this.billboardList = billboardList;
    }

    @Override
    public String toString() {
        return "FeaturedData{" +
                "bannerList=" + bannerList +
                ", billboardList=" + billboardList +
                '}';
    }
}
