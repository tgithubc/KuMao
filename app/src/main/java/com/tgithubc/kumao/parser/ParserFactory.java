package com.tgithubc.kumao.parser;

/**
 * Created by tc :)
 */
public class ParserFactory {

    public static final int PARSE_BANNER = 1;
    public static final int PARSE_BILLBOARD = 2;

    public static IParser createParser(int type) {
        switch (type) {
            case PARSE_BANNER:
                return new BannerParser();
            case PARSE_BILLBOARD:
                return new BillboardParser();
        }
        return null;
    }
}
