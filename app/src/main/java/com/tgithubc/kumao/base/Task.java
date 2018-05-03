package com.tgithubc.kumao.base;

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
                .compose(new ApplySchedulers<RP>());
    }

    protected abstract Observable<RP> executeTask(RQ requestValues);

    public interface RequestValues {
    }

    public interface ResponseValue {
    }

    private class ApplySchedulers<T> implements Observable.Transformer<T, T> {

        @Override
        public Observable<T> call(Observable<T> observable) {
            return observable
                    .subscribeOn(mBackgroundScheduler)
                    .observeOn(AndroidSchedulers.mainThread());
        }
    }
}