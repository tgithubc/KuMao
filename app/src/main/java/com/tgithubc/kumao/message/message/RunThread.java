package com.tgithubc.kumao.message.message;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by tc :)
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({
        RunThread.MAIN,
        RunThread.BACKGROUND
})
public @interface RunThread {
    int MAIN = 0;
    int BACKGROUND = 1;
}