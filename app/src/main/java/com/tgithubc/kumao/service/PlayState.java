package com.tgithubc.kumao.service;

/**
 * Created by tc :)
 */

public class PlayState {

    public static final int STATE_ERROR = -1;//error
    public static final int STATE_IDLE = 0;//闲置
    public static final int STATE_PREPARE = 1;//准备
    public static final int STATE_CONTINUE = 2;//继续播放
    public static final int STATE_PAUSE = 3;//暂停
    public static final int STATE_PLAY_COMPLETE = 4;//播放完成
    public static final int STATE_REAL_PLAY = 5;//真正播放
    public static final int STATE_PLAYING = 5;//播放中
    public static final int STATE_STOP = 6;//停止
    public static final int STATE_BUFFERING = 7;//缓冲中
    public static final int STATE_BUFFERING_END = 8;//缓冲结束
}
