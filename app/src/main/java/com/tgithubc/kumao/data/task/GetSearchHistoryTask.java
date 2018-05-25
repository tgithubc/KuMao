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
public class GetSearchHistoryTask extends Task<GetSearchHistoryTask.RequestValues, GetSearchHistoryTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValues requestValues) {
        return RepositoryProvider.getRepository()
                .getSearchHistory()
                .map(ResponseValue::new);
    }

    public static final class RequestValues implements Task.RequestValues {

    }

    public static final class ResponseValue implements Task.ResponseValue {

        private List<KeyWord> mResult;

        public ResponseValue(@NonNull List<KeyWord> result) {
            mResult = result;
        }

        public List<KeyWord> getResult() {
            return mResult;
        }
    }
}
