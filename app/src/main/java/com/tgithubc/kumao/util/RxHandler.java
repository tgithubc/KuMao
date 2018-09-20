package com.tgithubc.kumao.util;

import com.tgithubc.kumao.parser.IParser;
import com.tgithubc.kumao.parser.ParserFactory;

import org.json.JSONException;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

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
    public static <T> ObservableTransformer<String, T> handlerResult(int type) {
        return observable -> observable
                .flatMap((Function<String, Observable<T>>) s -> {
                    IParser<T> parser = ParserFactory.createParser(type);
                    if (parser != null) {
                        try {
                            return Observable.just(parser.parse(s));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    return Observable.error(new RuntimeException("Parser Error"));
                });
    }

    public static <T> ObservableTransformer<T, T> applyScheduler(Scheduler mBackgroundScheduler) {
        return upstream -> upstream
                .subscribeOn(mBackgroundScheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }
}
