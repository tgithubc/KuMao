package com.tgithubc.kumao.data.task;


import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import rx.Observable;

/**
 * Created by tc :)
 */
public class SaveSearchHistoryTask extends Task<SaveSearchHistoryTask.RequestValue, SaveSearchHistoryTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValue requestValues) {
        return Observable.create(subscriber -> {
            RepositoryProvider.getRepository().saveSearchHistory(requestValues.getParameter());
            subscriber.onNext(new ResponseValue());
            subscriber.onCompleted();
        });
    }

    public static final class RequestValue implements Task.RequestValue {

        private String keyWord;

        public RequestValue(String keyWord) {
            this.keyWord = keyWord;
        }

        public String getParameter() {
            return keyWord;
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

    }
}
