package com.tgithubc.kumao.base;

import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tc :)
 */
public abstract class Task<RQ extends Task.RequestValues, RP extends Task.ResponseValue> {

    private Scheduler mBackgroundScheduler;

    protected Task(Scheduler backgroundScheduler) {
        this.mBackgroundScheduler = backgroundScheduler;
    }

    protected Task() {
        this.mBackgroundScheduler = Schedulers.io();
    }

    public Observable<RP> execute(RQ requestValues) {
        return executeTask(requestValues)
                .compose(ApplySchedulers.apply());
    }

    protected abstract Observable<RP> executeTask(RQ requestValues);

    public interface RequestValues {
    }

    public interface ResponseValue {
    }

    public static class SimpleRequestValues implements RequestValues {

        private String url;
        private Map<String, String> parameter;

        public SimpleRequestValues(String url, Map<String, String> parameter) {
            this.url = url;
            this.parameter = parameter;
        }

        public String getUrl() {
            return url;
        }

        public Map<String, String> getParameter() {
            return parameter;
        }
    }

    private static class ApplySchedulers {

        private static <T> Observable.Transformer<T, T> apply() {
            return observable -> observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}