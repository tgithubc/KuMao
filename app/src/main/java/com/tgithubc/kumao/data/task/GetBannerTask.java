package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.List;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBannerTask extends Task<Task.CommonRequestValue, GetBannerTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getBanner(requestValues.getUrl(), requestValues.getParameter())
                .map(result -> {
                    BaseData<List<Banner>> bannerData = new BaseData<>();
                    bannerData.setType(BaseData.TYPE_BANNER);
                    bannerData.setData(result);
                    return new ResponseValue(bannerData);
                });
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private BaseData<List<Banner>> result;

        public ResponseValue(@NonNull BaseData<List<Banner>> result) {
            this.result = result;
        }

        public BaseData<List<Banner>> getResult() {
            return result;
        }
    }
}
