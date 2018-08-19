package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.Map;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBillboardTask extends Task<GetBillboardTask.RequestValue, GetBillboardTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValue requestValues) {
        return RepositoryProvider.getRepository()
                .getBillboard(requestValues.getUrl(), requestValues.getParameter())
                .map(ResponseValue::new);
    }

    public static final class RequestValue extends Task.CommonRequestValue {

        public RequestValue(String url, Map<String, String> parameter) {
            super(url, parameter);
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private Billboard mResult;

        public ResponseValue(@NonNull Billboard result) {
            mResult = result;
        }

        public Billboard getResult() {
            return mResult;
        }
    }
}
