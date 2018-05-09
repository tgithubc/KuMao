package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.api.MusicApi;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.http.RetrofitManager;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by tc :)
 */
public class GetBillboardListTask extends Task<GetBillboardListTask.RequestValues, GetBillboardListTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValues requestValues) {
        Observable<Observable<Billboard>> all =
                Observable.from(requestValues.getParameter())
                        .flatMap(new Func1<RequestValues.RequestParameter, Observable<Observable<Billboard>>>() {
                            @Override
                            public Observable<Observable<Billboard>> call(RequestValues.RequestParameter requestParameter) {
                                return Observable.just(RetrofitManager.getInstance()
                                        .createService(MusicApi.class)
                                        .getBillboard(requestParameter.type, requestParameter.offset, requestParameter.size));
                            }
                        });
        return Observable.merge(all)
                .toList()
                .flatMap(new Func1<List<Billboard>, Observable<ResponseValue>>() {
                    @Override
                    public Observable<ResponseValue> call(List<Billboard> billboards) {
                        return Observable.create(subscriber -> {
                            subscriber.onNext(new ResponseValue(billboards));
                            subscriber.onCompleted();
                        });
                    }
                });
    }

    public static final class RequestValues implements Task.RequestValues {

        private List<RequestParameter> parameters;

        public RequestValues(List<RequestParameter> parameters) {
            this.parameters = parameters;
        }

        public List<RequestParameter> getParameter() {
            return parameters;
        }

        public static final class RequestParameter {
            public int type;
            public int offset;
            public int size;

            public RequestParameter(int type, int offset, int size) {
                this.type = type;
                this.offset = offset;
                this.size = size;
            }
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private List<Billboard> mResult;

        public ResponseValue(@NonNull List<Billboard> result) {
            mResult = result;
        }

        public List<Billboard> getResult() {
            return mResult;
        }
    }
}
