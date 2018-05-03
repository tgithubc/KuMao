package com.tgithubc.kumao.module.featured;

import android.util.Log;

import com.tgithubc.kumao.base.BasePresenter;
import com.tgithubc.kumao.data.task.GetBannerTask;
import com.tgithubc.kumao.http.HttpSubscriber;

import rx.Subscription;

/**
 * Created by tc :)
 */
public class FeaturedPresenter extends BasePresenter<IFeaturedContract.V> implements IFeaturedContract.P {

    private static final String TAG = "FeaturedPresenter";
    private GetBannerTask mGetBannerTask;

    public FeaturedPresenter() {
        mGetBannerTask = new GetBannerTask();
    }

    @Override
    public void getFeaturedData() {
        getView().showLoading();
        Subscription subscription = mGetBannerTask.execute(new GetBannerTask.RequestValues(10))
                .subscribe(new HttpSubscriber<GetBannerTask.ResponseValue>() {

                    @Override
                    protected void onError(String msg) {
                        Log.d(TAG, "onError ：" + msg);
                        getView().showError();
                    }

                    @Override
                    public void onNext(GetBannerTask.ResponseValue responseValue) {
                        super.onNext(responseValue);
                        getView().showContent();
                        Log.d(TAG, "onNext ：" + responseValue.getResult());
                    }
                });
        subscribe(subscription);
    }
}
