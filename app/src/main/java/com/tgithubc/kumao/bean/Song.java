package com.tgithubc.kumao.bean;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tc :)
 */
@Entity
public class Song extends BaseData implements Parcelable {

    @Id(autoincrement = true)
    private Long ID;

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

    // 大图 1000*1000
    private String bigMorePic;

    // 歌词下载地址
    private String lrclink;

    //文件地址
    private String filelink;

    // 是否new
    private String isNew;

    // 应该是热度，收听数
    private String hot;

    // 这首歌的所有格式 example：（64,128,256,320,flac）
    private String rateArrary;

    // 这首歌的格式
    private String rate;

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

    // 文件大小
    private long fileSize;

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

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


    public String getBigMorePic() {
        return bigMorePic;
    }

    public void setBigMorePic(String bigMorePic) {
        this.bigMorePic = bigMorePic;
    }

    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
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
        dest.writeString(bigMorePic);
        dest.writeString(rate);
        dest.writeLong(fileSize);
    }

    public Song() {
    }

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
        bigMorePic = in.readString();
        rate = in.readString();
        fileSize = in.readLong();
    }

    @Generated(hash = 1887854520)
    public Song(Long ID, String songId, String songName, String authorName,
            String artistId, String albumId, String albumName, String smallPic,
            String bigPic, String bigMorePic, String lrclink, String filelink,
            String isNew, String hot, String rateArrary, String rate, int duration,
            String freeBitrate, String biaoshi, String info, String company,
            String content, long fileSize) {
        this.ID = ID;
        this.songId = songId;
        this.songName = songName;
        this.authorName = authorName;
        this.artistId = artistId;
        this.albumId = albumId;
        this.albumName = albumName;
        this.smallPic = smallPic;
        this.bigPic = bigPic;
        this.bigMorePic = bigMorePic;
        this.lrclink = lrclink;
        this.filelink = filelink;
        this.isNew = isNew;
        this.hot = hot;
        this.rateArrary = rateArrary;
        this.rate = rate;
        this.duration = duration;
        this.freeBitrate = freeBitrate;
        this.biaoshi = biaoshi;
        this.info = info;
        this.company = company;
        this.content = content;
        this.fileSize = fileSize;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (duration != song.duration) return false;
        if (fileSize != song.fileSize) return false;
        if (ID != null ? !ID.equals(song.ID) : song.ID != null) return false;
        if (songId != null ? !songId.equals(song.songId) : song.songId != null) return false;
        if (songName != null ? !songName.equals(song.songName) : song.songName != null) return false;
        if (authorName != null ? !authorName.equals(song.authorName) : song.authorName != null) return false;
        if (artistId != null ? !artistId.equals(song.artistId) : song.artistId != null) return false;
        if (albumId != null ? !albumId.equals(song.albumId) : song.albumId != null) return false;
        if (albumName != null ? !albumName.equals(song.albumName) : song.albumName != null) return false;
        if (smallPic != null ? !smallPic.equals(song.smallPic) : song.smallPic != null) return false;
        if (bigPic != null ? !bigPic.equals(song.bigPic) : song.bigPic != null) return false;
        if (bigMorePic != null ? !bigMorePic.equals(song.bigMorePic) : song.bigMorePic != null) return false;
        if (lrclink != null ? !lrclink.equals(song.lrclink) : song.lrclink != null) return false;
        if (filelink != null ? !filelink.equals(song.filelink) : song.filelink != null) return false;
        if (isNew != null ? !isNew.equals(song.isNew) : song.isNew != null) return false;
        if (hot != null ? !hot.equals(song.hot) : song.hot != null) return false;
        if (rateArrary != null ? !rateArrary.equals(song.rateArrary) : song.rateArrary != null) return false;
        if (rate != null ? !rate.equals(song.rate) : song.rate != null) return false;
        if (freeBitrate != null ? !freeBitrate.equals(song.freeBitrate) : song.freeBitrate != null) return false;
        if (biaoshi != null ? !biaoshi.equals(song.biaoshi) : song.biaoshi != null) return false;
        if (info != null ? !info.equals(song.info) : song.info != null) return false;
        if (company != null ? !company.equals(song.company) : song.company != null) return false;
        return content != null ? content.equals(song.content) : song.content == null;
    }

    @Override
    public int hashCode() {
        int result = ID != null ? ID.hashCode() : 0;
        result = 31 * result + (songId != null ? songId.hashCode() : 0);
        result = 31 * result + (songName != null ? songName.hashCode() : 0);
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (artistId != null ? artistId.hashCode() : 0);
        result = 31 * result + (albumId != null ? albumId.hashCode() : 0);
        result = 31 * result + (albumName != null ? albumName.hashCode() : 0);
        result = 31 * result + (smallPic != null ? smallPic.hashCode() : 0);
        result = 31 * result + (bigPic != null ? bigPic.hashCode() : 0);
        result = 31 * result + (bigMorePic != null ? bigMorePic.hashCode() : 0);
        result = 31 * result + (lrclink != null ? lrclink.hashCode() : 0);
        result = 31 * result + (filelink != null ? filelink.hashCode() : 0);
        result = 31 * result + (isNew != null ? isNew.hashCode() : 0);
        result = 31 * result + (hot != null ? hot.hashCode() : 0);
        result = 31 * result + (rateArrary != null ? rateArrary.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + duration;
        result = 31 * result + (freeBitrate != null ? freeBitrate.hashCode() : 0);
        result = 31 * result + (biaoshi != null ? biaoshi.hashCode() : 0);
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (company != null ? company.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (int) (fileSize ^ (fileSize >>> 32));
        return result;
    }

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