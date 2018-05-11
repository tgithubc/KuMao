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
                        .flatMap(new Func1<CommonRequestValues, Observable<Observable<Billboard>>>() {
                            @Override
                            public Observable<Observable<Billboard>> call(CommonRequestValues values) {
                                return Observable.just(RepositoryProvider.getTasksRepository()
                                        .getBillboard(values.getUrl(), values.getParameter()));
                            }
                        });
        return Observable.merge(all)
                .toList()
                .flatMap(new Func1<List<Billboard>, Observable<ResponseValue>>() {
                    @Override
                    public Observable<ResponseValue> call(List<Billboard> billboards) {
                        return Observable.just(new ResponseValue(billboards));
                    }
                });
    }

    public static final class RequestValues implements Task.RequestValues {

        private List<CommonRequestValues> parameters;

        public RequestValues(List<CommonRequestValues> parameters) {
            this.parameters = parameters;
        }

        public List<CommonRequestValues> getParameter() {
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
