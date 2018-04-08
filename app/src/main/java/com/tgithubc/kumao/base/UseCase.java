package com.tgithubc.kumao.base;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by tc :)
 */
public abstract class UseCase<RQ extends UseCase.RequestValues, RP extends UseCase.ResponseValue> {

    /**
     * UseCase 需要在哪个线程运行，后面可以考虑dagger2注入进去
     */
    private final Scheduler mBackgroundScheduler;

    protected UseCase(Scheduler backgroundScheduler) {
        this.mBackgroundScheduler = backgroundScheduler;
    }

    public Observable<RP> run(RQ requestValues) {
        return executeUseCase(requestValues)
                .subscribeOn(mBackgroundScheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected abstract Observable<RP> executeUseCase(RQ requestValues);

    /**
     * Data passed to a request.
     */
    public interface RequestValues {
    }

    /**
     * Data received from a request.
     */
    public interface ResponseValue {
    }
}