package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.List;
import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBannerTask extends Task<GetBannerTask.RequestValue, GetBannerTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getBanner(requestValues.getUrl(), requestValues.getParameter())
                .map(result -> {
                    BaseData<List<Banner>> bannerData = new BaseData<>();
                    bannerData.setType(BaseData.TYPE_BANNER);
                    bannerData.setData(result);
                    return new ResponseValue(bannerData);
                });
    }

    public static final class RequestValue extends Task.CommonRequestValue {

        public RequestValue(String url, Map<String, String> parameter) {
            super(url, parameter);
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private BaseData<List<Banner>> mResult;

        public ResponseValue(@NonNull BaseData<List<Banner>> result) {
            mResult = result;
        }

        public BaseData<List<Banner>> getResult() {
            return mResult;
        }
    }
}
