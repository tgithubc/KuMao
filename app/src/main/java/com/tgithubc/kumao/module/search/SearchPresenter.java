package com.tgithubc.kumao.module.search;

import android.util.Log;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.constant.Constant;
import com.tgithubc.kumao.data.task.GetHotWordTask;
import com.tgithubc.kumao.http.HttpSubscriber;

import java.util.List;

import rx.Observable;
import rx.Subscription;


/**
 * Created by tc :)
 */
public class SearchPresenter extends BasePresenter<ISearchContract.V> implements ISearchContract.P {

    @Override
    public void getHotWord() {
        Observable<GetHotWordTask.ResponseValue> hotWordTask
                = new GetHotWordTask()
                .execute(new GetHotWordTask.RequestValues(Constant.Api.URL_HOTWORD, null));
        Subscription subscription = hotWordTask
                .subscribe(new HttpSubscriber<GetHotWordTask.ResponseValue>() {

                    @Override
                    protected void onError(String msg, Throwable e) {
                        getView().showHotWordErrorTip();
                    }

                    @Override
                    public void onNext(GetHotWordTask.ResponseValue responseValue) {
                        super.onNext(responseValue);
                        List<String> hotword = responseValue.getResult();
                        getView().showHotWord(hotword);
                    }
                });
        addSubscribe(subscription);
    }

}
