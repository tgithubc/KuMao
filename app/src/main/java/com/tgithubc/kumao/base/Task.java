package com.tgithubc.kumao.base;

import com.tgithubc.kumao.util.RxHandler;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;


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

    public interface ResponseValue<T> {

        T getResult();
    }

    /**
     * 通用请求参数
     */
    public static class CommonRequestValue implements RequestValue {

        private String url;
        private Map<String, String> parameter;
        // 对应在Task复用，但是拿到数据又需要设置不同的ui展示类型的情况下
        // 从请求参数携带过去你想要的展示类型，传递给返回数据，构造出不同的type作ui展示区分
        private int uiType;

        public CommonRequestValue(String url, Map<String, String> parameter, int uiType) {
            this.url = url;
            this.parameter = parameter;
            this.uiType = uiType;
        }

        public CommonRequestValue(String url, Map<String, String> parameter) {
            this.url = url;
            this.parameter = parameter;
        }

        public String getUrl() {
            return url;
        }

        public int getUiType() {
            return uiType;
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

        @Override
        public Object getResult() {
            return null;
        }
    }
}