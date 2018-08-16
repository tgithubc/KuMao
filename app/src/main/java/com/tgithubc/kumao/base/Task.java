package com.tgithubc.kumao.base;

import com.tgithubc.kumao.util.RxHandler;

import java.util.Map;

import rx.Observable;
import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by tc :)
 */
public abstract class Task<RQ extends Task.RequestValue, RP extends Task.ResponseValue> {

    private Scheduler mBackgroundScheduler;

    protected Task(Scheduler backgroundScheduler) {
        this.mBackgroundScheduler = backgroundScheduler;
    }

    protected Task() {
        this(Schedulers.io());
    }

    public Observable<RP> execute(RQ requestValues) {
        return executeTask(requestValues)
                .compose(RxHandler.applyScheduler(mBackgroundScheduler));
    }

    protected abstract Observable<RP> executeTask(RQ requestValues);

    public interface RequestValue {
    }

    public interface ResponseValue {
    }

    /**
     * 通用请求参数
     */
    public static class CommonRequestValue implements RequestValue {

        private String url;
        private Map<String, String> parameter;

        public CommonRequestValue(String url, Map<String, String> parameter) {
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

    /**
     * 空请求参数
     */
    public static class EmptyRequestValue implements RequestValue {

    }

    /**
     * 空返回数据
     */
    public static class EmptyResponseValue implements ResponseValue {

    }
}