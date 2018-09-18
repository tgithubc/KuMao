package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import io.reactivex.Observable;

/**
 * Created by tc :)
 */
public class GetBillboardTask extends Task<Task.CommonRequestValue, GetBillboardTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(CommonRequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getBillboard(requestValues.getUrl(), requestValues.getParameter())
                .map(ResponseValue::new);
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private Billboard billboard;

        public ResponseValue(@NonNull Billboard billboard) {
            this.billboard = billboard;
        }

        public Billboard getResult() {
            return billboard;
        }
    }
}
