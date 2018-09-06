package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Banner;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBannerTask extends Task<Task.CommonRequestValue, GetBannerTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getBanner(requestValues.getUrl(), requestValues.getParameter())
                .map(banner -> {
                    banner.setType(BaseData.TYPE_BANNER);
                    return new ResponseValue(banner);
                });
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private Banner result;

        public ResponseValue(@NonNull Banner result) {
            this.result = result;
        }

        public Banner getResult() {
            return result;
        }
    }
}
