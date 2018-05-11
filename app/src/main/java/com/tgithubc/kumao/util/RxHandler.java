package com.tgithubc.kumao.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by tc :)
 */
public class RxHandler {

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<String, T> handlerResult(Class<T> tClazz) {
        return observable -> observable
                .flatMap((Func1<String, Observable<T>>) s ->
                        Observable.just(GsonHelper.getInstance().fromJson(s, tClazz)));
    }

    public static <T> Observable.Transformer<T, T> applyScheduler() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
