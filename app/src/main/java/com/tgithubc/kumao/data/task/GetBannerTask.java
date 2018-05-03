package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BannerResult;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBannerTask extends Task<GetBannerTask.RequestValues, GetBannerTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValues requestValues) {
        return RepositoryProvider.getTasksRepository()
                .getBanner(requestValues.getNumber())
                .map(ResponseValue::new);
    }

    public static final class RequestValues implements Task.RequestValues {

        private int number;

        public RequestValues(int number) {
            this.number = number;
        }

        public int getNumber() {
            return number;
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
