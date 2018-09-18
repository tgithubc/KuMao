package com.tgithubc.kumao.module.ranking;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.base.Task;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetBillboardListTask;
import com.tgithubc.kumao.http.HttpSubscriber;


import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by tc :)
 */
public class RankingPresenter extends BasePresenter<IRankingContract.V> implements IRankingContract.P {

    @Override
    public void getRankingData() {
        Observable<GetBillboardListTask.ResponseValue> task = new GetBillboardListTask()
                .execute(new Task.CommonRequestValue(Constant.Api.URL_BILLBOARD_LIST, null));
        Disposable disposable = task.subscribeWith(
                new HttpSubscriber<GetBillboardListTask.ResponseValue>() {

                    @Override
                    protected void onError(String msg, Throwable e) {
                        getView().showError();
                    }

                    @Override
                    public void onStart() {
                        getView().showLoading();
                    }

                    @Override
                    public void onNext(GetBillboardListTask.ResponseValue responseValue) {
                        getView().showRankingView(responseValue.getResult());
                    }
                });
        addSubscribe(disposable);
    }
}
