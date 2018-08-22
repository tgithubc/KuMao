package com.tgithubc.kumao.util;

import com.tgithubc.kumao.parser.IParser;
import com.tgithubc.kumao.parser.ParserFactory;

import org.json.JSONException;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by tc :)
 */
public class RxHandler {


    /*
    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<String, T> handlerResult(Class<T> tClazz) {
        return observable -> observable
                .flatMap((Func1<String, Observable<T>>) s ->
                        Observable.just(GsonHelper.getInstance().fromJson(s, tClazz)));
    }
    */

    // gson对非RESTful的结果解析不太好用，手动解析吧
    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<String, T> handlerResult(int type) {
        return observable -> observable
                .flatMap(new Func1<String, Observable<T>>() {
                    @Override
                    public Observable<T> call(String s) {
                        IParser<T> parser = ParserFactory.createParser(type);
                        if (parser != null) {
                            try {
                                return Observable.just(parser.parse(s));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        return Observable.error(new RuntimeException("Parser Error"));
                    }
                });
    }

    public static <T> Observable.Transformer<T, T> applyScheduler(Scheduler mBackgroundScheduler) {
        return observable -> observable
                .subscribeOn(mBackgroundScheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
