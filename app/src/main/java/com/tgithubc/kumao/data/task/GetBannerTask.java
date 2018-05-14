package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBannerTask extends Task<GetBannerTask.RequestValues, GetBannerTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValues requestValues) {
        return RepositoryProvider.getRepository()
                .getBanner(requestValues.getUrl(), requestValues.getParameter())
                .map(result -> new ResponseValue(result));
    }

    public static final class RequestValues extends Task.CommonRequestValues {

        public RequestValues(String url, Map<String, String> parameter) {
            super(url, parameter);
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private BannerResult mResult;

        public ResponseValue(@NonNull BannerResult result) {
            mResult = result;
        }

        public BannerResult getResult() {
            return mResult;
        }
    }
}
