package com.tgithubc.kumao.module.ranking;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetBillboardListTask;
import com.tgithubc.kumao.http.HttpSubscriber;

import rx.Observable;
import rx.Subscription;

/**
 * Created by tc :)
 */
public class RankingPresenter extends BasePresenter<IRankingContract.V> implements IRankingContract.P {

    @Override
    public void getRankingData() {
        Observable<GetBillboardListTask.ResponseValue> task = new GetBillboardListTask()
                .execute(new GetBillboardListTask.RequestValue(Constant.Api.URL_BILLBOARD_LIST, null));
        Subscription subscription = task.subscribe(new HttpSubscriber<GetBillboardListTask.ResponseValue>() {

            @Override
            public void onStart() {
                super.onStart();
                getView().showLoading();
            }

            @Override
            protected void onError(String msg, Throwable e) {
                getView().showError();
            }

            @Override
            public void onNext(GetBillboardListTask.ResponseValue responseValue) {
                super.onNext(responseValue);
                getView().showRankingView(responseValue.getResult());
            }
        });
        addSubscribe(subscription);
    }
}
