package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.SearchResult;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetSearchResultTask extends Task<GetSearchResultTask.RequestValues, GetSearchResultTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValues requestValues) {
        return RepositoryProvider.getRepository()
                .getSearchResult(requestValues.getUrl(), requestValues.getParameter())
                .map(ResponseValue::new);
    }

    public static final class RequestValues extends Task.CommonRequestValues {

        public RequestValues(String url, Map<String, String> parameter) {
            super(url, parameter);
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private SearchResult mResult;

        public ResponseValue(@NonNull SearchResult result) {
            mResult = result;
        }

        public SearchResult getResult() {
            return mResult;
        }
    }
}
