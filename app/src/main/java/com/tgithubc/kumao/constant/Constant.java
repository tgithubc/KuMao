package com.tgithubc.kumao.constant;

/**
 * Created by tc :)
 */
public class Constant {

    public interface Api {

        String HOST = "http://tingapi.ting.baidu.com/v1/restserver/ting?";
        String URL_BANNER = HOST + "method=baidu.ting.plaza.getFocusPic";
        String URL_BILLBOARD = HOST + "method=baidu.ting.billboard.billList";
        String URL_HOTWORD = HOST + "method=baidu.ting.search.hot";
        String URL_SEARCH = HOST + "method=baidu.ting.search.common";
        String URL_SONGINFO = HOST + "method=baidu.ting.song.play";

        /**
         * 榜单type
         * 1，新歌榜
         * 2，热歌榜
         * 6，KTV热歌榜
         * 7，叱咤歌曲榜
         * 8，美国Billboard单曲榜
         * 9，雪碧音碰音榜
         * 11，摇滚榜
         * 14，影视金曲榜
         * 18，Hito中文榜
         * 20，华语金曲榜
         * 21，欧美金曲榜
         * 22，经典老歌榜
         * 23，情歌对唱榜
         * 25，网络歌曲榜
         * 32，韩语榜
         */
        int BILLBOARD_TYPE_NEW = 1;
        int BILLBOARD_TYPE_HOT = 2;
        int BILLBOARD_TYPE_NET_HOT = 25;
    }
}
