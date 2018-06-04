package com.tgithubc.kumao.bean;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tc :)
 */
public class Song implements Parcelable{

    // 歌曲的id
    private String songId;

    // 歌曲名
    private String songName;

    // 歌手名
    private String authorName;

    // 歌手的id
    private String artistId;

    // 专辑id
    private String albumId;

    // 专辑名
    private String albumName;

    // 小图留一个150*150的
    private String smallPic;

    // 大图 500*500
    private String bigPic;

    // 歌词下载地址
    private String lrclink;

    //文件地址
    private String filelink;

    // 是否new
    private String isNew;

    // 应该是热度，收听数
    private String hot;

    // 这首歌的格式 example：（64,128,256,320,flac）
    private String rateArrary;

    // 歌曲时长
    private int duration;

    //不知道怎么计算的免费格式 example：（{"0":"129|-1","1":"-1|-1"}）
    private String freeBitrate;

    // vip标示？？
    private String biaoshi;

    // 一些补充描述吧 example：（"电影《将爱情进行到底》主题曲"）
    private String info;

    // 唱片公司的名字
    private String company;

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtistId() {
        return this.artistId;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getSmallPic() {
        return this.smallPic;
    }

    public void setLrclink(String lrclink) {
        this.lrclink = lrclink;
    }

    public String getLrclink() {
        return this.lrclink;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getHot() {
        return this.hot;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getIsNew() {
        return this.isNew;
    }

    public void setRateArrary(String rateArrary) {
        this.rateArrary = rateArrary;
    }

    public String getRateArrary() {
        return this.rateArrary;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setFreeBitrate(String freeBitrate) {
        this.freeBitrate = freeBitrate;
    }

    public String getFreeBitrate() {
        return this.freeBitrate;
    }

    public void setBiaoshi(String biaoshi) {
        this.biaoshi = biaoshi;
    }

    public String getBiaoshi() {
        return this.biaoshi;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return this.info;
    }

    public String getFilelink() {
        return filelink;
    }

    public void setFilelink(String filelink) {
        this.filelink = filelink;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompany() {
        return this.company;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongId() {
        return this.songId;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumId() {
        return this.albumId;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return this.albumName;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getBigPic() {
        return this.bigPic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songId);
        dest.writeString(songName);
        dest.writeString(authorName);
        dest.writeString(artistId);
        dest.writeString(albumId);
        dest.writeString(albumName);
        dest.writeString(smallPic);
        dest.writeString(bigPic);
        dest.writeString(lrclink);
        dest.writeString(isNew);
        dest.writeString(hot);
        dest.writeString(rateArrary);
        dest.writeInt(duration);
        dest.writeString(freeBitrate);
        dest.writeString(biaoshi);
        dest.writeString(info);
        dest.writeString(company);
        dest.writeString(content);
        dest.writeString(filelink);
    }

    public Song(){}

    protected Song(Parcel in) {
        songId = in.readString();
        songName = in.readString();
        authorName = in.readString();
        artistId = in.readString();
        albumId = in.readString();
        albumName = in.readString();
        smallPic = in.readString();
        bigPic = in.readString();
        lrclink = in.readString();
        isNew = in.readString();
        hot = in.readString();
        rateArrary = in.readString();
        duration = in.readInt();
        freeBitrate = in.readString();
        biaoshi = in.readString();
        info = in.readString();
        company = in.readString();
        content = in.readString();
        filelink = in.readString();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    @Override
    public String toString() {
        return "Song{" +
                "songId='" + songId + '\'' +
                ", songName='" + songName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", artistId='" + artistId + '\'' +
                ", albumId='" + albumId + '\'' +
                ", albumName='" + albumName + '\'' +
                ", smallPic='" + smallPic + '\'' +
                ", bigPic='" + bigPic + '\'' +
                ", lrclink='" + lrclink + '\'' +
                ", filelink='" + filelink + '\'' +
                ", isNew='" + isNew + '\'' +
                ", hot='" + hot + '\'' +
                ", rateArrary='" + rateArrary + '\'' +
                ", duration=" + duration +
                ", freeBitrate='" + freeBitrate + '\'' +
                ", biaoshi='" + biaoshi + '\'' +
                ", info='" + info + '\'' +
                ", company='" + company + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}