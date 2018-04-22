package com.tgithubc.kumao.message.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * Created by tc :)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Decorate {
    @RunThread int runThread() default RunThread.MAIN;
    long delayedTime() default 0L;
    boolean isSticky() default false;
}
