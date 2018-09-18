package com.tgithubc.kumao.data.task;


import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import io.reactivex.Observable;

/**
 * Created by tc :)
 */
public class DeleteAllSearchHistoryTask extends Task<Task.EmptyRequestValue, Task.EmptyResponseValue> {

    @Override
    protected Observable<EmptyResponseValue> executeTask(EmptyRequestValue requestValues) {
        return Observable.create(subscriber -> {
            RepositoryProvider.getRepository().clearSearchHistory();
            subscriber.onNext(new EmptyResponseValue());
            subscriber.onComplete();
        });
    }
}
