package com.tgithubc.kumao.data.task;


import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import io.reactivex.Observable;

/**
 * Created by tc :)
 */
public class DeleteSearchHistoryTask extends Task<DeleteSearchHistoryTask.RequestValue, Task.EmptyResponseValue> {

    @Override
    protected Observable<EmptyResponseValue> executeTask(RequestValue requestValues) {
        return Observable.create(subscriber -> {
            RepositoryProvider.getRepository().deleteSearchHistory(requestValues.getKeyWord());
            subscriber.onNext(new EmptyResponseValue());
            subscriber.onComplete();
        });
    }

    public static final class RequestValue implements Task.RequestValue {

        private KeyWord keyWord;

        public RequestValue(KeyWord keyWord) {
            this.keyWord = keyWord;
        }

        public KeyWord getKeyWord() {
            return keyWord;
        }
    }
}
