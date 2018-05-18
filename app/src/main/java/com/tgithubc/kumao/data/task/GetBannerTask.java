package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.List;
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
                .map(ResponseValue::new);
    }

    public static final class RequestValues extends Task.CommonRequestValues {

        public RequestValues(String url, Map<String, String> parameter) {
            super(url, parameter);
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private List<Banner> mResult;

        public ResponseValue(@NonNull List<Banner> result) {
            mResult = result;
        }

        public List<Banner> getResult() {
            return mResult;
        }
    }
}
