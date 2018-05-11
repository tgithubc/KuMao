package com.tgithubc.kumao.base;

import com.tgithubc.kumao.util.RxHandler;

import java.util.Map;

import rx.Observable;
import rx.Scheduler;
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
                .compose(RxHandler.applyScheduler());
    }

    protected abstract Observable<RP> executeTask(RQ requestValues);

    public interface RequestValues {
    }

    public interface ResponseValue {
    }

    public static class CommonRequestValues implements RequestValues {

        private String url;
        private Map<String, String> parameter;

        public CommonRequestValues(String url, Map<String, String> parameter) {
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
}