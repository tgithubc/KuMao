package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

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
                        .flatMap(new Func1<SimpleRequestValues, Observable<Observable<Billboard>>>() {
                            @Override
                            public Observable<Observable<Billboard>> call(SimpleRequestValues commonRequestValues) {
                                return Observable.just(RepositoryProvider.getTasksRepository()
                                        .getBillboard(commonRequestValues.getUrl(), commonRequestValues.getParameter()));
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

        private List<SimpleRequestValues> parameters;

        public RequestValues(List<SimpleRequestValues> parameters) {
            this.parameters = parameters;
        }

        public List<SimpleRequestValues> getParameter() {
            return parameters;
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
