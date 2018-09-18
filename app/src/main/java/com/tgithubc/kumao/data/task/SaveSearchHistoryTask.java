package com.tgithubc.kumao.data.task;


import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import io.reactivex.Observable;


/**
 * Created by tc :)
 */
public class SaveSearchHistoryTask extends Task<SaveSearchHistoryTask.RequestValue, Task.EmptyResponseValue> {

    @Override
    protected Observable<EmptyResponseValue> executeTask(RequestValue requestValues) {
        return Observable.create(subscriber -> {
            RepositoryProvider.getRepository().saveSearchHistory(requestValues.getParameter());
            subscriber.onNext(new EmptyResponseValue());
            subscriber.onComplete();
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
}
