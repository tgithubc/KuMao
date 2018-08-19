package com.tgithubc.kumao.module.listpage.billboard;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.bean.Billboard;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetBillboardTask;
import com.tgithubc.kumao.http.HttpSubscriber;
import com.tgithubc.kumao.util.RxMap;

import java.util.Map;

import rx.Observable;
import rx.Subscription;

/**
 * Created by tc :)
 */
public class BillboardPresenter extends BasePresenter<IBillboardContract.V> implements IBillboardContract.P {

    @Override
    public void getBillboard(int id) {
        Map<String, String> rq = new RxMap<String, String>()
                .put("type", String.valueOf(id))
                .build();
        Observable<GetBillboardTask.ResponseValue> observable =
                new GetBillboardTask().execute(new GetBillboardTask.RequestValue(Constant.Api.URL_BILLBOARD, rq));
        Subscription subscription = observable.subscribe(new HttpSubscriber<GetBillboardTask.ResponseValue>() {
            @Override
            protected void onError(String msg, Throwable e) {

            }

            @Override
            public void onNext(GetBillboardTask.ResponseValue responseValue) {
                super.onNext(responseValue);
                Billboard billboard = responseValue.getResult();
                getView().showBillboard(billboard);
            }
        });
        addSubscribe(subscription);
    }
}
