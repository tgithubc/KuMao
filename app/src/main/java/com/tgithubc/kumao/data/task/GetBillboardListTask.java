package com.tgithubc.kumao.data.task;

import android.support.annotation.NonNull;

import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.bean.BaseData;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.data.repository.RepositoryProvider;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by tc :)
 */
public class GetBillboardListTask extends Task<GetBillboardListTask.RequestValue, GetBillboardListTask.ResponseValue> {

    @Override
    protected Observable<ResponseValue> executeTask(RequestValue requestValues) {
        Observable<Observable<Billboard>> all = Observable
                .from(requestValues.getParameter())
                .flatMap(values -> {
                    Observable<Billboard> single = RepositoryProvider.getRepository().getBillboard(values.getUrl(), values.getParameter());
                    return Observable.just(single);
                });
        return Observable
                .merge(all)
                .toList()
                .flatMap(billboards -> {
                            List<BaseData<Billboard>> list = new ArrayList<>();
                            Observable.from(billboards).forEach(billboard -> {
                                BaseData<Billboard> billboardData = new BaseData<>();
                                billboardData.setType(BaseData.TYPE_BILLBOARD);
                                billboardData.setData(billboard);
                                list.add(billboardData);
                            });
                            return Observable.just(new ResponseValue(list));
                        }
                );
    }

    public static final class RequestValue implements Task.RequestValue {

        private List<CommonRequestValue> parameters;

        public RequestValue(List<CommonRequestValue> parameters) {
            this.parameters = parameters;
        }

        public List<CommonRequestValue> getParameter() {
            return parameters;
        }
    }

    public static final class ResponseValue implements Task.ResponseValue {

        private List<BaseData<Billboard>> mResult;

        public ResponseValue(@NonNull List<BaseData<Billboard>> result) {
            mResult = result;
        }

        public List<BaseData<Billboard>> getResult() {
            return mResult;
        }
    }
}
