package com.tgithubc.kumao.bean;


import java.util.List;


/**
 * 单条榜单
 * Created by tc :)
 */
public class Billboard {
    /**
     * 榜单的歌曲列表
     */
    private List<Song> songList;

    /**
     * 榜单的信息
     */
    private Info billboardInfo;

    public List<Song> getSongList() {
        return this.songList;
    }

    public Info getBillboardInfo() {
        return this.billboardInfo;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public void setBillboardInfo(Info billboardInfo) {
        this.billboardInfo = billboardInfo;
    }

    @Override
    public String toString() {
        return "Billboard{" +
                "songList=" + songList +
                ", billboardInfo=" + billboardInfo +
                '}';
    }

    public static class Info {

        // 榜单类型
        private int billboardType;

        // 榜单更新时间
        private String updateDate;

        // 是否有更多（1有，0没有）
        private int havemore;

        // 榜单名字
        private String name;

        // 榜单描述
        private String comment;

        // 这个应该是中的方的尺寸
        private String pic_s192;

        // 这个应该是大的方的尺寸
        private String pic_s640;

        // 这个应该是大的横的尺寸
        private String pic_s444;

        // 这个应该是小的方的尺寸
        private String pic_s260;

        // 这个应该是小的横的尺寸
        private String pic_s210;

        public void setBillboardType(int billboardType) {
            this.billboardType = billboardType;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public void setHavemore(int havemore) {
            this.havemore = havemore;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public void setPic_s192(String pic_s192) {
            this.pic_s192 = pic_s192;
        }

        public void setPic_s640(String pic_s640) {
            this.pic_s640 = pic_s640;
        }

        public void setPic_s444(String pic_s444) {
            this.pic_s444 = pic_s444;
        }

        public void setPic_s260(String pic_s260) {
            this.pic_s260 = pic_s260;
        }

        public void setPic_s210(String pic_s210) {
            this.pic_s210 = pic_s210;
        }

        public int getBillboardType() {
            return this.billboardType;
        }

        public String getUpdateDate() {
            return this.updateDate;
        }

        public int getHavemore() {
            return this.havemore;
        }

        public String getName() {
            return this.name;
        }

        public String getComment() {
            return this.comment;
        }

        public String getPic_s192() {
            return this.pic_s192;
        }

        public String getPic_s640() {
            return this.pic_s640;
        }

        public String getPic_s444() {
            return this.pic_s444;
        }

        public String getPic_s260() {
            return this.pic_s260;
        }

        public String getPic_s210() {
            return this.pic_s210;
        }

        @Override
        public String toString() {
            return "Billboard{" +
                    "billboardType='" + billboardType + '\'' +
                    ", updateDate='" + updateDate + '\'' +
                    ", havemore=" + havemore +
                    ", name='" + name + '\'' +
                    ", comment='" + comment + '\'' +
                    ", pic_s192='" + pic_s192 + '\'' +
                    ", pic_s640='" + pic_s640 + '\'' +
                    ", pic_s444='" + pic_s444 + '\'' +
                    ", pic_s260='" + pic_s260 + '\'' +
                    ", pic_s210='" + pic_s210 + '\'' +
                    '}';
        }
    }
}