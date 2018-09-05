package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.KeyWord;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.List;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetSearchHistoryTask extends Task<Task.EmptyRequestValue, GetSearchHistoryTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(EmptyRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getSearchHistory()
                .map(ResponseValue::new);
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private List<KeyWord> result;

        public ResponseValue(@NonNull List<KeyWord> result) {
            this.result = result;
        }

        public List<KeyWord> getResult() {
            return result;
        }
    }
}
