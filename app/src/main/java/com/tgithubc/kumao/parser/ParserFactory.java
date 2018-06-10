package com.tgithubc.kumao.parser;

/**
 * Created by tc :)
 */
public class ParserFactory {

    public static final int PARSE_BANNER = 1;
    public static final int PARSE_BILLBOARD = 2;
    public static final int PARSE_HOTWORD = 3;
    public static final int PARSE_SEARCH_RESULT = 4;
    public static final int PARSE_SONG_INFO = 5;

    public static IParser createParser(int type) {
        switch (type) {
            case PARSE_BANNER:
                return new BannerParser();
            case PARSE_BILLBOARD:
                return new BillboardParser();
            case PARSE_HOTWORD:
                return new HotWordParser();
            case PARSE_SEARCH_RESULT:
                return new SearchResultParser();
            case PARSE_SONG_INFO:
                return new SongInfoParser();
        }
        return null;
    }
}
